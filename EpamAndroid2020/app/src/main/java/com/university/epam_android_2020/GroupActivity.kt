package com.university.epam_android_2020

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.university.epam_android_2020.firebaseDB.FirebaseDB
import com.university.epam_android_2020.user_data.CurrentGroup
import com.university.epam_android_2020.user_data.Gps
import com.university.epam_android_2020.user_data.Group
import com.university.epam_android_2020.user_data.User
import kotlinx.android.synthetic.main.activity_group.*
import java.util.*

class GroupActivity : AppCompatActivity() {
    val mFirebaseDB = FirebaseDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        supportActionBar?.title = "Group list"

        rvMaps.layoutManager = LinearLayoutManager(this)
        mFirebaseDB.getAllGroups {
            rvMaps.adapter = MapsAdapter(this, it, object: MapsAdapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@GroupActivity, MainActivity::class.java)
                    val groupName = it[position]
                    val currentGroup = CurrentGroup.instance
                    if (groupName != null) {
                        currentGroup.groupName = groupName
                        mFirebaseDB.joinToGroup(groupName)
                    }

                    mFirebaseDB.getUsersFromGroup(groupName!!) {
                        val group = Group(groupName, it)
                        intent.putExtra(EXTRA_USER_MAP, group) //Group
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                }
            })

            addButton.setOnClickListener {
                showAlertDialog(rvMaps.adapter as MapsAdapter)
            }
        }
        //val maps = generateSampleData()
    }

    private fun showAlertDialog(adapter: MapsAdapter) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_place, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add new group")
            .setView(placeFormView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Apply", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val title = placeFormView.findViewById<EditText>(R.id.etTitle).text.toString()

            if (title.trim().isEmpty()) {
                Toast.makeText(this, "Place must have non-empty title", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            adapter.groups.add(title)
            adapter.notifyDataSetChanged()
            mFirebaseDB.createGroup(title)

            //need add extra map
            dialog.dismiss()
        }
    }
}
