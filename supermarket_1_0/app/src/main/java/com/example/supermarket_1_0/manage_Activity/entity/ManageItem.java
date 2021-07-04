package com.example.supermarket_1_0.manage_Activity.entity;

public class ManageItem {
    private int CategoryID;
    private String CategoryName;
    private String imageURL;
    private String id;
    private String Name;
    private String OtherInfo;
    private String Stat;
    private String[] Category;
    private int number;
    private Double price;


    public ManageItem() {
    }

    public ManageItem(int CategoryID, String CategoryName, String[] Category, String imageURL, String id, String Name, String OtherInfo, String Stat) {
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.Category = Category;
        this.imageURL = imageURL;
        this.id = id;
        this.Name = Name;
        this.OtherInfo = OtherInfo;
        this.Stat = Stat;
    }

    @Override
    public String toString() {
        return "ManageItem{" +
                "id=" + id + "," +
                "imageURL=" + imageURL + "," +
                "price=" + price + "," +
                "CategoryName=" + CategoryName + "," +
                "Name=" + Name + "," +
                "detail=" + OtherInfo + "," +
                "number=" + number + "," +
                '}';
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
        this.CategoryName = this.Category[CategoryID];
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
        int i = 0;
        for (String str : Category) {
            if (str == CategoryName) {
                this.CategoryID = i;
                break;
            }
            i += 1;
        }
    }

    public String getimageURL() {
        return imageURL;
    }

    public void setimageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getOtherInfo() {
        return OtherInfo;
    }

    public void setOtherInfo(String OtherInfo) {
        this.OtherInfo = OtherInfo;
    }

    public String getStat() {
        return Stat;
    }

    public void setStat(String Stat) {
        this.Stat = Stat;
    }

    public void setCategory(String[] Category) {
        this.Category = Category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
