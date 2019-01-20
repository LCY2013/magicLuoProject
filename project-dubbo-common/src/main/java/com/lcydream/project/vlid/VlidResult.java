package com.lcydream.project.vlid;

/**
 * VlidResult
 *
 * @author Luo Chun Yun
 * @date 2018/9/16 11:24
 */
public class VlidResult {

    private boolean isSuccess;

    private String massage;

    public VlidResult() {
    }

    public VlidResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public VlidResult(boolean isSuccess, String massage) {
        this.isSuccess = isSuccess;
        this.massage = massage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @Override
    public String toString() {
        return "VlidResult{" +
                "isSuccess=" + isSuccess +
                ", massage='" + massage + '\'' +
                '}';
    }
}
