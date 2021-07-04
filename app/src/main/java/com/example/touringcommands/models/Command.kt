package com.example.touringcommands.models

import com.example.touringcommands.CommandType

data class Command(val name: String, val commandType: CommandType, val imgId:Int = 0)