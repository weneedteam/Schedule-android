package com.playgilround.schedule.client.signup

import com.google.gson.JsonObject
import com.playgilround.schedule.client.data.User
import com.playgilround.schedule.client.retrofit.APIClient
import com.playgilround.schedule.client.retrofit.UserAPI
import com.playgilround.schedule.client.signup.model.UserDataModel
import com.playgilround.schedule.client.signup.view.SignUpAdapter

class SignUpPresenter constructor(private val mView: SignUpContract.View, private val mUserDataModel: UserDataModel): SignUpContract.Presenter {

    companion object {
        const val ERROR_SIGN_UP = 0x0001
        const val ERROR_NETWORK_CUSTOM = 0x0002
    }

    private var mUser: User

    init {
        mView.setPresenter(this)

        mUser = User()
    }

    override fun start() {

    }

    override fun onClickNext(position: Int) {
        var check = false

        when (position) {
            SignUpAdapter.TYPE_NAME -> {
                mUser.username = mUserDataModel.getNameField()
                check = mUser.username != null
            }

            SignUpAdapter.TYPE_EMAIL -> {
                mUser.email = mUserDataModel.getEmailField()
                check = mUser.email != null
            }

            SignUpAdapter.TYPE_PASSWORD -> {
                mUser.password = mUserDataModel.getPasswordField()
                check = mUser.password != null
            }

            SignUpAdapter.TYPE_REPEAT_PASSWORD -> {
                mUser.password2 = mUserDataModel.getRepeatPasswordField()
                check = mUser.password2 != null
            }

            SignUpAdapter.TYPE_NICK_NAME -> {
                mUser.nickname = mUserDataModel.getNicknameField()
                check = mUser.nickname != null
            }

            SignUpAdapter.TYPE_BIRTH -> {
                mUser.birth = mUserDataModel.getBirthField()
                check = mUser.birth != null
            }
        }
        mView.fieldCheck(check)
    }

    override fun onClickBack(position: Int) {
        when (position) {
            SignUpAdapter.TYPE_NAME -> mUser.username = null
            SignUpAdapter.TYPE_EMAIL -> mUser.email = null
            SignUpAdapter.TYPE_PASSWORD -> mUser.password = null
            SignUpAdapter.TYPE_REPEAT_PASSWORD -> mUser.password2 = null
            SignUpAdapter.TYPE_NICK_NAME -> mUser.nickname = null
            SignUpAdapter.TYPE_BIRTH -> mUser.birth = null
        }
    }

    override fun signUp() {
        //추후 Disposable (rx) 변경 예정
        val retrofit = APIClient.getClient()
        val userAPI = retrofit.create(UserAPI::class.java)

        mUser.password = User.base64Encoding(mUser.password)
        mUser.password2 = User.base64Encoding(mUser.password2)

        val jsonObject = JsonObject()
        val userJson = JsonObject()

        userJson.addProperty("username", mUser.username)
        userJson.addProperty("nickname", mUser.nickname)
        userJson.addProperty("email", mUser.email)
        userJson.addProperty("password", mUser.password)
        userJson.addProperty("password2", mUser.password2)

        jsonObject.add("user", userJson)
        jsonObject.addProperty("birth", mUser.birth)
        jsonObject.addProperty("language", mUser.language)

        mView.signUpComplete()
        /*userAPI.signUp(jsonObject).enqueue(new Callback<ResponseMessage>() {
          @Override
          public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
              if (response.isSuccessful() && response.body() != null) {
                  mView.singUpComplete();
              } else {
                  CrashlyticsCore.getInstance().log(response.toString());
                  mView.signUpError(ERROR_SIGN_UP);
              }
          }

          @Override
          public void onFailure(Call<ResponseMessage> call, Throwable t) {
              CrashlyticsCore.getInstance().log(t.toString());
              mView.signUpError(ERROR_NETWORK_CUSTOM);
          }
      });*/
    }



}