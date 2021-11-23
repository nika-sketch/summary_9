package ge.nlatsabidze.roomexample.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.nlatsabidze.roomexample.ApiData.InformationItem
import ge.nlatsabidze.roomexample.Repository
import ge.nlatsabidze.roomexample.database.UserDataBase
import ge.nlatsabidze.roomexample.databasedao.UserDao
import ge.nlatsabidze.roomexample.network.ApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val repository: Repository
): ViewModel() {


    private var _userInformation = MutableLiveData<List<InformationItem>>()
    val userInformation: LiveData<List<InformationItem>>
        get() = _userInformation

    fun setResult() {

        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                ApiInstance.API.getItems()
            }

            try {
                if (data.isSuccessful && data.body() != null) {
                    _userInformation.postValue(data.body()!!)
                    add()
                }

            } catch (e: Exception) {
                Log.d("error", e.toString())
            }
        }
    }

    
    private fun add() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert()
        }
    }
}