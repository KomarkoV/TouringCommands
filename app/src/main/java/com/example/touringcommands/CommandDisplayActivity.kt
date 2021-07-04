package com.example.touringcommands

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.touringcommands.Constants.Companion.GENERAL_MESSAGE_COMMAND_VALUE_TEMPLATE
import com.example.touringcommands.Constants.Companion.GENERAL_MESSAGE_COMMAND_NAME_WHOLE_TEMPLATE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CommandDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_display)

        val extras = intent.extras
        parseMessage(extras?.getString(Constants.SMS_INTENT_EXTRA_NAME) as String)
        delayFinishActivity()
    }

    private fun parseMessage(message: String) {
        val commandLabel = findViewById<TextView>(R.id.message_display_command_label)
        val commandName = getCommandNameFromMessage(message)

        commandLabel.text = commandName
        val messageType = getMessageType(commandName)
        if(messageType != null)
            setLayout(messageType)
        else
            finish()
    }

    private fun getCommandNameFromMessage(message: String): String {
        var regex = GENERAL_MESSAGE_COMMAND_NAME_WHOLE_TEMPLATE.toRegex()
        if(regex.containsMatchIn(message)) {
            regex = GENERAL_MESSAGE_COMMAND_VALUE_TEMPLATE.toRegex()
            val match = regex.find(message)
            if(match != null)
                return match.value.removePrefix("<").removeSuffix(">##")
        }
        return String()
    }

    private fun getMessageType(messageName: String): CommandType? {
        try {
            return CommandType.valueOf(messageName)
        } catch(e: IllegalArgumentException) {
            Log.d("MESSAGE PARSING", "INVALID MessageType value | $e")
        }
        return null
    }

    private fun setLayout(commandType: CommandType) {
        val mainWindow = findViewById<ConstraintLayout>(R.id.message_display_command_layout)
        when(commandType) {
            CommandType.STOP -> mainWindow.setBackgroundColor(Color.RED)
            CommandType.DANGER -> mainWindow.setBackgroundColor(Color.YELLOW)
            CommandType.POLICE -> mainWindow.setBackgroundColor(Color.BLUE)
            CommandType.FUEL -> mainWindow.setBackgroundColor(Color.WHITE)
            CommandType.CUSTOM -> mainWindow.setBackgroundColor(Color.WHITE)
        }
    }

    private fun delayFinishActivity() {
        GlobalScope.launch { // launch new coroutine in background and continue
            delay(5000L) // non-blocking delay for 1 second (default time unit is ms)
            finish()
        }
    }
}