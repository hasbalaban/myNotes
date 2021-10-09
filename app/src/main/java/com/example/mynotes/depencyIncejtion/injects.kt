package com.example.mynotes.depencyIncejtion

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.View.feedFragment
import com.example.mynotes.View.feedFragmentDirections
import com.example.mynotes.clickListeners.ClickListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object injects {


    @Singleton
    @Provides
    fun injectContext(@ApplicationContext context: Context): Context {

        return context
    }


    @Singleton
    @Provides
    fun injectlistener(@ApplicationContext context: Context): ClickListener {


        val clickListener = object : ClickListener {
            override fun clicks(view: View) {
                //  view.visibility=View.GONE
                val lastLocation = view.rotation
                val animator = view.animate()

                animator.rotation(lastLocation + 180f)
                animator.duration = 300L
                animator.start()

                var action: NavDirections
                when (view.id) {

                    R.id.addNote -> {
                        action = feedFragmentDirections.actionFeedFragmentToAddFragment()
                        navigater(view,action)
                    }

                    R.id.settings -> {
                        Toast.makeText(context, "settings", Toast.LENGTH_SHORT).show()
                        action = feedFragmentDirections.actionFeedFragmentToSettings2()
                        navigater(view,action)
                    }

                }


            }

        }
        return clickListener

    }


/*
    @Singleton
    @Provides
    fun injectlistener(@ApplicationContext context: Context): ClickListener {


        val clickListener = object : ClickListener {
            override fun clicks(view: View) {
                //  view.visibility=View.GONE
                val lastLocation = view.rotation
                val animator = view.animate()

                animator.rotation(lastLocation + 180f)
                animator.duration = 300L
                animator.start()

                var action: NavDirections
                when (view.id) {
                    R.id.photo1 -> {
                        Toast.makeText(context, "photo1", Toast.LENGTH_LONG).show()
                    }
                    R.id.photo2 -> {
                        Toast.makeText(context, "photo2", Toast.LENGTH_LONG).show()
                    }
                    R.id.addNote -> {
                        Toast.makeText(context, "addNote", Toast.LENGTH_LONG).show()
                        action = feedFragmentDirections.actionFeedFragmentToAddFragment()
                        navigater(view,action)
                    }

                }


            }

        }
        return clickListener

    }

 */

    fun navigater (view: View,action:NavDirections){


        Navigation.findNavController(view).navigate(action)
    }
}

