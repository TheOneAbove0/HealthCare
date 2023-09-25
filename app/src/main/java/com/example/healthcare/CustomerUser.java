package com.example.healthcare;

public class CustomerUser
{
    private String fullName;
    private String Dob;
    private String Address;
    private String fatherName;
    private String Phone;
    private String profilePicture;
    private String citizenFrontPic;
    private String CitizenBackPic;


    public CustomerUser(){

    }

    public CustomerUser(String fullName, String Dob, String Address, String fatherName,
                     String Phone, String profilePicture, String citizenFrontPic,
                     String CitizenBackPic) {
        this.fullName = fullName;
        this.Dob = Dob;
        this.Address = Address;
        this.fatherName = fatherName;
        this.Phone = Phone;
        this.profilePicture = profilePicture;
        this.citizenFrontPic = citizenFrontPic;
        this.CitizenBackPic = CitizenBackPic;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        this.Dob = Dob;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCitizenFrontPic() {
        return citizenFrontPic;
    }

    public void setCitizenFrontPic(String citizenFrontPic) {
        this.citizenFrontPic = citizenFrontPic;
    }

    public String getCitizenBackPic() {
        return CitizenBackPic;
    }

    public void setCitizenBackPic(String CitizenBackPic) {
        this.CitizenBackPic = CitizenBackPic;
    }


}
