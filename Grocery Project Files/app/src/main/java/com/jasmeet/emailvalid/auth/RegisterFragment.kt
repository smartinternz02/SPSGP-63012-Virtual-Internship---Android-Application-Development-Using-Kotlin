package com.jasmeet.emailvalid.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jasmeet.emailvalid.activities.SecondActivity
import com.jasmeet.emailvalid.databinding.FragmentRegisterBinding

import com.jasmeet.emailvalid.interfac.FragmentNav


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email: String
    private lateinit var pass: String
    private lateinit var confirmPass: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            validateData()
        }
        binding.btnLogin.setOnClickListener {
            val navRegister = activity as FragmentNav
            navRegister.navFrag(EmailLoginFragment(),true)
        }



        return binding.root
    }

    private fun validateData() {
         email = binding.email.text.toString()
         pass = binding.pass.text.toString()
        confirmPass = binding.confirmPass.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
            if(pass == confirmPass){

                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {task->
                    if (task.isSuccessful){
                        val intent = Intent(activity,SecondActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(requireContext(),task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }
            else{
                Snackbar.make(binding.root, "Password does not match", Snackbar.LENGTH_SHORT).show()
            }

        }else{
            Snackbar.make(binding.root, "Please fill all the fields", Snackbar.LENGTH_SHORT).show()
        }
    }


}
