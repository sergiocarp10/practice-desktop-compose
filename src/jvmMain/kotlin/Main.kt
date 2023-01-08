// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {

    var name by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }
    var scoreNumber by remember { mutableStateOf<Int?>(null) }
    val enabled = scoreNumber != null && name.isNotBlank()

    MaterialTheme {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = { Text("Nombre") }
            )

            OutlinedTextField(
                value = score,
                onValueChange = {
                    val result = updateScoreInput(it)
                    score = result.first?: score
                    scoreNumber = result.second
                },
                label = { Text("Puntaje") }
            )

            Button(
                onClick = { },
                enabled = enabled
            ) {
                Text("Guardar")
            }
        }


    }
}

private fun updateScoreInput(input: String) : Pair<String?, Int?> {
    if (input.isBlank() || input == "-"){
        return Pair(input, null)
    }

    val aux = input.toIntOrNull()
    if (aux != null) return Pair(input, aux)

    return Pair(null, null)
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Desktop Compose Practice"
    ) {
        App()
    }
}
