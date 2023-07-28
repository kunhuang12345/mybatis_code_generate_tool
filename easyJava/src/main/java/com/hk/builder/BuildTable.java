package com.hk.builder;


import com.hk.bean.Constants;
import com.hk.bean.FieldInfo;
import com.hk.bean.TableInfo;
import com.hk.utils.PropertiesUtils;
import com.hk.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildTable {
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);

    private static String SQL_SHOW_TABLE_STATUS = "show table status";

    private static String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";

    private static String SQL_SHOW_TABLE_INDEX = "show index from %s";
    static {
        String driverName = PropertiesUtils.getString("db.driver.name");
        String url = PropertiesUtils.getString("db.url");
        String username = PropertiesUtils.getString("db.username");
        String password = PropertiesUtils.getString("db.password");
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("数据库连接失败");
        }
    }

    public static List<TableInfo> getTables(){
        PreparedStatement ps = null;
        ResultSet tableResult = null;
        List<TableInfo> tableInfoList = new ArrayList<>();
        try {
            ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
            tableResult = ps.executeQuery();
            while(tableResult.next()){
                String tableName = tableResult.getString("name");
                String comment = tableResult.getString("comment");
//                logger.info("tableName:{},comment:{}",tableName,comment);

                String beanName = tableName;
                if (Constants.IGNORE_TABLE_PREFIX){
                    beanName = tableName.substring(beanName.indexOf("_")+1);
                }
                beanName = processField(beanName, true);
//                logger.info("beanName:{}",beanName);
                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);
                tableInfo.setBeanName(beanName);
                tableInfo.setComment(comment);

                tableInfo.setBeanParamName(beanName + Constants.SUFFIX_BEAN_QUERY);
                List<FieldInfo> fieldInfos = (List<FieldInfo>) readFieldInfos(tableInfo);
                tableInfo.setFieldList(fieldInfos);

//                logger.info("表：{}", JsonUtils.convertObj2Json(tableInfo));
                tableInfoList.add(tableInfo);

                getIndexInfo(tableInfo);
//                logger.info("表：{},备注：{}，JavaBean：{},JavaParamBean：{}",tableInfo.getTableName(),tableInfo.getComment(),tableInfo.getBeanName(),tableInfo.getBeanParamName());
            }
        } catch (Exception e){
            logger.error("读取表失败",e);
        } finally {
            if (tableResult != null){
                try {
                    tableResult.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return tableInfoList;
    }

    public static List<FieldInfo> readFieldInfos(TableInfo tableInfo){
        PreparedStatement ps = null;
        ResultSet fieldResult = null;
        List<FieldInfo> fieldinfolist = new ArrayList<>();
        List<FieldInfo> fieldInfoExtendList = new ArrayList<>();
        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS,tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while(fieldResult.next()){
                String field = fieldResult.getString("field");
                String type = fieldResult.getString("type");
                String extra = fieldResult.getString("extra");
                String comment = fieldResult.getString("comment");

                if (type.indexOf("(") > 0){
                    type = type.substring(0,type.indexOf("("));
                }
                String property = processField(field,false);

                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setFieldName(field);
                fieldInfo.setComment(comment);
                fieldInfo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra));
                fieldInfo.setSqlType(type);
                fieldInfo.setPropertyName(property);
                fieldInfo.setJavaType(processJavaType(type));
//                logger.info("{}  | {} |  {}  | {}",field,type,comment,extra);

//                logger.info("{}",fieldInfo.getJavaType());

                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
                    tableInfo.setHaveDateTime(true);
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
                    tableInfo.setHaveDate(true);
                }
                if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
                    tableInfo.setHaveBigDecimal(true);
                }

                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE,type)){
                    FieldInfo fuzzyField = new FieldInfo();
                    fuzzyField.setJavaType(fieldInfo.getJavaType());
                    fuzzyField.setSqlType(type);
                    fuzzyField.setPropertyName(property + Constants.SUFFIX_BEAN_QUERY_FUZZY);
                    fuzzyField.setFieldName(fieldInfo.getFieldName());
                    fieldInfoExtendList.add(fuzzyField);
                }

                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,type) || ArrayUtils.contains(Constants.SQL_DATE_TYPES,type)){
                    FieldInfo timeStartField = new FieldInfo();
                    timeStartField.setJavaType("String");
                    timeStartField.setPropertyName(property + Constants.SUFFIX_BEAN_QUERY_TIME_START);
                    timeStartField.setFieldName(fieldInfo.getFieldName());
                    timeStartField.setSqlType(type);
                    fieldInfoExtendList.add(timeStartField);

                    FieldInfo timeEndField = new FieldInfo();
                    timeEndField.setJavaType("String");
                    timeEndField.setPropertyName(property + Constants.SUFFIX_BEAN_QUERY_TIME_END);
                    timeEndField.setFieldName(fieldInfo.getFieldName());
                    timeEndField.setSqlType(type);
                    fieldInfoExtendList.add(timeEndField);
                }

                tableInfo.setFieldExtendList(fieldInfoExtendList);
                fieldinfolist.add(fieldInfo);
            }
        } catch (Exception e){
            logger.error("读取表失败",e);
        } finally {
            if (fieldResult != null){
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return fieldinfolist;
    }

    public static void getIndexInfo(TableInfo tableInfo){
        PreparedStatement ps = null;
        ResultSet fieldResult = null;
        List<FieldInfo> fieldinfolist = new ArrayList<>();

        Map<String,FieldInfo> tempMap = new HashMap<>();
        tableInfo.getFieldList().forEach(fieldInfo -> tempMap.put(fieldInfo.getFieldName(),fieldInfo));

        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_INDEX,tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while(fieldResult.next()){
                String keyName = fieldResult.getString("key_name");
                int nonUnique= fieldResult.getInt("non_unique");
                String columnName = fieldResult.getString("column_name");
                if (nonUnique == 1) continue;

                List<FieldInfo> keyFieldList = tableInfo.getKeyIndexMap().computeIfAbsent(keyName, k -> new ArrayList<>());
                //                for (FieldInfo fieldInfo: tableInfo.getFieldList()){
//                    if (fieldInfo.getFieldName().equals(columnName)) {
//                        keyFieldList.add(fieldInfo);
//                    }
//                }
                keyFieldList.add(tempMap.get(columnName));
            }
        } catch (Exception e){
            logger.error("读取表失败",e);
        } finally {
            if (fieldResult != null){
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static String processField(String field,Boolean upCaseFirstLetter){
        StringBuilder sb = new StringBuilder();
        String[] fields = field.split("_");
        sb.append(upCaseFirstLetter? StringUtils.uperCaseFirstLetter(fields[0]) :fields[0]);
        for (int i = 1; i < fields.length; i++){
            sb.append(StringUtils.uperCaseFirstLetter(fields[i]));
        }
        return sb.toString();
    }

    public static String processJavaType(String type){
        if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPES,type))return "Integer";
        else if(ArrayUtils.contains(Constants.SQL_LONG_TYPES,type)) return "Long";
        else if(ArrayUtils.contains(Constants.SQL_STRING_TYPE,type)) return "String";
        else if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,type) || ArrayUtils.contains(Constants.SQL_DATE_TYPES,type)) return "Date";
        else if(ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE,type)) return "BigDecimal";
        else throw new RuntimeException("无法识别的类型" + type);
    }
}
