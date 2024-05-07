package com.example.mysportsapp

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Room

@Entity(tableName = "football_leagues")
data class FootballLeague(
    @PrimaryKey val idLeague: Int,
    val strLeague: String,
    val strSport: String,
    val strLeagueAlternate: String
)


@Dao
interface FootballLeagueDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(leagues: List<FootballLeague>)
}

@Database(entities = [FootballLeague::class,Club::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun footballLeagueDao(): FootballLeagueDao
    abstract fun clubDao(): ClubDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "football-leagues-db"
                ).fallbackToDestructiveMigration() // Add this line for destructive migration
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

