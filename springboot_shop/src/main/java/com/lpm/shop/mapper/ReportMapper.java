package com.lpm.shop.mapper;

import com.lpm.shop.entity.Report;

import java.util.List;

public interface ReportMapper {

    List<Report> getArea();

    List<Report> getDate();

    List<Report> getAreaUserCount(String area);
}
