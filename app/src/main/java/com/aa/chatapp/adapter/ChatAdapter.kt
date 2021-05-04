package com.aa.chatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aa.chatapp.databinding.ChatRowBinding
import com.aa.chatapp.databinding.ChatRowYourBinding

class ChatAdapter(): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(val itemBinding: ChatRowBinding): RecyclerView.ViewHolder(itemBinding.root)
    class ChatView2Holder(val itemBinding: ChatRowYourBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }

}