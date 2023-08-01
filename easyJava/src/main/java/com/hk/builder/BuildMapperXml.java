package com.hk.builder;

import com.hk.bean.Constants;
import com.hk.bean.FieldInfo;
import com.hk.bean.TableInfo;
import com.hk.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildMapperXml {
    public static Logger logger = LoggerFactory.getLogger(BuildMapperXml.class);
    private static final String BASE_COLUMN_LIST = "base_column_list";
    private static final String BASE_QUERY_CONDITION = "base_query_condition";
    private static final String BASE_QUERY_CONDITION_EXTEND = "base_query_condition_extend";
    private static final String QUERY_CONDITION = "query_condition";

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_MAPPER_XML);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + Constants.SUFFIX_MAPPER;
        File poFile = new File(folder, className + ".xml");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out);
            bw = new BufferedWriter(outw);

            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                    "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.newLine();
            bw.write("<mapper namespace=\"" + Constants.PACKAGE_MAPPER + "." + className + "\">");
            bw.newLine();

            bw.write("\t<!--实体映射-->");
            bw.newLine();

            String poClass = Constants.PACKAGE_PO + "." + tableInfo.getBeanName();
            bw.write("\t<resultMap id=\"base_result_map\" type=\"" + poClass + "\">");
            bw.newLine();

            Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();

            FieldInfo idField = null;
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                if ("PRIMARY".equals(entry.getKey())) {
                    List<FieldInfo> fieldInfoList = entry.getValue();
                    if (fieldInfoList.size() == 1) {
                        idField = fieldInfoList.get(0);
                        break;
                    }
                }
            }

            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t<!--" + fieldInfo.getComment() + "-->");
                bw.newLine();

                String key = "";
                if (idField != null && fieldInfo.getPropertyName().equals(idField.getPropertyName())) {
                    key = "id";
                } else {
                    key = "result";
                }
                bw.write("\t\t<" + key + " column=\"" + fieldInfo.getFieldName() + "\" property=\"" + fieldInfo.getPropertyName() + "\"/>");
                bw.newLine();
                bw.newLine();
            }


            bw.write("\t</resultMap>");
            bw.newLine();
            bw.newLine();
            bw.newLine();


            // 通用查询列
            bw.write("\t<!--通用查询结果列-->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_COLUMN_LIST + "\">");
            bw.newLine();

            StringBuilder columnBuilder = new StringBuilder();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                columnBuilder.append(fieldInfo.getFieldName()).append(",");
            }
            String getColumnBuilder = columnBuilder.substring(0, columnBuilder.lastIndexOf(","));
            bw.write("\t\t" + getColumnBuilder);
            bw.newLine();

            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            // 基础查询条件
            bw.write("\t<!--基础查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_QUERY_CONDITION + "\">");
            bw.newLine();


            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                String stringQuery = "";
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    stringQuery = " and query." + fieldInfo.getPropertyName() + " != ''";
                }
                bw.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + " != null" + stringQuery + "\">");
                bw.newLine();
                bw.write("\t\t\tand " + fieldInfo.getFieldName() + " = #{query." + fieldInfo.getPropertyName() + "}");
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }


            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();


            bw.write("\t<!--扩展的查询条件-->");
            bw.newLine();

            bw.write("\t<sql id=\"" + BASE_QUERY_CONDITION_EXTEND + "\">");
            bw.newLine();


            for (FieldInfo fieldInfo : tableInfo.getFieldExtendList()) {
                String andWhere = "";
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    andWhere = "and " + fieldInfo.getFieldName() + " like concat('%', #{query." + fieldInfo.getPropertyName() + "}, '%')";
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_START)){
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " >= str_to_date(#{query." + fieldInfo.getPropertyName() + "},'%Y-%m-%d') ]]>";
                    } else if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_END)){
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " < date_sub(str_to_date(#{query." + fieldInfo.getPropertyName() + "},'%Y-%m-%d'),interval -1 day) ]]>";
                    }

                }
                bw.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + " != null and query." + fieldInfo.getPropertyName() + " !=''\">");
                bw.newLine();


                bw.write("\t\t\t" + andWhere);
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }


            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();


            // 通用查询条件
            bw.write("\t<!--通用查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\"" + QUERY_CONDITION + "\">");
            bw.newLine();
            bw.write("\t\t<where>");bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION + "\"/>");bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION_EXTEND + "\"/>");bw.newLine();
            bw.write("\t\t</where>");bw.newLine();
            bw.write("\t</sql>");bw.newLine();bw.newLine();


            // 查询列表
            bw.write("\t<!--查询列表-->");bw.newLine();
            bw.write("\t<select id=\"selectList\" resultMap=\"base_result_map\">");bw.newLine();
            bw.write("\t\tselect \n\t\t<include refid=\"" + BASE_COLUMN_LIST + "\"/> \n\t\tfrom " + tableInfo.getTableName() + "\n\t\t<include refid=\"" + QUERY_CONDITION + "\"/>");bw.newLine();
            bw.write("\t\t<if test=\"query.orderBy != null\">\n\t\torder by ${query.orderBy}\n\t\t</if>");bw.newLine();
            bw.write("\t\t<if test=\"query.simplePage != null\">\n\t\tlimit #{query.simplePage.start}, #{query.simplePage.end}\n\t\t</if>");bw.newLine();
            bw.write("\t</select>");bw.newLine();bw.newLine();


            // 查询数量
            bw.write("\t<!--查询数量-->");bw.newLine();
            bw.write("\t<select id=\"selectCount\" resultType=\"java.lang.Integer\">");bw.newLine();
            bw.write("\t\tselect count(1) from " + tableInfo.getTableName() + " <include refid=\"" + QUERY_CONDITION + "\"/>");bw.newLine();
            bw.write("\t</select>");bw.newLine();bw.newLine();


            // 单条插入
            bw.write("\t<!--插入（匹配有值的字段）-->");bw.newLine();
            bw.write("\t<insert id=\"insert\" parameterType=\"" + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + "\">");bw.newLine();

            FieldInfo auto_incrementField = null;
            for (FieldInfo fieldInfo: tableInfo.getFieldList())
                if (fieldInfo.getAutoIncrement() != null && fieldInfo.getAutoIncrement()){auto_incrementField = fieldInfo;break;}
            if (auto_incrementField != null) {
                bw.write("\t\t<selectKey keyProperty=\"bean." + auto_incrementField.getPropertyName() + "\" resultType=\"Integer\" order=\"AFTER\">");bw.newLine();
                bw.write("\t\t\tselect last_insert_id()");bw.newLine();
                bw.write("\t\t</selectKey>");bw.newLine();
            }
            bw.write("\t\t<trim prefix=\"insert into " + tableInfo.getTableName() + "(\" suffix=\")\" suffixOverrides=\",\">");bw.newLine();

            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");bw.newLine();
                bw.write("\t\t\t</if>");bw.newLine();
            }
            bw.write("\t\t</trim>");bw.newLine();


            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");bw.newLine();

            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");bw.newLine();
                bw.write("\t\t\t</if>");bw.newLine();
            }

            bw.write("\t\t</trim>");bw.newLine();


            bw.write("\t</insert>");bw.newLine();bw.newLine();


            // 插入或者更新
            bw.write("\t<!--插入或者更新（匹配有值的字段）-->");bw.newLine();
            bw.write("\t<insert id=\"insertOrUpdate\" parameterType=\"" + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + "\">");bw.newLine();
            bw.write("\t\t<trim prefix=\"insert into " + tableInfo.getTableName() + "(\" suffix=\")\" suffixOverrides=\",\">");bw.newLine();
            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");bw.newLine();
                bw.write("\t\t\t</if>");bw.newLine();
            }
            bw.write("\t\t</trim>");bw.newLine();
            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");bw.newLine();
            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");bw.newLine();
                bw.write("\t\t\t</if>");bw.newLine();
            }
            bw.write("\t\t</trim>");bw.newLine();
            bw.write("\t\ton duplicate key update");bw.newLine();
            Map<String,String> KeytempMap = new HashMap<>();
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                if ("PRIMARY".equals(entry.getKey())) {
                    List<FieldInfo> fieldInfoList = entry.getValue();
                    for (FieldInfo item: fieldInfoList){
                        KeytempMap.put(item.getFieldName(),item.getFieldName());
                    }
                }
            }
            bw.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");bw.newLine();
            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                if (KeytempMap.get(fieldInfo.getFieldName()) != null){
                    continue;
                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + " = #{bean." + fieldInfo.getPropertyName() + "},");bw.newLine();
                bw.write("\t\t\t</if>");bw.newLine();
            }
            bw.write("\t\t</trim>");bw.newLine();
            bw.write("\t</insert>");bw.newLine();bw.newLine();


            // 批量插入
            bw.write("\t<!--批量插入-->");bw.newLine();
            bw.write("\t<insert id=\"insertBatch\" parameterType=\"" + poClass + "\">");bw.newLine();
            StringBuilder insertFieldBuilder = new StringBuilder();
            StringBuilder insertPropertyBuilder = new StringBuilder();
            insertPropertyBuilder.append("(");
            for (FieldInfo fieldInfo: tableInfo.getFieldList()){
                if (fieldInfo.getAutoIncrement())continue;
                insertFieldBuilder.append(fieldInfo.getFieldName()).append(",");
                insertPropertyBuilder.append("#{item.").append(fieldInfo.getPropertyName()).append("},");
            }
            String insertFieldBuilderStr = insertFieldBuilder.substring(0,insertFieldBuilder.lastIndexOf(","));
            bw.write("\t\tinsert into " + tableInfo.getTableName() + "(" + insertFieldBuilderStr + ") values");bw.newLine();
            bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");bw.newLine();
            String insertPropertyBuilderStr = insertPropertyBuilder.substring(0,insertPropertyBuilder.lastIndexOf(","));
            bw.write("\t\t" + insertPropertyBuilderStr + ")");bw.newLine();
            bw.write("\t\t</foreach>");bw.newLine();
            bw.write("\t</insert>");bw.newLine();bw.newLine();




            // 批量插入或者更新
            bw.write("\t<!--批量插入或者更新-->");bw.newLine();
            bw.write("\t<insert id=\"insertOrUpdateBatch\" parameterType=\"" + poClass + "\">");bw.newLine();
            bw.write("\t\tinsert into " + tableInfo.getTableName() + "(" + insertFieldBuilderStr + ") values");bw.newLine();
            bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");bw.newLine();
            bw.write("\t\t" + insertPropertyBuilderStr + ")");bw.newLine();
            bw.write("\t\t</foreach>");bw.newLine();
            bw.write("\t\ton duplicate key update");bw.newLine();
            StringBuilder insertBatchUpdateBuilder = new StringBuilder();
            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                insertBatchUpdateBuilder.append(fieldInfo.getFieldName()).append("=values(").append(fieldInfo.getFieldName()).append("),").append("\n\t\t");
            }
            String insertBatchUpdateBuilderStr = insertBatchUpdateBuilder.substring(0,insertBatchUpdateBuilder.lastIndexOf(","));
            bw.write("\t\t" + insertBatchUpdateBuilderStr);bw.newLine();
            bw.write("\t</insert>");bw.newLine();bw.newLine();


            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();
                StringBuilder methodName = new StringBuilder();
                StringBuilder paramNames = new StringBuilder();
                int index = 0;
                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    paramNames.append(fieldInfo.getFieldName()).append("=#{").append(fieldInfo.getFieldName()).append("}");
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        paramNames.append(" and ");
                    }
                };

                // 根据索引查询
                bw.write("\t<!--根据" + methodName + "查询-->");bw.newLine();
                bw.write("\t<select id=\"selectBy" + methodName + "\" resultMap=\"base_result_map\" >");bw.newLine();
                bw.write("\t\tselect");bw.newLine();
                bw.write("\t\t<include refid=\"" + BASE_COLUMN_LIST + "\"/>");bw.newLine();
                bw.write("\t\tfrom " + tableInfo.getTableName() + "\n\t\twhere " + paramNames);bw.newLine();
                bw.write("\t</select>");bw.newLine();bw.newLine();


                // 根据索引更新
                bw.write("\t<!--根据" + methodName + "更新-->");bw.newLine();
                bw.write("\t<update id=\"updateBy" + methodName + "\" parameterType=\"" + poClass + "\">");bw.newLine();
                bw.write("\t\tupdate " + tableInfo.getTableName());bw.newLine();
                bw.write("\t\t<set>");bw.newLine();
                int i = 0;
                for (FieldInfo fieldInfo : tableInfo.getFieldList()){
                    bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");bw.newLine();
                    bw.write("\t\t\t" + fieldInfo.getFieldName() + "=#{bean." + fieldInfo.getPropertyName() + "}");if (i<tableInfo.getFieldList().size()-1)bw.write(",");bw.newLine();
                    bw.write("\t\t\t</if>");bw.newLine();
                    i++;
                }
                bw.write("\t\t</set>");bw.newLine();
                bw.write("\t\twhere " + paramNames);bw.newLine();
                bw.write("\t</update>");bw.newLine();bw.newLine();


                // 根据索引删除
                bw.write("\t<!--根据" + methodName + "删除-->");bw.newLine();
                bw.write("\t<delete id=\"deleteBy" + methodName + "\">");bw.newLine();
                bw.write("\t\tdelete from " + tableInfo.getTableName());bw.newLine();
                bw.write("\t\twhere " + paramNames);bw.newLine();
                bw.write("\t</delete>");bw.newLine();bw.newLine();

            }


            bw.newLine();
            bw.newLine();
            bw.write("</mapper>");




            bw.flush();
        } catch (Exception e) {
            logger.error("创建mapper.xml文件失败", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
