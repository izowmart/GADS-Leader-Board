package ps.room.gadsleaderboard.api

import ps.room.gadsleaderboard.model.Learners
import retrofit2.Response

interface ApiHelper {
    suspend fun getLearningLeaders() : Response<List<Learners>>
}