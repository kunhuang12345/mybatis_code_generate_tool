package com.hk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.InflaterInputStream;

public class PropertiesUtils {
    private static Properties props = new Properties();
    private static Map<String,String> PROPER_MAP = new ConcurrentHashMap<String, String>();

    static {
        InputStream is = null;
        try{

            // 使用类加载器将application.yml文件加载到is，理论上来说，任何一个类的类加载器都可以用来获取任意一个资源文件
            is = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");

            // 它调用 props.load(is) 方法来从这个输入流中加载属性列表
            assert is != null;
            props.load(new InputStreamReader(is, StandardCharsets.UTF_8));

            // 将application.yml文件中的内容读入以key-value方式读入Map中
            Iterator<Object> iterator = props.keySet().iterator();
            while (iterator.hasNext()){
                String key = (String) iterator.next();
                PROPER_MAP.put(key,props.getProperty(key));
            }

        }catch (Exception e){
            if (is != null){
                try {
                    is.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getString(String key){
        return PROPER_MAP.get(key);
    }

    public static void main(String[] args) {
        System.out.println(getString("db.driver.name"));
        System.out.println(getString("db.username"));
        System.out.println(getString("db.password"));
        System.out.println(getString("db.url") );
    }
}
