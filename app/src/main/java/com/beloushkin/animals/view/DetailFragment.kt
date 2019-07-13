package com.beloushkin.animals.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette

import com.beloushkin.animals.R
import com.beloushkin.animals.model.Animal
import com.beloushkin.animals.util.getProgressDrawable
import com.beloushkin.animals.util.loadNetworkImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    lateinit var animal: Animal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {

            animal = DetailFragmentArgs.fromBundle(it).animal
            context?.let {
                animalImage.loadNetworkImage(animal.imageUrl, getProgressDrawable(it))
            }
            animalName.text = animal.name
            animalLocation.text = animal.location
            animalLifeSpan.text = animal.lifeSpan
            animalDiet.text = animal.diet

            animal.imageUrl?.let { url ->
                setupBackgroundColor(url)
            }

        }
    }

    private fun setupBackgroundColor(imageUrl: String) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object: CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate {palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            animalLayout.setBackgroundColor(intColor)

                        }
                }
            })

    }
}
