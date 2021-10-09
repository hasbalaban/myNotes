package com.example.mynotes.View

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mynotes.R
import com.example.mynotes.clickListeners.ClickListener
import com.example.mynotes.databinding.FragmentSettingsBinding


var giris : Boolean=false
class settings : Fragment() {


    //public declares
    lateinit var  sharedPreferences : SharedPreferences
    private lateinit var settingsBinding: FragmentSettingsBinding
    lateinit var sifre:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    // first setting and a few animate and initialize the sharedpreferences here.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        giris=true
        settingsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        val animator = settingsBinding.screenOfPassword.animate()
        animator.alpha(0f).duration = 0L
        animator.start()

        sharedPreferences = requireContext().getSharedPreferences( requireContext().packageName,
            Context.MODE_PRIVATE
        )


        return settingsBinding.root


    }

    // get your private password and save a variable then
    // if it no exists ,hide  yours last password button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setSetting()
        settingsBinding.listener = clickListener

         sifre = sharedPreferences.getString("password", "")!!

        if (sifre==""){
            settingsBinding.eskiSifre.visibility = View.GONE
        }

        super.onViewCreated(view, savedInstanceState)
    }

    // show flowing animate
    fun setSetting() {

        val settingsAnimater = settingsBinding.loading.animate()
        settingsAnimater.rotation(360f).duration = 1000L
        settingsAnimater.alpha(0f).duration = 1000L
        settingsAnimater.start()


        val animator = settingsBinding.screenOfPassword.animate()
        animator.alpha(1f).duration = 3000L
        animator.start()

    }



    //click listener of view and check password
    // if it is a first enter in application or you don't have a password
    // we will send you create a password (settings)page for create a password
    val clickListener = object : ClickListener {
        override fun clicks(view: View) {

            when (view.id) {

                settingsBinding.forgetPassword.id -> {
                    val mesaj = "yakında şifre unutma işlemi için işlev verilecek"
                    makeToastMessage(mesaj)
                }

                settingsBinding.kaydet.id -> {

                    val yeniSifre = settingsBinding.yeniSifre.text.toString()
                    val yeniSifreTekrar = settingsBinding.yeniSifreTekrar.text.toString()

                    if (sifre == "") {


                        if (yeniSifre == yeniSifreTekrar ) {

                            if( yeniSifre!=""&& yeniSifre!=" "  && yeniSifre!="  "){
                                sharedPreferences.edit().putString("password", yeniSifre).apply()
                                val mesaj = "veri tabanına kayıt edildi"
                                makeToastMessage(mesaj)
                            }
                            else {
                                val mesaj = "şifre alanını boş bırakmayın"
                                makeToastMessage(mesaj)
                            }


                        }
                        else{
                            val mesaj = "şifreler eşleşmiyor"
                            makeToastMessage(mesaj)
                        }


                    }





                    else {

                        val eskiSifre = settingsBinding.eskiSifre.text.toString()
                        if (eskiSifre == sifre && yeniSifre == yeniSifreTekrar && yeniSifre!="" && yeniSifre!=" "  && yeniSifre!="  ") {

                            sharedPreferences.edit().putString("password", yeniSifre).apply()
                            val mesaj = "veri tabanında  eski kayıt"
                            makeToastMessage(mesaj)
                        }
                        else{
                            val mesaj = "eski şifre yanlış"
                            makeToastMessage(mesaj)
                        }
                    }
                }

            }

        }

    }


    // a function foe show toast message
    fun makeToastMessage(message: String) {
        val ToastMessage = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        ToastMessage.setText(message)
        ToastMessage.show()

    }


}