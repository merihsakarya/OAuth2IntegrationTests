package com.oauth.integration.custom.serilizer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.oauth.integration.entity.User;

public class UserModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public UserModule() {
        super();
        addSerializer(User.class, new UserSerializer());
        
    }
}
