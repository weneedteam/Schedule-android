package com.playgilround.schedule.client.retrofit;

public class BaseUrl {

    static final String BASE_URL = "http://schedule-prod.ap-northeast-2.elasticbeanstalk.com/api/";

    static final String PATH_SIGN_UP = "accounts/signup";

    static final String PATH_EMAIL_SIGN_IN = "accounts/jwt/create";
    static final String PATH_TOKEN_SIGN_IN = "accounts/jwt/verify";
}
