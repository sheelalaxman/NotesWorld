package lmndevelopers.com.cse3

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle

import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.subjectdiaglog.view.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.subjectdiaglog.*


class SubjectDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var v=activity!!.layoutInflater.inflate(R.layout.subjectdiaglog,null)
        var builder = AlertDialog.Builder(context!! as Main2Activity)
        builder.setView(v)
        builder.setTitle("Subject")
        builder.setCancelable(false)

        builder.setPositiveButton("submit",DialogInterface.OnClickListener { dialogInterface, i ->

            if(v.scode.text.toString() == "1234"){
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("subjects")
                var subjectkey = myRef.push().key
                myRef.child(subjectkey).setValue(v.sname.text.toString())
                Toast.makeText(activity,"inserted",Toast.LENGTH_LONG).show()


            }
            else{
                Toast.makeText(activity,"failed to insert",Toast.LENGTH_LONG).show()
            }
        })

        builder.setNegativeButton("cancel",DialogInterface.OnClickListener { dialogInterface, i ->
            Toast.makeText(activity,"negative clicked",Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()

        })


        return builder.create()
    }
}