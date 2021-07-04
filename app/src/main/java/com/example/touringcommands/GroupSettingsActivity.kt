package com.example.touringcommands

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.touringcommands.Constants.Companion.GROUP_SETTINGS_INTENT_EXTRA_NAME
import com.example.touringcommands.models.Group
import com.example.touringcommands.models.GroupMember
import com.google.gson.Gson

class GroupSettingsActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_RECEIVE_SMS = 0X00
    private val PERMISSION_REQUEST_READ_SMS = 0X01
    private val PERMISSION_REQUEST_SEND_SMS = 0X10
    private val PERMISSION_REQUEST_READ_CONTACTS = 0X100

    private lateinit var group: Group;
    private lateinit var groupMembersContent: LinearLayout
    private lateinit var firstMemberEditText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_settings)

        checkPermissions()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        groupMembersContent = findViewById(R.id.group_settings_members_content)
        firstMemberEditText = findViewById(R.id.group_settings_first_member)
    }

    fun confirmButtonClick(view: View) {
        if(firstMemberEditText.text.isNullOrEmpty()) {
            Toast.makeText(this, "At least one member must be provided", Toast.LENGTH_LONG)
                .show()
            return
        }

        val member = GroupMember(firstMemberEditText.text.toString().toInt())
        var groupName = findViewById<EditText>(R.id.group_settings_group_name).text.toString()
        group = Group(mutableListOf(member), groupName)
        val gson = Gson()
        val groupJson = gson.toJson(group)

        val commandsActivity = Intent(this, CommandsActivity::class.java)
        commandsActivity.putExtra(GROUP_SETTINGS_INTENT_EXTRA_NAME, groupJson)
        startActivity(commandsActivity)
    }

    private fun checkPermissions() {
        checkPermission(Manifest.permission.RECEIVE_SMS, PERMISSION_REQUEST_RECEIVE_SMS)
        checkPermission(Manifest.permission.READ_SMS, PERMISSION_REQUEST_READ_SMS)
        checkPermission(Manifest.permission.SEND_SMS, PERMISSION_REQUEST_SEND_SMS)
        checkPermission(Manifest.permission.READ_CONTACTS, PERMISSION_REQUEST_READ_CONTACTS)
    }

    private fun checkPermission(permissionName: String, permissionRequest: Int) {
        if(ContextCompat.checkSelfPermission(this, permissionName)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(permissionName), permissionRequest)
        }
    }
}