package com.example.course_mobile_3.api.body;

public class User {
    private Long id;
    private String email;
    private String fullName;
    private String nickname;
    private Long sessionId;

    public User () {}

    public User(Long id, String email, String fullName, String nickname, Long sessionId) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.nickname = nickname;
        this.sessionId = sessionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
