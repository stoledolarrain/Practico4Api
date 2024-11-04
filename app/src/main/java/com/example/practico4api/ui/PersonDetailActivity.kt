package com.example.practico4api.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practico4api.databinding.ActivityPersonDetailActivityBinding
import com.example.practico4api.models.Persona
import com.example.practico4api.repositories.PersonaRepository

class PersonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonDetailActivityBinding
    private var personId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personId = intent.getLongExtra("person_id", -1)
        if (personId != -1L) {
            loadPersonData(personId)
        }

        // Configurar el botón Guardar
        binding.btnSavePerson.setOnClickListener {
            if (personId != -1L) {
                updatePerson()
            } else {
                addNewPerson()
            }
        }

        // Configurar el botón Cancelar
        binding.btnCancelForm.setOnClickListener {
            finish() // Cierra la actividad y regresa a la pantalla anterior sin guardar cambios
        }
    }

    private fun loadPersonData(personId: Long) {
        PersonaRepository.getPersonaById(personId.toInt(), { person ->
            binding.txtPersonName.editText?.setText(person.name)
            binding.txtPersonLastName.editText?.setText(person.last_name)
            binding.txtPersonCompany.editText?.setText(person.company)
            binding.txtPersonAddress.editText?.setText(person.address)
            binding.txtPersonCity.editText?.setText(person.city)
            binding.txtPersonState.editText?.setText(person.state)
        }, { error ->
            Toast.makeText(this, "Error al cargar contacto: ${error.message}", Toast.LENGTH_SHORT).show()
        })
    }

    private fun addNewPerson() {
        val name = binding.txtPersonName.editText?.text.toString()
        val lastName = binding.txtPersonLastName.editText?.text.toString()
        val company = binding.txtPersonCompany.editText?.text.toString()
        val address = binding.txtPersonAddress.editText?.text.toString()
        val city = binding.txtPersonCity.editText?.text.toString()
        val state = binding.txtPersonState.editText?.text.toString()

        val newPerson = Persona(
            id = 0,
            name = name,
            last_name = lastName,
            company = company,
            address = address,
            city = city,
            state = state,
            profile_picture = "",
            phones = listOf(),
            emails = listOf()
        )

        PersonaRepository.addPersona(newPerson, {
            Toast.makeText(this, "Contacto agregado", Toast.LENGTH_SHORT).show()
            finish()
        }, { error ->
            Toast.makeText(this, "Error al agregar contacto: ${error.message}", Toast.LENGTH_SHORT).show()
        })
    }

    private fun updatePerson() {
        val name = binding.txtPersonName.editText?.text.toString()
        val lastName = binding.txtPersonLastName.editText?.text.toString()
        val company = binding.txtPersonCompany.editText?.text.toString()
        val address = binding.txtPersonAddress.editText?.text.toString()
        val city = binding.txtPersonCity.editText?.text.toString()
        val state = binding.txtPersonState.editText?.text.toString()

        val updatedPerson = Persona(
            id = personId,
            name = name,
            last_name = lastName,
            company = company,
            address = address,
            city = city,
            state = state,
            profile_picture = "",
            phones = listOf(),
            emails = listOf()
        )

        PersonaRepository.updatePersona(updatedPerson, {
            Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show()
            finish()
        }, { error ->
            Toast.makeText(this, "Error al actualizar contacto: ${error.message}", Toast.LENGTH_SHORT).show()
        })
    }
}
