package com.example.appharrypotter.api

import com.example.appharrypotter.model.Estudante
import com.example.appharrypotter.model.Feitico
import com.example.appharrypotter.model.Personagem
import com.example.appharrypotter.model.Professor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class PersonagemApiDTO(
    val name: String?,
    val alternate_names: List<String>?,
    val species: String?,
    val house: String?,
    val image: String?,
    val hogwartsStudent: Boolean?
)

data class FeiticoDTO(
    val name: String?,
    val description: String?
)

interface HarryPotterApi {
    @GET("api/character/{id}")
    suspend fun getPersonagemPorId(@Path("id") id: String): List<PersonagemApiDTO>

    @GET("api/characters/staff")
    suspend fun getProfessores(): List<PersonagemApiDTO>

    @GET("api/characters/house/{house}")
    suspend fun getPersonagensDaCasa(@Path("house") house: String): List<PersonagemApiDTO>

    @GET("api/spells")
    suspend fun getFeiticos(): List<FeiticoDTO>
}

object HarryPotterService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hp-api.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(HarryPotterApi::class.java)

    suspend fun buscarPersonagemPorId(id: String): Personagem? {
        val personagem = api.getPersonagemPorId(id).firstOrNull()
        return personagem?.toPersonagem()
    }

    suspend fun buscarProfessorPorNome(nome: String): Professor? {
        val professor = api.getProfessores().firstOrNull {
            it.name.orEmpty().contains(nome, ignoreCase = true)
        }

        return professor?.toProfessor()
    }

    suspend fun listarEstudantesDaCasa(casa: String): List<Estudante> {
        return api.getPersonagensDaCasa(casa)
            .filter { it.hogwartsStudent == true }
            .map { it.toEstudante() }
    }

    suspend fun listarFeiticos(): List<Feitico> {
        return api.getFeiticos().map { it.toFeitico() }
    }

    private fun PersonagemApiDTO.toPersonagem(): Personagem {
        return Personagem(
            name = name.orEmpty(),
            species = species.orEmpty(),
            house = house.orEmpty(),
            fotoURL = image.orEmpty()
        )
    }

    private fun PersonagemApiDTO.toProfessor(): Professor {
        return Professor(
            name = name.orEmpty(),
            alternate_names = alternate_names ?: emptyList(),
            species = species.orEmpty(),
            house = house.orEmpty()
        )
    }

    private fun PersonagemApiDTO.toEstudante(): Estudante {
        return Estudante(
            fotoURL = image.orEmpty(),
            name = name.orEmpty(),
            house = house.orEmpty()
        )
    }

    private fun FeiticoDTO.toFeitico(): Feitico {
        return Feitico(
            name = name.orEmpty(),
            description = description.orEmpty()
        )
    }
}
