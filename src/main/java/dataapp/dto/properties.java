package dataapp.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class properties {
	private String introspectionUrl;
	private String clientId;
	private long jwtExpiredTime;

	public String getIntrospectionUrl() {
		return introspectionUrl;
	}

	public void setIntrospectionUrl(String introspectionUrl) {
		this.introspectionUrl = introspectionUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public long getJwtExpiredTime() {
		return jwtExpiredTime;
	}

	public void setJwtExpiredTime(long jwtExpiredTime) {
		this.jwtExpiredTime = jwtExpiredTime;
	}

}