package com.example.demo.service.impl;

import com.example.demo.common.FaceAttribute;
import com.example.demo.mapper.InsertSQLMapper;
import com.example.demo.model.FaceEntryTime;
import com.example.demo.service.InsertSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Service
public class InsertSQLServiceImpl implements InsertSQLService {
    @Autowired
    InsertSQLMapper insertSQLMapper;

    @Override
    public int insertFaceAttribute(ArrayList<FaceAttribute> faceAttributes) {

        for (FaceAttribute face:faceAttributes)
        {
            insertSQLMapper.insertFaceAttribute(face);
        }

        return 0;
    }

    @Override
    public int insertFaceEntryTime(ArrayList<FaceEntryTime> faceEntryTimes) {
        for (FaceEntryTime time:faceEntryTimes)
        {
            FaceEntryTime lastEntryTime = getLastEntryTime(time.getFace_id(),time.getUser_id());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date lastDate = null;
            Date thisDate = null;
            try {
                if(lastEntryTime!=null)
                    lastDate = sdf.parse(lastEntryTime.getEntry_time());
                thisDate = sdf.parse(time.getEntry_time()) ;
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            String lastDate = lastEntryTime.getEntry_time().replaceAll("-","");
//            String thisDate = time.getEntry_time().replaceAll("-","");
            //一分钟内重复进入商城 不增加客流量
            if(lastEntryTime==null || lastDate.before(thisDate) || lastEntryTime.getEntry_hour()<time.getEntry_hour() || (time.getEntry_minute()-lastEntryTime.getEntry_minute())>=1)
                insertSQLMapper.insertEntryTime(time);
        }
        return 0;
    }

    @Override
    public FaceEntryTime getLastEntryTime(String face_id, int user_id) {
        return insertSQLMapper.getLastEntryTime(face_id,user_id);
    }
}
