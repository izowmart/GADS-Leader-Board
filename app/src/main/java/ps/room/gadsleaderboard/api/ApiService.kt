package ps.room.gadsleaderboard.api

import ps.room.gadsleaderboard.model.Learners
import ps.room.gadsleaderboard.util.DataState
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/api/hours")
    suspend fun getLearningLeaders() :Response<List<Learners>>

}