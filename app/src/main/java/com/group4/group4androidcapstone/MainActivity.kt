package com.group4.group4androidcapstone

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color.GRAY
import android.graphics.Color.WHITE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.group4.group4androidcapstone.core.structure.CapstoneApp
import com.group4.group4androidcapstone.features.HomeScreen.presentation.HomeContent
import com.group4.group4androidcapstone.features.HomeScreen.presentation.HomeScreen
import com.group4.group4androidcapstone.ui.theme.Group4AndroidCapstoneTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Group4Application : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Group4AndroidCapstoneTheme {

                Surface(modifier = Modifier.fillMaxSize()) {
                   CapstoneApp()

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Group4AndroidCapstoneTheme {
        Greeting("Android")
    }
}