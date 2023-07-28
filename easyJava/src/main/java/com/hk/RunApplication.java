package com.hk;


import com.hk.bean.TableInfo;
import com.hk.builder.*;
import com.hk.utils.JsonUtils;
import com.hk.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class RunApplication {
    private static final Logger logger = LoggerFactory.getLogger(RunApplication.class);

    public static void main(String[] args) {
        List<TableInfo> tableInfoList = BuildTable.getTables();

        BuildBase.execute();

        tableInfoList.forEach(BuildPo::execute);
        tableInfoList.forEach(BuildMapper::execute);
        tableInfoList.forEach(BuildQuery::execute);
        tableInfoList.forEach(BuildMapperXml::execute);
        tableInfoList.forEach(BuildService::execute);
        tableInfoList.forEach(BuildController::execute);
        tableInfoList.forEach(BuildServiceImpl::execute);
    }

}
