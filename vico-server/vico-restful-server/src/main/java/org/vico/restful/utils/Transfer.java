package org.vico.restful.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.vico.common.annotation.Json;
import org.vico.common.constant.StatusCode;

import java.lang.reflect.Field;
import java.util.*;

@Data
@ToString
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    private int code = 1;
    private String msg;
    private StatusCode status;
    private Map<String, Object> attrs;


    public Transfer(StatusCode statusCode){
        this.status = statusCode;
    }

    public Transfer param(String key, Object value){
        if(attrs == null)
            attrs = new HashMap<>();
        attrs.put(key, value);
        return this;
    }

    public Transfer clearParams(){
        if(attrs != null){
            attrs.clear();
        }
        return this;
    }

    private Json checkAnnotation(Object object){
        Class<?> clazz = object.getClass();
        Json annotation = null;
        if((annotation = clazz.getAnnotation(Json.class)) != null){
            return annotation;
        }
        return null;
    }

    private JSON analyseField(Object object, Set<String> excludes){
        JSONObject json = new JSONObject();
        try {
            // List
            if(object instanceof List){
                JSONArray array = new JSONArray();
                ((List<?>) object).forEach((item) -> {
                    array.add(analyseField(item, excludes));
                });
                return array;
            }

            // Map
            if(object instanceof Map){
                ((Map<String, ? extends Object>) object).forEach((k, v) -> {
                    if(!(excludes.contains(k))){
                        if(v instanceof Map || v instanceof List || v.getClass().getClassLoader() != null){
                            json.put(k, analyseField(v, excludes));
                        }else{
                            json.put(k, v);
                        }
                    }
                });
                return json;
            }

            // Object
            Json annotation = checkAnnotation(object);
            if(annotation != null){
                for(String str : annotation.excludeField())
                    excludes.add(str);
            }
            System.out.println(excludes);
            Class<?> clazz = object.getClass();
            if (clazz.getClassLoader() != null){
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields){
                    if(excludes.contains(field.getName())){
                        continue;
                    }
                    field.setAccessible(true);
                    Object subObject = field.get(object);
                    if(subObject instanceof Map || subObject instanceof List || subObject.getClass().getClassLoader() != null){
                        json.put(field.getName(), analyseField(subObject, excludes));
                    }else{
                        json.put(field.getName(), subObject);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return json;
    }


    public String build() {
        JSONObject result = new JSONObject();
        Set<String> excludes = new HashSet<>();
        if(attrs != null){
            attrs.forEach((k, v) -> {
                if(v instanceof Map || v instanceof List || v.getClass().getClassLoader() != null){
                    result.put(k, analyseField(v, excludes));
                }else{
                    result.put(k, v);
                }
            });
        }
        try{
            if(status != null){
                this.code = status.getCode();
                this.msg = status.getMsg();
            }
            result.put("code", code);
            if(msg != null && !"".equals(msg)){
                result.put("msg", msg);
            }
        }catch(JSONException e){
            return null;
        }
        return result.toString();
    }
}