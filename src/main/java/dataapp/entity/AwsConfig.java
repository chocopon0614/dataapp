package dataapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for the AwsConfig database table.
 * 
 */
@Entity
public class AwsConfig extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String userpoolid;

	private String clientid;

	private String accesskey;

	public String getUserpoolid() {
		return userpoolid;
	}

	public void setUserpoolid(String userpoolid) {
		this.userpoolid = userpoolid;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	private String secretKey;

	public AwsConfig() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}