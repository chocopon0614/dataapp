package dataapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the USERDATABLOOD database table.
 * 
 */
@Entity
@NamedQuery(name = "UserDatablood.findByUserid", query = "select u from UserDatablood u where u.userInformation =?1")
public class UserDatablood extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable = false)
	private int id;

	private double tg;

	private double gtp;

	private double hdl;

	private double ldl;

	private double fpg;

	@ManyToOne
	@JoinColumn(name = "USERID")
	private UserInformation userInformation;

	public UserDatablood() {
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

	public double getFpg() {
		return this.fpg;
	}

	public void setFpg(double fpg) {
		this.fpg = fpg;
	}

	@JsonIgnore
	public UserInformation getUserinformation() {
		return this.userInformation;
	}

	public void setUserinformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

}