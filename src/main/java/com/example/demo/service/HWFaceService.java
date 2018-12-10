package com.example.demo.service;

import com.example.demo.common.FaceDetectResponse;
import com.example.demo.common.ImageObject;

import java.io.IOException;
import java.util.ArrayList;

public interface HWFaceService {
    public ArrayList<FaceDetectResponse> faceDetect(ImageObject image) throws IOException;

    public String faceSearch(String faceBase64) throws IOException;

    public String faceInsert(String faceBase64) throws IOException;

}
