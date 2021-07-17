package dataapp.entity;

import java.io.Serializable;

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
		@NamedQuery(name = "UserData.findByUserid", query = "select u from UserData u where u.userInformation =?1 order by u.id"),
//		@NamedQuery(name = "UserData.findUseridDesc", query = "select u from UserData u where u.userInformation =?1 order by u.id desc"),
		@NamedQuery(name = "UserData.findByUseridSelected", query = "select u.createTime, u.height, u.weight from UserData u where u.userInformation =?1 order by u.id desc")
//		@NamedQuery(name = "UserData.selectData", query = "select u from UserData u where u.userInformation =?1 and u.id =?2"),
//		@NamedQuery(name = "UserData.deleteData", query = "delete from UserData u where u.userInformation =?1 and u.id =?2") 
})
public class UserData extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable = false)
	private Integer id;

	@JsonProperty("Height")
	private double height;

	@JsonProperty("Weight")
	private double weight;

	// bi-directional many-to-one association to UserInformation
	@ManyToOne
	@JoinColumn(name = "USERID")
	private UserInformation userInformation;

	public UserData() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@JsonIgnore
	public UserInformation getUserinformation() {
		return this.userInformation;
	}

	public void setUserinformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

}