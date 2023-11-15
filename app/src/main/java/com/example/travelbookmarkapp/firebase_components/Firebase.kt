package com.example.travelbookmarkapp.firebase_components

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dataCollection = db.collection("travelData")

    data class travelListData(
        val title: String,
        val depYear: String,
        val depMonth: String,
        val depDay: String,
        val depHour: String,
        val depMinute: String
    )
    private val _travelList = mutableListOf<travelListData>()
    val travelList : List<travelListData> = _travelList

    // firestoreにデータを保存する関数
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

    // firestoreからリストを表示するためのデータを取得する関数
    suspend fun loadTravelDataFromFirestore() {
        try {
            val querySnapshot = dataCollection.get().await()

            // firestoreからデータを取得
            for (document in querySnapshot.documents) {
                val title = document.data?.get("title").toString()
                val depYear = document.data?.get("depYear").toString()
                val depMonth = document.data?.get("depMonth").toString()
                val depDay = document.data?.get("depDay").toString()
                val depHour = document.data?.get("depHour").toString()
                val depMinute = document.data?.get("depMinute").toString()

                // _travelListにデータを追加
                _travelList.add(travelListData(title, depYear, depMonth, depDay, depHour, depMinute))
            }
        } catch (e: Exception) {

        }
    }
}