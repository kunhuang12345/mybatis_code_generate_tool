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

public class BuildServiceImpl {

    public static Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_SERVICE_IMPL);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String className = tableInfo.getBeanName() + "Service";
        String classNameImpl = tableInfo.getBeanName() + "ServiceImpl";
        File poFile = new File(folder, tableInfo.getBeanName() + "ServiceImpl.java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out);
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_SERVICE_IMPL + ";");

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
            bw.write("import " + Constants.PACKAGE_SERVICE + "." + className + ";");bw.newLine();
            bw.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");bw.newLine();
            bw.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");bw.newLine();
            bw.write("import " + Constants.PACKAGE_MAPPER + "." + tableInfo.getBeanName() + "Mapper;");bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + "SimplePage;");bw.newLine();
            bw.write("import " + Constants.PACKAGE_ENUMS + "." + "PageSize;");bw.newLine();

            bw.write("import org.springframework.stereotype.Service;");bw.newLine();bw.newLine();
            bw.write("import javax.annotation.Resource;");bw.newLine();bw.newLine();


            BuildComment.createClassComment(bw,tableInfo.getComment() + "ServiceImpl");
            bw.write("@Service(\"" + StringUtils.lowerCaseFirstLetter(className) + "\")");bw.newLine();
            bw.write("public class " + classNameImpl + " implements " + className + " {");bw.newLine();bw.newLine();

            String mapperName = tableInfo.getBeanName() + "Mapper";
            String mapperNameFirstLetterLower = StringUtils.lowerCaseFirstLetter(mapperName);

            bw.write("\t@Resource\n");
            bw.write("\tprivate " + mapperName + "<" + tableInfo.getBeanName() + ", " + tableInfo.getBeanParamName() +"> " + mapperNameFirstLetterLower + ";\n\n");

            BuildComment.createFieldComment(bw,"根据条件查询列表");
            bw.write("\tpublic List<" + tableInfo.getBeanName() + "> findListByParam(" + tableInfo.getBeanParamName() + " query) {");bw.newLine();
            bw.write("\t\treturn this." + mapperNameFirstLetterLower + ".selectList(query);");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            BuildComment.createFieldComment(bw,"根据条件查询数量");
            bw.write("\tpublic Integer findCountByParam(" + tableInfo.getBeanParamName() + " query) {");bw.newLine();
            bw.write("\t\treturn this." + mapperNameFirstLetterLower + ".selectCount(query);");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            BuildComment.createFieldComment(bw,"分页查询");
            bw.write("\tpublic PaginationResultVO<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query) {");bw.newLine();
            bw.write("\t\tInteger count = this.findCountByParam(query);");bw.newLine();
            bw.write("\t\tInteger pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();");bw.newLine();
            bw.write("\t\tSimplePage page = new SimplePage(query.getPageNo(), count, pageSize);");bw.newLine();
            bw.write("\t\tquery.setSimplePage(page);");bw.newLine();
            bw.write("\t\tList<" + tableInfo.getBeanName() + "> list = this.findListByParam(query);");bw.newLine();
            bw.write("\t\tPaginationResultVO<" + tableInfo.getBeanName() + "> result = new PaginationResultVO<>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);");bw.newLine();
            bw.write("\t\treturn result;");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            BuildComment.createFieldComment(bw,"新增");
            bw.write("\tpublic Integer add(" + tableInfo.getBeanName() + " bean) {");bw.newLine();
            bw.write("\t\treturn this." + mapperNameFirstLetterLower + ".insert(bean);");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            BuildComment.createFieldComment(bw,"批量新增");
            bw.write("\tpublic Integer addBatch(List<" + tableInfo.getBeanName() + "> beanList) {");bw.newLine();
            bw.write("\t\tif (beanList == null || beanList.isEmpty()) {");bw.newLine();
            bw.write("\t\t\treturn 0;");bw.newLine();
            bw.write("\t\t}");bw.newLine();
            bw.write("\t\treturn this." + mapperNameFirstLetterLower + ".insertBatch(beanList);");bw.newLine();
            bw.write("\t}");bw.newLine();bw.newLine();

            BuildComment.createFieldComment(bw,"批量新增或修改");
            bw.write("\tpublic Integer addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> beanList) {");bw.newLine();
            bw.write("\t\tif (beanList == null || beanList.isEmpty()) {");bw.newLine();
            bw.write("\t\t\treturn 0;");bw.newLine();
            bw.write("\t\t}");bw.newLine();
            bw.write("\t\treturn this." + mapperNameFirstLetterLower + ".insertOrUpdateBatch(beanList);");bw.newLine();
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
                bw.write("\tpublic " + tableInfo.getBeanName() + " get" + tableInfo.getBeanName() + "By" + methodName + "(" + methodParams + ") {");bw.newLine();
                bw.write("\t\treturn this." + mapperNameFirstLetterLower + ".selectBy" + methodName + "(" + params + ");");bw.newLine();
                bw.write("\t}");bw.newLine();bw.newLine();

                BuildComment.createFieldComment(bw,"根据" + methodName + "更新");
                bw.write("\tpublic Integer update" + tableInfo.getBeanName() + "By" + methodName + "(" + tableInfo.getBeanName() + " " + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + ", " + methodParams + ") {");bw.newLine();
                bw.write("\t\treturn this." + mapperNameFirstLetterLower + ".updateBy" + methodName + "(" + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + ", " + params + ");");bw.newLine();
                bw.write("\t}");bw.newLine();bw.newLine();

                BuildComment.createFieldComment(bw,"根据" + methodName + "删除");
                bw.write("\tpublic Integer delete" + tableInfo.getBeanName() + "By" + methodName + "(" + methodParams + ") {");bw.newLine();
                bw.write("\t\treturn this." + mapperNameFirstLetterLower + ".deleteBy" + methodName + "(" + params + ");");bw.newLine();
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
