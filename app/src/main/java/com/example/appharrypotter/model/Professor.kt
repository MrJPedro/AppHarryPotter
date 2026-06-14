package com.example.appharrypotter.model

import java.io.Serializable

data class Professor(
    var name: String,
    var alternate_names: List<String>,
    var species: String,
    var house : String
): Serializable {

}