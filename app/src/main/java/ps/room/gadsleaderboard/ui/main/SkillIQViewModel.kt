package ps.room.gadsleaderboard.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ps.room.gadsleaderboard.model.SkilledIQLearners
import ps.room.gadsleaderboard.repository.MainRepository
import ps.room.gadsleaderboard.util.DataState
import ps.room.gadsleaderboard.util.DataState.Success
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
            _skilledLearners.postValue(DataState.Loading)
            if (networkHelper.isNetworkConnected()) {
                repository.getSkilledIQLearners().let {
                    if (it.isSuccessful) {
                        _skilledLearners.postValue(Success(it.body()))
                    } else {
                        _skilledLearners.postValue(DataState.Error(it.errorBody().toString()))
                    }
                }
            } else {
                _skilledLearners.postValue(DataState.Error("No internet connection"))
            }
        }
    }

}

