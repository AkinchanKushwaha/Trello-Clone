package com.example.trelloclone.firebase

import android.util.Log
import com.example.trelloclone.activites.SignInActivity
import com.example.trelloclone.activites.SignUpActivity
import com.example.trelloclone.models.User
import com.example.trelloclone.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity : SignUpActivity, userInfo: User){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnCompleteListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                Log.e(activity.javaClass.simpleName, "Error writing document", it)
            }
    }

    private fun getCurrentUserId(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun signInUser(activity : SignInActivity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnCompleteListener {document ->
                val loggedInUser = document to User::class.java
                if(loggedInUser != null){
                    activity.signInSuccess(loggedInUser)
                }
            }.addOnFailureListener {
                Log.e( TAG,"Error writing document", it)
            }
    }
    companion object{
        private const val TAG = "SignInUser"
    }
}