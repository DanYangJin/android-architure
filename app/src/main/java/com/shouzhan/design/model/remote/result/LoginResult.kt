package com.shouzhan.design.model.remote.result

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author danbin
 * @version LoginResult.java, v 0.1 2019-02-27 上午12:26 danbin
 */
class LoginResult : Parcelable {

    @SerializedName("accessToken")
    var accessToken: String? = null
    @SerializedName("userId")
    var userId: String? = null
    @SerializedName("headImg")
    var headImg: String? = null
    @SerializedName("username")
    var username: String? = null
    @SerializedName("realName")
    var realName: String? = null
    @SerializedName("flowerName")
    var flowerName: String? = null
    @SerializedName("gender")
    var gender: Int = 0
    @SerializedName("phone")
    var phone: String? = null
    @SerializedName("teamId")
    var teamId: String? = null
    @SerializedName("teamName")
    var teamName: String? = null
    @SerializedName("groupId")
    var groupId: String? = null
    @SerializedName("groupName")
    var groupName: String? = null
    @SerializedName("roleId")
    var roleId: String? = null
    @SerializedName("roleName")
    var roleName: String? = null
    @SerializedName("isJoin")
    var isJoin: Int = 0

    constructor(parcel: Parcel) {
        accessToken = parcel.readString()
        userId = parcel.readString()
        headImg = parcel.readString()
        username = parcel.readString()
        realName = parcel.readString()
        flowerName = parcel.readString()
        gender = parcel.readInt()
        phone = parcel.readString()
        teamId = parcel.readString()
        teamName = parcel.readString()
        groupId = parcel.readString()
        groupName = parcel.readString()
        roleId = parcel.readString()
        roleName = parcel.readString()
        isJoin = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(accessToken)
        parcel.writeString(userId)
        parcel.writeString(headImg)
        parcel.writeString(username)
        parcel.writeString(realName)
        parcel.writeString(flowerName)
        parcel.writeInt(gender)
        parcel.writeString(phone)
        parcel.writeString(teamId)
        parcel.writeString(teamName)
        parcel.writeString(groupId)
        parcel.writeString(groupName)
        parcel.writeString(roleId)
        parcel.writeString(roleName)
        parcel.writeInt(isJoin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginResult> {
        override fun createFromParcel(parcel: Parcel): LoginResult {
            return LoginResult(parcel)
        }

        override fun newArray(size: Int): Array<LoginResult?> {
            return arrayOfNulls(size)
        }
    }

}
