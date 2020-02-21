package com.example.isitvacant;

public class Contacts {

    public String name , Address, image;

    public Contacts()
    {
        //Empty Constructor needed
    }

    public Contacts(String name, String status, String image) {
        this.name = name;
        this.Address = Address;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return Address;
    }

    public void setStatus(String Address) {
        this.Address = Address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
