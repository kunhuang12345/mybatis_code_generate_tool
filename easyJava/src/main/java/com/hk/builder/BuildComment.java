package com.hk.builder;

import com.hk.bean.Constants;
import com.hk.utils.DateUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildComment {
    public static void createClassComment(BufferedWriter bw,String classComment) throws IOException {
        bw.write("/**");
        bw.newLine();
        bw.write(" * @Description:" + classComment);
        bw.newLine();
        bw.write(" * @author:" + Constants.AUTHOR_COMMENT);
        bw.newLine();
        bw.write(" * @date:" + DateUtils.format(new Date(),DateUtils._YYYYMMDD));
        bw.newLine();
        bw.write(" */");
        bw.newLine();
    }

    public static void createFieldComment(BufferedWriter bw,String fieldComment) throws IOException {
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * " + (fieldComment == null? "":fieldComment));

        bw.newLine();
        bw.write("\t */");
        bw.newLine();
    }

    public static void createMethodComment(){

    }
}
