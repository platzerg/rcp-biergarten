package com.platzerworld.e4.biergarten.views;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.platzerworld.e4.biergarten.model.BiergartenRepository;
import com.platzerworld.e4.biergarten.model.mock.MockBiergarten;

@SuppressWarnings("restriction")
public class SucheComposite extends Composite {
	private static Logger logger = LoggerFactory.getLogger(SucheComposite.class);

	@Inject
	@Preference(value="actualCountryCode")
	private String actualCountryCode;
	
	private Text text;
	
	@Inject
	private ESelectionService selectionService;
	
	@Inject
    private EPartService partService;
	
	private Table table;

	private TableViewer tableViewer;
	
	private BiergartenRepository biergartenService;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	@Inject
	public SucheComposite(Composite parent, BiergartenRepository biergartenService) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(1, false));
		this.biergartenService = biergartenService;
		text = new Text(this, SWT.BORDER);
		text.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				sucheAusfuehren(text.getText());
				text.selectAll();
			};
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn tableViewerColumnLand = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumnLand = tableViewerColumnLand.getColumn();
		tableColumnLand.setWidth(45);
		tableColumnLand.setText("Land");

		TableViewerColumn tableViewerColumnPLZ = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumnPLZ = tableViewerColumnPLZ.getColumn();
		tableColumnPLZ.setWidth(55);
		tableColumnPLZ.setText("PLZ");
		
		TableViewerColumn tableViewerColumnOrt = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumnOrt = tableViewerColumnOrt.getColumn();
		tableColumnOrt.setWidth(107);
		tableColumnOrt.setText("Ort");
		
		
		tableViewer.setLabelProvider(new PlzResultLabelProvider());
		tableViewer.setContentProvider(new CollectionContentProvider());
		tableViewer.setInput(null);
		
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
		        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		        selectionService.setSelection(selection.getFirstElement());
		    }
		  });

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				partService.showPart(partService.createPart("codeId"), PartState.ACTIVATE);
			}
		});
	}

	public void sucheAusfuehren(String suchen) {
		List result = biergartenService.getAllContacts();		
     	tableViewer.setInput(result);
     	
     	Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
		    Statement st = conn.createStatement();
		    //st.executeUpdate("create table survey (id int,name varchar(30));");
		    //st.executeUpdate("insert into survey (id,name ) values (1,'nameValue')");

		    st = conn.createStatement();
		    ResultSet rs = st.executeQuery("SELECT * FROM DLR_COM_DATA");
		    while (rs.next()) {
	            String coffeeName = rs.getString("DLR_ID");
	            String supplierID = rs.getString("DLR_TITLE");
	            String price = rs.getString("DLR_NAME");
	            String sales = rs.getString("DLR_NAME_EXTS");
	            String total = rs.getString("DLR_STREET");
	            logger.debug("DLR_ID: " + coffeeName);
	           
	        }
		    
		    String proc3StoredProcedure = "{ call proc3(?, ?, ?) }";
		    // Step-3: prepare the callable statement
		    CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
		    // Step-4: set input parameters ...
		    // first input argument
		    cs.setString(1, "abcd");
		    // third input argumentcoffeeName
		    cs.setInt(3, 10);
		    // Step-5: register output parameters ...
		    cs.registerOutParameter(2, java.sql.Types.VARCHAR);
		    cs.registerOutParameter(3, java.sql.Types.INTEGER);
		    // Step-6: execute the stored procedures: proc3
		    // cs.execute();
		    // Step-7: extract the output parameters
		    // get parameter 2 as output
		    String param2 = cs.getString(2);
		    // get parameter 3 as output
		    int param3 = cs.getInt(3);
		    System.out.println("param2=" + param2);
		    System.out.println("param3=" + param3);
		    
		    
		    
		    rs.close();
		    st.close();
		    conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	private static Connection getConnection() throws Exception {
	    String driver = "oracle.jdbc.driver.OracleDriver";
	    String url = "jdbc:oracle:thin:@tldb172.muc:1572:srpmmi";
	    String username = "qqrsrpmm";
	    String password = "srp1";
	    Class.forName(driver);
	    return DriverManager.getConnection(url, username, password);
	  }
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private class PlzResultLabelProvider implements ITableLabelProvider {
		
		public Image getColumnImage(Object element, int columnIndex) {
				if (columnIndex != 0)
					return null;
				URL url;
				MockBiergarten code = (MockBiergarten) element;
				try {
					url = FileLocator.resolve(new URL("platform:/plugin/net.teufel.e4.geo.ui/icons/flags/" + code.getFirstName().toLowerCase() + ".gif"));
					return ImageDescriptor.createFromURL(url).createImage();
				} catch (Exception e) {
					return null;
				}
		}

		public String getColumnText(Object element, int columnIndex) {
			MockBiergarten plzResult = (MockBiergarten) element;
			
			switch (columnIndex) {
			case 0:
				return plzResult.getFirstName();
			case 1:
				return plzResult.getLastName();
			case 2:
				return plzResult.getEmail();
			}
			return "???";
			
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}
	}

}
