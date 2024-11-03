package com.example.job3nsda

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.job3nsda.databinding.ActivityEventDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailsBinding
    private lateinit var participantAdapter: ParticipantAdapter
    private val participantList = mutableListOf<Participant>()
    private val firestore = FirebaseFirestore.getInstance()
    private val eventViewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get event data from the intent or ViewModel
        val eventId = intent.getStringExtra("EVENT_ID") ?: ""
        loadEventDetails(eventId)

        // Set up RecyclerView
        participantAdapter = ParticipantAdapter(participantList)
        binding.participantsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.participantsRecyclerView.adapter = participantAdapter

        // Load participants from Firestore
        loadParticipants(eventId)
    }

    private fun loadEventDetails(eventId: String) {
        firestore.collection("events").document(eventId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val eventName = document.getString("name") ?: "Unknown Event"
                    val eventLocation = document.getString("location") ?: "Unknown Location"
                    val eventTime = document.getString("time") ?: "Unknown Time"

                    binding.eventNameTextView.text = eventName
                    binding.eventDetailsTextView.text = "Location: $eventLocation\nTime: $eventTime"
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors here
            }
    }

    private fun loadParticipants(eventId: String) {
        firestore.collection("events").document(eventId).collection("participants")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val name = document.getString("name") ?: "Unknown"
                    val checkInTime = document.getString("checkInTime") ?: "Unknown"
                    val distance = document.getString("distance") ?: "Unknown"

                    participantList.add(Participant(name, checkInTime, distance))
                }
                participantAdapter.notifyDataSetChanged() // Notify adapter of data changes
            }
            .addOnFailureListener { exception ->
                // Handle any errors here
            }
    }
}
