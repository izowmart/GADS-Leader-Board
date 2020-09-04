package ps.room.gadsleaderboard.repository

import ps.room.gadsleaderboard.api.ApiHelper
import ps.room.gadsleaderboard.model.Learners
import ps.room.gadsleaderboard.model.SkilledIQLearners
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper){
    suspend fun getLearningLeaders(): Response<List<Learners>>{
        return apiHelper.getLearningLeaders()
    }
    suspend fun getSkilledIQLearners(): Response<List<SkilledIQLearners>>{
        return apiHelper.getSkillIQLeaders()
    }
}