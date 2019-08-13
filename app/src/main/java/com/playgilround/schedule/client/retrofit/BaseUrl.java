package com.playgilround.schedule.client.retrofit;

public class BaseUrl {

    // Readme 앞으로는 아래에 IP만 바꿔서 사용하기
    // static final String BASE_URL = "http://schedule-prod.ap-northeast-2.elasticbeanstalk.com/api/v1/";
    static final String BASE_URL = "http://127.0.0.1:8000/";


    static final String PATH_TOKEN_SIGN_IN = "api-token-verify/";

    static final String PATH_FACEBOOK_SIGN_IN = "rest-auth/facebook/";
    static final String PATH_NAVER_SIGN_IN  = "rest-auth/naver/";
    static final String PATH_KAKAO_SIGN_IN = "rest-auth/kakao/";
    static final String PATH_GOOGLE_SIGN_IN = "rest-auth/google/";

    public static final String PARAM_SIGN_IN_EMAIL = "email";
    public static final String PARAM_SIGN_IN_PASSWORD = "password";
    public static final String PARAM_SIGN_IN_TOKEN = "token";
    public static final String PARAM_SIGN_IN_NICKNAME = "nickname";

    static final String PARAM_USER_NAME_SEARCH = "name";

    //Schedule
    static final String PATH_NEW_SCHEDULE = "/v1/schedule/schedule/"; //일정 생성
    static final String PATH_DETAIL_SCHEDULE = "/v1/schedule/schedule/{schedule_id}/"; //일정 상세 정보
    static final String PATH_UPDATE_SCHEDULE = "/v1/schedule/schedule/{schedule_id}/"; //일정 정보 업데이트
    static final String PATH_INVITE_SCHEDULE = "/v1/schedule/schedule/{schedule_id}/join/?id={user_id}"; //일정 유저 초대
    static final String PATH_LEAVE_SCHEDULE = "/v1/schedule/schedule/{schedule_id}/leave/"; //일정 떠나기
    static final String PATH_EXPULSION_SCHEDULE = "/v1/schedule/schedule/{schedule_id}/expulsion/?id={user_id}"; //일정 유저 추방
    static final String PATH_ARRIVAL_SCHEDULE = "/v1/schedule/schedule/{schedule_id}/arrival/"; //일정 도착 확인
    //Friend
    static final String PATH_CHECK_LIST_FRIEND = "/v1/user/user/{user_id}/friend_list/"; //내 친구 리스트 확인
    static final String PATH_CHECK_REQUEST_FRIEND = "/v1/user/user/{user_id}/friend_request_list"; //내 친구 요청 리스트 확인
    static final String PATH_REQUEST_FRIEND = "/v1/user/friend-request/"; //친구 요청 보내기, 수락
    static final String PATH_DETAIL_REQUEST_FRIEND = "/v1/user/friend-request/{friend_request_id/"; //친구 요청 상세 정보
    static final String PATH_REQUEST_DELETE_FRIEND = "/v1/user/friend-request/{friend_request_id}/"; //친구 요청 삭제, 거절
    static final String PATH_DELETE_FRIEND = "v1/user/friend-request/{user_id}/"; //친구 삭제
    static final String PARAM_FRIEND_CHECK = "friend";

    //User
    static final String PATH_SIGN_UP = "/v1/user/user/"; //회원가입
    static final String PATH_DUPLICATE_EMAIL = "/v1/user/email-validate/?email={email}";//이메일 중복 검사
    static final String PATH_DUPLICATE_NICKNAME = "/v1/user/nickname-validate/?nickname={nickname}";//닉네임 중복 검사
    static final String PATH_EMAIL_SIGN_IN = "/api-token-auth/"; //로그인
    static final String PATH_USER_PROFILE = "/v1/user/user/{user_id}/";//유저 프로필
    static final String PATH_USER_PROFILE_UPDATE = "/v1/user/user/{user_id}/";//유저 프로필 업데이트
    static final String PATH_USER_SEARCH = "/v1/user/user/search/?name={nickname}"; //유저 검색

}