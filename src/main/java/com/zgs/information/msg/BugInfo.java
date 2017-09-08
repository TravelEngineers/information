package com.zgs.information.msg;

import java.util.Date;

/**
 * @author Shmily
 * @email shmily_zgs@163.com
 * @date 2017/9/6 14:05
 */
public class BugInfo {
    private String name;
    private String url;
    private String title;
    private String type;
    private String plat;
    private Date   findDate;

    public BugInfo(String name, String url, String title, String type, String plat, Date findDate) {
        this.name = name;
        this.url = url;
        this.title = title;
        this.type = type;
        this.plat = plat;
        this.findDate = findDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public Date getFindDate() {
        return findDate;
    }

    public void setFindDate(Date findDate) {
        this.findDate = findDate;
    }
}
