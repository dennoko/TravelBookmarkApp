package com.example.travelbookmarkapp.firebase_components

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class loadTravelListViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dataCollection = db.collection("travelData")

    // TravelListに表示するデータの型
    data class TravelListData(
        val title: String,
        val departure: String,
        val depYear: String,
        val depMonth: String,
        val depDay: String,
        val depHour: String,
        val depMinute: String,
        val destination: String,
        val desYear: String,
        val desMonth: String,
        val desDay: String,
        val desHour: String,
        val desMinute: String,
        val todoList: List<String>,
        val documentID: String
    )

    // TravelListに表示するデータを格納するリスト
    private val _travelList = mutableListOf<TravelListData>()
    val travelList : List<TravelListData> = _travelList

    suspend fun loadTravelDataFromFirestore() {
        try {
            val querySnapshot = dataCollection.get().await()

            // firestoreからデータを取得
            for (document in querySnapshot.documents) {
                val title = document.data?.get("title").toString()
                val departure = document.data?.get("departure").toString()
                val depYear = document.data?.get("depYear").toString()
                val depMonth = document.data?.get("depMonth").toString()
                val depDay = document.data?.get("depDay").toString()
                val depHour = document.data?.get("depHour").toString()
                val depMinute = document.data?.get("depMinute").toString()
                val destination = document.data?.get("destination").toString()
                val desYear = document.data?.get("desYear").toString()
                val desMonth = document.data?.get("desMonth").toString()
                val desDay = document.data?.get("desDay").toString()
                val desHour = document.data?.get("desHour").toString()
                val desMinute = document.data?.get("desMinute").toString()
                val todoList = document.data?.get("todoList") as List<String>
                val documentID = document.id

                // _travelListにデータを追加
                _travelList.add(
                    TravelListData(
                        title = title,
                        departure = departure,
                        depYear = depYear,
                        depMonth = depMonth,
                        depDay = depDay,
                        depHour = depHour,
                        depMinute = depMinute,
                        destination = destination,
                        desYear = desYear,
                        desMonth = desMonth,
                        desDay = desDay,
                        desHour = desHour,
                        desMinute = desMinute,
                        todoList = todoList,
                        documentID = documentID
                    )
                )
            }
        } catch (e: Exception) {
            Log.d("Firestore", "エラーが発生しました: $e")
        }
    }
}