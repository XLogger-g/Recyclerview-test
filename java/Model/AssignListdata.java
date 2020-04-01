package com.example.schoolteacher.Model;

public class AssignListdata {

    public String getTitle() {
        return titleas;
    }

    public void setTitle(String title) {
        this.titleas = title;
    }

    public String getDesc() {
        return descas;
    }

    public void setDesc(String desc) {
        this.descas = desc;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String id;
    public String titleas;
    public String descas;

    public AssignListdata() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public AssignListdata(String id,String title, String desc) {
        this.id=id;
        this.titleas = title;
        this.descas = desc;

    }
}
