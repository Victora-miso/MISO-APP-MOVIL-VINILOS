package com.alphazetakapp.misoappvinilos.ui.album.associate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.model.Track
import com.alphazetakapp.misoappvinilos.data.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssociateTrackViewModel @Inject constructor(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>?>()
    val albums: LiveData<List<Album>?> get() = _albums

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _trackAssociated = MutableLiveData<Result<Boolean>>()
    val trackAssociated: LiveData<Result<Boolean>> get() = _trackAssociated

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            _loading.value = true
            val result = albumRepository.getAlbums()
            _loading.value = false

            result.onSuccess { albumList ->
                _albums.value = albumList
            }.onFailure { exception ->
                _error.value = exception.message
                _albums.value = null
            }
        }
    }

    fun associateTrackToAlbum(albumId:Int, track: Track) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val result = albumRepository.associateTrack(albumId,track)
                _trackAssociated.value = result
            } catch (e: Exception) {
                _error.value = "Failed to associate track: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}