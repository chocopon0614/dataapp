package dataapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the USERDATA database table.
 * 
 */
@MappedSuperclass
public class AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Column(name = "MODIFIED_TIME")
	private Timestamp modifiedTime;

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@JsonIgnore
	public Timestamp getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@PrePersist
	public void prePersist() {
		createTime = new Timestamp(System.currentTimeMillis());
		modifiedTime = new Timestamp(System.currentTimeMillis());
	}

	@PreUpdate
	public void preUpdate() {
		modifiedTime = new Timestamp(System.currentTimeMillis());
	}

}