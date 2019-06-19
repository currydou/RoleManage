package com.example.administrator.rolemanage.model.base;

import java.util.List;

/**
 */
public class RespListWrapper<T> extends BaseModel {

    private int number;
    private int size;
    private int numberOfElements;
    private int totalPages;
    private int totalElements;
    private List<T> content;
    private List<T> receiverCard;
    //
    private String currentMonth;
    private String applyMoneySum;

    //微币明细
    private int total;
    private List<T> microcoinDetail;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getMicrocoinDetail() {
        return microcoinDetail;
    }

    public void setMicrocoinDetail(List<T> microcoinDetail) {
        this.microcoinDetail = microcoinDetail;
    }

    public List<T> getReceiverCard() {
        return receiverCard;
    }

    public void setReceiverCard(List<T> receiverCard) {
        this.receiverCard = receiverCard;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getApplyMoneySum() {
        return applyMoneySum;
    }

    public void setApplyMoneySum(String applyMoneySum) {
        this.applyMoneySum = applyMoneySum;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
