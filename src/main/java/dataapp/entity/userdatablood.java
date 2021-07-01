package dataapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the USERDATABLOOD database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Userdatablood.finduserid", query = "SELECT u FROM userdatablood u where u.userinformation =?1 order by u.id desc"),
		@NamedQuery(name = "Userdatablood.update_tg", query = "UPDATE userdatablood u set u.tg =?1 where u.userinformation =?2"),
		@NamedQuery(name = "Userdatablood.update_gtp", query = "UPDATE userdatablood u set u.gtp =?1 where u.userinformation =?2"),
		@NamedQuery(name = "Userdatablood.update_hdl", query = "UPDATE userdatablood u set u.hdl =?1 where u.userinformation =?2"),
		@NamedQuery(name = "Userdatablood.update_ldl", query = "UPDATE userdatablood u set u.ldl =?1 where u.userinformation =?2"),
		@NamedQuery(name = "Userdatablood.update_fpg", query = "UPDATE userdatablood u set u.fpg =?1 where u.userinformation =?2") })
public class userdatablood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable = false)
	private int id;

	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	private double tg;

	private double gtp;

	private double hdl;

	private double ldl;

	@Column(name = "MODIFIED_TIME")
	private Timestamp modifiedTime;

	private double fpg;

	@ManyToOne
	@JoinColumn(name = "USERID")
	private userinformation userinformation;

	public userdatablood() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTg() {
		return this.tg;
	}

	public void setTg(double tg) {
		this.tg = tg;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public double getGtp() {
		return this.gtp;
	}

	public void setGtp(double gtp) {
		this.gtp = gtp;
	}

	public double getHdl() {
		return this.hdl;
	}

	public void setHdl(double hdl) {
		this.hdl = hdl;
	}

	public double getLdl() {
		return this.ldl;
	}

	public void setLdl(double ldl) {
		this.ldl = ldl;
	}

	@JsonIgnore
	public Timestamp getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public double getFpg() {
		return this.fpg;
	}

	public void setFpg(double fpg) {
		this.fpg = fpg;
	}

	@JsonIgnore
	public userinformation getUserinformation() {
		return this.userinformation;
	}

	public void setUserinformation(userinformation userinformation) {
		this.userinformation = userinformation;
	}

}