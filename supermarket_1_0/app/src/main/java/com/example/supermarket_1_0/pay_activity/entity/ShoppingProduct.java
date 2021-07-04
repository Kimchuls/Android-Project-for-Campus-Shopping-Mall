package com.example.supermarket_1_0.pay_activity.entity;

import org.litepal.crud.DataSupport;

/**
 * 购买的当前商品信息类
 */
public class ShoppingProduct {
    private String productID;              //商品id（编号）
    private String productName;         //商品名
    private String productDescription;  //商品描述
    private String type;        //商品类型
    private double price;       //商品价格
    private int inventory;      //商品库存数量
    private int buyNum;         //购买数量
    private String url;   //图片地址

    public ShoppingProduct() {
    }

    public ShoppingProduct(String productID, String productName, String productDescription, String type, double price, int inventory, int buyNum, String url) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.type = type;
        this.price = price;
        this.inventory = inventory;
        this.buyNum = buyNum;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShoppingProduct{" +
                "id=" + productID + "," +
                "imageURL=" + url + "," +
                "price=" + price + "," +
                "CategoryName=" + type + "," +
                "Name=" + productName + "," +
                "detail=" + productDescription + "," +
                "number=" + inventory + "," +
                '}';
    }


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public String getProductUrl() {
        return url;
    }

    public void setProductUrl(String url) {
        this.url = url;
    }
}
