package com.playgilround.schedule.client.retrofit;

public class BaseUrl {

    // static final String BASE_URL = "http://192.168.0.5:8000/";
    static final String BASE_URL = "http://172.16.6.234:8000/";
    // static final String BASE_URL = "http://schedule-prod.ap-northeast-2.elasticbeanstalk.com/api/v1/";

    static final String PATH_SIGN_UP = "v1/user/user/";

    static final String PATH_EMAIL_SIGN_IN = "api-token-auth/";
    static final String PATH_TOKEN_SIGN_IN = "api-token-verify/";

    static final String PATH_USER_SEARCH = "v1/user/user/search";

    public static final String PARAM_SIGN_IN_EMAIL = "email";
    public static final String PARAM_SIGN_IN_PASSWORD = "password";
    public static final String PARAM_SIGN_IN_TOKEN = "token";

    static final String PARAM_USER_NAME_SEARCH = "name";

}
