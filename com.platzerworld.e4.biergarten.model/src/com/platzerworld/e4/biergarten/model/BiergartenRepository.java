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

package com.platzerworld.e4.biergarten.model;

import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;

public interface BiergartenRepository {
	IObservableList getAllContacts();
	
	void addBiergarten(Biergarten contact);

	void removeBiergarten(Biergarten contact);
	
}
