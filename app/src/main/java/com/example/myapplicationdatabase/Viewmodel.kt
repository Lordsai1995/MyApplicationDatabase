package com.example.myapplicationdatabase

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "my_database"
    ).build()

    private val userDao = db.userDao()
    private val repository = UserRepository(userDao)

    var userList by mutableStateOf<List<User>>(emptyList())
        private set

    fun loadUsers() {
        viewModelScope.launch {
            userList = repository.getUsers()
        }
    }

    fun addUser(name: String) {
        viewModelScope.launch {
            repository.addUser(User(name = name))
            loadUsers()
        }
    }
}
