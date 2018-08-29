package lmndevelopers.com.cse3

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Toast

class ChoiceDialog : DialogFragment(){
    lateinit  var a : Main2Activity
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Main2Activity) {
            a = context as Main2Activity
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(context!! as Main2Activity)
        builder.setTitle("select any one...").setSingleChoiceItems(arrayOf("add subject","add notes"),-1,DialogInterface.OnClickListener { dialogInterface, i ->
            when(i){
                0 -> {
                     this.dismiss()
                     a.getSecondDialog(1.toInt())

                    }
                1 -> {
                    this.dismiss()
                    a.getSecondDialog(2.toInt())
                }
                else -> { }
            }
        })
        return builder.create()
    }
}