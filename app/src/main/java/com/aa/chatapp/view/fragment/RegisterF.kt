package com.aa.chatapp.view.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.aa.chatapp.R
import com.aa.chatapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class RegisterF : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var selectedPicture: Uri? = null
    private var selectedBitMap: Bitmap? = null
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val storage = Firebase.storage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        val view = binding.root

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        if (auth.currentUser != null){
            findNavController().navigate(R.id.action_registerF_to_homeF)
        }

        binding.textViewHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerF_to_loginF)
        }

        binding.imageViewChooseYourPicture.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                    !=PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            } else {
                // we have permission, go to gallery
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,2)
            }
        }

        binding.buttonRegister.setOnClickListener {
            if (isValidInput()){
                val username = binding.editTextUsername.text.toString()
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()

                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                Toast.makeText(requireContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show()

                                val user = Firebase.auth.currentUser
                                val profileUpdates = userProfileChangeRequest {
                                    displayName = username
                                }
                                user!!.updateProfile(profileUpdates)
                                        .addOnCompleteListener {
                                            if (task.isSuccessful) {
                                                println("Kullanıcı Adı Eklendi")
                                            }
                                        }

                                uploadImageToFirebaseStorage()

                                findNavController().navigate(R.id.action_registerF_to_homeF)
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
                        }
            } else {
                Toast.makeText(requireContext(),"Lütfen Bütün Alanları Doldurun!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToFirebaseStorage(){
        //val currentUser = user
        if(selectedPicture != null){
            val reference = storage.reference
            val uuid = UUID.randomUUID()
            val imageName = "$uuid"
            val imageReference = reference.child("images").child(imageName)
            imageReference.putFile(selectedPicture!!).addOnSuccessListener { task ->
                val uploadedPictureReference = reference.child("images").child(imageName)
                uploadedPictureReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    saveUserToFirebaseDatabase(downloadUrl)
                }
            }
        }
    }

    private fun saveUserToFirebaseDatabase(downloadUrl: String){
        val uid = auth.uid
        val username = auth.currentUser?.displayName
        val user = hashMapOf(
                "uid" to uid,
                "username" to username,
                "profileImageUrl" to downloadUrl
        )
        db.collection("Users")
                .add(user).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        println("Her sey tamam")
                    }
                }.addOnFailureListener { exception ->
                    println(exception.localizedMessage)
                }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            selectedPicture = data.data
            if(selectedPicture != null){
                if(Build.VERSION.SDK_INT >= 28){
                    val source = ImageDecoder.createSource(requireActivity().contentResolver,selectedPicture!!)
                    selectedBitMap = ImageDecoder.decodeBitmap(source)
                    binding.imageViewChooseYourPicture.setImageBitmap(selectedBitMap)
                } else {
                    selectedBitMap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,selectedPicture)
                    binding.imageViewChooseYourPicture.setImageBitmap(selectedBitMap)
                }
            }
        }
    }

    private fun isValidInput(): Boolean = binding.editTextEmail.text.isNotEmpty()
            && binding.editTextUsername.text.isNotEmpty()
            && binding.editTextPassword.text.isNotEmpty() && selectedPicture != null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}