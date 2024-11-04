package com.example.practico4api.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practico4api.databinding.PersonListItemBinding
import com.example.practico4api.models.Persona

class PersonAdapter(
    private var personList: List<Persona>,
    private val onItemLongClickListener: (Persona) -> Unit
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    // ViewHolder con View Binding
    inner class PersonViewHolder(private val binding: PersonListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Persona) {
            binding.lblPersonFullName.text = "${person.name} ${person.last_name}"
            binding.lblPersonCity.text = person.city

            // Configura la pulsación larga para cada elemento
            binding.root.setOnLongClickListener {
                onItemLongClickListener(person)
                true
            }
        }
    }

    //infla
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = PersonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }
    //Asigna
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int = personList.size
    //Actualiza
    fun updateData(newPersonList: List<Persona>) {
        personList = newPersonList
        notifyDataSetChanged()
    }
}
