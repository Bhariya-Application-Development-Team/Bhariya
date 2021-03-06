package com.sudhir.bhariya.fragments

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Looper
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.RepoManager.clear
import com.google.firebase.installations.FirebaseInstallations
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sudhir.bhariya.NotificationClass.FirebaseService
import com.sudhir.bhariya.R
import com.sudhir.bhariya.Repository.UserRepository
import com.sudhir.bhariya.RequestDriverActivity
import com.sudhir.bhariya.api.Common
import com.sudhir.bhariya.entity.Driver
import com.sudhir.bhariya.entity.EventBus.SelectedPlaceEvent
import com.sudhir.bhariya.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DriverFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var slidingUpPanelLayout : SlidingUpPanelLayout
    private lateinit var txt_welcome: TextView
    private lateinit var autocompleteSupportFragment : AutocompleteSupportFragment

    //To Use Location
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    //Data Online
    private lateinit var onlineRef: DatabaseReference
    //    private lateinit var currentUserRef : DatabaseReference
    private lateinit var driversLocationRef: DatabaseReference
    private lateinit var geoFire : GeoFire
    lateinit var database: DatabaseReference
    lateinit var firebase : DatabaseReference
    var fullname : String? = ""
    var phonenumber : String? = ""
    var firebaseToken : String? = ""
    private val driverInformation: MutableList<Driver> = mutableListOf()



    //

    override fun onDestroy() {
        database.child("Driver-Location/"+phonenumber).removeValue()
        super.onDestroy()
    }
    private val onlineValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
//        if (snapshot.exists())
//            println("Working")
//            currentUserRef.onDisconnect().removeValue()
        }

        override fun onCancelled(error: DatabaseError) {
            Snackbar.make(mapFragment.requireView(), error.message, Snackbar.LENGTH_LONG).show()
        }
    }



    private var param1: String? = null
    private var param2: String? = null

//    override fun onDestroy() {
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//        geoFire.removeLocation(FirebaseAuth.getInstance().currentUser!!.uid)
//        onlineRef.removeEventListener(onlineValueEventListener)
//        super.onDestroy()
//    }

//    override fun onResume() {
//        super.onResume()
//        registerOnlineSystem()
//    }
//
//    private fun registerOnlineSystem() {
//        onlineRef.addValueEventListener(onlineValueEventListener)
//    }
//
//    private fun registerOnlineSystem() {
//        onlineRef.addValueEventListener(onlineValueEventListener)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        database = FirebaseDatabase.getInstance().reference
        firebase = FirebaseDatabase.getInstance().getReference().child("Driver-Location/")

        val vehicleType = getArguments()?.getString("VehicleType")
        val Labour = getArguments()?.getString("num_Workers")
        Log.e("main", "Vehicle is $vehicleType and labour required is $Labour")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_driver, container, false)
        init()
        initViews(root)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        return root
    }

    private fun initViews(root: View?) {
        slidingUpPanelLayout = root!!.findViewById(R.id.activity_main) as SlidingUpPanelLayout
//        txt_welcome = root!!.findViewById(R.id.txt_welcome) as  TextView
//        Common.setWelcomeMessage(txt_welcome )
    }

    private fun init() {
        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected")
        driversLocationRef = FirebaseDatabase.getInstance().getReference(Common.DRIVERS_LOCATION_REFERENCE)
//        currentUserRef = FirebaseDatabase.getInstance().getReference(Common.DRIVERS_LOCATION_REFERENCE).child(
//            FirebaseAuth.getInstance().currentUser!!.uid
//        )

        geoFire = GeoFire(driversLocationRef)


//        registerOnlineSystem()
        Places.initialize(requireContext(),getString(R.string.google_maps_key))


//        var currentUserReference : String = ""
//        CoroutineScope(Dispatchers.IO).launch {
//            val repository = UserRepository()
//            val response = repository.viewUser()
//            if(response.success==true)
//                Snackbar.make(mapFragment.requireView(), response.Fullname.toString(), Snackbar.LENGTH_LONG).show()
////                currentUserReference = response.PhoneNumber.toString()
//        }

//        geoFire = GeoFire(driversLocationRef)
//
//        registerOnlineSystem()
        locationRequest = LocationRequest()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setFastestInterval(3000)
        locationRequest.interval = 5000
        locationRequest.setSmallestDisplacement(10f)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                val newPos = LatLng(p0!!.lastLocation.latitude, p0!!.lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPos, 18f))
                get()
                //Location Update
                driverLocation(phonenumber!!,fullname!!, p0.lastLocation.latitude.toString(), p0.lastLocation.longitude.toString())

                println("###################")
                println(p0.lastLocation.latitude.toString() + " ### " + p0.lastLocation.longitude.toString())
//                geoFire.setLocation(
//                    "user",
//                    GeoLocation(p0.lastLocation.latitude,p0.lastLocation.longitude)
//                )
//
//                {key:String?, error:DatabaseError? ->
//                    if(error!=null)
//                         Snackbar.make(mapFragment.requireView(),error.message,Snackbar.LENGTH_LONG).show()
//                    else
//                        Snackbar.make(mapFragment.requireView(),"Online!!!",Snackbar.LENGTH_SHORT).show()
//                }

            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        Dexter.withActivity(context as Activity?)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    mMap.isMyLocationEnabled = true
                    mMap.uiSettings.isMyLocationButtonEnabled = true
                    mMap.setOnMyLocationClickListener {
                        fusedLocationProviderClient.lastLocation
                            .addOnFailureListener { e ->
                                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                            }.addOnSuccessListener { location ->
                                val userLatLng = LatLng(location.latitude, location.longitude)
                                mMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        userLatLng,
                                        18f
                                    )
                                )
                            }
                        true
                    }

                    val view = mapFragment.view
                        ?.findViewById<View>("1".toInt())
                        ?.parent as View

                    val location = view.findViewById<View>("2".toInt())
                    val params = location.layoutParams as RelativeLayout.LayoutParams
                    params.addRule(RelativeLayout.ALIGN_TOP, 0)
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                    params.bottomMargin = 50
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        context,
                        response.toString() + "- Permission not Granted!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }

            }).check()


        // ### Code for Decorating the style of the Maps Displayed

//        try {
//            val success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.))
//        }catch (e:Resources.NotFoundException{
//        Log.e("EDMT_ERROR", e.message)
//    }

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))    }
    }

    fun driverLocation(phonenumber : String, name : String, latitude: String, longitude : String) {

        FirebaseService.sharedPref = requireActivity()!!.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
            firebaseToken = it.result!!.token
            Log.e("main", "token is $firebaseToken")
            FirebaseService.token = it.result!!.token
            val driver = Driver(phonenumber, name, longitude, latitude,firebaseToken.toString())


            database.child("Driver-Location").child(phonenumber).setValue(driver)
            getData()
        }
    }

    fun get(){
        val sharefPref = requireActivity().getSharedPreferences("MyPreference", MODE_PRIVATE)
        fullname = sharefPref.getString("fullname", "").toString()
        phonenumber = sharefPref.getString("phonenumber","").toString()
    }

    private fun getData(){
        firebase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<Driver>()
                for (data in snapshot.children){
                    var model = data.getValue(Driver::class.java)
                    list.add(model as Driver)
                }
                Log.e("Users are: ", list.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }
        })
    }


}
