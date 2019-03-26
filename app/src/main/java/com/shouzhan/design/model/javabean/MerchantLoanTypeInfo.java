package com.shouzhan.design.model.javabean;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lijie on 2018/12/29
 */
public class MerchantLoanTypeInfo {

    /**
     * false未确认,true已确认
     * */
    @SerializedName("confirmed")
    private boolean confirmed;
    /**
     * 贷款产品类型 1, "极速云" 2, "首新" 3, "360"
     * */
    @SerializedName("loan_type")
    private int loanType;

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getLoanType() {
        return loanType;
    }

    public void setLoanType(int loanType) {
        this.loanType = loanType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
