package com.tulinghuo.studyenglish.model;

import com.tulinghuo.studyenglish.vo.BookVO;

import java.io.Serializable;

public class Task implements Serializable {

    private static final long serialVersionUID = -7419435043690677460L;

    private int id;
    private int bookid;
    private int perDayWords;            // 每日学习单词数
    private int totalStudyWords;        // 已学习单词数
    private int remainDays;             // 剩余天数
    private int todayNewWords;          // 今日已学单词数
    private int todayTotalNewWords;     // 今日需要学单词数
    private int todayReviewWords;       // 今日已复习单词数
    private int todayTotalReviewWords;  // 今日需要复习单词数

    private BookVO book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getPerDayWords() {
        return perDayWords;
    }

    public void setPerDayWords(int perDayWords) {
        this.perDayWords = perDayWords;
    }

    public int getTotalStudyWords() {
        return totalStudyWords;
    }

    public void setTotalStudyWords(int totalStudyWords) {
        this.totalStudyWords = totalStudyWords;
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public int getTodayNewWords() {
        return todayNewWords;
    }

    public void setTodayNewWords(int todayNewWords) {
        this.todayNewWords = todayNewWords;
    }

    public int getTodayTotalNewWords() {
        return todayTotalNewWords;
    }

    public void setTodayTotalNewWords(int todayTotalNewWords) {
        this.todayTotalNewWords = todayTotalNewWords;
    }

    public int getTodayReviewWords() {
        return todayReviewWords;
    }

    public void setTodayReviewWords(int todayReviewWords) {
        this.todayReviewWords = todayReviewWords;
    }

    public int getTodayTotalReviewWords() {
        return todayTotalReviewWords;
    }

    public void setTodayTotalReviewWords(int todayTotalReviewWords) {
        this.todayTotalReviewWords = todayTotalReviewWords;
    }

    public BookVO getBook() {
        return book;
    }

    public void setBook(BookVO book) {
        this.book = book;
    }
}
