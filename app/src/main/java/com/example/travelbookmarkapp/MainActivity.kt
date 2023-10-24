package com.example.travelbookmarkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.travelbookmarkapp.ui.theme.TravelBookmarkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelBookmarkAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //画面遷移用のコード
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            // MainScreen.ktの@Composable関数を呼び出す
                        }
                    }
                }
            }
        }
    }
}//うおおおおおおおおおおおおおおおおおおおおお
//俺は人間をやめるぞおおおおおお！！！！！
// ジョジョーーーーーー！！！！

