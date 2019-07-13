package com.beloushkin.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.beloushkin.animals.R
import com.beloushkin.animals.model.Animal
import com.beloushkin.animals.util.getProgressDrawable
import com.beloushkin.animals.util.loadNetworkImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(
    private val animalList: ArrayList<Animal>
): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {


    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val currentAnimal = animalList[position]
        holder.view.animalName.text = currentAnimal.name
        holder.view.animalImage.loadNetworkImage(animalList[position].imageUrl,
            getProgressDrawable(holder.view.context))
        holder.view.animalLayout.setOnClickListener {
            val action = ListFragmentDirections.actionDetail(currentAnimal)
            Navigation.findNavController(holder.view).navigate(action)
        }
    }

    class AnimalViewHolder(var view: View):RecyclerView.ViewHolder(view)
}