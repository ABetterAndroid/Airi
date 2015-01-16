package com.joe.airi.model;

import java.util.List;

/**
 * Created by qiaorongzhu on 2015/1/15.
 */
public class DataResult<T> {

    public static final int OK = 0;
    //错误码
    public static final int NETWORK_INVALID = -1;

    int resultType;
    String resultcode;
    String reason;
    List<T> result;
    int error_code;

    public DataResult() {
    }

    public DataResult(List<T> result, int error_code) {
        this.result = result;
        this.error_code = error_code;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
