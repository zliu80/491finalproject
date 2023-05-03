package com.wordle.client.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * This LocalDBHelper will using sqlite to manage data created by the ap
 */
class LocalDBHelper(context: Context?):SQLiteOpenHelper(context, "translator", null,1) {
    // first table store the supported translate languages
//    val table1 = "create table languages(id integer primary key,language text,name text, supports_formality text)"
    // second table store user favorite translated language
    val table1 = "create table plans(id integer primary key, city_id text, city_name text, user_id text)"
    val table2 = "create table plansdetail(id integer primary key, plans_id integer, attraction_id text, attraction_name text, start_date text, end_date text, description text)"

    // create those table when onCreate happen
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(table1)
        p0?.execSQL(table2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}