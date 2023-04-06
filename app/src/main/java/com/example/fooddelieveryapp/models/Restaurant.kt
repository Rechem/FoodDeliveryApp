package com.example.fooddelieveryapp.models

import android.os.Parcel
import android.os.Parcelable

data class Restaurant(
    val name: String, val logo: Int, val location: String, val cuisineType:String,
    var rating: Float, var reviews: Int, val phone: String, val email:String,
    val fbMob: String, val fbWeb: String, val igMob: String, val igWeb: String, val adresse:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(logo)
        parcel.writeString(location)
        parcel.writeString(cuisineType)
        parcel.writeFloat(rating)
        parcel.writeInt(reviews)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(fbMob)
        parcel.writeString(fbWeb)
        parcel.writeString(igMob)
        parcel.writeString(igWeb)
        parcel.writeString(adresse)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}
