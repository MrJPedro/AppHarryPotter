package com.example.appharrypotter.model

import java.io.Serializable

data class Personagem(
    var name: String,
    var species: String,
    var house: String,
    var foto: String
): Serializable {

}
