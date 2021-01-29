package org.vico.auth.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.*;
import lombok.experimental.Accessors;

import org.vico.auth.annotation.Json;
import org.vico.common.constant.StatusCode;

import java.lang.reflect.Field;
import java.util.*;

@Data
@ToString
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transfer{

    private int code = 1;
    private String msg;
    private StatusCode status;
    private Map<String, Object> attrs;

    @Json
    public static class JsonMapWrapper{
        public Map<String, Object> value;
        public JsonMapWrapper(Map<String, Object> value){
            this.value = value;
        }
    }

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

    private JSONObject analyseField(Object object, Set<String> excludes, int recursiveLevel){
        JSONObject json = new JSONObject();
        try {
            if(object instanceof Map){
                ((Map<String, ? extends Object>) object).forEach((k, v) -> {
                    if(!(recursiveLevel > 0 && excludes.contains(k))){
                        if(v instanceof Map || v.getClass().getClassLoader() != null){
                            json.put(k, analyseField(v, excludes, recursiveLevel - 1));
                        }else{
                            json.put(k, v);
                        }
                    }
                });
                return json;
            }
            Class<?> clazz = object.getClass();
            if (clazz.getClassLoader() != null){
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields){
                    if(recursiveLevel > 0 && excludes.contains(field.getName())){
                        continue;
                    }
                    field.setAccessible(true);
                    Object subObject = field.get(object);
                    if(subObject instanceof Map || subObject.getClass().getClassLoader() != null){
                        json.put(field.getName(), analyseField(subObject, excludes, recursiveLevel - 1));
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


    public JSONObject toJson(Object... objects) {
        JSONObject json = new JSONObject();
        for(Object object : objects){
            try{
                if(object instanceof JsonMapWrapper){
                    return analyseField(object.getClass().getDeclaredField("value").get(object), new HashSet<>(), 1);
                }
            }catch (NoSuchFieldException | IllegalAccessException e){
                e.printStackTrace();
            }
            Class<?> clazz = object.getClass();
            Json annotation = null;
            int level = 1;
            Set<String> excludes;
            if((annotation = clazz.getAnnotation(Json.class)) != null){
                excludes = new HashSet<>(Arrays.asList(annotation.excludeField()));
                level = annotation.recursiveLevel();
            }else{
                excludes = new HashSet<>();
            }
            JSONObject subJson = analyseField(object, excludes, level);
            subJson.forEach((k, v) -> { json.put(k, v); });
        }
        return json;
    }

    public String build() {
        JSONObject result = new JSONObject();
        if(attrs != null){
            attrs.forEach((k, v) -> {
                if(v instanceof JsonMapWrapper || v.getClass().getClassLoader() != null){
                    result.put(k, toJson(v));
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