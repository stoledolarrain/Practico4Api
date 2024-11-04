// MainViewModel.kt
package com.example.practico4api.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4api.models.Persona
import com.example.practico4api.repositories.PersonaRepository

class MainViewModel : ViewModel() {

    private val _personList = MutableLiveData<List<Persona>>()
    val personList: LiveData<List<Persona>> get() = _personList

    private val _filteredPersonList = MutableLiveData<List<Persona>>()
    val filteredPersonList: LiveData<List<Persona>> get() = _filteredPersonList

    fun fetchPersonList() {
        PersonaRepository.getPersonas { personList ->
            _personList.value = personList
            _filteredPersonList.value = personList // Inicialmente, lista completa
        }
    }

    fun searchByNameAndLastName(context: Context, nameQuery: String, lastNameQuery: String) {
        val fullList = _personList.value ?: return
        val filteredList = fullList.filter { person ->
            person.name.contains(nameQuery, ignoreCase = true) || // Ignora mayúsculas y minúsculas
                    person.last_name.contains(lastNameQuery, ignoreCase = true)
        }
        _filteredPersonList.value = filteredList
    }
}
