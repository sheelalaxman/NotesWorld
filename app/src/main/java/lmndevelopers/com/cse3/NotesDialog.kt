package lmndevelopers.com.cse3

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.notesdialog.*
import kotlinx.android.synthetic.main.notesdialog.view.*
import java.util.*


 class NotesDialog : DialogFragment() {
   lateinit var v : View
   lateinit var list:MutableList<String>
    lateinit var listkey:MutableList<String>
    lateinit var d: Uri
     var downloadurl:Uri? = null
     var flag:Any=0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        v=activity!!.layoutInflater.inflate(R.layout.notesdialog,null)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("subjects")

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                try {
                    var it = p0!!.children
                    list= mutableListOf()
                    listkey= mutableListOf()
                    it.forEach {
                        if (it.key != null) {
                            list.add(it.value.toString())
                            listkey.add(it.key)
                        }
                    }
                }catch (e : Exception)
                {
                    Toast.makeText(activity,e.message,Toast.LENGTH_LONG).show()
                }

                try {

                    var dataAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, list)
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    v.nspin.adapter = dataAdapter

                }catch (e : java.lang.Exception){
                    Toast.makeText(activity,e.message,Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(p0: DatabaseError?) {

            }

        })
        var builder = AlertDialog.Builder(context!! as Main2Activity)
        builder.setView(v)
        builder.setTitle("Subject")
        builder.setCancelable(false)

        builder.setPositiveButton("submit",DialogInterface.OnClickListener { dialogInterface, i ->
            try {

            if(v.ncode.text.toString() == "1234" && flag==1){
                val sref = FirebaseStorage.getInstance().getReference("notes")

                  sref.child(listkey.get(v.nspin.selectedItemPosition)).child(UUID.randomUUID().toString())
                        .putFile(d).addOnSuccessListener(OnSuccessListener {
                               downloadurl=it.downloadUrl
                          })

                   if(downloadurl.toString().isNotEmpty())
                   {
                      Toast.makeText(activity as Main2Activity,"sucess",Toast.LENGTH_LONG).show()
                   }
                   else
                   {
                       Toast.makeText(activity as Main2Activity,"sucess",Toast.LENGTH_LONG).show()
                   }


            }
            else{
                if(v.ncode.text!!.isEmpty())
                  Toast.makeText(activity as Main2Activity,"enter security code",Toast.LENGTH_LONG).show()
                if(flag==0)
                    Toast.makeText(activity as Main2Activity,"select your file",Toast.LENGTH_LONG).show()
            }
            }catch (e:java.lang.Exception)
            {
                Toast.makeText(activity as Main2Activity, e.message, Toast.LENGTH_LONG).show()
            }
        })

        builder.setNegativeButton("cancel",DialogInterface.OnClickListener { dialogInterface, i ->
            Toast.makeText(activity,"negative clicked",Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()

        })

        v.browse.setOnClickListener {
            Toast.makeText(activity as Main2Activity,"browse clicked",Toast.LENGTH_LONG).show()
            try{
              var i= Intent()
                i.action = Intent.ACTION_PICK

              startActivityForResult(i,100)
           }catch (e : java.lang.Exception)
            {

                Toast.makeText(activity as Main2Activity,e.message,Toast.LENGTH_LONG).show()

            }
        }



        return builder.create()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == 100){
            try {
                d = data!!.data
                v.npath.text = d.path
                flag=1

            }
            catch (e : Exception){
                Toast.makeText(activity as Main2Activity,e.message,Toast.LENGTH_LONG).show()
            }
        }
        else{
            flag=0
        }
    }
}