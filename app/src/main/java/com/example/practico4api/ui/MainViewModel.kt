package com.example.practico4api.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4api.models.Persona
import com.example.practico4api.repositories.PersonaRepository

class MainViewModel : ViewModel() {

    // LiveData para observar la lista de contactos
    private val _personList = MutableLiveData<List<Persona>>()
    val personList: LiveData<List<Persona>> get() = _personList

    // Función para obtener la lista de contactos
    fun fetchPersonList() {
        PersonaRepository.getPersonas { personList ->
            _personList.value = personList
        }
    }
}
