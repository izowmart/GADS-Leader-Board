package ps.room.gadsleaderboard.api

import ps.room.gadsleaderboard.model.Learners
import ps.room.gadsleaderboard.model.SkilledIQLearners
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/api/hours")
    suspend fun getLearningLeaders(): Response<List<Learners>>

    @GET("/api/skilliq")
    suspend fun getSkillIQLeaders(): Response<List<SkilledIQLearners>>

    @POST("https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    suspend fun submitProject(
        @Field("entry.1824927963") emailAddress :String,
        @Field("entry.1877115667") firstName :String,
        @Field("entry.2006916086") lastName :String,
        @Field("entry.284483984") linkToProject :String
    ): Call<Void>

}