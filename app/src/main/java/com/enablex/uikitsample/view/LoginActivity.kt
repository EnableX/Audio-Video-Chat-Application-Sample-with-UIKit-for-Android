package com.enablex.uikitsample.view

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.enablex.enxuikit_android.utility.EnxLoader
import com.enablex.uikitsample.R
import com.enablex.uikitsample.databinding.ActivityLoginBinding
import com.enablex.uikitsample.network.ApiClient
import com.enablex.uikitsample.network.RetrofitHelper
import com.enablex.uikitsample.utility.JsonParser
import com.enablex.uikitsample.utility.Utils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )
     private var roomId=""
     private var role ="moderator"  //moderator /participant
    private val permissionsAll = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Utils.hasPermissions(this, permissions.toString())) {
                ActivityCompat.requestPermissions(this, permissions, permissionsAll)
            }
        }
        Log.e("onCrete","===================================")
    }
    fun createRoom(view: View) {
        try {
            if(!validate())
                return
           EnxLoader.showDialog(this,false)
            EnxLoader.setMessage("Please wait...")
            val apiClient = RetrofitHelper.getClient().create(ApiClient::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = apiClient.createRoom(JsonParser.getParams())
                    
                    withContext(Dispatchers.Main) {
                        EnxLoader.hideDialog()
                        
                        if (result != null) {
                            if(JSONObject(result.toString()).getInt("result")==0) {
                                roomId = JsonParser.getRoomId(result.toString())
                                binding.enterRoomId.text = Editable.Factory.getInstance().newEditable(roomId)
                            } else {
                                Toast.makeText(this@LoginActivity,"something went wrong",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        EnxLoader.hideDialog()
                        Toast.makeText(this@LoginActivity,"Network error",Toast.LENGTH_LONG).show()
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
    fun joinMeeting(view: View) {

          try {
            if(!verify())
                return
            EnxLoader.showDialog(this,false)
            EnxLoader.setMessage("Please wait...")
            val apiClient = RetrofitHelper.getClient().create(ApiClient::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val tokenResult = apiClient.createToken(
                        JsonParser.getTokenParams(
                            roomId,
                            binding.name.text.toString(),
                            role
                        )
                    )
                    
                    withContext(Dispatchers.Main) {
                        EnxLoader.hideDialog()
                        
                        if (tokenResult != null) {
                            val token = JsonParser.getToken(tokenResult.toString())
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("token", token)
                            startActivity(intent)
                            finish()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        EnxLoader.hideDialog()
                        Toast.makeText(this@LoginActivity,"Network error",Toast.LENGTH_LONG).show()
                    }
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
    private fun validate():Boolean{
        try{
            if(binding.name.text.toString().isEmpty()){
                Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show()
                return false
            }
        }catch(ex: Exception){
            ex.printStackTrace()
        }
        return true
    }

    private fun verify():Boolean{
        try{
            if(binding.name.text.toString().isEmpty()){
                Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show()
                return false
            }else if(binding.enterRoomId.text.toString().isEmpty()){
                Toast.makeText(this,"Please first create roomID",Toast.LENGTH_LONG).show()
                return false;
            }
        }catch(ex: Exception){
            ex.printStackTrace()
        }
        return true
    }
    override fun onStart() {
        super.onStart()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    override fun onConfigurationChanged(newConfig: Configuration){
        super.onConfigurationChanged(newConfig)
    }


}
