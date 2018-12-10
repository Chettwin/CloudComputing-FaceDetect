package com.example.demo.controller;

import com.example.demo.common.FaceAttribute;
import com.example.demo.common.FaceDetectResponse;
import com.example.demo.common.ImageObject;
import com.example.demo.common.Result;
import com.example.demo.model.FaceEntryTime;
import com.example.demo.service.HWFaceService;
import com.example.demo.service.InsertSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/faceMonitor")
public class FaceMonitorController {

    @Autowired
    HWFaceService hwFaceService;
    @Autowired
    InsertSQLService insertSQLService;

    /*
    ImageObject：
    private String imageBase64;
    private String entryDate;
    private String entryHour;
    private String entryMinute;
    private String userId;
     */
    @RequestMapping("/getFaceDetectResponse")
    public Result getFaceDetectResponse(@RequestBody ImageObject image) throws IOException {
        System.out.print(image);
        //人脸检测 得到人脸的base64
        ArrayList<FaceDetectResponse> faceList = hwFaceService.faceDetect(image);
        ArrayList<FaceAttribute> faceAttributes = new ArrayList<>();
        ArrayList<FaceEntryTime> faceEntryTimes = new ArrayList<>();
        for (FaceDetectResponse face:faceList)
        {
            //人脸搜索（返回是否有置信度>=0.9的人脸ID）
            String faceId = "";
            faceId = hwFaceService.faceSearch(face.getImageBase64());
            //人脸库中无此脸，将人脸特征存入数据库，将脸存入人脸库，返回faceId
            if(faceId.equals(""))
            {
                faceId = hwFaceService.faceInsert(face.getImageBase64());
                int sex = face.getSex().equals("male") ? 1 : 2;
                FaceAttribute faceAttribute = new FaceAttribute(faceId,sex,face.getAge());
                faceAttributes.add(faceAttribute);
            }
            //通过userId与faceId将人的进入时间插入进数据库
            FaceEntryTime faceEntryTime = new FaceEntryTime(faceId,image.getEntryDate(),Integer.parseInt(image.getUserId()),Integer.parseInt(image.getEntryHour()),Integer.parseInt(image.getEntryMinute()),Integer.parseInt(image.getEntrySecond()));
            faceEntryTimes.add(faceEntryTime);
        }
        //将list插入数据库
        if(faceAttributes.size() != 0 )
            insertSQLService.insertFaceAttribute(faceAttributes);

        insertSQLService.insertFaceEntryTime(faceEntryTimes);
//        insertSQLService.insertFaceEntryTime(faceEntryTimes);

        return Result.success(faceList);
    }
}
