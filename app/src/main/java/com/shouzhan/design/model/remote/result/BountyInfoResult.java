package com.shouzhan.design.model.remote.result;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lijie on 2018/12/3
 */
public class BountyInfoResult {

    @SerializedName("today_income")
    private double todayIncome;

    @SerializedName("available_balance")
    private double availableBalance;

    @SerializedName("bank_card")
    private String bankCard;
    @SerializedName("bank_name")
    private String bankName;
    @SerializedName("bank_logo")
    private String bankLogo;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("single_min_limit")
    private double singleMinLimit;
    @SerializedName("single_max_limit")
    private double singleMaxLimit;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("status_message")
    private String statusMessage;

    public double getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(double todayIncome) {
        this.todayIncome = todayIncome;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getSingleMinLimit() {
        return singleMinLimit;
    }

    public void setSingleMinLimit(double singleMinLimit) {
        this.singleMinLimit = singleMinLimit;
    }

    public double getSingleMaxLimit() {
        return singleMaxLimit;
    }

    public void setSingleMaxLimit(double singleMaxLimit) {
        this.singleMaxLimit = singleMaxLimit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
