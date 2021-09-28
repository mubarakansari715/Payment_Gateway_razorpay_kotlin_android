package com.mubarakansari.payment_gateway_razorpay_kotlin_android


import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mubarakansari.payment_gateway_razorpay_kotlin_android.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //for screen secure with ss and recoding
       window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnMoneyProcess.setOnClickListener {

            binding.apply {
                val amount = binding.money.text.toString()
                if (amount.isEmpty()) {
                    money.error = " Please Enter Amount"
                    return@setOnClickListener
                } else {
                    startPayment(amount.toInt())
                }
            }

        }
    }

    private fun startPayment(amount: Int) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_dDmenzrO2iGyRT")
        try {
            val options = JSONObject()
            options.put("name", "Razorpay Integration")
            options.put("description", "Learning tutorial")

            //You can omit the image option to fetch the image from dashboard
            //   options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")

            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")

            /** Generated from backend **/
            //      options.put("order_id", "order_DBJOWzybf0sJbb");

            /** Pass in paise in INR  ( Example  Rs 5 = 500 paise ) **/
            options.put("amount", "${(amount) * (100)}")//pass amount in currency subunits

            options.put("prefill.email", "random@gmail.com")
            options.put("prefill.contact", "+919442009211")

            checkout.open(this, options)

        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG)
                .show()
            e.printStackTrace()
        }

    }


    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("TAG", "onPaymentError: $p0")
        Log.d("TAG", "onPaymentError: $p1")
        Toast.makeText(this, "Payment Not Successful", Toast.LENGTH_SHORT).show()
    }

}