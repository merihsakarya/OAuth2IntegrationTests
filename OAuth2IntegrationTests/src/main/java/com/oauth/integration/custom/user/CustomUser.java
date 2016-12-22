package com.oauth.integration.custom.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.oauth.integration.entity.User;
import com.oauth.integration.entity.UserRole;
import com.oauth.integration.enums.RoleEnum;

/**
 * A custom {@link UserDetailsService} where user information
 * is retrieved from a JPA repository
 */
public class CustomUser implements UserDetails {
 
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public CustomUser(User user) {
		this.user = user;
	}
	
	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles());
		return authList;
	}
	
	/**
	 * Converts a numerical role to an equivalent list of roles
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(List<UserRole> userRoles) {
		List<String> roles = new ArrayList<String>();
		
		for (UserRole role : userRoles) {
			roles.add(role.getRole().getLabel());
		}
		
		return roles;
	}
	
	public List<String> getRoles() {
		List<String> roles = new ArrayList<String>();
		roles.add(RoleEnum.ROLE_USER.getLabel());
		return roles;
	}

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
 
	public String getPassword() {
		return user.getPassword();
	}
 
	public String getUsername() {
		return user.getEmail();
	}
 
	public boolean isAccountNonExpired() {
		return true;
	}
 
	public boolean isAccountNonLocked() {
		return true;
	}
 
	public boolean isCredentialsNonExpired() {
		return true;
	}
 
	public boolean isEnabled() {
		return true;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}