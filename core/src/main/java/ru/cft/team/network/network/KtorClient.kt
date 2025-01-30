package ru.cft.team.network.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import ru.cft.team.network.model.PackageTypeResponseModel
import ru.cft.team.network.model.PointResponseModel
import javax.inject.Inject

class KtorClient @Inject constructor() {

    private val BASE_URL = "https://shift-intensive.ru/api/delivery/"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = Logger.DEFAULT
        }
    }

    suspend fun getPoints(): PointResponseModel = client.get(BASE_URL + "points") {
        contentType(ContentType.Application.Json)
    }.body()

    suspend fun getPackageTypes(): PackageTypeResponseModel =
        client.get(BASE_URL + "package/types") {
            contentType(ContentType.Application.Json)
        }.body()
}