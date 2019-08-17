package com.indore;

import org.hibernate.validator.constraints.NotEmpty;

public class AwsConfig {
    @NotEmpty
    String accesskey;

    @NotEmpty
    String secretaccesskey;

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
