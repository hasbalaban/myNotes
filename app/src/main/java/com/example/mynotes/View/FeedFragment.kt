package com.example.mynotes.View

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.NotesRecyclerViewAdapter.NotesRecyclerViewAdapter
import com.example.mynotes.NotesViewModels.FeedFragmentViewModel
import com.example.mynotes.R
import com.example.mynotes.clickListeners.ClickListener
import com.example.mynotes.database.Notes
import com.example.mynotes.databinding.FragmentFeedBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

var Ozelsifre: String? = null

@AndroidEntryPoint
class feedFragment : Fragment() {

    // declare public varaibles
    private lateinit var feedDataBinding: FragmentFeedBinding

    @Inject
    lateinit var ClickListenerInject: ClickListener

    lateinit var feedFragmentViewModel: FeedFragmentViewModel

    lateinit var Notes: List<Notes>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    // bind layout here
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        feedDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)



        return feedDataBinding.root
    }

    //operation start make here
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        feedDataBinding.listener = ClickListenerInject

        feedFragmentViewModel = FeedFragmentViewModel(requireContext())



        openAnimetor()
        clickToMenu()
        super.onViewCreated(view, savedInstanceState)
    }
    // when you see view make following procces
    // bottom menu set settings
    override fun onResume() {

        if (Ozelsifre != null) {
            recyclerViewSettings("ozel")
            feedDataBinding.bottomMenu.menu.getItem(1).setChecked(true)


        } else {
            feedDataBinding.bottomMenu.menu.getItem(1).setChecked(false)
            feedDataBinding.bottomMenu.menu.getItem(0).setChecked(true)
            recyclerViewSettings()
        }
        super.onResume()
    }

    // animate after application start
    fun openAnimetor() {

        var settingsAnimater = feedDataBinding.settings.animate()
        settingsAnimater.rotation(360f).duration = 2000L
        settingsAnimater.start()


    }

//menu implement and choisee menu items
    fun clickToMenu() {


        feedDataBinding.bottomMenu.setOnItemSelectedListener() {


            when (it.itemId) {

                R.id.genel -> recyclerViewSettings("genel")

                R.id.ozel -> {
                    if (Ozelsifre == null) {
                        val action = feedFragmentDirections.actionFeedFragmentToCheckPassword()
                        Navigation.findNavController(feedDataBinding.root).navigate(action)

                    } else
                        recyclerViewSettings("ozel")
                }

            }


            return@setOnItemSelectedListener true
        }
    }


    // settting of recyclerview and set adapter to recyclerview
    fun recyclerViewSettings(category: String = "genel") {


        feedFragmentViewModel.LoadingNotesFromNotesDatabase(category)
        feedFragmentViewModel.NotesLiveData.observe(viewLifecycleOwner, Observer {

            Notes = it
            val adapter = NotesRecyclerViewAdapter(Notes)
            feedDataBinding.RecyclerViewForNotes.layoutManager = LinearLayoutManager(context)
            feedDataBinding.RecyclerViewForNotes.adapter = adapter
        })


    }


}


/*
        val clickListener = object : ClickListener {
            override fun clicks(view: View) {
                Toast.makeText(context, "text cliked", Toast.LENGTH_LONG).show()

            }
            }
        //feedDataBinding.listener= clickListener


 */
