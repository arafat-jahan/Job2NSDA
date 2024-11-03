package com.example.job3nsda

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CreateEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        val eventNameField: EditText = findViewById(R.id.editTextEventName)
        val eventLocationField: EditText = findViewById(R.id.editTextEventLocation)
        val eventTimeField: EditText = findViewById(R.id.editTextEventTime)
        val createEventButton: Button = findViewById(R.id.btnCreateEvent)

        createEventButton.setOnClickListener {
            val name = eventNameField.text.toString()
            val location = eventLocationField.text.toString()
            val time = eventTimeField.text.toString()

            if (name.isNotEmpty() && location.isNotEmpty() && time.isNotEmpty()) {
                createEvent(name, location, time)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createEvent(name: String, location: String, time: String) {
        val db = FirebaseFirestore.getInstance()
        val event = hashMapOf(
            "name" to name,
            "location" to location,
            "time" to time
        )

        db.collection("events")
            .add(event)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Event created successfully!", Toast.LENGTH_SHORT).show()
                // You can also navigate to another screen if needed
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to create event: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
