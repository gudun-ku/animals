package com.beloushkin.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.beloushkin.animals.model.Animal

class ListViewModel(application: Application): AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>()}
    val loadError by lazy { MutableLiveData<Boolean>() }
    val isLoading by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        // Mock data
        getMockAnimals()
    }

    private fun getMockAnimals() {
        val a0 = Animal("rat")
        val a1 = Animal("alligator")
        val a2 = Animal("bear")
        val a3 = Animal("rabbit")
        val a4 = Animal("snake")
        val a5 = Animal("tiger")
        val a6 = Animal("turtle")
        val a7 = Animal("groundhog")
        val a8 = Animal("hedgehog")
        val a9 = Animal("lion")
        val aA = Animal("elephant")
        val aB = Animal("giraffe")
        val aC = Animal("coyote")
        val aD = Animal("python")
        val aE = Animal("goat")
        val aF = Animal("mustang")

        val animalList = arrayListOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, aA, aB, aC, aD, aE, aF)

        animals.value = animalList
        loadError.value = false
        isLoading.value = false

    }
}