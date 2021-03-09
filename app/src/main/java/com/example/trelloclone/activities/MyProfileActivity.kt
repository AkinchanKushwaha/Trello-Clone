package com.example.trelloclone.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.trelloclone.R
import com.example.trelloclone.firebase.FirestoreClass
import com.example.trelloclone.models.User
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.IOException

class MyProfileActivity : BaseActivity() {

    private var mSelectedImageFileURI : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        setupActionBar()

        FirestoreClass().loadUserData(this)

        iv_user_image_myProfile.setOnClickListener {
            if(ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
                showImageChooser()

            }else{
                ActivityCompat.requestPermissions(
                    this,
                     arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == READ_STORAGE_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
           showImageChooser()
        }else{
            Toast.makeText(this, "Oops, you just denied the permission for storage. You can allow it from the settings.", Toast.LENGTH_LONG).show()
        }
    }
    private fun setupActionBar() {

        setSupportActionBar(toolbar_my_profile_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile_title)
        }
        toolbar_my_profile_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun setUserDataInUI(user: User){
        Glide.with(this@MyProfileActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(iv_user_image_myProfile)

        et_name_myProfile.setText(user.name)
        et_email_myProfile.setText(user.email)
        if(user.mobile != 0L){
            et_mobile_myProfile.setText(user.mobile.toString())
        }
    }

    private fun showImageChooser(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data  != null){
            mSelectedImageFileURI = data.data

            try {
                Glide.with(this@MyProfileActivity)
                    .load(mSelectedImageFileURI)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(iv_user_image_myProfile)
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    companion object{
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }
}