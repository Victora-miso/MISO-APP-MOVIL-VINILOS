package com.alphazetakapp.misoappvinilos.ui.artist.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.model.Musician
import com.alphazetakapp.misoappvinilos.data.repository.MusicianRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicianDetailViewModel @Inject constructor(
    private val repository: MusicianRepository
): ViewModel(){

    private val _musician = MutableLiveData<Musician>()
    val musician: LiveData<Musician> = _musician

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadMusician(musicianId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val result = repository.getMusicianById(musicianId)
                result.onSuccess { musician ->
                    _musician.value = musician
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