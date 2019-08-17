package com.playgilround.schedule.client.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseResponse {
    @SerializedName("status")
    @Expose
    var code: String = ""

    @SerializedName("message")
    @Expose
    var message: String = ""

}