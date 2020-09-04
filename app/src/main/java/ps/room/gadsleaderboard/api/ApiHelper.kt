package ps.room.gadsleaderboard.api

import ps.room.gadsleaderboard.model.Learners
import ps.room.gadsleaderboard.model.SkilledIQLearners
import retrofit2.Response

interface ApiHelper {
    suspend fun getLearningLeaders() : Response<List<Learners>>
    suspend fun getSkillIQLeaders() :Response<List<SkilledIQLearners>>
}