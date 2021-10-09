package com.example.mynotes.View

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.clickListeners.ClickListener
import com.example.mynotes.databinding.FragmentCheckPasswordBinding

class check_password : Fragment() {

    //we declare here
    lateinit var sharedPreferences: SharedPreferences
    lateinit var check_Password_Binding: FragmentCheckPasswordBinding
    var sifre = ""
    //for visibility of icon
    private var toogle = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        check_Password_Binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_check_password, container, false)

        // we create shared preferences her for password controll if exists
        val sharedPreferences = requireContext().getSharedPreferences(
            requireContext().packageName,
            Context.MODE_PRIVATE
        )
        sifre = sharedPreferences.getString("password", "")!!





        return check_Password_Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        check_Password_Binding.listener = clickListener
        if (giris != false) {
            giris = false
            val action = check_passwordDirections.actionCheckPasswordToFeedFragment()

            Navigation.findNavController(check_Password_Binding.root).navigate(action)
        } else {

            if (sifre == "") {

                val action = check_passwordDirections.actionCheckPasswordToSettings2()

                Navigation.findNavController(check_Password_Binding.root).navigate(action)
                Toast.makeText(context, "Lütfen bir şifre ekleyin..", Toast.LENGTH_LONG).show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


    fun passwordCheck() {

        val girilenSifre = check_Password_Binding.password.text.toString()



        if (sifre == girilenSifre) {
            Ozelsifre = sifre
            val action = check_passwordDirections.actionCheckPasswordToFeedFragment()

            Navigation.findNavController(check_Password_Binding.root).navigate(action)


        } else {
            Toast.makeText(context, "Girilen Şifre yanlış", Toast.LENGTH_LONG).show()
        }


    }


    //listener here , for buttons
    // and a few animate
    val clickListener = object : ClickListener {
        override fun clicks(view: View) {
            when (view.id) {

                check_Password_Binding.forgetPassword.id ->
                    Toast.makeText(requireContext(), "bekleyin..", Toast.LENGTH_SHORT).show()
                check_Password_Binding.buttonCheckPassword.id -> {
                    passwordCheck()
                }
                check_Password_Binding.toogle.id -> {
                    val lastRotation = check_Password_Binding.toogle.rotation
                    val animator = check_Password_Binding.toogle.animate()


                    if (toogle == false) {

                        Toast.makeText(
                            requireContext(),
                            check_Password_Binding.password.inputType.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        check_Password_Binding.password.inputType =
                            InputType.TYPE_CLASS_TEXT
                        toogle = true

                        animator.rotation(lastRotation + 180f)
                    } else {

                        Toast.makeText(requireContext(), "eskisi..", Toast.LENGTH_SHORT).show()
                        check_Password_Binding.password.inputType = 129
                        toogle = false

                        animator.rotation(lastRotation + 180f)
                    }

                }

            }

        }
    }

}


