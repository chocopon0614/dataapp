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
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the USERDATA database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Userdata.findAll", query="SELECT u FROM Userdata u"),
@NamedQuery(name="Userdata.findUserid", query="SELECT u FROM Userdata u where u.userinformation =?1 order by u.id"),
@NamedQuery(name="Userdata.findUserid_desc", query="SELECT u FROM Userdata u where u.userinformation =?1 order by u.id desc")
})
public class Userdata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="CREATE_TIME")
	@JsonProperty("Registration time")
	private Timestamp createTime;

	@JsonProperty("Height")
	private double height;

	@Column(name="MODIFIED_TIME")
	private Timestamp modifiedTime;

	@JsonProperty("Weight")
	private double weight;

	//bi-directional many-to-one association to Userinformation
	@ManyToOne
	@JoinColumn(name="USERID")
	private Userinformation userinformation;

	public Userdata() {
	}

	@JsonIgnore
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@JsonIgnore
	public Timestamp getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@JsonIgnore
	public Userinformation getUserinformation() {
		return this.userinformation;
	}

	public void setUserinformation(Userinformation userinformation) {
		this.userinformation = userinformation;
	}

}