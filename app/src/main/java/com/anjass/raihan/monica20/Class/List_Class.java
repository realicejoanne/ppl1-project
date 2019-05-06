package com.anjass.raihan.monica20.Class;

import java.util.Date;
import java.util.HashMap;

public class List_Class {

    String id,
        isiPesan = "";
    boolean isChecked;
    HashMap<String, Object> timestamp;
    Date dueDate;

    public List_Class() {
        //Empty container
    }

    public List_Class(String id, String isiPesan, boolean isChecked, HashMap<String, Object> timestamp, Date dueDate) {
        this.id = id;
        this.isiPesan = isiPesan;
        this.isChecked = isChecked;
        this.timestamp = timestamp;
        this.dueDate = dueDate;
    }

    public void setIsChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public String getIsiPesan() {
        return isiPesan;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public HashMap<String, Object> getTimestamp() {
        return timestamp;
    }
}