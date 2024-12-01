package com.alphazetakapp.misoappvinilos.ui.album.detail

import android.util.Log
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
class AlbumDetailViewModel @Inject constructor(
    private val repository: AlbumRepository
): ViewModel() {

    private val _track = MutableLiveData<List<Track>>()
    val track: LiveData<List<Track>> = _track

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> = _album

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val result = repository.getAlbumById(albumId)
                result.onSuccess { album ->
                    _album.value = album
                    Log.d("AlbumDetailViewModel", "Album loaded: $album")
                    val tracksResult = repository.getTracksByAlbumId(albumId)
                    tracksResult.onSuccess { tracks ->
                        _track.value = tracks
                        Log.d("AlbumDetailViewModel", "Tracks loaded: $tracks")
                    }.onFailure { exception ->
                        _error.value = exception.message
                    }
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