package com.nice.myapplication.localDatabase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.nice.myapplication.model.ListMovie
import com.nice.myapplication.model.Movie
import com.nice.myapplication.model.PopularMovie
import com.nice.myapplication.model.Result
import com.nice.myapplication.repo.ListPopularMovieRepo

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "MovieDetail"
val COL_TITLE = "title"
val COL_ID = "id"
val COL_MOVIE_ID = "movieid"
val COL_POSTER = "posterPath"
val COL_OVERVIEW = "overview"
class DatabaseHandler (var context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
            val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_MOVIE_ID + " INTEGER," +
                    COL_POSTER + " VARCHAR(256)," +
                    COL_OVERVIEW + " VARCHAR(256)," +
                    COL_TITLE + " VARCHAR(256))"
            db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun insertData (list_movie: Movie){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_OVERVIEW,list_movie.overview)
        cv.put(COL_POSTER,list_movie.poster_path)
        cv.put(COL_TITLE,list_movie.title)
        cv.put(COL_MOVIE_ID,list_movie.id)
        val result = db.insert(TABLE_NAME,null,cv)
        if(result == (-1).toLong()){
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"Movie was Added to Database", Toast.LENGTH_SHORT).show()
        }
    }
    @SuppressLint("Recycle")
    fun count():Int{
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        return result.count
    }
    fun readData() : MutableList<PopularMovie>{
        val list : MutableList<PopularMovie> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val movie = PopularMovie()
                movie.title = result.getString(result.getColumnIndex(COL_TITLE))
                movie.poster = result.getString(result.getColumnIndex(COL_POSTER))
                movie.overview = result.getString(result.getColumnIndex(COL_OVERVIEW))
                movie.id = result.getString(result.getColumnIndex(COL_MOVIE_ID)).toInt()
                list.add(movie)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

}