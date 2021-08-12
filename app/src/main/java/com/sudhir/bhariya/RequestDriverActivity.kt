package com.sudhir.bhariya

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.ui.IconGenerator
import com.sudhir.bhariya.Remote.IGoogleAPI
import com.sudhir.bhariya.Remote.RetrofitClient
import com.sudhir.bhariya.api.Common
import com.sudhir.bhariya.databinding.ActivityRequestDriverBinding
import com.sudhir.bhariya.entity.EventBus.SelectedPlaceEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import org.w3c.dom.Text

class RequestDriverActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityRequestDriverBinding
    private var selectedPlaceEvent : SelectedPlaceEvent?=null

    private lateinit var mapFragment : SupportMapFragment

    //Routes
    private val compositeDisposable = CompositeDisposable()
    private lateinit var iGoogleAPI : IGoogleAPI
    private var blackPolyline: Polyline? = null
    private var greyPolyline : Polyline? = null
    private var polylineOptions : PolylineOptions?=null
    private var blackPolyLineOptions : PolylineOptions?= null
    private var polylineList : ArrayList<LatLng>?= null
    private var originMarker: Marker?=null
    private var destinationMarker : Marker?= null

    private lateinit var txt_distance : TextView
    private lateinit var txt_time : TextView
    private lateinit var txt_fare : TextView




    override fun onStart() {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        compositeDisposable.clear()
        if(EventBus.getDefault().hasSubscriberForEvent(SelectedPlaceEvent::class.java))
            EventBus.getDefault().removeStickyEvent(SelectedPlaceEvent::class.java)
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onSelectPlaceEvent(event:SelectedPlaceEvent){
        selectedPlaceEvent = event
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRequestDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        txt_distance = findViewById(R.id.txt_distance)
        txt_time = findViewById(R.id.txt_time)
        txt_fare = findViewById(R.id.txt_fare)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val intent = intent
        if(intent.extras !=null){
            val worker = intent.getStringExtra("Number_Of_Worker")
            val type = intent.getStringExtra("Vehicle")

            val num = worker.toString()
            val cat = type.toString()
            Log.e("main", " $num is for $cat")

        }
    }

    private fun init() {
        iGoogleAPI = RetrofitClient.getInstance()!!.create(IGoogleAPI::class.java)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.setOnMyLocationClickListener {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlaceEvent!!  .origin,18f))
            true

        }

        drawPath(selectedPlaceEvent!! )
        val locationButton = (findViewById<View>("1".toInt())!!.parent!! as View)
            .findViewById<View>("2".toInt())
        val params = locationButton.layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP,0)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE)
        params.bottomMargin = 250

        mMap.uiSettings.isZoomControlsEnabled = true

    }

    private fun drawPath(selectedPlaceEvent: SelectedPlaceEvent) {

        compositeDisposable.add(iGoogleAPI.getDirections("driving",
        "less_driving",
        selectedPlaceEvent.originString,selectedPlaceEvent.destinationString,
       "AIzaSyC88v00XL7qZe0KaylSIKUmNRjBQ1wII9Q")!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { returnResult ->
            Log.d("API_RETURN",returnResult)
            try{
                val jsonObject = JSONObject(returnResult)
                val jsonArray = jsonObject.getJSONArray("routes");
                for(i in 0 until jsonArray.length())
                {
                    val route = jsonArray.getJSONObject(i)
                    val poly = route.getJSONObject("overview_polyline")
                    val polyline = poly.getString("points")
                    polylineList = com.sudhir.bhariya.entity.Common.decodePoly(polyline) as ArrayList<LatLng>?
                }

                polylineOptions = PolylineOptions()
                polylineOptions!!.color(Color.GRAY)
                polylineOptions!!.width(12f)
                polylineOptions!!.startCap(SquareCap())
                polylineOptions!!.jointType(JointType.ROUND)
                polylineOptions!!.addAll(polylineList)
                greyPolyline = mMap.addPolyline(polylineOptions)


                blackPolyLineOptions = PolylineOptions()
                blackPolyLineOptions!!.color(Color.GRAY)
                blackPolyLineOptions!!.width(12f)
                blackPolyLineOptions!!.startCap(SquareCap())
                blackPolyLineOptions!!.jointType(JointType.ROUND)
                blackPolyLineOptions!!.addAll(polylineList)
                blackPolyline = mMap.addPolyline(blackPolyLineOptions)

                //Animator
                val valueAnimator = ValueAnimator.ofInt(0,100)
                valueAnimator.duration = 1100
                valueAnimator.repeatCount = ValueAnimator.INFINITE
                valueAnimator.interpolator = LinearInterpolator()
                valueAnimator.addUpdateListener { value ->
                    val points = greyPolyline!!.points
                    val percentValue = value.animatedValue.toString().toInt()
                    val size = points.size
                    val newpoints = (size * (percentValue/100.0f)).toInt()
                    val p = points.subList(0,newpoints)
                    blackPolyline!!.points = p
                }
                valueAnimator.start()

                val latLngBound = LatLngBounds.Builder().include(selectedPlaceEvent.origin)
                    .include(selectedPlaceEvent.destination)
                    .build()

                //Add car icon for origin
                val objects = jsonArray.getJSONObject(0)
                val legs = objects.getJSONArray("legs")
                val legsObject = legs.getJSONObject(0)

                val time = legsObject.getJSONObject("duration")
                val duration = time.getString("text")
                val distance = legsObject.getJSONObject("distance")
                val distanceText = distance.getString("text")

                val start_address = legsObject.getString("start_address")
                val end_address = legsObject.getString("end_address")

                //Value Setting

                txt_distance.text = distanceText
                txt_time.text = duration

                fare(distanceText.toString(),3)

                addOriginMarker(duration,start_address)
                addDestinationMarker(end_address)

                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBound,160))
                mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.cameraPosition!!.zoom-1))

            }
            catch(e:java.lang.Exception)
            {
                Toast.makeText(this, e.message!!, Toast.LENGTH_SHORT).show()
            }
            })

    }

    private fun fare(distance: String, labour: Int) {
        var total_fare = 0.0
        val km = distance.split(" ")[0]

        total_fare = 500 + (km.toDouble() * 50) + (labour*500)
        txt_fare.text = "Rs. " + total_fare.toString()
    }

    private fun addDestinationMarker(endAddress: String) {

        val view = layoutInflater.inflate(R.layout.destination_info_windows,null)

        val txt_destination = view.findViewById<View>(R.id.txt_destination) as TextView
        txt_destination.text = Common.formatAddress(endAddress)

        val generator = IconGenerator(this)
        generator.setContentView(view)
        generator.setBackground(ColorDrawable(Color.TRANSPARENT))
        val icon = generator.makeIcon()

        destinationMarker = mMap.addMarker(MarkerOptions()
            .position(selectedPlaceEvent!!.destination))
    }

    private fun addOriginMarker(duration: String, startAddress: String) {

        val view = layoutInflater.inflate(R.layout.origin_info_windows,null)

        val txt_time = view.findViewById<View>(R.id.txt_time) as TextView
        val txt_origin = view.findViewById<View>(R.id.txt_origin) as TextView

        txt_time.text = Common.formatDuration(duration)
        txt_origin.text = "My Location"

        val generator = IconGenerator(this)
        generator.setContentView(view)
        generator.setBackground(ColorDrawable(Color.TRANSPARENT))
        val icon = generator.makeIcon()

        originMarker = mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(icon))
            .position(selectedPlaceEvent!!.origin))

    }
}