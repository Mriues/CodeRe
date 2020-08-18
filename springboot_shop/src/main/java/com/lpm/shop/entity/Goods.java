package com.lpm.shop.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Goods {

    private int Id;
    private String Name;
    private BigDecimal Price;
    private int number;
    private int weight;
    private int state;
    private int addTime;
    private int upTime;
    private int hotNumber;
    private int isPromote;
    private int catOneId;
    private int catTwoId;
    private int catThreeId;
    private String introduce;
    private String attrValue;

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public int getCatOneId() {
        return catOneId;
    }

    public void setCatOneId(int catOneId) {
        this.catOneId = catOneId;
    }

    public int getCatTwoId() {
        return catTwoId;
    }

    public void setCatTwoId(int catTwoId) {
        this.catTwoId = catTwoId;
    }

    public int getCatThreeId() {
        return catThreeId;
    }

    public void setCatThreeId(int catThreeId) {
        this.catThreeId = catThreeId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getAddTime() {
        return addTime;
    }

    public void setAddTime(int addTime) {
        this.addTime = addTime;
    }

    public int getUpTime() {
        return upTime;
    }

    public void setUpTime(int upTime) {
        this.upTime = upTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getHotNumber() {
        return hotNumber;
    }

    public void setHotNumber(int hotNumber) {
        this.hotNumber = hotNumber;
    }

    public int getIsPromote() {
        return isPromote;
    }

    public void setIsPromote(int isPromote) {
        this.isPromote = isPromote;
    }
}
