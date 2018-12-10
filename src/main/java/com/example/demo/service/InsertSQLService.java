package com.example.demo.service;

import com.example.demo.common.FaceAttribute;
import com.example.demo.model.FaceEntryTime;

import java.util.ArrayList;

public interface InsertSQLService {

    public int insertFaceAttribute(ArrayList<FaceAttribute> faceAttributes);

    public int insertFaceEntryTime(ArrayList<FaceEntryTime> faceEntryTimes);

    public FaceEntryTime getLastEntryTime(String face_id,int user_id);
}
