package com.lpm.shop.entity;

public class Categories {

    private int catId;
    private String catName;
    private int catPid;
    private int catLevel;
    private int catDeleted;

    public int getCatDeleted() {
        return catDeleted;
    }

    public void setCatDeleted(int catDeleted) {
        this.catDeleted = catDeleted;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatPid() {
        return catPid;
    }

    public void setCatPid(int catPid) {
        this.catPid = catPid;
    }

    public int getCatLevel() {
        return catLevel;
    }

    public void setCatLevel(int catLevel) {
        this.catLevel = catLevel;
    }
}
