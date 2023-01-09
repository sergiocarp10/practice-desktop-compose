// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.Card
import viewmodel.CardVM
import java.io.File
import java.io.FileOutputStream

@Composable
@Preview
fun App() {

    MaterialTheme {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterHorizontally)
        ) {

            val vm = remember { CardVM() }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.width(400.dp)
            ) {

                var name by remember { mutableStateOf("") }
                var score by remember { mutableStateOf("") }
                var scoreNumber by remember { mutableStateOf<Int?>(null) }
                val enabled = scoreNumber != null && name.isNotBlank()

                Text(
                    text = "Ingreso de datos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") }
                )

                OutlinedTextField(
                    value = score,
                    onValueChange = {
                        val result = updateScoreInput(it)
                        score = result.first ?: score
                        scoreNumber = result.second
                    },
                    label = { Text("Puntaje") }
                )

                Button(
                    onClick = {
                        val card = Card(name, scoreNumber!!)
                        onSave(card)
                        vm.updateData()
                    },
                    enabled = enabled
                ) {
                    Text("Guardar")
                }
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.width(400.dp)
            ) {

                Text(
                    text = "Datos guardados",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                LazyColumn(modifier = Modifier.width(200.dp)) {
                    items(vm.data) {
                        Text(
                            text = "${it.name}: ${it.score}",
                            color = if (it.score < 0) Color.Red else Color.Blue
                        )

                        Divider(thickness = 8.dp)
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun inputColumn() {


}

@Preview
@Composable
fun dataColumn() {

}

private fun updateScoreInput(input: String): Pair<String?, Int?> {
    if (input.isBlank() || input == "-") {
        return Pair(input, null)
    }

    val aux = input.toIntOrNull()
    if (aux != null) return Pair(input, aux)

    return Pair(null, null)
}



private fun onSave(card: Card) {
    val file = getCommonFile()

    val bufferedWriter = FileOutputStream(file, true).bufferedWriter()
    bufferedWriter.append(card.toString() + "\n")
    bufferedWriter.close()
}

fun getCommonFile(): File {
    val file = File("data.txt")
    if (!file.exists()) file.createNewFile()
    return file
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Desktop Compose Practice"
    ) {
        App()
    }
}
