package com.platzerworld.e4.biergarten.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.platzerworld.e4.biergarten.model.mock.MockBiergarten;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("restriction")
public class CodeDetailComposite extends Composite {

	private MockBiergarten myCode;
	@Inject
	private MPart part;
	private Text text;
	private Text text_1;
	private Text text_2;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	@SuppressWarnings("unused")
	@Inject
	public CodeDetailComposite(Composite parent, @Optional @Named(IServiceConstants.ACTIVE_SELECTION) MockBiergarten code) {
		super(parent, SWT.NONE);
		this.myCode = code;
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(42, 55, 55, 15);
		lblNewLabel.setText("New Label");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(130, 52, 76, 21);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBounds(42, 96, 55, 15);
		lblNewLabel_1.setText("New Label");
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setBounds(42, 144, 55, 15);
		lblNewLabel_2.setText("New Label");
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(130, 96, 76, 21);
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setBounds(130, 138, 76, 21);
	}
	
	@PostConstruct
	private void initUi() {
		part.setLabel(myCode.getFirstName());		
		text.setText(this.myCode.getLastName());
		text_1.setText(this.myCode.getFirstName());
		text_2.setText(this.myCode.getEmail());
	}
}