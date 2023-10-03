package com.example.cityweatherdetails.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cityweatherdetails.util.Constants
import com.example.cityweatherdetails.model.WeatherDetailsModel
import com.example.cityweatherdetails.network.WeatherRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherDetailsViewModelFactory constructor(private val repository: WeatherRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherDetailsViewModel::class.java)) {
            WeatherDetailsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

class WeatherDetailsViewModel(private val repository: WeatherRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val weatherDetailsModel = MutableLiveData<WeatherDetailsModel>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()

    fun getWeatherDetails(cityName: String) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getCityWeatherDetails(cityName, Constants.APP_ID)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    loading.value = false
                    weatherDetailsModel.postValue(response.body())
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}