package com.alphazetakapp.misoappvinilos.ui.collector.list

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
class CollectorListViewModel @Inject constructor(
    private val repository: CollectorRepository
) : ViewModel()
{
    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> = _collectors

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadCollectors() {
        viewModelScope.launch {
            _loading.value = true
            repository.getCollectors()
                .onSuccess { _collectors.value = it }
                .onFailure { _error.value = it.message }

            _loading.value = false
        }

    }
}