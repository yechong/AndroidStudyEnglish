package com.tulinghuo.studyenglish.event;

public class BookCategoryChangeEvent {
    private int categoryId;

    public BookCategoryChangeEvent(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
