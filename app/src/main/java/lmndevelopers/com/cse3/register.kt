package lmndevelopers.com.cse3

import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.registerfrag.view.*

class register : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.registerfrag,container,false)

        v.register.setOnClickListener {
            if(!v.remail.text.toString().isEmpty() && !v.rp.text.toString().isEmpty()){
             FirebaseAuth.getInstance().createUserWithEmailAndPassword(v.remail.text.toString(),v.rp.text.toString()).addOnCompleteListener {
               if(it.isSuccessful){
                   Toast.makeText(activity,"${FirebaseAuth.getInstance().uid}",Toast.LENGTH_LONG).show()
                   sendEmailVerification()
               }
               else{
                   Toast.makeText(activity,"${ it.exception }",Toast.LENGTH_LONG).show()
                   Log.w(TAG, "signInWithEmail:failure", it.exception);
               }

           }
         }
        }
        return v
    }

    private fun sendEmailVerification() {
        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnCompleteListener {
            if(it.isSuccessful)
            {
                Toast.makeText(activity,"verification link sent to an email",Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
            }
            else{
                Toast.makeText(activity,"please enter correct email",Toast.LENGTH_LONG).show()
            }
        }
    }
}