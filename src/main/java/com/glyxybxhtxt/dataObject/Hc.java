package com.glyxybxhtxt.dataObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hc {
    private Integer id;

    private String mc;

    private Double jg;

    private String dw;

    private Integer kc;

    private String lb;

    private String xh;

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc == null ? null : mc.trim();
    }

    public Double getJg() {
        return jg;
    }

    public void setJg(Double jg) {
        this.jg = jg;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }

    public Integer getKc() {
        return kc;
    }

    public void setKc(Integer kc) {
        this.kc = kc;
    }
}