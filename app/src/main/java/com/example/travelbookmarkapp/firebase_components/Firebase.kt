package com.example.travelbookmarkapp.firebase_components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dataCollection = db.collection("travelData")

    // firestore登録した旅行データを保存する関数
    suspend fun saveTravelDataToFirestore(title: String, departure: String, depYear: String,
                                          depMonth: String, depDay: String, depHour: String,
                                          depMinute: String, destination: String, desYear: String,
                                          desMonth: String, desDay: String, desHour: String,
                                          desMinute: String, todoList: List<String>
    ) {
        try {
            val document = dataCollection.document("$depYear$depMonth$depDay$depHour$depMinute$title")
            document.set(
                hashMapOf(
                    "title" to title,
                    "departure" to departure,
                    "depYear" to depYear,
                    "depMonth" to depMonth,
                    "depDay" to depDay,
                    "depHour" to depHour,
                    "depMinute" to depMinute,
                    "destination" to destination,
                    "desYear" to desYear,
                    "desMonth" to desMonth,
                    "desDay" to desDay,
                    "desHour" to desHour,
                    "desMinute" to desMinute,
                    "todoList" to todoList
                )
            ).await()
        } catch (e: Exception) {
            Log.d("Firestore", "エラーが発生しました: $e")
        }
    }


}