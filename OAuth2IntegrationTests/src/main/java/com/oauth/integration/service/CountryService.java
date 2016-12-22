package com.oauth.integration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oauth.integration.dao.CountryDao;
import com.oauth.integration.entity.Country;

@Service
public class CountryService {

    @Autowired
    CountryDao countryDao;
    
    public Country getCountryByIsoCode(String isoCode){
        return countryDao.getCountryByIsoCode(isoCode);
    }
    
    public List<Country> listCountries(){
        return countryDao.listCountries();
    }
}
