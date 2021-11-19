package com.glyxybxhtxt.dataObject;

import java.util.List;


public class Bxqy {
    // id ：
    private Integer id;
    // 区域名称
    private String qy;
    // 区域类别
    private String qylb;
    // 校区
    private String xq;
    // 后台管理x坐标
    private String x;
    // 后台管理Y坐标
    private String y;
    // 二维码集合
    private List<Ewm> ewm;
    // 保修单集合
    private List<Bxd> b;
    private int countb;

	public int getCountb() {
		return countb;
	}

	public void setCountb(int countb) {
		this.countb = countb;
	}

	public List<Bxd> getB() {
		return b;
	}

	public void setB(List<Bxd> b) {
		this.b = b;
	}

	public List<Ewm> getEwm() {
		return ewm;
	}

	public void setEwm(List<Ewm> ewm) {
		this.ewm = ewm;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy == null ? null : qy.trim();
    }

    public String getQylb() {
        return qylb;
    }

    public void setQylb(String qylb) {
        this.qylb = qylb == null ? null : qylb.trim();
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq == null ? null : xq.trim();
    }

}