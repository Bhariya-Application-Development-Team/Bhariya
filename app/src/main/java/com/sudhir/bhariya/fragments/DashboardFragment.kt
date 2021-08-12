package com.sudhir.bhariya.fragments

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.sudhir.bhariya.R
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import java.lang.Integer.parseInt


class DashboardFragment : Fragment() {
    var sampleImages = intArrayOf(
        R.drawable.logo,
        R.drawable.logo,

        )
    private lateinit var myDialog : Dialog
    private lateinit var miniButton  : LinearLayout
    private lateinit var mediumButton  : LinearLayout
    private lateinit var  typeVehicle : TextView
    private val mapsFragment = MapsFragment()
    private lateinit var vehicleImage : ImageView
    private lateinit var  heavyButton : LinearLayout
    private lateinit var InviteButton  : Button
    private lateinit var addlabour : TextView
    private lateinit var  numlabour : TextView
    private lateinit var btnProceed : Button
    private lateinit var laborsub : TextView

   var worker : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val carouselView = root.findViewById(R.id.carouselviews) as CarouselView;

        val pop = inflater.inflate(R.layout.custompopup, container, false)
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.size);
        miniButton = root.findViewById(R.id.btnMini)
        mediumButton = root.findViewById(R.id.mediumButton)
        heavyButton = root.findViewById(R.id.heavyButton)
        InviteButton = root.findViewById(R.id.btninvite)
        addlabour = pop.findViewById(R.id.btnadd)
        laborsub = pop.findViewById(R.id.btnmin)
        numlabour = pop.findViewById(R.id.txtquan)
        btnProceed = pop.findViewById(R.id.btnProceed)

        myDialog = context?.let { Dialog(it) }!!

        miniButton.setOnClickListener{
            getAttr(pop)
            typeVehicle.setText("Mini Truck")
            vehicleImage.setImageResource(R.drawable.truck1)
            showPopup(pop)
        }

        mediumButton.setOnClickListener{
            getAttr(pop)
            typeVehicle.setText("Medium Truck")
            vehicleImage.setImageResource(R.drawable.truck3)
            showPopup(pop)



        }

        heavyButton.setOnClickListener {
            getAttr(pop)
            typeVehicle.setText("Heavy Truck")
            vehicleImage.setImageResource(R.drawable.truck2)
            showPopup(pop)
        }

        addlabour.setOnClickListener {
            worker = parseInt(numlabour.text.toString())
            if (worker >= 5){
                Toast.makeText(context,"Maximum Labour Limit Exceeded. ", Toast.LENGTH_SHORT)
                    .show()
                numlabour.text = "5"
            }
            else{
                worker += 1
                numlabour.text = worker.toString()
            }

        }

        laborsub.setOnClickListener {
            worker = parseInt(numlabour.text.toString())
            if (worker <= 1){
                Toast.makeText(context,"Minimum Labour Limit Exceeded. ", Toast.LENGTH_SHORT)
                    .show()
                numlabour.text = "1"
            }
            else{
                worker -= 1
                numlabour.text = worker.toString()
            }

        }


        btnProceed.setOnClickListener{
              val vehicleCategory = typeVehicle.text.toString()
            Log.e("main", "This is $vehicleCategory ")
//            val intent = Intent(context, MapsFragment::class.java)
//            intent.putExtra("Number_Of_Worker", worker)
//            intent.putExtra("Vehicle",vehicleCategory )
//            startActivity(intent)
            Log.e("main", " Number of worker is $worker")
//
            myDialog.dismiss()

            val ldf = MapsFragment()
            val args = Bundle()
            //sending value to the mapfragment
            args.putString("VehicleType", "$vehicleCategory")
            args.putString("num_Workers", "$worker")
            ldf.setArguments(args)

            getFragmentManager()?.beginTransaction()?.add(R.id.fragmentcontainer, ldf)?.commit();
//            val mapsFragment = MapsFragment()
//            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
//            transaction.replace(R.id.activity_main, mapsFragment)
//            transaction.commit()
//           replaceFragment(mapsFragment)


        }




        InviteButton.setOnClickListener {

            createLink();
        }

        return root

    }
//    private  fun replaceFragment(fragment: Fragment){
//
//        if(fragment != null){
//            val transaction = requireFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragmentcontainer, fragment)
//            transaction.commit()
//        }
//    }

    private fun createLink(){
        val dynamicLink = Firebase.dynamicLinks.dynamicLink {
            link = Uri.parse("https://www.bhariya.com/")
            domainUriPrefix = "https://bhariyaapp.page.link"
            // Open links with this app on Android
            androidParameters { }
            // Open links with com.example.ios on iOS
            iosParameters("com.example.ios") { }
        }

        val dynamicLinkUri = dynamicLink.uri
        //click --- link --- google play store --- installed or not ----

//    val shareLinkText : String = "https://bhariya.page.link/?"+
//            "link=http://www.bhariyaapp.com/"+
//            "&apn="+ getPackageName()+
//            "&st="+"My Refer Link"+
//            "&sd="+"Reward coins 20"+
//            "&si="+


        // manual link



        Log.e("main", "Long Link $dynamicLinkUri")
        //https://bhariya.page.link?apn=com.sudhir.bhariya&ibi=com.example.ios&link=https%3A%2F%2Fwww.bhariyaapp.com%2F
        //apn  ibi   link
        // shorten the link

        val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
            longLink = dynamicLinkUri
        }.addOnSuccessListener { (shortLink, flowChartLink) ->
            // You'll need to import com.google.firebase.dynamiclinks.ktx.component1 and
            // com.google.firebase.dynamiclinks.ktx.component2
            Log.e("main", "Short link = $shortLink")
            // Short link created
//            processShortLink(shortLink, flowChartLink)


            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
            intent.type = "text/plain"
            startActivity(intent)
        }.addOnFailureListener {
            // Error
            // ...
            Log.e("main", "error")
        }


    }
    private fun getAttr(view : View){
        typeVehicle = view.findViewById(R.id.typeVehicle)
        vehicleImage = view.findViewById(R.id.vehicleImage)
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here

            imageView.setImageResource(sampleImages[position])

        }
    }


    private fun showPopup(view: View){
        myDialog.setContentView(view)
        myDialog.show()
    }

}