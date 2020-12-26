package com.example.poem5_12_25.pojo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Classname PoemPojo
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 12:30
 * @Version 1.0
 */
public class PoemPojo {
    private static final String TAG = "Poem";

    private long id;
    private String name = "";
    private String author = "";
    private String content = "";
    private String years = "";

    @Override
    public String toString() {
        return "Poem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", content=" + content +
                ", years='" + years + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }
}
