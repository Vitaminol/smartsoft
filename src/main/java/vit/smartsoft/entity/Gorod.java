package vit.smartsoft.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Gorod implements Serializable {
private static final long serialVersionUID = 1L;
	
	private String ssoid;
	private Long ts;
	private String grp;
	private String type;
	private String subtype;
	private String url;
	private String orgid;
	private String formid;
	private String code;
	private String ltpa;
	private String sudirresponse;
	private String ymdh;
	
	public String getSsoid() {
		return ssoid;
	}
	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
	}
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	public String getGrp() {
		return grp;
	}
	public void setGrp(String grp) {
		this.grp = grp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getFormid() {
		return formid;
	}
	public void setFormid(String formid) {
		this.formid = formid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLtpa() {
		return ltpa;
	}
	public void setLtpa(String ltpa) {
		this.ltpa = ltpa;
	}
	public String getSudirresponse() {
		return sudirresponse;
	}
	public void setSudirresponse(String sudirresponse) {
		this.sudirresponse = sudirresponse;
	}
	public String getYmdh() {
		return ymdh;
	}
	public void setYmdh(String ymdh) {
		this.ymdh = ymdh;
	}
	
}
