package com.lpm.shop.server.imp;

import com.lpm.shop.entity.Report;
import com.lpm.shop.mapper.ReportMapper;
import com.lpm.shop.server.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportMapper reportMapper;


    @Override
    public List getArea() {
        List<Report> area = reportMapper.getArea();
        List areaList = new ArrayList();
        for (Report report : area) {
            areaList.add(report.getArea());
        }
        return areaList;
    }

    @Override
    public List getDate() {
        List<Report> area = reportMapper.getDate();
        List dateList = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        for (Report report : area) {
            dateList.add(dateFormat.format(report.getDate()));
        }
        return dateList;
    }

    @Override
    public List getAreaUserCount() {
        List area1 = getArea();
        List countList = new ArrayList();
        //HashMap<String,Object> dataMap = new HashMap<>();
        for (int i = 0;i < area1.size();i++){
            List list1 = new ArrayList();
            HashMap<String,Object> map = new HashMap<>();
            map.put("area",area1.get(i));
            List<Report> userCount = reportMapper.getAreaUserCount(area1.get(i).toString());
            for (Report report : userCount) {
                list1.add(report.getUserCount());
            }
            map.put("user_count",list1);
            countList.add(map);
        }
        return countList;
    }
}
