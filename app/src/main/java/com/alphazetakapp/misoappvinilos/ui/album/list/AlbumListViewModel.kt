package com.alphazetakapp.misoappvinilos.ui.album.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //This ViewModel can be injected using hilt
class AlbumListViewModel @Inject constructor(
    private val repository: AlbumRepository //Injected repository
) : ViewModel() {
    private val _albums = MutableLiveData<List<Album>>() //Private state mutable (Only ViewModel modifies)
    val albums: LiveData<List<Album>> = _albums //Public state immutable

    //Handle progress states
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    //Handle errors
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadAlbum(){
        viewModelScope.launch { //Corrutine that is automatically cancelled when the ViewModel is destroyed
            _loading.value = true // show progressbar

            repository.getAlbums() //Calling repository
                .onSuccess { _albums.value = it} //For success then update álbumes
                .onFailure { _error.value = it.message } //For error, just show error

            _loading.value = false // Hide progressbar
        }
    }
}

