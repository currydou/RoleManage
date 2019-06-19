package com.example.administrator.rolemanage.model.base;


/**
 * Created by lib on 2016/9/12.
 */
public class Head extends BaseModel {
    private String Uid;
    private String Pws;
    private String Active_uid;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getPws() {
        return Pws;
    }

    public void setPws(String pws) {
        Pws = pws;
    }

    public String getActive_uid() {
        return Active_uid;
    }

    public void setActive_uid(String active_uid) {
        Active_uid = active_uid;
    }
}
