package ps.room.gadsleaderboard.util

import ps.room.gadsleaderboard.model.SkilledIQLearners

sealed class DataState<out R:Any> {
    data class Success<out T:Any>(val data: List<SkilledIQLearners>?) : DataState<T>()
    data class Error(val exception: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}