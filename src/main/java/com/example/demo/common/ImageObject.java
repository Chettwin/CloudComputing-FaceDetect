package com.example.demo.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.print.attribute.standard.PrinterURI;

@Setter
@Getter
@ToString
public class ImageObject {

    private String imageBase64;
    private String entryDate;
    private String entryHour;
    private String entryMinute;
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryHour() {
        return entryHour;
    }

    public void setEntryHour(String entryHour) {
        this.entryHour = entryHour;
    }

    public String getEntryMinute() {
        return entryMinute;
    }

    public void setEntryMinute(String entryMinute) {
        this.entryMinute = entryMinute;
    }

    public String getEntrySecond() {
        return entrySecond;
    }

    public void setEntrySecond(String entrySecond) {
        this.entrySecond = entrySecond;
    }

    private String entrySecond;


    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }


}
