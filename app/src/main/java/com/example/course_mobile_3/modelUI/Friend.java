package com.example.course_mobile_3.modelUI;

public class Friend {
    Long id;
    String nickname, fullName, email;

    public Friend(Long id, String nickname, String fullName) {
        this.id = id;
        this.nickname = nickname;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
