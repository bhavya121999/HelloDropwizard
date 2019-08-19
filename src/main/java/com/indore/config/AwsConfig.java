package com.indore.config;

import org.hibernate.validator.constraints.NotEmpty;

public class AwsConfig {
	@NotEmpty
	String accesskey;
	@NotEmpty
	String secretaccesskey;
	@NotEmpty
	String clientregion;
	@NotEmpty
	String bucketname;

	public String getClientregion() {
		return clientregion;
	}

	public void setClientregion(String clientregion) {
		this.clientregion = clientregion;
	}

	public String getBucketname() {
		return bucketname;
	}

	public void setBucketname(String bucketname) {
		this.bucketname = bucketname;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {

		this.accesskey = accesskey;
	}

	public String getSecretaccesskey() {

		return secretaccesskey;
	}

	public void setSecretaccesskey(String secretaccesskey) {
		this.secretaccesskey = secretaccesskey;
	}


}
