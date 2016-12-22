package com.oauth.integration.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.integration.entity.Country;
import com.oauth.integration.service.CountryService;

@RestController
class CountryResource {
	
	@Autowired
	private CountryService countryService;
	
	@RequestMapping(value = "/country/echo", method = RequestMethod.GET)
	public String echo() {
        return "echo";
    }
	
	@RequestMapping(value = "/country/{isoCode}", method = RequestMethod.GET)
	public ResponseEntity<Country> getCountry(@PathVariable String isoCode) {	
		Country country = countryService.getCountryByIsoCode(isoCode);
		return new ResponseEntity<Country>(country, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/country/list", method = RequestMethod.GET)
	public ResponseEntity<List<Country>> listCountries() {	
		List<Country> countries = countryService.listCountries();
		return new ResponseEntity<List<Country>>(countries, HttpStatus.OK);	
	}
	
}
