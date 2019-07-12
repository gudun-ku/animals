package com.beloushkin.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.beloushkin.animals.model.Animal
import com.beloushkin.animals.model.AnimalApiService
import com.beloushkin.animals.model.ApiKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application): AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>()}
    val loadError by lazy { MutableLiveData<Boolean>() }
    val isLoading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val apiService = AnimalApiService()

    fun refresh() {
        // Mock data
        // getMockAnimals()
        isLoading.value = true
        getKey()
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {
                        if(key.key.isNullOrEmpty()) {
                            loadError.value = true
                            isLoading.value =false
                        } else {
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        isLoading.value = false
                        loadError.value = true
                    }
                })
        )
    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value = false
                        animals.value = list
                        isLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        isLoading.value = false
                        animals.value = null
                        loadError.value = true
                    }
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}