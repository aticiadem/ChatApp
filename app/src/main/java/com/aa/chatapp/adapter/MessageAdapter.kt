package com.aa.chatapp.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aa.chatapp.databinding.MessageRowBinding
import com.aa.chatapp.model.UserModel
import com.aa.chatapp.view.fragment.NewMessageFDirections
import com.squareup.picasso.Picasso

class MessageAdapter(private val usersList: ArrayList<UserModel>, val context: Context)
    : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

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

        holder.itemBinding.textViewUsername.setOnClickListener {
            val action = NewMessageFDirections.actionNewMessageFToChatF()
            Navigation.findNavController(it).navigate(action)
            val sharedPreferences = context.getSharedPreferences("USERNAME",Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username",currentUsername)
            editor.apply()
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

}