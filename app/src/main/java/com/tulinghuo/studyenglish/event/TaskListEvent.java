package com.tulinghuo.studyenglish.event;

public class TaskListEvent {

    private int listSize;

    public TaskListEvent(int listSize) {
        this.listSize = listSize;
    }

    public boolean existData() {
        return listSize > 0;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
}
