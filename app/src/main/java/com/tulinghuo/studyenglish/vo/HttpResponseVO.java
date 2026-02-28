package com.tulinghuo.studyenglish.vo;

public class HttpResponseVO {

    private int ret;
    private String msg;

    public boolean isRequestSuccess() {
        return ret == 0;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
