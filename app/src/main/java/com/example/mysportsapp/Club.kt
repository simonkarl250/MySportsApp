package com.example.mysportsapp

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "clubs")
data class Club(
   @PrimaryKey val idTeam: Int,
    val name: String,
    val shortName: String,
    val alternateName: String,
    val formedYear: String,
    val league: String,
    val idLeague: String,
    val stadium: String,
    val keywords: String,
    val stadiumThumb: String,
    val stadiumLocation: String,
    val stadiumCapacity: String,
    val website: String,
    val teamJersey: String,
    val teamLogo: String
)
@Dao
interface ClubDao {

 @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(clubs: List<Club>)

 @Query("SELECT * FROM clubs WHERE LOWER(name) LIKE '%' || LOWER(:searchQuery) || '%' OR LOWER(league) LIKE '%' || LOWER(:searchQuery) || '%'")
 suspend fun searchClubs(searchQuery: String): List<Club>

}
//
//@Database(entities = [Club::class], version = 1)
//abstract class App2Database : RoomDatabase() {
//    abstract fun clubDao(): ClubDao
//
//    companion object {
//        private var INSTANCE: App2Database? = null
//
//        fun getDatabase(context: Context): App2Database {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    App2Database::class.java,
//                    "app_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
