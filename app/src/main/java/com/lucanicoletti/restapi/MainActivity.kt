package com.lucanicoletti.restapi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lucanicoletti.restapi.data.FactEntity
import com.lucanicoletti.restapi.ui.theme.RestAPITheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val service = NetworkModule.getApiService()

        setContent {
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current
            var factTexts by remember {
                mutableStateOf(listOf<FactEntity>())
            }
            var loading by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    loading = true
                    try {
                        val response = service.getRandomFacts()
                        factTexts = response
                    } catch (e: Exception) {
                        print(e.stackTrace)
                    } finally {
                        loading = false
                    }
                }
            }

            fun checkFactDetailsAndShowToast(id: String) {
                coroutineScope.launch {
                    try {
                        val singleFact =
                            service.getSingleFact(id)
                        withContext(Dispatchers.Main) {
                            Toast
                                .makeText(
                                    context,
                                    "Version: ${singleFact.version}\nUpdated at: ${singleFact.updatedAt}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast
                                .makeText(
                                    context,
                                    "Something went wrong. Try again.",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                }
            }

            RestAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentPadding = PaddingValues(16.dp),
                        ) {
                            items(factTexts) { fact ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            checkFactDetailsAndShowToast(fact.id)
                                        }
                                        .padding(vertical = 8.dp)
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                ) {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = fact.text,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
