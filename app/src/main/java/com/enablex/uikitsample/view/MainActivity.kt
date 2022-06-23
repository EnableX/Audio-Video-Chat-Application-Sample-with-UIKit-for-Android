package com.enablex.uikitsample.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.enablex.enxuikit_android.observer.EnxVideoStateObserver
import com.enablex.enxuikit_android.view.EnxVideoView
import com.enablex.uikitsample.R
import org.json.JSONObject

class MainActivity : AppCompatActivity(), EnxVideoStateObserver {
    private var enxVideoView: EnxVideoView?=null
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        token = intent.getStringExtra("token").toString()
        enxVideoView = EnxVideoView(this,token,this)
        val rlp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        this.addContentView(enxVideoView,rlp)
    }

   /* override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        enxVideoView?.configurationChanged(newConfig)
    }


    override fun onBackPressed() {
        enxVideoView?.backPressed()
    }

*/
    override fun disconnect(jsonObject: JSONObject) {
        try{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override fun connectError(jsonObject: JSONObject) {
        try{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}