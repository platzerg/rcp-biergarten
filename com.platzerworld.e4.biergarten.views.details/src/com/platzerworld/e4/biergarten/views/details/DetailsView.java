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

package com.platzerworld.e4.biergarten.views.details;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.platzerworld.e4.biergarten.model.Biergarten;
import com.platzerworld.e4.geo.service.interfaces.IGeonameService;

public class DetailsView {
	static final int Navigate = 0x68;
	private DataBindingContext dbc;
	private WritableValue contactValue;
	private Text firstNameText;
	private Text lastNameText;
	private Text emailText;
	private OleClientSite site;
	private static Logger logger = LoggerFactory.getLogger(DetailsView.class);

	@Inject
	IGeonameService iGeonameService;
	
	@Inject
	public DetailsView(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		
		 try {
		      OleFrame frame = new OleFrame(parent, SWT.NONE);
		      site = new OleClientSite(frame, SWT.NONE, "Excel.Sheet");
		    } catch (SWTError e) {
		      System.out.println("Unable to open activeX control");
		      return;
		    }
		
		 try {
		      OleFrame frame = new OleFrame(parent, SWT.NONE);
		      site = new OleClientSite(frame, SWT.NONE, "Shell.Explorer.1");
		      site.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
		      OleAutomation auto = new OleAutomation(site);
		      auto.invoke(Navigate, new Variant[] { new Variant("c:\\temp") });
		    } catch (SWTError e) {
		      System.out.println("Unable to open activeX control");
		      return;
		    }
		
		composite.setLayout(new GridLayout(2, false));
		Realm.runWithDefault(SWTObservables.getRealm(Display.getDefault()),
				new Runnable() {

					@Override
					public void run() {
						dbc = new DataBindingContext();
						contactValue = new WritableValue();
						firstNameText = createText(composite, "First Name:",
								"firstName");
						lastNameText = createText(composite, "Last Name:",
								"lastName");
						emailText = createText(composite, "Email:", "email");
					}
				});
	}

	private Text createText(final Composite parent, final String labelText,
			final String property) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(labelText);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		label.setLayoutData(gridData);

		final Text text = new Text(parent, SWT.NONE);
		GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
		gridData2.horizontalIndent = 0;
		gridData2.horizontalSpan = 1;
		text.setLayoutData(gridData2);

		if (property != null) {
			dbc.bindValue(SWTObservables.observeText(text, SWT.Modify),
					PojoObservables.observeDetailValue(contactValue, property,
							String.class));
		}

		return text;
	}

	@Inject
	public void setSelection(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Biergarten contact) {
		logger.debug("setSelection with: " + contact);
		if (contact != null) {
			contactValue.setValue(contact);
		}
	}

	public Text getFirstNameText() {
		return firstNameText;
	}

	public Text getLastNameText() {
		return lastNameText;
	}

	public Text getEmailText() {
		return emailText;
	}

}
