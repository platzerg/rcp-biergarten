package com.platzerworld.e4.geo.service.interfaces;

import java.util.List;

import com.platzerworld.e4.geo.service.domain.Code;
import com.platzerworld.e4.geo.service.domain.Country;

public interface IGeonameService {

	List<Code> getCodeByName(String name);
	
	List<Code> getCodeByNameAndCountry(String name, String country);
	
	List<Country> getAllCountries();
	
	Country getCountry(String countryCode);
	
	List<Country> getNeighbours(Country country);
	
}
