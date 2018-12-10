package com.example.demo.common;

public class FaceAttribute {
    private String faceId;
    private int sex;
    private int age;

    public FaceAttribute(String faceId, int sex, int age) {
        this.faceId = faceId;
        this.sex = sex;
        this.age = age;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
