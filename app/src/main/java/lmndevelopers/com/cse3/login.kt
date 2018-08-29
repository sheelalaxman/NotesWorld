package lmndevelopers.com.cse3

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.loginfrag.view.*

class login : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.loginfrag,container,false)

        v.login.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(v.lEmail.text.toString(),v.lp.text.toString()).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    if(FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                        startActivity(Intent(activity, Main2Activity::class.java))
                    }
                    else{
                        Toast.makeText(activity,"verify your email", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


        return v
    }
}