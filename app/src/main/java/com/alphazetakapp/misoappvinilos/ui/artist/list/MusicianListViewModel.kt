package com.alphazetakapp.misoappvinilos.ui.artist.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphazetakapp.misoappvinilos.data.model.Musician
import com.alphazetakapp.misoappvinilos.data.repository.MusicianRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel //This ViewModel can be injected using hilt
class MusicianListViewModel @Inject constructor(
    private val repository: MusicianRepository //Injected repository
) : ViewModel() {
    private val _musicians = MutableLiveData<List<Musician>>() //Private state mutable (Only ViewModel modifies)
    val musicians: LiveData<List<Musician>> = _musicians //Public state immutable

    //Handle progress states
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    //Handle errors
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadMusician(){
        viewModelScope.launch { //Corrutine that is automatically cancelled when the ViewModel is destroyed
            _loading.value = true // show progressbar

            repository.getMusicians() //Calling repository
                .onSuccess { _musicians.value = it} //For success then update artists
                .onFailure { _error.value = it.message } //For error, just show error

            _loading.value = false // Hide progressbar
        }
    }
}