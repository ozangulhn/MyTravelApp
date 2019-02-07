package com.example.hezeyan.myapplication;
public class Place {
    public String NameTurkish, NameEnglish, InformationTurkish, InformationEnglish, Location, City, Image1, Image2, Image3, Image4,Key;
    public Place() {
    }
    public Place(String nameTurkish,
                 String nameEnglish,
                 String informationTurkish,
                 String informationEnglish,
                 String location,
                 String city,
                 String image1,
                 String image2,
                 String image3,
                 String image4) {
        NameTurkish = nameTurkish;
        NameEnglish = nameEnglish;
        InformationTurkish = informationTurkish;
        InformationEnglish = informationEnglish;
        Location = location;
        City = city;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Image4 = image4;
    }
}
