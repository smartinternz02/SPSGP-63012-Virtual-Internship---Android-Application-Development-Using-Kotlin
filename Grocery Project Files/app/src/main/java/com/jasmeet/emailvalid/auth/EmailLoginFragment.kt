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
import com.jasmeet.emailvalid.databinding.FragmentEmailLoginBinding
import com.jasmeet.emailvalid.interfac.FragmentNav

class EmailLoginFragment : Fragment() {

    private lateinit var binding: FragmentEmailLoginBinding
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEmailLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmailLoginBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()


        binding.btnRegister.setOnClickListener {
            val navRegister = activity as FragmentNav
            navRegister.navFrag(RegisterFragment(),true)
        }

        binding.btnLogin.setOnClickListener {

            email = binding.email.text.toString()
            password = binding.pass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->

                    if (task.isSuccessful){

                        val intent = Intent(activity, SecondActivity::class.java)

                        startActivity(intent)
                        Snackbar.make(binding.root,"Login was Successful",Snackbar.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(requireContext(),task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                if (email.isEmpty()||password.isEmpty()){
                    Snackbar.make(binding.root,"Empty Fields are not allowed !!",Snackbar.LENGTH_SHORT).show()
                }
            }
        }


        return binding.root



    }


}