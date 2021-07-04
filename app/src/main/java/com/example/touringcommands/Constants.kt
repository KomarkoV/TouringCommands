package com.example.touringcommands

class Constants {
    companion object {
        const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        const val SMS_INTENT_EXTRA_NAME = "GroupMessage"
        const val GROUP_SETTINGS_INTENT_EXTRA_NAME = "GroupSettings"

        const val WHOLE_MESSAGE_TEMPLATE = "##T0UR1NG_C0MM4ND##!##C0MM4ND_NAME:<\\w+>##"
        const val GENERAL_MESSAGE_COMMAND_NAME_WHOLE_TEMPLATE = "##C0MM4ND_NAME:<\\w+>##"
        const val GENERAL_MESSAGE_COMMAND_VALUE_TEMPLATE = "<\\w+>##"
        const val MESSAGE_CUSTOM_COMMAND_TEMPLATE = "##T0UR1NG_C0MM4ND##!##C0MM4ND_NAME:<CUSTOM>##V4LU3:<\\w+>##"
    }
}