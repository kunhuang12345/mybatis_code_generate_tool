package com.hk.builder;

import com.hk.bean.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BuildBase {

    private static Logger logger = LoggerFactory.getLogger(BuildBase.class);

    public static void execute() {
        List<String> headerInfoList = new ArrayList<>();

        // 生成date枚举
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS + ";");
        build(headerInfoList, "DateTimePatternEnum", Constants.PATH_ENUMS);

        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_UTILS + ";");
        build(headerInfoList, "DateUtils", Constants.PATH_UTILS);

        // 生成baseMapper
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_MAPPER + ";");
        build(headerInfoList, "BaseMapper", Constants.PATH_MAPPER);

        // 生成PageSize枚举
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS + ";");
        build(headerInfoList, "PageSize", Constants.PATH_ENUMS);

        // 生成SimplePage
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_QUERY + ";");
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".PageSize;");
        build(headerInfoList, "SimplePage", Constants.PATH_QUERY);

        // 生成BaseQuery
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_QUERY + ";");
        build(headerInfoList, "BaseQuery", Constants.PATH_QUERY);

        // 生成PaginationResultVO
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_VO + ";");
        build(headerInfoList,"PaginationResultVO",Constants.PATH_VO);

        // 生成ResponseCodeEnum
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS + ";");
        build(headerInfoList,"ResponseCodeEnum",Constants.PATH_ENUMS);

        // 生成ResponseVO
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_VO + ";");
        build(headerInfoList,"ResponseVO",Constants.PATH_VO);

        // 生成BusinessException
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_EXCEPTION + ";");
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;");
        build(headerInfoList,"BusinessException",Constants.PATH_EXCEPTION);

        // 生成ABaseController
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_CONTROLLER + ";");
        headerInfoList.add("import " + Constants.PACKAGE_VO + ".ResponseVO;");
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;");
        build(headerInfoList,"ABaseController",Constants.PATH_CONTROLLER);

        // 生成AGlobalExceptionHandlerController
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_CONTROLLER + ";");
        headerInfoList.add("import " + Constants.PACKAGE_CONTROLLER + ".ABaseController;\n" +
                "import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;\n" +
                "import " + Constants.PACKAGE_VO +  ".ResponseVO;\n" +
                "import " + Constants.PACKAGE_EXCEPTION + ".BusinessException;\n" +
                "\n" +
                "import org.slf4j.Logger;\n" +
                "import org.slf4j.LoggerFactory;\n" +
                "import org.springframework.dao.DuplicateKeyException;\n" +
                "import org.springframework.validation.BindException;\n" +
                "import org.springframework.web.bind.annotation.RestControllerAdvice;\n" +
                "import org.springframework.web.servlet.NoHandlerFoundException;\n" +
                "\n" +
                "import javax.servlet.http.HttpServletRequest;");
        build(headerInfoList,"AGlobalExceptionHandlerController",Constants.PATH_CONTROLLER);

    }

    private static void build(List<String> headerInfoList, String fileName, String outPutPath) {
        File folder = new File(outPutPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File javaFile = new File(outPutPath, fileName + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        InputStream in = null;
        InputStreamReader inr = null;
        BufferedReader br = null;
        try {
            out = new FileOutputStream(javaFile);
            outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            bw = new BufferedWriter(outw);

            String templatePath = BuildBase.class.getClassLoader().getResource("template/" + fileName + ".txt").getPath();
            in = new FileInputStream(templatePath);
            inr = new InputStreamReader(in, StandardCharsets.UTF_8);
            br = new BufferedReader(inr);

            for (String head : headerInfoList) {
                bw.write(head);
                bw.newLine();
                bw.newLine();

            }
            String lineInfo = null;
            while ((lineInfo = br.readLine()) != null) {
                bw.write(lineInfo);
                bw.newLine();
            }
            bw.flush();
        } catch (Exception e) {
            logger.error("生成基础类:{},失败", fileName, e);
        } finally {
            if (br != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
