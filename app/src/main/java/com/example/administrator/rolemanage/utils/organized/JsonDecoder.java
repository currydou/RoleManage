/**
 * Copyright  All right reserved by IZHUO.NET.
 */
package com.example.administrator.rolemanage.utils.organized;

import android.support.v4.util.Pair;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 主要用于json与对象之间的互相转换
 *
 * @author Changlei
 *         <p/>
 *         2014年7月29日
 */
public class JsonDecoder {

    /**
     * 把json格式的字符串转换成一个对象或者集合
     *
     * @param json json格式字符串
     * @param type 转换的类型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static <T> T jsonToObject(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    @SafeVarargs
    public static <T> T jsonToObject(String json, Type type, Pair<Type, Object>... pairs) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (Pair<Type, Object> pair : pairs) {
            gsonBuilder = gsonBuilder.registerTypeAdapter(pair.first, pair.second);
        }
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, type);
    }

    /**
     * 把一个对象转化成json格式字符串
     * @param object 需要转换的对象
     * @return 转换后的json格式字符串
     */
    public static String objectToJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    /**
     * 把json格式的字符串转换成一个对象或者集合（包含时间的）
     *
     * @param json json格式字符串
     * @param type 转换的类型
     * @return 转换后的对象
     */
    public static <T> T jsonToObject(String json, Type type, String pattern) {
        Gson gson = new GsonBuilder().setDateFormat(pattern).create();
        T x = gson.fromJson(json, type);
        return x;
    }

    /**
     * 把一个对象转化成json格式字符串（包含时间的）
     *
     * @param object 需要转换的对象
     * @return 转换后的json格式字符串
     */
    public static String objectToJson(Object object, String pattern) {
        Gson gson = new GsonBuilder().setDateFormat(pattern).create();
        String json = gson.toJson(object);
        return json;
    }

    private static JsonElement getJsonElement(String json) {
        return new JsonParser().parse(json);
    }

    public static boolean isJsonArray(String json) {
        JsonElement jsonElement = getJsonElement(json);
        return jsonElement.isJsonArray();
    }

    public static boolean isJsonNull(String json) {
        JsonElement jsonElement = getJsonElement(json);
        return jsonElement.isJsonNull();
    }

    public static boolean isJsonObject(String json) {
        JsonElement jsonElement = getJsonElement(json);
        return jsonElement.isJsonObject();
    }

    public static boolean isJsonPrimitive(String json) {
        JsonElement jsonElement = getJsonElement(json);
        return jsonElement.isJsonPrimitive();
    }

    @Deprecated
    public static String jsonGetValue(String result, String key) {
        try {
            return getJSONObject(result).getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(String result, String key) {
        try {
            return getJSONObject(result).getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getBoolean(String result, String key) {
        try {
            return getJSONObject(result).getBoolean(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static double getDouble(String result, String key) {
        try {
            return getJSONObject(result).getDouble(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getInt(String result, String key) {
        try {
            return getJSONObject(result).getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getLong(String result, String key) {
        try {
            return getJSONObject(result).getLong(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static JSONArray getJSONArray(String result, String key) {
        try {
            return getJSONObject(result).getJSONArray(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(String result, String key) {
        try {
            return getJSONObject(result).getJSONObject(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object get(String result, String key) {
        try {
            return getJSONObject(result).get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject getJSONObject(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将map数据解析出来，并拼接成json字符串
     *
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static JSONObject setJosn(Map<String, ?> map) throws Exception {
        JSONObject json = null;
        StringBuffer temp = new StringBuffer();
        if (!map.isEmpty()) {
            temp.append("{");
            // 遍历map
            Set<?> set = map.entrySet();
            Iterator<?> i = set.iterator();
            while (i.hasNext()) {
                Entry<String, ?> entry = (Entry<String, ?>) i.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                temp.append("\"" + key + "\":");
                if (value instanceof Map<?, ?>) {
                    temp.append(setJosn((Map<String, Object>) value) + ",");
                } else if (value instanceof List<?>) {
                    temp.append(setList((List<Map<String, Object>>) value)
                            + ",");
                } else {
                    temp.append(value + ",");
                }
            }
            if (temp.length() > 1) {
                temp = new StringBuffer(temp.substring(0, temp.length() - 1));
            }
            temp.append("}");
            json = new JSONObject(temp.toString());
        }
        return json;
    }

    /**
     * 将单个list转成json字符串
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String setList(List<Map<String, Object>> list)
            throws Exception {
        String jsonL = "";
        StringBuffer temp = new StringBuffer();
        temp.append("[");
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> m = list.get(i);
            if (i == list.size() - 1) {
                temp.append(setJosn(m));
            } else {
                temp.append(setJosn(m) + ",");
            }
        }
        if (temp.length() > 1) {
            temp = new StringBuffer(temp.substring(0, temp.length()));
        }
        temp.append("]");
        jsonL = temp.toString();
        return jsonL;
    }

    /**
     * 将整个json字符串解析，并放置到map<String,Object>中
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> getJosn(String jsonStr) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!TextUtils.isEmpty(jsonStr)) {
            JSONObject json = new JSONObject(jsonStr);
            Iterator<?> i = json.keys();
            while (i.hasNext()) {
                String key = (String) i.next();
                String value = json.getString(key);
                if (value.indexOf("{") == 0) {
                    map.put(key.trim(), getJosn(value));
                } else if (value.indexOf("[") == 0) {
                    map.put(key.trim(), getList(value));
                } else {
                    map.put(key.trim(), value.trim());
                }
            }
        }
        return map;
    }

    /**
     * 将单个json数组字符串解析放在list中
     *
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getList(String jsonStr)
            throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray ja = new JSONArray(jsonStr);
        for (int j = 0; j < ja.length(); j++) {
            String jm = ja.get(j) + "";
            if (jm.indexOf("{") == 0) {
                Map<String, Object> m = getJosn(jm);
                list.add(m);
            }
        }
        return list;
    }

}
