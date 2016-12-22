package com.oauth.integration.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oauth.integration.dao.CountryDao;
import com.oauth.integration.entity.Country;

@Repository
public class CountryDaoImpl implements CountryDao {

	private EntityManager entityManager;
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	@Transactional
	public Country getCountryByIsoCode(String isoCode) {
		Country country = entityManager.find(Country.class, isoCode);		
		return country;
	}

	@Transactional
	public List<Country> listCountries() {
		TypedQuery<Country> query = entityManager.createQuery("SELECT e FROM Country e ORDER BY e.name", Country.class);
		return query.getResultList();
	}

}
