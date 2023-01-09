package model

data class Card(val name: String, val score: Int) : Comparable<Card> {

    companion object {

        fun fromFile(text: String) : Card {
            val params = text.split("\"; ")
            return Card(params[0].replace("\"", ""), params[1].toInt())
        }

    }

    override fun toString(): String {
        return String.format("\"%s\"; %d", name, score)
    }

    override fun compareTo(other: Card): Int = other.score.compareTo(this.score)
}