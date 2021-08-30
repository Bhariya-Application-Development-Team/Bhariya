package com.sudhir.bhariya

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import com.bumptech.glide.Glide

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.maps.android.ui.IconGenerator
import com.sudhir.bhariya.NotificationClass.FirebaseService
import com.sudhir.bhariya.Remote.IGoogleAPI
import com.sudhir.bhariya.Remote.RetrofitClient
import com.sudhir.bhariya.api.Common
import com.sudhir.bhariya.databinding.ActivityRequestDriverBinding
import com.sudhir.bhariya.entity.Driver
import com.sudhir.bhariya.entity.EventBus.SelectedPlaceEvent
import com.sudhir.bhariya.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception


class DriverRideActivity : AppCompatActivity(), OnMapReadyCallback {

    //Animation for Spinning
    var animator : ValueAnimator? = null
    private val DESIRED_NUM_OF_SPINS = 3
    private val DESIRED_SECONDS_PER_ONE_FULL_SPIN = 60

    //Searching Animation
    var lastUserCircle : Circle? = null
    val duration = 1000
    var lastPulseAnimator : ValueAnimator? = null
    var used : Boolean? = false

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
    private lateinit var database : FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var btn_confirm_ride : Button
    private lateinit var txt_address : TextView
    private  lateinit var  btnAcceptTrip : Button
    private lateinit var confirm_pickup_layout : View
    private lateinit var confirm_ride_layout : View
    private lateinit var accepttrip : View
    private lateinit var fill_maps : View
    private lateinit var searching_driver : View

    var listtoken = ArrayList<String>()



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


        txt_distance = findViewById(R.id.txt_distance)
        txt_time = findViewById(R.id.txt_time)
        txt_fare = findViewById(R.id.txt_fare)
        txt_address = findViewById(R.id.txt_address)
        btn_confirm_ride = findViewById(R.id.btn_confirm_ride)


        confirm_pickup_layout = findViewById(R.id.confirm_pickup_layout)
        confirm_ride_layout = findViewById(R.id.confirm_ride_layout)
        fill_maps = findViewById(R.id.fill_maps)
        searching_driver = findViewById(R.id.searching_driver)
        accepttrip = findViewById(R.id.accepttrip)
        btnAcceptTrip = findViewById(R.id.btn_acccpet_trip)
        //Changed code
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("drivers")

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        init()




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

        getData()
//        //Event
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        btn_confirm_ride.setOnClickListener{


            getData()

            Log.e("hellooooooooooooo token", listtoken.toString())
            val  title = "Trip Request"
            val   message ="You have new trip"
            PushNotification(
                NotificationData("27.01","81.05", title, message),
                "fnpb_PrVRtGPopf30WKU3C:APA91bE8AxoxLBFuQwU0Ax1ExoE1bvxIrulEoalh50Fzxx8799ukOO4ifHRfFEE6lycId1dM8sWSoQTbcKyDKRcQWbVdgl2KYGBh4GWs27LidJ8cK40VyYlMSLvYJLGGbjw3JWmDpppX"
            ).also {
                sendNotification(it)
            }
//            if (title.isNotEmpty() && message.isNotEmpty() ){
//                for(i in listtoken){
//                    println("############3000")
//                    println(i)
//                    PushNotification("27.01","81.05",
//                        NotificationData(title, message),
//                        i
//                    ).also {
//                        sendNotification(it)
//                    }
//                }
//            }


            Log.e("main  ######", title)
            if(mMap == null) return@setOnClickListener
            if(selectedPlaceEvent == null) return@setOnClickListener

            //Map Clearing
            mMap.clear()

            //Tilting

            val cameraPos = CameraPosition.Builder().target(selectedPlaceEvent!!.origin)
                .tilt(45f)
                .zoom(16f)
                .build()
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPos))



            addMarkerWithPulseAnimation()



        }

    }
    private fun sendNotification(notification: PushNotification)= CoroutineScope(Dispatchers.IO).launch {

        try{
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful){
                Log.e("mainSuccess", "Message: ${Gson().toJson(response)}")
            }
            else{
                Log.e("error", response.errorBody().toString())
            }
        } catch (e: Exception){
            Log.e("Main", e.toString())
        }
    }


    private fun getData(){
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var list = ArrayList<Driver>()
                for (data in snapshot.children){
                    var model = data.getValue(Driver::class.java)
                    list.add(model as Driver)
                    var tllist = model.Token
                    listtoken.add(tllist.toString())
                    Log.e("Users are: ", tllist.toString())
                }
                println("#########################")

                Log.e("Token are", listtoken.toString())


            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }

        })

    }


    private fun addMarkerWithPulseAnimation() {
        confirm_pickup_layout.visibility = View.GONE
        fill_maps.visibility = View.VISIBLE
        searching_driver.visibility = View.VISIBLE

        originMarker = mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker())
            .position(selectedPlaceEvent!!.origin))

        addPulsatingEffect(selectedPlaceEvent!!.origin)
    }

    private fun addPulsatingEffect(origin: LatLng) {
        lastPulseAnimator?.cancel()
        if(lastUserCircle != null) lastUserCircle!!.center = origin
        lastPulseAnimator = Common.valueAnimate(duration, object:ValueAnimator.AnimatorUpdateListener{
            override fun onAnimationUpdate(p0: ValueAnimator?) {
                if(lastUserCircle != null) lastUserCircle!!.radius = p0!!.animatedValue.toString().toDouble() else{
                    lastUserCircle = mMap.addCircle(CircleOptions()
                        .center(origin)
                        .radius(p0!!.animatedValue.toString().toDouble())
                        .strokeColor(Color.WHITE)
                        .fillColor(ContextCompat.getColor(this@DriverRideActivity,R.color.darker_map)))

                }
            }

        })!!

        //Start Rotation
        startMapCameraSpinningAnimation(mMap.cameraPosition.target)
    }

    private fun startMapCameraSpinningAnimation(target: LatLng?) {
        if(animator!= null) animator!!.cancel()
        animator = ValueAnimator.ofFloat(0f,(DESIRED_NUM_OF_SPINS*360).toFloat())
        animator!!.duration = (DESIRED_NUM_OF_SPINS*DESIRED_SECONDS_PER_ONE_FULL_SPIN * 1000).toLong()
        animator!!.interpolator = LinearInterpolator()
        animator!!.startDelay = (100)
        animator!!.addUpdateListener { valueAnimator ->
            val newBearingValue = valueAnimator.animatedValue as Float
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.Builder()
                .target(target)
                .zoom(16f)
                .tilt(45f)
                .bearing(newBearingValue)
                .build()
            ))
        }
        animator!!.start()
    }

    override fun onDestroy() {
        if(animator != null) animator!!.end()
        super.onDestroy()
    }

    private fun setDataPickup() {
        //This is the Code
//        txt_address.text = if(txt_origin!=null) txt_origin.text else "None"

        txt_address.text = "Balaju"
        mMap.clear()
        addPickupMarker()
    }

    private fun addPickupMarker() {
        val view = layoutInflater.inflate(R.layout.pickup_info_window, null)

        val generator = IconGenerator(this)
        generator.setContentView(view)
        generator.setBackground(ColorDrawable(Color.TRANSPARENT))
        val icon = generator.makeIcon()

        originMarker = mMap.addMarker(MarkerOptions()
            .icon(BitmapDescriptorFactory.fromBitmap(icon))
            .position(selectedPlaceEvent!!.origin))
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

//        mMap.isMyLocationEnabled = true
//        mMap.uiSettings.isMyLocationButtonEnabled = true
//        mMap.setOnMyLocationClickListener {
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlaceEvent!!  .origin,18f))
//            true
//
//        }

        drawPath(selectedPlaceEvent!! )
//        val locationButton = (findViewById<View>("1".toInt())!!.parent!! as View)
//            .findViewById<View>("2".toInt())
//        val params = locationButton.layoutParams as RelativeLayout.LayoutParams
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP,0)
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE)
//        params.bottomMargin = 250
//
//        mMap.uiSettings.isZoomControlsEnabled = true

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