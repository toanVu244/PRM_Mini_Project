package com.example.mini_project;

import androidx.annotation.NonNull;

import java.util.List;

public class Account {
    private String id;
    private String Username;
    private String Password;
    private double money;
    List<History> histories;

    public Account(List<History> histories, double money, String password, String username, String id) {
        this.histories = histories;
        this.money = money;
        Password = password;
        Username = username;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
