package ge.nlatsabidze.roomexample.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.nlatsabidze.roomexample.ApiData.InformationItem
import ge.nlatsabidze.roomexample.App.Companion.context
import ge.nlatsabidze.roomexample.database.UserDataBase
import ge.nlatsabidze.roomexample.databasedao.UserDao
import ge.nlatsabidze.roomexample.network.ApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeFragmentViewModel: ViewModel() {

    private var _userInformation = MutableLiveData<List<InformationItem>>()
    val userInformation: LiveData<List<InformationItem>>
        get() = _userInformation

    private var userdao: UserDao = UserDataBase.db.userDao()

    private val _readData: LiveData<List<InformationItem>> = userdao.getAll()
    val readData: LiveData<List<InformationItem>> get() = _readData

    fun setResult() {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                ApiInstance.API.getItems()
            }

            try {
                if (data.isSuccessful && data.body() != null) {
                    _userInformation.postValue(data.body()!!)
                    add(data.body()!!)
                }

            } catch (e: Exception) {
                Log.d("error", e.toString())
            }
        }
    }

    private fun add(user: List<InformationItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            userdao.insert(*user.toTypedArray())
        }
    }


}