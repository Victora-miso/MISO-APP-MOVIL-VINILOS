package com.alphazetakapp.misoappvinilos.ui.collector.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphazetakapp.misoappvinilos.data.model.Collector
import com.alphazetakapp.misoappvinilos.data.repository.CollectorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectorDetailViewModel @Inject constructor(
    private val repository: CollectorRepository
) : ViewModel()
{
    private val _collectors = MutableLiveData<Collector>()
    val collectors: LiveData<Collector> = _collectors

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadCollectors(collectorId: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val result = repository.getCollectorById(collectorId)
                result.onSuccess { collector ->
                    _collectors.value = collector
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