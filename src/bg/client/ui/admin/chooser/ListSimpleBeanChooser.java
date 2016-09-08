package bg.client.ui.admin.chooser;

import java.util.ArrayList;
import java.util.List;

import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.ui.admin.AdminGUI;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ListSimpleBeanChooser {

	private static ListSimpleBeanChooser instance;

	public static ListSimpleBeanChooser getInstance() {
		if (instance == null) {
			instance = new ListSimpleBeanChooser();
		}
		return instance;
	}

	DataGrid<SimpleBean> dataGrid;

	private ListSimpleBeanChooser() {
		initGrid();
	}

	
	/**
	 * Un datagrid est tellement long a decrire, que le uiBinder n'apprte pas
	 * grand chose. Autant s'en passer: Le code gagne ainsi en lisibilité.
	 */
	public void initGrid() {
		dataGrid = new DataGrid<SimpleBean>();
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		TextColumn<SimpleBean> lexiqueName = new TextColumn<SimpleBean>() {
			@Override
			public String getValue(SimpleBean object) {
				return object.getName();
			}
		};
		dataGrid.addColumn(lexiqueName, "Lexique");
		/*
		 * ********** Colonne de buttons Display
		 * ********************************************************
		 */

		ButtonCell buttonDisplay = new ButtonCell();
		Column<SimpleBean, String> buttonDisplayColumn = new Column<SimpleBean, String>(buttonDisplay) {
			@Override
			public String getValue(SimpleBean object) {
				// The value to display in the button.
				return "Display";
			}
		};
		/* Le listener sur le button */
		buttonDisplayColumn.setFieldUpdater(new FieldUpdater<SimpleBean, String>() {
			public void update(int index, SimpleBean lexiqueBean, String value) {
				LexiqueFactory.getInstance().getLexiqueByNameInLocalStore(lexiqueBean.getName());
				AdminGUI.getInstance().hidePopup();
			}
		});
		dataGrid.addColumn(buttonDisplayColumn, "Display");
		/*
		 * ************ Colonne de buttons delete
		 * *****************************************************************
		 */
		ButtonCell buttonCellDelete = new ButtonCell();
		Column<SimpleBean, String> buttonColumn = new Column<SimpleBean, String>(buttonCellDelete) {
			@Override
			public String getValue(SimpleBean object) {
				// The value to display in the button.
				return "Delete";
			}
		};
		buttonColumn.setFieldUpdater(new FieldUpdater<SimpleBean, String>() {
			public void update(int index, SimpleBean lexiqueBean, String value) {
				LexiqueFactory.getInstance().deleteLexiqueByName(lexiqueBean.getName());
				AdminGUI.getInstance().hidePopup();

			}
		});
		dataGrid.addColumn(buttonColumn, "Action");
		/* ***************************************************************************************** */

		/* ******Si l'on ne met pas la taille, ca ne marche pas *** */
		dataGrid.setWidth("400px");
		dataGrid.setHeight("400px");
		/*
		 * On ne peut pas le mettre dans n'importe quel conteneur : Dans la
		 * litterature , on voit souvent (uniquement ?) le dockPanel
		 */
		dockPanel = new DockPanel();
		dockPanel.setTitle("List Lexiques");
		dockPanel.add(dataGrid, DockPanel.EAST);
		// If no Center, it doen't work !!!
		dockPanel.add(new Label(" "), DockPanel.CENTER);

	}

	public void setLexiquesBean(List<SimpleBean> list) {
		dataGrid.setRowCount(list.size(), true);
		dataGrid.setRowData(0, list);
		dataGrid.redraw();
	}

	DockPanel dockPanel;

	public Widget getWidget() {
		return dockPanel;
	}

	public void setLexiques(List<String> list) {
		List<SimpleBean> listBean = new ArrayList<SimpleBean>();
		for (String name : list) {
			SimpleBean lb = new SimpleBean(name);
			listBean.add(lb);
		}
		setLexiquesBean(listBean);

	}

}
