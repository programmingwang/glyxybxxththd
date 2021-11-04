package com.glyxybxhtxt.dataObject;


public class Ewm {
    // id
    private Integer id;
    // 区域id
    private Integer qid;
    // 二维码详细地点（不等于报修的详细地点）
    private String xxdd;
    // 报修区域
    private Bxqy qy;
    
    public Bxqy getQy() {
		return qy;
	}

	public void setQy(Bxqy qy) {
		this.qy = qy;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getXxdd() {
        return xxdd;
    }

    public void setXxdd(String xxdd) {
        this.xxdd = xxdd == null ? null : xxdd.trim();
    }
}