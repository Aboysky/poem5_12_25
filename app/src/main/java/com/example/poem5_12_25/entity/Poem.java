package com.example.poem5_12_25.entity;

import android.os.Build;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.poem5_12_25.conveter.ArrayListConveter;
import com.example.poem5_12_25.pojo.PoemPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Classname Poem
 * @Description TODO
 * @Date 2020/12/25 16:30
 * @Created by Huan
 */
@Entity
@TypeConverters(ArrayListConveter.class)
public class Poem {
    private static final String TAG = "Poem";
    @PrimaryKey(autoGenerate = false)
    private long id;
    private String name = "";
    private String author = "";
    private ArrayList<String> content = new ArrayList<>();
    private String years;

    @Override
    public String toString() {
        return "Poem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", content=" + Arrays.toString(content.toArray()) +
                ", years='" + years + '\'' +
                '}';
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public Poem() {
    }
    public Poem(PoemPojo poemPojo) {
        this.author = poemPojo.getAuthor();
        this.id = poemPojo.getId();
        this.content = new ArrayList<String>(Arrays.asList(poemPojo.getContent().split("\\n")));
        this.name = poemPojo.getName();
        this.years = poemPojo.getYears();
    }



    public Poem(long id, String author, String name, JSONArray contentArray) {
        this.id = id;
        this.setAuthor(author);
        this.setName(name);
        this.setContent(contentArray);
    }

    public Poem(String author, String name, JSONArray contentArray) {
        this.setAuthor(author);
        this.setName(name);
        this.setContent(contentArray);
    }

    public Poem(long id, String author, String name, String csvContent) {
        this.id = id;
        this.setAuthor(author);
        this.setName(name);
        this.setContent(csvContent);
    }

    public Poem(String author, String name, String csvContent) {
        this.setAuthor(author);
        this.setName(name);
        this.setContent(csvContent);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<String> getContent() {
        return this.content;
    }

    // 返回以逗号分割的内容
    public String getContentCsv() {
        // 根据不同安卓系统版本做的优化
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return String.join(",", this.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return this.content.stream().collect(Collectors.joining(","));
        } else {
            StringBuilder joinContent = new StringBuilder();
            for (String s : this.content) {
                joinContent.append(s).append(",");
            }
            // 删除最后一个换行符
            joinContent.deleteCharAt(joinContent.length() - 1);
            return joinContent.toString();
        }
    }

    public void setContent(JSONArray contentArray) {
        this.content.clear();
        for (int i = 0; i < contentArray.length(); i++) {
            try {
                this.content.add(contentArray.getString(i));
            } catch (JSONException ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
    }

    public void setContent(ArrayList<String> arrayList) {
        this.content.clear();
        this.content = arrayList;
    }


    public void setContent(String csvContent) {
        String[] contentList = csvContent.split(",");
        this.content.addAll(Arrays.asList(contentList));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("author", this.author);
            jsonObject.put("title", this.name);
            jsonObject.put("content", this.getContentCsv());
        } catch (JSONException ex) {
            Log.e(TAG, ex.getMessage());
        }
        return jsonObject.toString();
    }

    public static Poem parseJsonString(String jsonString) {
        Poem poem = new Poem();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            poem.setId(jsonObject.getInt("id"));
            poem.setAuthor(jsonObject.getString("author"));
            poem.setName(jsonObject.getString("title"));
            poem.setContent(jsonObject.getString("content"));
        } catch (JSONException ex) {
            Log.e(TAG, ex.getMessage());
        }
        return poem;
    }
}
