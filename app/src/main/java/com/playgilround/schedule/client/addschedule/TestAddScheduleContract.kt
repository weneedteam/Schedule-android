package com.playgilround.schedule.client.addschedule

import com.playgilround.schedule.client.base.BasePresenter
import com.playgilround.schedule.client.base.BaseView
import com.playgilround.schedule.client.data.FriendList
import com.playgilround.schedule.client.model.Friend
import io.realm.RealmResults

interface TestAddScheduleContract {

    interface View: BaseView<Presenter> {
        fun fieldCheck(check: Boolean)

        fun updateFriendInfo(list: FriendList)

        fun setLocalFriendData(data: RealmResults<Friend>)
    }

    interface Presenter: BasePresenter {
        fun onClickNext(position: Int)

        fun onClickBack(position: Int)

        fun scheduleSave()

        fun getFriendList()

        fun rxUnSubscribe()

        fun getLocalFriendList()
    }
}