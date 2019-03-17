package com.playgilround.schedule.client.signup.model;


import android.util.Base64;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private int id;
    private String username;
    private String nickname;
    private String birth;
    private String email;
    private String password;
    private String password2;
    private String language = "KR";
    private String token;

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword2() {
        return password2;
    }

    public String getLanguage() {
        return language;
    }

    public String getToken() {
        return token;
    }

    public static boolean checkEmail(String email) {
        String mail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(mail);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /*
     * 패스워드 유효성검사
     * 영문, 숫자입력
     * 정규식 (영문, 숫자 8자리 이상)
     */
    public static boolean checkPassWord(String password) {
        String vailPass = "^(?=.*[a-z])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(vailPass);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String base64Encoding(String content) {
        return Base64.encodeToString(content.getBytes(), 0);
    }

    public static String base64Decoding(String content) {
        return new String(Base64.decode(content, 0));
    }

}
