package com.example.demo.service.impl;

import com.example.demo.common.FaceDetectResponse;
import com.example.demo.common.ImageObject;
import com.example.demo.common.OperateImage;
import com.example.demo.service.HWFaceService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class HWFaceServiceImpl implements HWFaceService {
    String url = "";
    HWAPI api = new HWAPI();
    public ArrayList<FaceDetectResponse> faceDetect(ImageObject image) throws IOException {
        url = "https://face.cn-north-1.myhuaweicloud.com/v1/5f1879d008564bd0981d40d12f08cd5a/face-detect";
        String imageBase64 = image.getImageBase64();
        String body = "{\"image_base64\":\""+image.getImageBase64()+"\",\"attributes\": \"1,2\"}";
        ArrayList<FaceDetectResponse> faceList = new ArrayList<FaceDetectResponse>();
        //得到json结果
        String R = api.sendPost(url,body);
        System.out.println(R);
        JSONObject result = new JSONObject(R);
        //得到脸集
        JSONArray faces = result.getJSONArray("faces");
        //遍历脸集，将脸的信息存入list
        for (int i=1;i<=faces.length();i++)
        {
            FaceDetectResponse face = new FaceDetectResponse();
            face.setAge((int)faces.getJSONObject(i-1).getJSONObject("attributes").get("age"));
            face.setSex((String) faces.getJSONObject(i-1).getJSONObject("attributes").get("gender"));
            //face.setImageBase64("");
            OperateImage operateImage = new OperateImage(imageBase64);
            int x = (int)faces.getJSONObject(i-1).getJSONObject("bounding_box").get("top_left_x");
            int y = (int)faces.getJSONObject(i-1).getJSONObject("bounding_box").get("top_left_y");
            int width = (int)faces.getJSONObject(i-1).getJSONObject("bounding_box").get("width");
            int height = (int)faces.getJSONObject(i-1).getJSONObject("bounding_box").get("height");
            String subImageBase64 = operateImage.cut(x,y,width,height);
            face.setImageBase64(subImageBase64);
            faceList.add(face);
        }
        //System.out.println(result.face);
        return faceList;
    }

    @Override
    public String faceSearch(String faceBase64) throws IOException {
        url = "https://face.cn-north-1.myhuaweicloud.com/v1/5f1879d008564bd0981d40d12f08cd5a/face-sets/couldComputingFaceSets/search";
        String body ="{\"image_base64\":\""+faceBase64+"\",\"top_n\":\"1\",\"threshold\":\"0.9\"}";
        String R = api.sendPost(url,body);
        System.out.println(R);
        JSONObject result = new JSONObject(R);
        JSONArray faces = result.getJSONArray("faces");
        if(faces.length() == 0)
        {
            return "";
        }
        else
        {
            return (String) faces.getJSONObject(0).get("face_id");
        }
    }

    @Override
    public String faceInsert(String faceBase64) throws IOException {
        String url = "https://face.cn-north-1.myhuaweicloud.com/v1/5f1879d008564bd0981d40d12f08cd5a/face-sets/couldComputingFaceSets/faces";
        String body = "{\"image_base64\":\""+faceBase64+"\"}";
        String R = api.sendPost(url,body);
        JSONObject result = new JSONObject(R);
        JSONArray face = result.getJSONArray("faces");
        String faceId = (String) face.getJSONObject(0).get("face_id");
        return faceId;
    }
}
