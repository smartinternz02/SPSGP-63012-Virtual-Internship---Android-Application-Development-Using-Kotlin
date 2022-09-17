package com.jasmeet.emailvalid.auth

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jasmeet.emailvalid.R
import com.jasmeet.emailvalid.databinding.FragmentForgotPasswordBinding
import com.jasmeet.emailvalid.interfac.FragmentNav


class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)

        binding.btnSubmit.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            if (email.isNotEmpty()){

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{task->

                    if (task.isSuccessful){

                        var navAg = activity as FragmentNav
                        navAg.navFrag(EmailLoginFragment(),false)
                        Toast.makeText(requireContext(),"Email sent successfully",Toast.LENGTH_SHORT).show()
                    }

                    else{
                        Toast.makeText(requireContext(),task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    }

                }

            }else if (email.isEmpty()){
                Snackbar.make(binding.root,"Please enter email",Snackbar.LENGTH_SHORT).show()

            }


        }
        return binding.root


    }


}