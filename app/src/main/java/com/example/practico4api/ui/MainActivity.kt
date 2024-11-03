package com.example.practico4api

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practico4api.databinding.ActivityMainBinding
import com.example.practico4api.models.Persona
import com.example.practico4api.ui.MainViewModel
import com.example.practico4api.ui.PersonDetailActivity
import com.example.practico4api.ui.adapters.PersonAdapter
import android.app.AlertDialog
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // Configura el FAB para abrir PersonDetailActivity
        binding.fabAddPerson.setOnClickListener {
            val intent = Intent(this, PersonDetailActivity::class.java)
            startActivity(intent)
        }

        // Observa el LiveData de la lista de personas
        viewModel.personList.observe(this) { personList ->
            personAdapter.updateData(personList)
        }

        // Llamar a fetchPersonList para cargar los datos desde la API
        viewModel.fetchPersonList()
    }

    private fun setupRecyclerView() {
        personAdapter = PersonAdapter(emptyList()) { person ->
            showOptionsDialog(person)
        }
        binding.rvPersonList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = personAdapter
        }
    }

    private fun showOptionsDialog(person: Persona) {
        val options = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Selecciona una opción")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> { // Editar
                        val intent = Intent(this, PersonDetailActivity::class.java)
                        intent.putExtra("person_id", person.id)
                        startActivity(intent)
                    }
                    1 -> { // Eliminar
                        deletePerson(person)
                    }
                }
            }
            .show()
    }

    private fun deletePerson(person: Persona) {
        // Implementación para eliminar el contacto de la API
        // Puedes llamar a un método en PersonaRepository para eliminar
        // Luego, vuelve a cargar la lista para actualizar el RecyclerView
        Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show()
        viewModel.fetchPersonList() // Vuelve a cargar la lista para reflejar los cambios
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchPersonList()  // Vuelve a cargar la lista de contactos al regresar a MainActivity
    }
}
