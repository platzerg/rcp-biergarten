package com.platzerworld.e4.biergarten.views;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.model.application.ui.menu.impl.ToolItemImpl;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.prefs.BackingStoreException;

import com.platzerworld.e4.biergarten.model.Biergarten;
import com.platzerworld.e4.biergarten.model.BiergartenRepository;
import com.platzerworld.e4.biergarten.model.mock.MockBiergarten;

@SuppressWarnings("restriction")
public class CountryComboViewerToolItem extends ToolItemImpl {

	@Inject
	@Preference(value="actualCountryCode")
	String actualCountryCode;
	
	private ComboViewer countryComboViewer;

	@Inject
	private BiergartenRepository geonameService;

	private final Composite parent;
	
	@Inject
	public CountryComboViewerToolItem(Composite parent) {
		
		this.parent = parent;
		countryComboViewer = new ComboViewer(parent, SWT.READ_ONLY);
		countryComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				MockBiergarten selectedCountry = (MockBiergarten) ((StructuredSelection) event.getSelection()).getFirstElement();
				setActualCountryCode(selectedCountry);
			}
		});
		countryComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		countryComboViewer.setLabelProvider(new LabelProvider() {
		    @Override
		    public String getText(Object element) {
		    	MockBiergarten country = (MockBiergarten) element;
		    	return country.getLastName();
		    }

		});
		setWidget(countryComboViewer);
	}
	

	@PostConstruct
	private void init() {

		countryComboViewer.setInput(geonameService.getAllContacts());
	}
	
	protected void setActualCountryCode(MockBiergarten country) {
		if (country.getFirstName() == null) {
			this.actualCountryCode = "";
		} else {
			this.actualCountryCode = country.getFirstName();			
		}
		IEclipsePreferences prefs = new InstanceScope().getNode("net.teufel.e4.geo.ui");
		prefs.put("actualCountryCode", this.actualCountryCode);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			ErrorDialog.openError(parent.getShell(), "Error",
					"Error while storing preferences",
					new Status(IStatus.ERROR, "net.teufel.e4.geo.ui", e.getMessage(),e)
			);
		}
	}
	
}
