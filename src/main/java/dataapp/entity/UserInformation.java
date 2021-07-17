package dataapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The persistent class for the USERINFORMATION database table.
 * 
 */
@Entity
@NamedQuery(name = "UserInformation.findByUsername", query = "select u from UserInformation u where u.userName =?1")

//@NamedQueries(
//		@NamedQuery(name = "UserInformation.findByUsername", query = "select u from UserInformation u where u.userName =?1") })
//		@NamedQuery(name = "UserInformation.deleteUsername", query = "delete from UserInformation u where u.userName =?1") })
public class UserInformation extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable = false)
	private int id;

	@Column(name = "\"PASSWORD\"")
	private String passWord;

	private String userName;

	// bi-directional many-to-one association to UserData
	@OneToMany(mappedBy = "userInformation")
	private List<UserData> userData;

	public UserInformation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.passWord;
	}

	public void setPassword(String passWord) {
		this.passWord = passWord;
	}

	public String getUsername() {
		return this.userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public List<UserData> getUserdata() {
		return this.userData;
	}

	public void setUserdata(List<UserData> userData) {
		this.userData = userData;
	}

	public UserData addUserdata(UserData userData) {
		getUserdata().add(userData);
		userData.setUserinformation(this);

		return userData;
	}

	public UserData removeUserdata(UserData userData) {
		getUserdata().remove(userData);
		userData.setUserinformation(null);

		return userData;
	}

}