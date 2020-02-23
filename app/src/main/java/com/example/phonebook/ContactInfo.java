package com.example.phonebook;

import java.io.Serializable;

public class ContactInfo implements Serializable {
    Integer contactlistId;
    String contactlistName;
    String contactlistNumber;



    public ContactInfo(Integer contactlistId, String contactlistName,String contactlistNumber) {

        this. contactlistId = contactlistId;
        this.contactlistName = contactlistName;
        this.contactlistNumber = contactlistNumber;
    }

    public Integer getContactlistId() {
        return contactlistId;
    }

    public void setContactlistId(Integer contactlistId) {
        this.contactlistId = contactlistId;
    }

    public String getContactlistName() {
        return contactlistName;
    }

    public void setContactlistName(String contactlistName) {
        this.contactlistName = contactlistName;
    }

    public String getContactlistNumber() {
        return contactlistNumber;
    }

    public void setContactlistNumber(String contactlistNumber) {
        this.contactlistNumber = contactlistNumber;
    }
}
