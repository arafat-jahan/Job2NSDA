package com.example.job3nsda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.job3nsda.databinding.ItemParticipantBinding



class ParticipantAdapter(private val participants: List<Participant>) : RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>() {

    inner class ParticipantViewHolder(private val binding: ItemParticipantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(participant: Participant) {
            binding.participantName.text = participant.name
            binding.checkInTime.text = "Check-in: ${participant.checkInTime}"
            binding.distance.text = "Distance: ${participant.distance}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val binding = ItemParticipantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParticipantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(participants[position])
    }

    override fun getItemCount(): Int = participants.size
}
