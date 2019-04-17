package com.playgilround.schedule.client.retrofit;

public class BaseUrl {

    // Readme 앞으로는 아래에 IP만 바꿔서 사용하기
    // static final String BASE_URL = "http://schedule-prod.ap-northeast-2.elasticbeanstalk.com/api/v1/";
    static final String BASE_URL = "http://10.0.1.4:8000/";

    static final String PATH_SIGN_UP = "v1/user/user/";

    static final String PATH_EMAIL_SIGN_IN = "api-token-auth/";
    static final String PATH_TOKEN_SIGN_IN = "api-token-verify/";

    static final String PATH_USER_SEARCH = "v1/user/user/search";

    public static final String PARAM_SIGN_IN_EMAIL = "email";
    public static final String PARAM_SIGN_IN_PASSWORD = "password";
    public static final String PARAM_SIGN_IN_TOKEN = "token";

    static final String PARAM_USER_NAME_SEARCH = "name";

    //Schedule
    static final String PATH_NEW_SCHEDULE = "/v1/schedule/schedule/";

    //Friend
    static final String PATH_REQUEST_FRIEND = "/v1/user/friend-request/";
    static final String PARAM_FRIEND_CHECK = "friend";

}
