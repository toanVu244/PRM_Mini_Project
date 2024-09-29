package com.example.mini_project;

public class History {
    private int MoneyBetting;
    private int MoneyGet;
    private int winer;

    public History(int moneyBetting, int moneyGet, int winer) {
        MoneyBetting = moneyBetting;
        MoneyGet = moneyGet;
        this.winer = winer;
    }

    public int getWiner() {
        return winer;
    }

    public void setWiner(int winer) {
        this.winer = winer;
    }

    public int getMoneyGet() {
        return MoneyGet;
    }

    public void setMoneyGet(int moneyGet) {
        MoneyGet = moneyGet;
    }

    public int getMoneyBetting() {
        return MoneyBetting;
    }

    public void setMoneyBetting(int moneyBetting) {
        MoneyBetting = moneyBetting;
    }
}
