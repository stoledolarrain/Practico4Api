package com.example.practico4api

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupEventListeners()

        // Observa la lista filtrada de personas
        viewModel.filteredPersonList.observe(this) { personList ->
            personAdapter.updateData(personList)
        }

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

    private fun setupEventListeners() {
        binding.fabAddPerson.setOnClickListener {
            val intent = Intent(this, PersonDetailActivity::class.java)
            startActivity(intent)
        }

        //Buscar
        binding.txtSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SEARCH", newText ?: "")
                viewModel.searchByNameAndLastName(this@MainActivity, newText ?: "", newText ?: "")
                return false
            }
        })
    }

    private fun showOptionsDialog(person: Persona) {
        val options = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Selecciona una opción")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(this, PersonDetailActivity::class.java)
                        intent.putExtra("person_id", person.id)
                        startActivity(intent)
                    }
                    1 -> deletePerson(person)
                }
            }
            .show()
    }

    private fun deletePerson(person: Persona) {
        Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show()
        viewModel.fetchPersonList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchPersonList()
    }
}
