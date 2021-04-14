package com.LH1100775.brainblastquizapp

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId

data class Quiz (
    @DocumentId
    var quiz_id: String?=null,
    var name: String?=null,
    var questions: Long?=null,
    var desc: String?=null,
    var image: String?=null
        ) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(quiz_id)
        parcel.writeString(name)
        parcel.writeValue(questions)
        parcel.writeString(desc)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quiz> {
        override fun createFromParcel(parcel: Parcel): Quiz {
            return Quiz(parcel)
        }

        override fun newArray(size: Int): Array<Quiz?> {
            return arrayOfNulls(size)
        }
    }
}
