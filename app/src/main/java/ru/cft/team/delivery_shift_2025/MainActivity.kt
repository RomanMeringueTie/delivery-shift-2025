package ru.cft.team.delivery_shift_2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.cft.team.delivery_shift_2025.ui.theme.Deliveryshift2025Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Deliveryshift2025Theme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        Text("Hello, CFT!!!")
                    }
                }
            }
        }
    }
}