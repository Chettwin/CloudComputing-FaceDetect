package com.example.demo.mapper;

import com.example.demo.common.FaceAttribute;
import com.example.demo.model.FaceEntryTime;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface InsertSQLMapper {
    public void insertEntryTime(FaceEntryTime faceEntryTime);

    public void insertFaceAttribute(FaceAttribute faceAttribute);

    public FaceEntryTime getLastEntryTime(String face_id, int user_id);
}
