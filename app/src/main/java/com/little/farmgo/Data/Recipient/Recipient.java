package com.little.farmgo.Data.Recipient;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by sarah on 29/05/2018.
 */

public class Recipient {
    String firstName;
    String familyName;
    String county;
    String district;
    String address;
    String phone;
    
    public Recipient(String firstName, String familyName, String county, String district, String address, String phone) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.county = county;
        this.district = district;
        this.address = address;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
