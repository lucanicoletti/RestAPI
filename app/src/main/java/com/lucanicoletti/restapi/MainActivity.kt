package com.lucanicoletti.restapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.lucanicoletti.restapi.ui.theme.RestAPITheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val service = NetworkModule.getApiService()

        setContent {
            val coroutineScope = rememberCoroutineScope()
            var factText by remember {
                mutableStateOf("")
            }
            var loading by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    loading = true
                    try {
                        val response = service.getRandomFact()
                        println(response.text)
                        factText = response.text
                        loading = false
                    } catch (e: Exception) {
                        print(e.stackTrace)
                    }
                }
            }
            RestAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (loading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {

                        Greeting(
                            fact = factText,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(fact: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Text(
            text = "Here's today fact: $fact!",
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RestAPITheme {
        Greeting("Android")
    }
}