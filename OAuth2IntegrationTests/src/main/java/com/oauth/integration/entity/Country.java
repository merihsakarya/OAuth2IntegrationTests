package com.oauth.integration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Country {

    @Id
    @PrimaryKeyJoinColumn
    @Column(name = "iso_code", unique = true, nullable = false)
    private String isoCode; 
    
    @NotNull
    private String name;
    
    @NotNull
    private String intPhoneCode;
    
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getIntPhoneCode() {
        return intPhoneCode;
    }

    public void setIntPhoneCode(String intPhoneCode) {
        this.intPhoneCode = intPhoneCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
