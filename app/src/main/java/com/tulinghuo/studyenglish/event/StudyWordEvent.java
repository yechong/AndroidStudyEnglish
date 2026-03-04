package com.tulinghuo.studyenglish.event;

public class StudyWordEvent {

    private int taskId;

    public StudyWordEvent(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}