package com.example.travelbookmarkapp.firebase_components

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DeleteTravelDataViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dataCollection = db.collection("travelData")

    suspend fun deleteTravelDataFromFirestore(documentID: String) {
        try {
            dataCollection.document(documentID).delete().await()
        } catch (e: Exception) {
            Log.d("Firestore", "エラーが発生しました: $e")
        }
    }
}