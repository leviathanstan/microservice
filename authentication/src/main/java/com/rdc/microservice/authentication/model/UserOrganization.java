package com.rdc.microservice.authentication.model;

import java.io.Serializable;

public class UserOrganization implements Serializable {
    String organizationId;
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }


}
