package com.example.touringcommands

import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.touringcommands.Constants.Companion.GENERAL_MESSAGE_COMMAND_VALUE_TEMPLATE
import com.example.touringcommands.Constants.Companion.WHOLE_MESSAGE_TEMPLATE
import com.example.touringcommands.models.Command
import com.example.touringcommands.models.Group
import com.google.gson.Gson


class CommandsActivity : AppCompatActivity() {
    private var commands: ArrayList<Command?>? = arrayListOf()
    private lateinit var currentGroup: Group

    private lateinit var buttonsGrid: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commands)

        initializeDefaultCommandButtons()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val extras = intent.extras
        val groupJson = extras?.getString(Constants.GROUP_SETTINGS_INTENT_EXTRA_NAME) as String
        val gson = Gson()
        currentGroup = gson.fromJson(groupJson, Group::class.java)
    }

    fun commandButtonClick(view: View) {
        if(view is Button) {
            val command = commands?.find { c -> c?.name == view.text.toString() }
            if (command != null) {
                sendCommand(command)
            }
        }
    }

    fun createNewCommand(view: View) {

    }

    fun exit(view: View) {
    }

    fun settings(view: View) {
        onBackPressed()
    }

    private fun initializeDefaultCommandButtons() {
        commands?.add(Command(CommandType.STOP.name, CommandType.STOP, R.drawable.ic_launcher_background))
        commands?.add(Command(CommandType.DANGER.name, CommandType.DANGER, R.drawable.ic_launcher_background))
        commands?.add(Command(CommandType.POLICE.name, CommandType.POLICE, R.drawable.ic_launcher_background))
        commands?.add(Command(CommandType.FUEL.name, CommandType.FUEL, R.drawable.ic_launcher_background))

        buttonsGrid = findViewById(R.id.commands_grid)

        val arrayList: ArrayList<Command?>? = commands
        val adapter = CommandGridViewAdapter(this, arrayList)
        buttonsGrid.adapter = adapter
    }

    private fun sendCommand(command: Command) {
        val smsManager = SmsManager.getDefault() as SmsManager
        val messageTemplate = WHOLE_MESSAGE_TEMPLATE
        val message = messageTemplate
            .replace(GENERAL_MESSAGE_COMMAND_VALUE_TEMPLATE, "<${command.commandType}>##")
        for (member in currentGroup.members)
            smsManager.sendTextMessage(member.phoneNumber.toString(), null,
                message, null, null)
    }
}