package com.example.myapplicationdatabase

class UserRepository(private val userDao: UserDao) {
    suspend fun addUser(user: User) = userDao.insertUser(user)
    suspend fun getUsers(): List<User> = userDao.getAllUsers()
}
