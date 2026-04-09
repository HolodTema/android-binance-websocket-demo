package com.terabyte.binancewebsocketdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.terabyte.binancewebsocketdemo.databinding.ListItemMessageBinding
import com.terabyte.domain.model.MessageModel
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter(
    private val inflater: LayoutInflater,
    private var messages: List<MessageModel>
) : RecyclerView.Adapter<MessageAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder {
        val binding = ListItemMessageBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    class Holder(private val binding: ListItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        fun bind(messageModel: MessageModel) {
            binding.textMessageText.text = messageModel.text
            binding.textMessageTime.text = dateFormat.format(messageModel.timestamp)
        }
    }
}