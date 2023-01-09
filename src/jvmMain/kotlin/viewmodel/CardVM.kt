package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import getCommonFile
import model.Card

class CardVM {

    var data by mutableStateOf(onLoad())

    fun updateData(){
        val list = onLoad()
        data = list
    }


    private fun onLoad(): MutableList<Card> {
        val file = getCommonFile()
        val bufferedReader = file.bufferedReader()
        val list = mutableListOf<Card>()
        var line = bufferedReader.readLine()

        while (line != null) {
            val card = Card.fromFile(line)
            list.add(card)
            line = bufferedReader.readLine()
        }

        list.sort()
        return list
    }
}