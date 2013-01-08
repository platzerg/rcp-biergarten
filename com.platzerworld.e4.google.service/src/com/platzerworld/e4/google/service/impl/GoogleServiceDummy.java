package com.platzerworld.e4.google.service.impl;

import java.util.List;

import com.platzerworld.e4.google.service.interfaces.IGoogleService;

public class GoogleServiceDummy implements IGoogleService {

	@Override
	public List<?> getCodeByName(String name) {
		List sampleData = getSampleCodes();				
		return sampleData;
	}
	
	private List<?> getSampleCodes() {
		return null;
	}
}
