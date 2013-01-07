package com.platzerworld.e4.biergarten.data.interfaces;

import java.util.List;

import com.platzerworld.e4.biergarten.data.domain.Code;
import com.platzerworld.e4.biergarten.data.domain.Country;

public interface IGeonameService {

	List<Code> getCodeByName(String name);
	
	List<Code> getCodeByNameAndCountry(String name, String country);
	
	List<Country> getAllCountries();
	
	Country getCountry(String countryCode);
	
	List<Country> getNeighbours(Country country);
	
}
