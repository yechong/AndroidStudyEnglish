package com.tulinghuo.studyenglish.model;

public class Book {

    private int id;
    private String name;
    private String cover;
    private int words;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }
}
