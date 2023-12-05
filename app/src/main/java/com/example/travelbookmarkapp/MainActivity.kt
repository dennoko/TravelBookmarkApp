package com.example.travelbookmarkapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelbookmarkapp.Room.Database_marker
import com.example.travelbookmarkapp.network.MarsViewModel
import com.example.travelbookmarkapp.screen.ConfirmEditSchedule
import com.example.travelbookmarkapp.screen.ConfirmSchedule
import com.example.travelbookmarkapp.screen.DirectionMap
import com.example.travelbookmarkapp.screen.EditSchedule
import com.example.travelbookmarkapp.screen.InputSchedule
import com.example.travelbookmarkapp.screen.ScheduleDetail
import com.example.travelbookmarkapp.screen.TestScreen
import com.example.travelbookmarkapp.screen.TravelList
import com.example.travelbookmarkapp.screen.photoOnMap
import com.example.travelbookmarkapp.ui.theme.TravelBookmarkAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var appDatabase: Database_marker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = applicationContext
        appDatabase = Database_marker.getDB(this)
        setContent {
            TravelBookmarkAppTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //画面遷移用のコード
                    val navController = rememberNavController()
                    val marsViewModel: MarsViewModel by viewModels()

                    NavHost(navController = navController, startDestination = "test") {
                        composable("test") { TestScreen(navController = navController) }
                        composable("travellist") { TravelList(navController = navController) }
                        composable("inputschedule") { InputSchedule(navController = navController) }
                        composable(
                            "confirmschedule/{title}/{departure}/{depYear}/{depMonth}/{depDay}/{depHour}/{depMinute}/{destination}/{desYear}/{desMonth}/{desDay}/{desHour}/{desMinute}/{todoList}",
                            arguments = listOf(
                                navArgument("title") { defaultValue = "" },
                                navArgument("departure") { defaultValue = "" },
                                navArgument("depYear") { defaultValue = "" },
                                navArgument("depMonth") { defaultValue = "" },
                                navArgument("depDay") { defaultValue = "" },
                                navArgument("depHour") { defaultValue = "" },
                                navArgument("depMinute") { defaultValue = "" },
                                navArgument("destination") { defaultValue = "" },
                                navArgument("desYear") { defaultValue = "" },
                                navArgument("desMonth") { defaultValue = "" },
                                navArgument("desDay") { defaultValue = "" },
                                navArgument("desHour") { defaultValue = "" },
                                navArgument("desMinute") { defaultValue = "" },
                                navArgument("todoList") { defaultValue = "" },
                            )
                        ) { ConfirmSchedule(navController = navController) }
                        composable(
                            "scheduledetail/{title}/{departure}/{depYear}/{depMonth}/{depDay}/{depHour}/{depMinute}/{destination}/{desYear}/{desMonth}/{desDay}/{desHour}/{desMinute}/{todoList}/{documentID}",
                            arguments = listOf(
                                navArgument("title") { defaultValue = "" },
                                navArgument("departure") { defaultValue = "" },
                                navArgument("depYear") { defaultValue = "" },
                                navArgument("depMonth") { defaultValue = "" },
                                navArgument("depDay") { defaultValue = "" },
                                navArgument("depHour") { defaultValue = "" },
                                navArgument("depMinute") { defaultValue = "" },
                                navArgument("destination") { defaultValue = "" },
                                navArgument("desYear") { defaultValue = "" },
                                navArgument("desMonth") { defaultValue = "" },
                                navArgument("desDay") { defaultValue = "" },
                                navArgument("desHour") { defaultValue = "" },
                                navArgument("desMinute") { defaultValue = "" },
                                navArgument("todoList") { defaultValue = "" },
                                navArgument("documentID") { defaultValue = "" }
                            )
                        ) { ScheduleDetail(navController = navController) }
                        composable("r_GoogleMap"){ photoOnMap(context = context, appDatabase)}
                        composable("II_GoogleMap"){ DirectionMap(marsViewModel.marsUiState)}
                        composable("editschedule/{title}/{departure}/{depYear}/{depMonth}/{depDay}/{depHour}/{depMinute}/{destination}/{desYear}/{desMonth}/{desDay}/{desHour}/{desMinute}/{todoList}/{documentID}",
                            arguments = listOf(
                                navArgument("title") { defaultValue = "" },
                                navArgument("departure") { defaultValue = "" },
                                navArgument("depYear") { defaultValue = "" },
                                navArgument("depMonth") { defaultValue = "" },
                                navArgument("depDay") { defaultValue = "" },
                                navArgument("depHour") { defaultValue = "" },
                                navArgument("depMinute") { defaultValue = "" },
                                navArgument("destination") { defaultValue = "" },
                                navArgument("desYear") { defaultValue = "" },
                                navArgument("desMonth") { defaultValue = "" },
                                navArgument("desDay") { defaultValue = "" },
                                navArgument("desHour") { defaultValue = "" },
                                navArgument("desMinute") { defaultValue = "" },
                                navArgument("todoList") { defaultValue = "" },
                                navArgument("documentID") { defaultValue = "" }
                            )
                        ) { EditSchedule(navController = navController) }
                        composable("confirmeditschedule/{title}/{departure}/{depYear}/{depMonth}/{depDay}/{depHour}/{depMinute}/{destination}/{desYear}/{desMonth}/{desDay}/{desHour}/{desMinute}/{todoList}/{documentID}",
                            arguments = listOf(
                                navArgument("title") { defaultValue = "" },
                                navArgument("departure") { defaultValue = "" },
                                navArgument("depYear") { defaultValue = "" },
                                navArgument("depMonth") { defaultValue = "" },
                                navArgument("depDay") { defaultValue = "" },
                                navArgument("depHour") { defaultValue = "" },
                                navArgument("depMinute") { defaultValue = "" },
                                navArgument("destination") { defaultValue = "" },
                                navArgument("desYear") { defaultValue = "" },
                                navArgument("desMonth") { defaultValue = "" },
                                navArgument("desDay") { defaultValue = "" },
                                navArgument("desHour") { defaultValue = "" },
                                navArgument("desMinute") { defaultValue = "" },
                                navArgument("todoList") { defaultValue = "" },
                                navArgument("documentID") { defaultValue = "" }
                            )
                        ) { ConfirmEditSchedule(navController = navController) }
                    }
                }
            }
        }
    }
}//うおおおおおおおおおおおおおおおおおおおおお
//俺は人間をやめるぞおおおおおお！！！！！
// ジョジョーーーーーー！！！！



