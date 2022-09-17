package com.jasmeet.emailvalid.repository

import com.jasmeet.emailvalid.roomDB.GroceryDatabase
import com.jasmeet.emailvalid.roomDB.GroceryItems

class GroceryRepository(private val db: GroceryDatabase) {

    suspend fun insert(items: GroceryItems) = db.getGroceryDao().insert(items)
    suspend fun delete(items: GroceryItems) = db.getGroceryDao().delete(items)

    fun getAllItems() = db.getGroceryDao().getAllGroceryItems()

}