package com.hk.builder;

import com.hk.bean.Constants;
import com.hk.bean.FieldInfo;
import com.hk.bean.TableInfo;
import com.hk.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public class BuildController {

    public static Logger logger = LoggerFactory.getLogger(BuildController.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_CONTROLLER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String className = tableInfo.getBeanName() + "Controller";
        File poFile = new File(folder, tableInfo.getBeanName() + "Controller.java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out);
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_CONTROLLER + ";");

            bw.newLine();
            bw.newLine();

            bw.write("import java.util.List;");bw.newLine();


            if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
                bw.write("import java.util.Date;");
                bw.newLine();
                bw.newLine();
                bw.write("import " + Constants.PACKAGE_UTILS + ".DateUtils;");
                bw.newLine();
                bw.write("import " + Constants.PACKAGE_ENUMS + ".DateTimePatternEnum;");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS + ";");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_UNFORMAT_CLASS + ";");
                bw.newLine();
            }

            bw.write("import " + Constants.PACKAGE_SERVICE + "." + tableInfo.getBeanName() +  "Service;");bw.newLine();
            bw.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");bw.newLine();
            bw.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");bw.newLine();
            bw.write("import " + Constants.PACKAGE_VO + ".ResponseVO;");bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");bw.newLine();
            bw.write("import " + Constants.PACKAGE_MAPPER + "." + tableInfo.getBeanName() + "Mapper;");bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + "SimplePage;");bw.newLine();
            bw.write("import " + Constants.PACKAGE_ENUMS + "." + "PageSize;");bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RestController;");bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestMapping;");bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestBody;");bw.newLine();bw.newLine();

            bw.write("import javax.annotation.Resource;");bw.newLine();bw.newLine();


            BuildComment.createClassComment(bw,tableInfo.getComment() + "Controller");
            bw.write("@RestController");bw.newLine();
            bw.write("@RequestMapping(\"/" + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + "\")");bw.newLine();
            bw.write("public class " + className + " extends ABaseController {");bw.newLine();bw.newLine();

            String serviceName = tableInfo.getBeanName() + "Service";
            String serviceBeanName = StringUtils.lowerCaseFirstLetter(serviceName);

            bw.write("\t@Resource\n");
            bw.write("\tprivate " + serviceName + " " + serviceBeanName + ";\n\n");

            BuildComment.createFieldComment(bw,"根据条件分页查询");
            bw.write("\t@RequestMapping(\"/loadDataList\")");bw.newLine();
            bw.write("\tpublic ResponseVO loadDataList(" + tableInfo.getBeanParamName() + " query) {");bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(" + serviceBeanName + ".findListByPage(query));");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            bw.write("\t@RequestMapping(\"/add\")");bw.newLine();
            BuildComment.createFieldComment(bw,"新增");
            bw.write("\tpublic ResponseVO add(" + tableInfo.getBeanName() + " bean) {");bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(this." + serviceBeanName + ".add(bean));");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            BuildComment.createFieldComment(bw,"批量新增");
            bw.write("\t@RequestMapping(\"/addBatch\")");bw.newLine();
            bw.write("\tpublic ResponseVO addBatch(@RequestBody List<" + tableInfo.getBeanName() + "> beanList) {");bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(this." + serviceBeanName + ".addBatch(beanList));");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            BuildComment.createFieldComment(bw,"批量新增或修改");
            bw.write("\t@RequestMapping(\"/addOrUpdateBatch\")");bw.newLine();
            bw.write("\tpublic ResponseVO addOrUpdateBatch(@RequestBody List<" + tableInfo.getBeanName() + "> beanList) {");bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(this." + serviceBeanName + ".addOrUpdateBatch(beanList));");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParams = new StringBuilder();
                StringBuilder params = new StringBuilder();
                int index = 0;
                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    params.append(fieldInfo.getPropertyName());
                    methodParams.append(fieldInfo.getJavaType()).append(" ").append(fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        params.append(", ");
                        methodParams.append(", ");
                    }
                };
                BuildComment.createFieldComment(bw,"根据" + methodName + "查询");
                bw.write("\t@RequestMapping(\"/get" + tableInfo.getBeanName() + "By" + methodName + "\")");bw.newLine();
                bw.write("\tpublic ResponseVO get" + tableInfo.getBeanName() + "By" + methodName + "(" + methodParams + ") {");bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(this." + serviceBeanName + ".get" + tableInfo.getBeanName() + "By" + methodName + "(" + params + "));");bw.newLine();
                bw.write("\t}");bw.newLine();bw.newLine();

                BuildComment.createFieldComment(bw,"根据" + methodName + "更新");
                bw.write("\t@RequestMapping(\"/update" + tableInfo.getBeanName() + "By" + methodName + "\")");bw.newLine();
                bw.write("\tpublic ResponseVO update" + tableInfo.getBeanName() + "By" + methodName + "(" + tableInfo.getBeanName() + " " + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + ", " + methodParams + ") {");bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(this." + serviceBeanName + ".update" + tableInfo.getBeanName() + "By" + methodName + "(" + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + ", " + params + "));");bw.newLine();
                bw.write("\t}");bw.newLine();bw.newLine();

                BuildComment.createFieldComment(bw,"根据" + methodName + "删除");
                bw.write("\t@RequestMapping(\"/delete" + tableInfo.getBeanName() + "By" + methodName + "\")");bw.newLine();
                bw.write("\tpublic ResponseVO delete" + tableInfo.getBeanName() + "By" + methodName + "(" + methodParams + ") {");bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(this." + serviceBeanName + ".delete" + tableInfo.getBeanName() + "By" + methodName + "(" + params + "));");bw.newLine();
                bw.write("\t}");bw.newLine();bw.newLine();
            }




            bw.write("}");
            bw.flush();
        } catch (Exception e) {
            logger.error("创建service impl失败", e);
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
