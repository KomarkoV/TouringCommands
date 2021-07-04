package com.example.touringcommands

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import com.example.touringcommands.models.Command


class CommandGridViewAdapter(context: Context, commandArrayList: ArrayList<Command?>?) :
    ArrayAdapter<Command?>(context, 0, commandArrayList!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.commands_card_item, parent, false)
        }

        val model: Command? = getItem(position)
        val button = listitemView!!.findViewById<Button>(R.id.command_card_btn)

        if (model != null)
            button.text = model.name
        //if (model != null)
        //    button.setCompoundDrawablesWithIntrinsicBounds()

        return listitemView
    }
}