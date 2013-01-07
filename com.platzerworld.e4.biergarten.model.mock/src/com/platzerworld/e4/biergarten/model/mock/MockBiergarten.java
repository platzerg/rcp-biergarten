/*******************************************************************************
 * Copyright (c) 2010 Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial implementation
 ******************************************************************************/

package com.platzerworld.e4.biergarten.model.mock;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;

import org.springframework.web.client.RestTemplate;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.NodeMapper;

import com.platzerworld.e4.biergarten.model.Biergarten;
import com.platzerworld.e4.biergarten.model.mock.domain.Country;
import com.platzerworld.e4.biergarten.model.mock.domain.mappers.CountryNodeMapper;
import com.platzerworld.e4.biergarten.model.mock.utils.ProxyAuthenticator;

public class MockBiergarten implements Biergarten {
	private String firstName;
	private String lastName;
	private String email;

	private final PropertyChangeSupport propertyChangeSupport;

	public MockBiergarten(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	@Override
	public String toString() {
		return "SimpleContact [firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + "]";
	}
	
	@Override
	public List<Biergarten> getAllCountries() {
		String url = "http://ws.geonames.org/countryInfo?";
		List<Country> jj = (List<Country>) doSearch(url, null, "//geonames/country", new CountryNodeMapper());
		return Collections.EMPTY_LIST;
	}
	
	private List<?> doSearch(String url, Map<String,String> urlVariables, String xpath, NodeMapper mapper) {
		ProxyAuthenticator.authenticate();
		Source payload;
		if (urlVariables==null) {
			payload = new RestTemplate().getForObject(url, Source.class);
		} else {
			payload = new RestTemplate().getForObject(url, Source.class, urlVariables);
		}
		return new Jaxp13XPathTemplate().evaluate(xpath, payload, mapper);
	}

}
