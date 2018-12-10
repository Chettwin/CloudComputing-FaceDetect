package com.example.demo.controller;

import com.example.demo.common.ParamObject;
import com.example.demo.common.Result;
import com.example.demo.model.AgeAnalysis;
import com.example.demo.model.SexAnalysis;
import com.example.demo.service.AgeAnalysisService;
import com.example.demo.service.SexAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/ageanalysis")
public class AgeAnalysisController {

    @Autowired
    AgeAnalysisService ageanalysis;
    /*
    *    param：ParamObject {userId startDate大 endDate小} 用户id, 查询时间(默认查询时间为当前日期直过去的七天)
    *    return: {code, msg, data} data: 用户id，以及男女数量[id, manNum, womanNum]
     */
    @RequestMapping("/ageAnalysisById")
    public Result showAgeAnalysisById(@RequestBody ParamObject params){

        System.out.print(params);

        boolean flog = false;
        List<AgeAnalysis> ageAnalyResult = ageanalysis.showAgeAnalysisById(params);
        System.out.print(ageAnalyResult.size());
        for(AgeAnalysis age:ageAnalyResult){
            if(age.getNum()!=0){  //有数据
                flog = true;
            }
        }
        if(flog){
            return Result.success(ageAnalyResult);
        }else{
            return Result.success("没有数据");
        }





    }
}
