package com.jasmeet.emailvalid.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.jasmeet.emailvalid.R
import com.jasmeet.emailvalid.auth.EmailLoginFragment
import com.jasmeet.emailvalid.databinding.ActivityMainBinding
import com.jasmeet.emailvalid.interfac.FragmentNav

class MainActivity : AppCompatActivity(),FragmentNav {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)




    }

    override fun navFrag(fragment: Fragment, addToStack: Boolean) {
        val trans = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)

        if (addToStack){
            trans.addToBackStack(null)
        }
        trans.commit()
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null){
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
        else{
            supportFragmentManager.beginTransaction().add(R.id.container, EmailLoginFragment()).commit()
        }
    }
}


