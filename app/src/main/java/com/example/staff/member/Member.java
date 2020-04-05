package com.example.staff.member;

import java.io.Serializable;

public class Member implements Serializable {
    private String staff_account;
    private String staff_password;
    private String name;
    private String email;

    public Member() {
        super();
    }

    public Member(String staff_account, String staff_password, String name, String email) {
        super();
        this.staff_account = staff_account;
        this.staff_password = staff_password;
        this.name = name;
        this.email = email;
    }

    public String getStaff_account() {
        return staff_account;
    }

    public void setStaff_account(String staff_account) {
        this.staff_account = staff_account;
    }

    public String getStaff_password() {
        return staff_password;
    }

    public void setStaff_password(String staff_password) {
        this.staff_password = staff_password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

