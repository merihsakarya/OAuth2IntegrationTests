package com.oauth.integration.dao;

import java.util.List;

import com.oauth.integration.entity.Country;

public interface CountryDao {

	public Country getCountryByIsoCode(String isoCode);
	
	public List<Country> listCountries();
}
