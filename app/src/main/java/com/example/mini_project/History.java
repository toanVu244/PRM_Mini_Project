package com.example.mini_project;

public class History {
    private double MoneyBetting;
    private double MoneyGet;
    private String winer;

    public History(double moneyBetting, double moneyGet, String winer) {
        MoneyBetting = moneyBetting;
        MoneyGet = moneyGet;
        this.winer = winer;
    }

    public String getWiner() {
        return winer;
    }

    public void setWiner(String winer) {
        this.winer = winer;
    }

    public double getMoneyGet() {
        return MoneyGet;
    }

    public void setMoneyGet(double moneyGet) {
        MoneyGet = moneyGet;
    }

    public double getMoneyBetting() {
        return MoneyBetting;
    }

    public void setMoneyBetting(double moneyBetting) {
        MoneyBetting = moneyBetting;
    }
}
