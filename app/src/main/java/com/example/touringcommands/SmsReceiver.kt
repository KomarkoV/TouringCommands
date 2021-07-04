package com.example.touringcommands

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.touringcommands.Constants.Companion.SMS_INTENT_EXTRA_NAME
import com.example.touringcommands.Constants.Companion.SMS_RECEIVED
import com.example.touringcommands.Constants.Companion.WHOLE_MESSAGE_TEMPLATE

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == SMS_RECEIVED) {
            val messagesFromIntent = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val sms = messagesFromIntent[0]

            val regex = WHOLE_MESSAGE_TEMPLATE.toRegex()
            if(regex.containsMatchIn(sms.messageBody)) {
                val messageDisplayIntent = Intent(context, CommandDisplayActivity::class.java)
                messageDisplayIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                messageDisplayIntent.putExtra(SMS_INTENT_EXTRA_NAME, sms.messageBody)
                context.startActivity(messageDisplayIntent)
            }
        }
    }
}