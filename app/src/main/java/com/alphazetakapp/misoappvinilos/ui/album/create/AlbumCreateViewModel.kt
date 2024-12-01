package com.alphazetakapp.misoappvinilos.ui.album.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphazetakapp.misoappvinilos.data.model.CreateAlbum
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAlbumViewModel @Inject constructor(
    private val repository: AlbumRepository
): ViewModel() {

    private val _albumCreated = MutableLiveData<Album?>()
    val albumCreated: LiveData<Album?> = _albumCreated

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun createAlbum(album: CreateAlbum) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val result = repository.createAlbum(album)
                result.onSuccess { newAlbum ->
                    _albumCreated.value = newAlbum
                }.onFailure { exception ->
                    _error.value = exception.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}