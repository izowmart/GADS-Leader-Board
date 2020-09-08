package ps.room.gadsleaderboard.ui.main

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ps.room.gadsleaderboard.model.SkilledIQLearners
import ps.room.gadsleaderboard.repository.MainRepository
import ps.room.gadsleaderboard.util.DataState
import ps.room.gadsleaderboard.util.NetworkHelper

class SkillIQViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper,
    @Assisted private var savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _skilledLearners = MutableLiveData<DataState<List<SkilledIQLearners>>>()

    val skilledIQLearners: LiveData<DataState<List<SkilledIQLearners>>>
        get() = _skilledLearners

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _skilledLearners.postValue(DataState.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getSkilledIQLearners().let {
                    if (it.isSuccessful) {
                        var data = it.body()
                        Log.d("This data should show", "fetchUsers:  " + it.body())
                        _skilledLearners.postValue(DataState.success(it.body()))

                    } else {
                        _skilledLearners.postValue(DataState.error(it.errorBody().toString(),null))
                    }
                }
            } else {
                _skilledLearners.postValue(DataState.error("No internet connection",null))
            }
        }
    }

}

