package com.example.picviewkontlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ImageDatabase"
        private const val TABLE_IMAGES = "ImagesTable"

        const val KEY_URI = "uri"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val table = (
                "CREATE TABLE $TABLE_IMAGES (" +
                        "$KEY_URI TEXT PRIMARY KEY" +
                        ")"
                )

        db?.execSQL(table)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_IMAGES")
        onCreate(db)
    }
    fun getAllImages(): ArrayList<Uri> {
        val uris = ArrayList<Uri>()
        val selectQuery = "SELECT $KEY_URI FROM $TABLE_IMAGES"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val uriString = cursor.getString(cursor.getColumnIndex(KEY_URI))
                    val uri = Uri.parse(uriString)
                    uris.add(uri)
                } while (cursor.moveToNext())
            }
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        } finally {
            cursor?.close()
        }

        return uris
    }

    fun addImage(imageItem: ImageItem): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_URI, imageItem.uri.toString())

        val success = db.insert(TABLE_IMAGES, null, contentValues)
        db.close()
        return success
    }
    fun deleteImageByUri(uri: Uri): Int {
        val db = this.writableDatabase
        val whereClause = "$KEY_URI = ?"
        val whereArgs = arrayOf(uri.toString())
        val deletedRows = db.delete(TABLE_IMAGES, whereClause, whereArgs)
        db.close()
        return deletedRows
    }
    fun isImageInDatabase(uri: Uri): Boolean {
        val uriString = uri.toString()
        val db = this.readableDatabase
        val selectQuery = "SELECT $KEY_URI FROM $TABLE_IMAGES WHERE $KEY_URI = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(uriString))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
}
