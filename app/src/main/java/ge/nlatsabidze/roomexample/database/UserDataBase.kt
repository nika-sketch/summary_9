package ge.nlatsabidze.roomexample.database

import androidx.room.*
import ge.nlatsabidze.roomexample.ApiData.InformationItem
import ge.nlatsabidze.roomexample.App
import ge.nlatsabidze.roomexample.databasedao.UserDao

@Database(entities = [InformationItem::class], version = 1)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
}