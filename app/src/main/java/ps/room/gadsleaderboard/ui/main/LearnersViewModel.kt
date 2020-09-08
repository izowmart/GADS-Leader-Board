package ps.room.gadsleaderboard.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ps.room.gadsleaderboard.model.Learners
import ps.room.gadsleaderboard.repository.MainRepository
import ps.room.gadsleaderboard.util.DataState
import ps.room.gadsleaderboard.util.NetworkHelper

class LearnersViewModel @ViewModelInject constructor(
    private val networkHelper: NetworkHelper,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _learningLearners = MutableLiveData<DataState<List<Learners>>>()
    val learningLearners: LiveData<DataState<List<Learners>>>
        get() = _learningLearners

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _learningLearners.postValue(DataState.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getLearningLeaders().let {
                    if (it.isSuccessful) {
                        _learningLearners.postValue(DataState.success(it.body()))
                    } else {
                        _learningLearners.postValue(
                            DataState.error(
                                it.errorBody().toString(),
                                null
                            )
                        )
                    }
                }
            } else {
                _learningLearners.postValue(DataState.error("No network connection", null))
            }
        }
    }
}