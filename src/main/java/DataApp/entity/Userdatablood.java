package DataApp.entity;

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
@NamedQuery(name="Userdatablood.findAll", query="SELECT u FROM Userdatablood u"),
@NamedQuery(name="Userdatablood.findUserid", query="SELECT u FROM Userdatablood u where u.userinformation =?1 order by u.id desc"),
@NamedQuery(name="Userdatablood.update_TG", query="UPDATE Userdatablood u set u.tg =?1 where u.userinformation =?2"),
@NamedQuery(name="Userdatablood.update_GTP", query="UPDATE Userdatablood u set u.gtp =?1 where u.userinformation =?2"),
@NamedQuery(name="Userdatablood.update_HDL", query="UPDATE Userdatablood u set u.hdl =?1 where u.userinformation =?2"),
@NamedQuery(name="Userdatablood.update_LDL", query="UPDATE Userdatablood u set u.ldl =?1 where u.userinformation =?2"),
@NamedQuery(name="Userdatablood.update_FPG", query="UPDATE Userdatablood u set u.fpg =?1 where u.userinformation =?2")
})
public class Userdatablood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable=false)
	private int id;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	private double tg;

	private double gtp;

	private double hdl;

	private double ldl;

	@Column(name="MODIFIED_TIME")
	private Timestamp modifiedTime;

	private double fpg;

	@ManyToOne
	@JoinColumn(name="USERID")
	private Userinformation userinformation;

	public Userdatablood() {
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
	public Userinformation getUserinformation() {
		return this.userinformation;
	}

	public void setUserinformation(Userinformation userinformation) {
		this.userinformation = userinformation;
	}

}