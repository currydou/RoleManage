package com.example.administrator.rolemanage.model.base;

/**
 * Created by lib on 2016/12/28.
 */

public class JsonRequest<T> extends BaseModel {
    private T Content;
    private Head Head;

    public T getContent() {
        return Content;
    }

    public void setContent(T content) {
        Content = content;
    }

    public Head getHead() {
        return Head;
    }

    public void setHead(Head head) {
        Head = head;
    }
}
