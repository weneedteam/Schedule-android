package com.playgilround.schedule.client.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
