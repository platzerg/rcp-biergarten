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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.NodeMapper;

import com.platzerworld.e4.biergarten.model.Biergarten;
import com.platzerworld.e4.biergarten.model.BiergartenRepository;
import com.platzerworld.e4.biergarten.model.mock.domain.Country;
import com.platzerworld.e4.biergarten.model.mock.domain.mappers.CountryNodeMapper;
import com.platzerworld.e4.biergarten.model.mock.utils.ProxyAuthenticator;

public class MockBiergartenRepository implements BiergartenRepository {
	private final IObservableList contacts;
	
	@SuppressWarnings("unchecked")
	public MockBiergartenRepository() {
		List<MockBiergarten> contactList = new ArrayList<MockBiergarten>();
		String url = "http://ws.geonames.org/countryInfo?";
		
		List<Country> countries = (List<Country>) doSearch(url, null, "//geonames/country", new CountryNodeMapper());
		for (Country country : countries) {
			contactList.add(new MockBiergarten(country.getCountryCode(), country.getCountryName(), country.getCurrencyCode()));
		}
		contacts = new WritableList(contactList, null);
	}

	@Override
	public void addBiergarten(final Biergarten contact) {
		contacts.add(contact);
	}

	@Override
	public void removeBiergarten(final Biergarten contact) {
		contacts.remove(contact);
	}

	@Override
	public IObservableList getAllContacts() {
		return contacts;
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
