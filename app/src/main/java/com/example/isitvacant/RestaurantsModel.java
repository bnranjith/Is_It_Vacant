package com.example.isitvacant;

public class RestaurantsModel {

    public String name , Address, image, discription,Type;

    public RestaurantsModel()
    {
        //Empty Constructor needed
    }

    public RestaurantsModel(String name, String Address, String image, String discription, String Type) {
        this.name = name;
        this.Address = Address;
        this.image = image;
        this.discription = discription;
        this.Type = Type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return Address;
    }

    public void setLocation(String Address) {
        this.Address = Address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
    public String getTypes() {
        return Type;
    }

    public void setTypes(String Type) {
        this.Type = Type;
    }
}
