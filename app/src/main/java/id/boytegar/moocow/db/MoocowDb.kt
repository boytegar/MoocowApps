package id.boytegar.moocow.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import id.boytegar.moocow.db.dao.DaoCart
import id.boytegar.moocow.db.dao.DaoCategory
import id.boytegar.moocow.db.dao.DaoMenuItem
import id.boytegar.moocow.db.dao.DaoTransactions
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.db.entity.Transactions

@Database(entities = [(Category::class) , (MenuItem::class), (Cart::class), (Transactions::class)], version = 1)
abstract class MoocowDb: RoomDatabase() {

    abstract fun DaoCategory(): DaoCategory
    abstract fun DaoMenuItem(): DaoMenuItem
    abstract fun DaoCart(): DaoCart
    abstract fun DaoTransactions(): DaoTransactions

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