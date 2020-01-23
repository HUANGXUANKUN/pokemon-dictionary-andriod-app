package com.example.dogs.view


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.dogs.R
import com.example.dogs.databinding.FragmentDetailBinding
import com.example.dogs.model.ChipColor
import com.example.dogs.model.DogPalette
import com.example.dogs.model.Pokemon
import com.example.dogs.util.Common
import com.example.dogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var hpBar: ProgressBar
    private lateinit var attackBar: ProgressBar
    private lateinit var defenseBar: ProgressBar
    private lateinit var sAttackBar: ProgressBar
    private lateinit var sDefenseBar: ProgressBar
    private lateinit var speedBar: ProgressBar
    private var dogUuid = 0

    private  lateinit var dataBinding: FragmentDetailBinding



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
                dataBinding.pokemon = dog
                it.url?.let{
                    setupBackGroundColor(it)
                    initProgressBar(dog)
                    dataBinding.typeChipColor = ChipColor(Common.getColorByType(dog.type.toString()))
                }
            }
        })
    }

    private fun initProgressBar(dog: Pokemon){
        hpBar = this.hpProgressBar
        attackBar = this.attackProgressBar
        defenseBar = this.defenseProgressBar
        sAttackBar = this.sAttackProgressBar
        sDefenseBar = this.sDefenseProgressBar
        speedBar = this.speedProgressBar

        val hpPercent : Int =  (dog.hp.toString().toInt()) * 100 / 150
        val attackPercent : Int =  (dog.attack.toString().toInt()) * 100 / 150
        val defensePercent : Int =  (dog.defense.toString().toInt()) * 100 / 150
        val sAttackPercent : Int =  (dog.spAttack.toString().toInt()) * 100 / 150
        val sDefensePercent : Int =  (dog.spDefense.toString().toInt()) * 100 / 150
        val speedPercent : Int =  (dog.speed.toString().toInt()) * 100 / 150

        hpBar.setProgress(hpPercent,false)
        attackBar.setProgress(attackPercent,false)
        defenseBar.setProgress(defensePercent,false)
        sAttackBar.setProgress(sAttackPercent,false)
        sDefenseBar.setProgress(sDefensePercent,false)
        speedBar.setProgress(speedPercent,false)
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
                            val intColor = palette?.dominantSwatch?.rgb ?:0
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
                intent.putExtra(Intent.EXTRA_TEXT, "${currentDog?.name} bred for ${currentDog?.name}")
                intent.putExtra(Intent.EXTRA_STREAM, currentDog?.url)
                startActivity(Intent.createChooser(intent, "Share with"))
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
