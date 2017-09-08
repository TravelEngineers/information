package com.zgs.information.utils;

import com.alibaba.fastjson.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/**
 * Created by Smily on 2017/8/29.
 */
public class YamlUtils {
    private static JSONObject loadYml(String location){
        try {
            Yaml yaml = new Yaml();
            Map<String,Object> yamlObj = (Map<String,Object>)yaml.load(YamlUtils.class.getClassLoader().getResourceAsStream(location));
            JSONObject yamlJSONObj=JSONObject.parseObject(JSONObject.toJSON(yamlObj).toString());
            return yamlJSONObj;
        }catch (Exception e){
            e.printStackTrace();;
        }
        return null;
    }
    public static String getValue(String key){
        return getValue("application.yml",key);
    }
    /**
     * 获取yml中的key对应的值
     * @param location yml文件名称
     * @param key  key值，以应为.分隔
     * @return
     */
    public static String getValue(String location,String key){
         JSONObject yml=loadYml(location);
         String[]keys=key.split("\\.");
         String value="";
         for(int i=0;i<keys.length;i++){
              if(i==keys.length-1){
                  value=yml.getString(keys[i]);
              }else {
                  yml=yml.getJSONObject(keys[i]);
              }
         }
         return value;
    }

    /**
     * 获取yml中的key对应的映射对象
     * @param location
     * @param key
     * @return
     */
    public static JSONObject getObject(String location,String key){
        JSONObject yml=loadYml(location);
        String[]keys=key.split("\\.");
        for(int i=0;i<keys.length;i++){
                yml=yml.getJSONObject(keys[i]);
        }
        return yml;
    }

    public static void main(String[] args) {
        System.out.println(YamlUtils.getValue("application.yml","information.task.struts2"));
        JSONObject yml=YamlUtils.getObject("application.yml","information.task");
        System.out.println(yml.getString("struts2"));
    }
}
