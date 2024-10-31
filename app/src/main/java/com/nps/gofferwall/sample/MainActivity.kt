package com.nps.gofferwall.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.adiscope.global.AdiscopeGlobalError
import com.adiscope.global.OfferwallListener
import com.adiscope.global.sdk.AdiscopeGlobalOfferwall
import com.adiscope.global.sdk.AdiscopeGlobalSdk
import com.tnkfactory.ad.TnkOfferwall

class MainActivity : AppCompatActivity(), OfferwallListener {

    companion object {
        const val TAG = "Adiscope-Global-Sample"
    }

    private lateinit var btnShowOfferwall: Button
    private lateinit var btnShowTapjoyOfferwall: Button
    private lateinit var btnShowTNKOfferwall: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowOfferwall = findViewById(R.id.btn_show_offerwall)
        btnShowTapjoyOfferwall = findViewById(R.id.btn_show_tapjoy_offerwall)
        btnShowTNKOfferwall = findViewById(R.id.btn_show_tnk_offerwall)

        AdiscopeGlobalSdk.initialize(this) { isSuccess ->
            if (isSuccess) {
                Log.d(TAG, "AdiscopeGlobalSdk.initialize: Success")
            }
        }

        btnShowOfferwall.setOnClickListener {
            AdiscopeGlobalSdk.setUserId("TEST_ID")
            AdiscopeGlobalOfferwall.show("GOFFERWALL", this@MainActivity, this)
        }

        btnShowTapjoyOfferwall.setOnClickListener {
            AdiscopeGlobalSdk.setUserId("TEST_ID")
            AdiscopeGlobalOfferwall.show("GOFFERWALL", "Tapjoy", this@MainActivity, this)
        }

        btnShowTNKOfferwall.setOnClickListener {
            AdiscopeGlobalSdk.setUserId("TEST_ID")
            AdiscopeGlobalOfferwall.show("GOFFERWALL", "TNK", this@MainActivity, this)
        }
    }

    override fun onOfferwallOpened(unitId: String) {
        Log.d(TAG, "AdiscopeGlobalOfferwall.onOfferwallOpened: $unitId")
    }

    override fun onOfferwallFailedToOpen(unitId: String, error: AdiscopeGlobalError) {
        Log.d(TAG, "AdiscopeGlobalOfferwall.onOfferwallFailedToOpen: $unitId - $error")
    }
}