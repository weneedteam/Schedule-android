package com.playgilround.schedule.client.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class Friend: RealmObject() {

    @PrimaryKey
    var friendId: Int = 0
    var nickName: String = ""
    var name: String = ""
    var image: Byte = 0
}