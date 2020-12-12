package com.glyxybxhtxt.dataObject;

import java.util.Date;


public class Bxd {
    private Integer id;

    private Date sbsj;

	private Integer eid;

    private String xxdd;

    private String yysj;

    private String bxlb;

    private String bxnr;

    private String tp;

    private String sp;

    private String sbr;

    private String sbrsj;

    private String sbrxh;

    private String hc;

    private String gs;

    private String jid;

    private String shy1;

    private Integer shy1state;

    private String shy2;

    private Integer shy2state;

    private Date wxsj;

    private String cxsy;

    private String pj;

    private String pjnr;

    private String pjzj;

    private Integer state;
    private String qy;
    private String qylb;
    private String xq;
    private String ewmdd;
    public String getEwmdd() {
		return ewmdd;
	}

    public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public void setEwmdd(String ewmdd) {
		this.ewmdd = ewmdd;
	}
	private Shy s1;
	private Shy s2;
	
	public Shy getS1() {
		return s1;
	}

	public void setS1(Shy s1) {
		this.s1 = s1;
	}

	public Shy getS2() {
		return s2;
	}

	public void setS2(Shy s2) {
		this.s2 = s2;
	}
	private Jdr j;
    public Jdr getJ() {
		return j;
	}

	public void setJ(Jdr j) {
		this.j = j;
	}

	public String getQy() {
		return qy;
	}

	public void setQy(String qy) {
		this.qy = qy;
	}

	public String getQylb() {
		return qylb;
	}

	public void setQylb(String qylb) {
		this.qylb = qylb;
	}

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSbsj() {
        return sbsj;
    }

    public void setSbsj(Date sbsj) {
        this.sbsj = sbsj;
    }

   

    public String getXxdd() {
        return xxdd;
    }

    public void setXxdd(String xxdd) {
        this.xxdd = xxdd == null ? null : xxdd.trim();
    }

    public String getYysj() {
        return yysj;
    }

    public void setYysj(String yysj) {
        this.yysj = yysj == null ? null : yysj.trim();
    }

    public String getBxlb() {
        return bxlb;
    }

    public void setBxlb(String bxlb) {
        this.bxlb = bxlb == null ? null : bxlb.trim();
    }

    public String getBxnr() {
        return bxnr;
    }

    public void setBxnr(String bxnr) {
        this.bxnr = bxnr == null ? null : bxnr.trim();
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp == null ? null : tp.trim();
    }

    public String getSbr() {
        return sbr;
    }

    public void setSbr(String sbr) {
        this.sbr = sbr == null ? null : sbr.trim();
    }

    public String getSbrsj() {
        return sbrsj;
    }

    public void setSbrsj(String sbrsj) {
        this.sbrsj = sbrsj == null ? null : sbrsj.trim();
    }

    public String getSbrxh() {
        return sbrxh;
    }

    public void setSbrxh(String sbrxh) {
        this.sbrxh = sbrxh == null ? null : sbrxh.trim();
    }

    public String getHc() {
        return hc;
    }

    public void setHc(String hc) {
        this.hc = hc == null ? null : hc.trim();
    }

    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs == null ? null : gs.trim();
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid == null ? null : jid.trim();
    }

    public String getShy1() {
        return shy1;
    }

    public void setShy1(String shy1) {
        this.shy1 = shy1 == null ? null : shy1.trim();
    }

    public Integer getShy1state() {
        return shy1state;
    }

    public void setShy1state(Integer shy1state) {
        this.shy1state = shy1state;
    }

    public String getShy2() {
        return shy2;
    }

    public void setShy2(String shy2) {
        this.shy2 = shy2 == null ? null : shy2.trim();
    }

    public Integer getShy2state() {
        return shy2state;
    }

    public void setShy2state(Integer shy2state) {
        this.shy2state = shy2state;
    }

    public Date getWxsj() {
        return wxsj;
    }

    public void setWxsj(Date wxsj) {
        this.wxsj = wxsj;
    }

    public String getCxsy() {
        return cxsy;
    }

    public void setCxsy(String cxsy) {
        this.cxsy = cxsy == null ? null : cxsy.trim();
    }

    public String getPj() {
        return pj;
    }

    public void setPj(String pj) {
        this.pj = pj == null ? null : pj.trim();
    }

    public String getPjnr() {
        return pjnr;
    }

    public void setPjnr(String pjnr) {
        this.pjnr = pjnr == null ? null : pjnr.trim();
    }

    public String getPjzj() {
        return pjzj;
    }

    public void setPjzj(String pjzj) {
        this.pjzj = pjzj == null ? null : pjzj.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }
}