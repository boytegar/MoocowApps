package id.boytegar.moocow.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import id.boytegar.moocow.db.dao.DaoCategory
import id.boytegar.moocow.db.dao.DaoMenuItem
import id.boytegar.moocow.db.entity.Catergory
import id.boytegar.moocow.db.entity.MenuItem

@Database(entities = [(Catergory::class) , (MenuItem::class)], version = 1)
abstract class MoocowDb: RoomDatabase() {

    abstract fun DaoCategory(): DaoCategory
    abstract fun DaoMenuItem(): DaoMenuItem

    companion object {
        private var instance: MoocowDb? = null
        @Synchronized
        fun getInstance(context: Context): MoocowDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    MoocowDb::class.java!!, "db_moocow")
                    .fallbackToDestructiveMigration()
                    // .addCallback(roomCallback)
                    .build()
            }
            return instance as MoocowDb
        }


        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //PopulateDbAsyncTask(instance).execute()
            }

        }
    }

}