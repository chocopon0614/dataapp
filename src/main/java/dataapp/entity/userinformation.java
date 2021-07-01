package dataapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The persistent class for the USERINFORMATION database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "userinformation.findbyusername", query = "SELECT u FROM userinformation u where u.username =?1"),
		@NamedQuery(name = "userinformation.deletebyusername", query = "DELETE FROM userinformation u where u.username =?1") })
public class userinformation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable = false)
	private int id;

	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Column(name = "MODIFIED_TIME")
	private Timestamp modifiedTime;

	@Column(name = "\"PASSWORD\"")
	private String password;

	private String username;

	// bi-directional many-to-one association to Userdata
	@OneToMany(mappedBy = "userinformation")
	private List<userdata> userdata;

	public userinformation() {
	}

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

	public Timestamp getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<userdata> getUserdata() {
		return this.userdata;
	}

	public void setUserdata(List<userdata> userdata) {
		this.userdata = userdata;
	}

	public userdata addUserdata(userdata userdata) {
		getUserdata().add(userdata);
		userdata.setUserinformation(this);

		return userdata;
	}

	public userdata removeUserdata(userdata userdata) {
		getUserdata().remove(userdata);
		userdata.setUserinformation(null);

		return userdata;
	}

}