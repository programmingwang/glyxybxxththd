package com.glyxybxhtxt.dataObject;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Shy {
    private String ybid;

    private String gh;

    private String xm;

    private Integer zw;

    private String xq;

    private String x;

    private String y;

    public String getYbid() {
        return ybid;
    }

    public void setYbid(String ybid) {
        this.ybid = ybid == null ? null : ybid.trim();
    }

    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh == null ? null : gh.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }

    public Integer getZw() {
        return zw;
    }

    public void setZw(Integer zw) {
        this.zw = zw;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq == null ? null : xq.trim();
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x == null ? null : x.trim();
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y == null ? null : y.trim();
    }
}