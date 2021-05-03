package com.aa.chatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aa.chatapp.databinding.MessageRowBinding
import com.aa.chatapp.model.UserModel
import com.squareup.picasso.Picasso

class MessageAdapter(private val usersList: ArrayList<UserModel>): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(val itemBinding: MessageRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentUsername = usersList[position].username
        val currentImageUrl = usersList[position].profileImageUrl
        holder.itemBinding.textViewUsername.text = currentUsername
        Picasso.get().load(currentImageUrl).into(holder.itemBinding.imageViewPerson)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

}