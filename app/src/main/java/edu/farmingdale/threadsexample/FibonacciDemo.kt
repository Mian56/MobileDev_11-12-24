package edu.farmingdale.threadsexample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

@Composable
fun FibonacciDemoWithCouroutine() {
    var answer by remember { mutableStateOf("") }
    var textInput by remember { mutableStateOf("40") }
    var isLoading by remember { mutableStateOf(false) }
    // Coroutine scope for launching background tasks
    val coroutineScope = rememberCoroutineScope()

    Column (modifier = Modifier.padding(40.dp)){
        Row {
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Number?") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Button(onClick = {
                val num = textInput.toLongOrNull() ?: 0
                isLoading = true
                coroutineScope.launch {
                val fibNumber = calculateFibonacci(num)
                answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
            }
            }) {
                Text("Fibonacci")
            }
        }

//        Text("Result: $answer")

        // Displaying loading state while computation is happening
        if (isLoading) {
            Text("Calculating...", modifier = Modifier.padding(top = 16.dp))
        } else {
            Text("Result: $answer", modifier = Modifier.padding(top = 16.dp))
        }
    }
}


// Function to calculate Fibonacci number in a background thread
suspend fun calculateFibonacci(n: Long): Long {
    return withContext(Dispatchers.Default) {  // Dispatch the task to a background thread
        fibonacci(n)
    }
}
fun fibonacci(n: Long): Long {
    return if (n <= 1) n else fibonacci(n - 1) + fibonacci(n - 2)
}
