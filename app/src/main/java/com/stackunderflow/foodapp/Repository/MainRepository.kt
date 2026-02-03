package com.stackunderflow.foodapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stackunderflow.foodapp.Domain.BannerModel

class MainRepository {
    private val firebaseDatabase= FirebaseDatabase.getInstance("https://myhelper-1b32c-default-rtdb.asia-southeast1.firebasedatabase.app/")

    fun loadBanner(): LiveData<MutableList<BannerModel>>{
        val listData= MutableLiveData<MutableList<BannerModel>>()
        val ref=firebaseDatabase.getReference("Banners")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                println("Firebase snapshot exists: ${snapshot.exists()}")
                println("Firebase children count: ${snapshot.childrenCount}")

                val list=mutableListOf<BannerModel>()
                for (childSnapshot in snapshot.children){
                    val item=childSnapshot.getValue(BannerModel::class.java)
                    println("Loaded banner item: $item")
                    item?.let{list.add(it)}
                }
                listData.value=list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return listData
    }
}