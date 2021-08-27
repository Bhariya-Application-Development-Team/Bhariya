//EsewaActivity
package com.sudhir.bhariya

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.esewa.android.sdk.payment.ESewaConfiguration
import com.esewa.android.sdk.payment.ESewaPayment
import com.esewa.android.sdk.payment.ESewaPaymentActivity
import com.sudhir.bhariya.Repository.TripRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class EsewaActivity : AppCompatActivity() {
    private lateinit var pay: ImageButton
    private var REQUEST_CODE_PAYMENT: Int = 1
    private var txnRefId = " "
    private var Source = " "
    private var Destination = " "
    private var Date = " "
    private var Cost = " "
    private var Vehicle = " "
    private var DriverId = "60eed1c6f17a2e328c7d5eb0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esewa)

        pay = findViewById(R.id.pay)

        pay.setOnClickListener {
            val eSewaPayment: ESewaPayment = ESewaPayment(
                "900",
                "Truck Heavy",
                "abjakjsb121",
                "https://60efeb43a276b8d5e8ceaa9f--jolly-torvalds-099307.netlify.app/"
            )

            val intent: Intent = Intent(this, ESewaPaymentActivity::class.java)
            intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, eSewaConfiguration)

            intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment)
            startActivityForResult(intent, REQUEST_CODE_PAYMENT)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                val s = data!!.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE)
                if (s != null) {
                    Log.e("Proof of Payment", s)

                }
                val jsonObject = JSONObject(data!!.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE)) //Here reponse is the yours server response

                val result: JSONObject = jsonObject.getJSONObject("transactionDetails")

                Date  = result.getString("date")
                Source = "Ratopool"
                Destination = "Putalisadak"
                Cost = jsonObject.optString("totalAmount")
                Vehicle = jsonObject.optString("productName")
                txnRefId = result.getString("referenceId").toString()
//                val name = result.getString("productName").toString()

                Log.e("main", "Sudhir travelled from $Source to $Destination utilizing $Cost  on $Date and his referenceId $txnRefId by $Vehicle")
                    Toast.makeText(this, "Payment Successfully Done.", Toast.LENGTH_LONG).show()

               saveTrip()


            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled By User", Toast.LENGTH_SHORT).show()
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                val s = data!!.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE)
                if (s != null) {
                    Log.i("Proof of Payment", s)
                }
            }
        }
    }

    private fun saveTrip() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = TripRepository()
                val response = repository.saveTrip(
                    Source = Source,
                    Destination = Destination,
                    Cost = Cost,
                    Status = "Confirmed",
                    Date = Date,
                    ReferenceId = txnRefId,
                    DriverId = DriverId,
                    Vehicle = Vehicle,
                )
                if (response.success == true) {

                    withContext(Dispatchers.Main) {

                        val intent = Intent(this@EsewaActivity, RecieptActivity::class.java)
                        intent.putExtra("Source", Source)
                        intent.putExtra("Destination", Destination)
                        intent.putExtra("cost", Cost)
                        intent.putExtra("productname", Vehicle)
                        intent.putExtra("refid", txnRefId)
                        intent.putExtra("Date", Date)
                        startActivity(intent)

                    }


                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EsewaActivity, ex.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
}


