package bg.client.ui.edit.chooser;

import java.util.ArrayList;
import java.util.List;

import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.ui.edit.EditGUI;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LexiqueChooser {

	private static LexiqueChooser instance;

	public static LexiqueChooser getInstance() {
		if (instance == null) {
			instance = new LexiqueChooser();
		}
		return instance;
	}

	DataGrid<LexiqueBean> dataGrid;

	private LexiqueChooser() {
		initGrid();
	}
	
	private static List<String> getListTest() {
		 List<String> listTest = new ArrayList<String>();
	        listTest.add("aaaaaaaaa");
	        listTest.add("bbbbbbbb");
	        return listTest;
	}

	public void initGrid() {
		dataGrid = new DataGrid<LexiqueBean>();
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		TextColumn<LexiqueBean> houseNumber = new TextColumn<LexiqueBean>() {
			@Override
			public String getValue(LexiqueBean object) {
				return object.getName();
			}
		};
		dataGrid.addColumn(houseNumber, "Lexique");
		/* ****************************************************************************** */

		ButtonCell buttonDisplay = new ButtonCell();
		Column<LexiqueBean, String> buttonDisplayColumn = new Column<LexiqueBean, String>(
				buttonDisplay) {
			@Override
			public String getValue(LexiqueBean object) {
				// The value to display in the button.
				return "Display";
			}
		};
		buttonDisplayColumn
				.setFieldUpdater(new FieldUpdater<LexiqueBean, String>() {
					public void update(int index, LexiqueBean lexiqueBean, String value) {
						LexiqueFactory.getInstance().getLexiqueByName(lexiqueBean.getName());
						EditGUI.getInstance().hidePopup();
						
					}
				});
		dataGrid.addColumn(buttonDisplayColumn, "Display");
		/* ***************************************************************************************** */
		ButtonCell buttonCellDelete = new ButtonCell();
		Column<LexiqueBean, String> buttonColumn = new Column<LexiqueBean, String>(
				buttonCellDelete) {
			@Override
			public String getValue(LexiqueBean object) {
				// The value to display in the button.
				return "Delete";
			}
		};
		buttonColumn.setFieldUpdater(new FieldUpdater<LexiqueBean, String>() {
			public void update(int index, LexiqueBean lexiqueBean, String value) {
				LexiqueFactory.getInstance().deleteLexiqueByName(lexiqueBean.getName());
				EditGUI.getInstance().hidePopup();
				
			}
		});
		dataGrid.addColumn(buttonColumn, "Action");
		/* ***************************************************************************************** */
       
		setLexiques(getListTest());
		/* **********/
		dataGrid.setWidth("400px");
		dataGrid.setHeight("400px");

		dockPanel = new DockPanel();
		dockPanel.setTitle("blablab");
		dockPanel.add(dataGrid, DockPanel.EAST);
		Button buttonExit = new Button("Exit");
		VerticalPanel panelSouth = new VerticalPanel();
		panelSouth.add(buttonExit);
		dockPanel.add(panelSouth, DockPanel.SOUTH);

	}

	public void setLexiquesBean(List<LexiqueBean> list) {
		dataGrid.setRowCount(list.size(), true);
		dataGrid.setRowData(0, list);
		dataGrid.redraw();
	}

	DockPanel dockPanel;

	public Widget getWidget() {
		return dockPanel;
	}

	public void setLexiques(List<String> list) {
		List<LexiqueBean> listBean = new ArrayList<LexiqueBean>();
		for (String name : list) {
			LexiqueBean lb = new LexiqueBean(name);
			listBean.add(lb);
		}
		setLexiquesBean(listBean);

	}

}
