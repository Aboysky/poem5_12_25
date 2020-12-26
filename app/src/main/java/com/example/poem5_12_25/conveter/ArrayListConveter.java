package com.example.poem5_12_25.conveter;

import androidx.room.TypeConverter;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @Classname ArrayListConveter
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 19:03
 * @Version 1.0
 */
public class ArrayListConveter {

    @TypeConverter
    public String objectToString(ArrayList<String> lists) {
        return JSON.toJSONString(lists);
    }

    @TypeConverter
    public ArrayList<String> stringToObject(String json) {
        ArrayList arrayList = JSON.parseObject(json, ArrayList.class);
        ArrayList<String> arrayList1 = new ArrayList<>();
        for (Object o : arrayList) {
            arrayList1.add((String) o);
        }
        return arrayList1;
    }

}
