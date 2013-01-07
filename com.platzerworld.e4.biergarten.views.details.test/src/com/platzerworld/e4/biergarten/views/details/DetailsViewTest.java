package com.platzerworld.e4.biergarten.views.details;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import com.platzerworld.e4.biergarten.model.Biergarten;
import com.platzerworld.e4.biergarten.model.mock.MockBiergarten;

public class DetailsViewTest {

	@Test
	public void shouldSetContactSelection() {
		Composite parent = new Shell();
		DetailsView detailsView = new DetailsView(parent);

		Biergarten contact = new MockBiergarten("Kai", "Tödter", "kai@toedter.com");
		detailsView.setSelection(contact);

		assertEquals("Kai", detailsView.getFirstNameText().getText());
		assertEquals("Tödter", detailsView.getLastNameText().getText());
		assertEquals("kai@toedter.com", detailsView.getEmailText().getText());
	}

}
