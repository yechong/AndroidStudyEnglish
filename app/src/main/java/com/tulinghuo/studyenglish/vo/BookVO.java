package com.tulinghuo.studyenglish.vo;

import java.io.Serializable;

public class BookVO implements Serializable {

    private static final long serialVersionUID = -1564808227779414014L;

    private String name;
    private String cover;
    private int words;

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
