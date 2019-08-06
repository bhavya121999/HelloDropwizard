package com.indore;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <File Description>.
 *
 * @author Amit Khandelwal
 */
public class ElasticsearchConfig {
	@NotEmpty
	String host;

	int port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
