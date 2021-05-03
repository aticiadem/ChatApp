package com.aa.chatapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aa.chatapp.R
import com.aa.chatapp.adapter.MessageAdapter
import com.aa.chatapp.databinding.FragmentNewMessageBinding
import com.aa.chatapp.model.UserModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewMessageF : Fragment() {

    private var _binding: FragmentNewMessageBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private val usersList = ArrayList<UserModel>()
    private lateinit var adapter: MessageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentNewMessageBinding.inflate(inflater,container,false)
        val view = binding.root
        (activity as AppCompatActivity?)!!.title = "New Message"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchUsers()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MessageAdapter(usersList)
        binding.recyclerView.adapter = adapter
    }

    private fun fetchUsers(){
        db.collection("Users")
            .orderBy("username", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
            if(error != null){
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
                if(snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents
                        usersList.clear()
                        for (document in documents){
                            val uid = document.get("uid") as String
                            val username = document.get("username") as String
                            val profileImageUrl = document.get("profileImageUrl") as String
                            println(uid)
                            println(username)
                            println(profileImageUrl)
                            val user = UserModel(uid,username,profileImageUrl)
                            usersList.add(user)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}