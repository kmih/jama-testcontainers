package com.jamatalks.citizen;

import java.math.BigDecimal;
import java.util.Date;

public class Fine {

    private BigDecimal sum;
    private Date date;
    private boolean paid;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
