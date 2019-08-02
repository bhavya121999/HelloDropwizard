package com.indore;

import javax.validation.constraints.NotNull;

/**
 * <File Description>.
 *
 * @author Amit Khandelwal
 */
public class ElasticsearchConfig {
	@NotNull
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
