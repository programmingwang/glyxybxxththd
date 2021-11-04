package com.glyxybxhtxt.dataObject;

import java.util.Date;

public class Qdb {
    // id ：
    private Integer id;
    // 签到时间
    private Date qdsj;
    // 审核员id
    private String shyid;
    // 校区
    private String xq;
    // 1签到，2签退
    private Integer state;
    
    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getQdsj() {
        return qdsj;
    }

    public void setQdsj(Date qdsj) {
        this.qdsj = qdsj;
    }

    public String getShyid() {
        return shyid;
    }

    public void setShyid(String shyid) {
        this.shyid = shyid == null ? null : shyid.trim();
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq == null ? null : xq.trim();
    }
}