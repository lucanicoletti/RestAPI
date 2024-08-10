package com.lucanicoletti.restapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lucanicoletti.restapi.ui.theme.RestAPITheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val service = NetworkModule.getApiService()

        setContent {
            val coroutineScope = rememberCoroutineScope()
            var loading by remember {
                mutableStateOf(false)
            }
            var generatedPw by remember {
                mutableStateOf("")
            }

            RestAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        var useCapitals by rememberSaveable {
                            mutableStateOf(false)
                        }
                        var pwLength by rememberSaveable {
                            mutableStateOf("")
                        }
                        var useNumbers by rememberSaveable {
                            mutableStateOf(false)
                        }
                        var useSpecialChars by rememberSaveable {
                            mutableStateOf(false)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(modifier = Modifier.weight(.8f), text = "Password length")
                            OutlinedTextField(
                                modifier = Modifier
                                    .width(IntrinsicSize.Min)
                                    .weight(0.2f),
                                value = pwLength,
                                onValueChange = {
                                    pwLength = it
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Use numbers")
                            Checkbox(checked = useNumbers, onCheckedChange = {
                                useNumbers = it
                            })
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Use capitals")
                            Checkbox(checked = useCapitals, onCheckedChange = {
                                useCapitals = it
                            })
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Use special chars")
                            Checkbox(checked = useSpecialChars, onCheckedChange = {
                                useSpecialChars = it
                            })
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                coroutineScope.launch {
                                    loading = true
                                    try {
                                        val randomPassword = service.generatePassword(
                                            length = pwLength.toInt(),
                                            hasDigits = useNumbers,
                                            hasUpperCase = useCapitals,
                                            hasSpecials = useSpecialChars
                                        )
                                        generatedPw = randomPassword
                                    } catch (e: Exception) {
                                        Log.e("ERR", e.localizedMessage, e)
                                    } finally {
                                        loading = false
                                    }
                                }
                            }) {
                                Text("Generate")
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (loading) {
                                CircularProgressIndicator()
                            } else {
                                OutlinedTextField(
                                    value = generatedPw,
                                    onValueChange = {},
                                    enabled = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
