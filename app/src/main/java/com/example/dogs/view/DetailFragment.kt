package com.example.dogs.view


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.example.dogs.R
import com.example.dogs.databinding.FragmentDetailBinding
import com.example.dogs.model.Pokemon
import com.example.dogs.model.DogPalette
import com.example.dogs.viewmodel.DetailViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var dogUuid = 0

    private  lateinit var dataBinding: FragmentDetailBinding
//    private var sendSmsStarted = false

    private var currentDog: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // argument can be null, if it is not null, then run
        arguments?.let {
            // retrieve argument pass from ListFragment
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let {
                dataBinding.dog = dog

                it.url?.let{
                    setupBackGroundColor(it)
                }
            }
        })
    }

    private fun setupBackGroundColor(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate{ palette ->
                            val intColor = palette?.vibrantSwatch?.rgb ?:0
                            val myPalette = DogPalette(intColor)
                            dataBinding.palette = myPalette
                        }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this pokemon")
                intent.putExtra(Intent.EXTRA_TEXT, "${currentDog?.name} bred for ${currentDog?.attack}")
                intent.putExtra(Intent.EXTRA_STREAM, currentDog?.url)
                startActivity(Intent.createChooser(intent, "Share with"))
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
