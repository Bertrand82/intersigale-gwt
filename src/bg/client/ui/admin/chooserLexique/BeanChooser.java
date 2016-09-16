package bg.client.ui.admin.chooserLexique;


import java.util.List;

import bg.client.LogGWT;
import bg.client.inter.sigal.beans.IBean;
import bg.client.inter.sigale.util.ILogListener;
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

public class BeanChooser {

	private static BeanChooser instance;
	private ILogListener logger = new LogGWT();
	
	public static BeanChooser getInstance() {
		if (instance == null) {
			instance = new BeanChooser();
		}
		return instance;
	}
	DataGrid<IBean> dataGrid;

	private BeanChooser() {
		initGrid();
	}

	private IAction iAction;
	
	/**
	 * Un datagrid est tellement long a decrire, que le uiBinder n'apprte pas
	 * grand chose. Autant s'en passer: Le code gagne ainsi en lisibilité.
	 */
	public void initGrid() {
		dataGrid = new DataGrid<IBean>();
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		TextColumn<IBean> lexiqueName = new TextColumn<IBean>() {
			@Override
			public String getValue(IBean object) {
				return object.getName();
			}
		};
		dataGrid.addColumn(lexiqueName, "Lexique");
		/*
		 * ********** Colonne de buttons Display
		 * ********************************************************
		 */

		ButtonCell buttonDisplay = new ButtonCell();
		Column<IBean, String> buttonDisplayColumn = new Column<IBean, String>(buttonDisplay) {
			@Override
			public String getValue(IBean object) {
				// The value to display in the button.
				return "Display";
			}
		};
		/* Le listener sur le button */
		buttonDisplayColumn.setFieldUpdater(new FieldUpdater<IBean, String>() {
			public void update(int index, IBean lexiqueBean, String value) {
				
				iAction.display(""+lexiqueBean.getId(),lexiqueBean.getName());
				AdminGUI.getInstance().hidePopup();

			}
		});
		dataGrid.addColumn(buttonDisplayColumn, "Display");
		/*
		 * ************ Colonne de buttons delete
		 * *****************************************************************
		 */
		ButtonCell buttonCellDelete = new ButtonCell();
		Column<IBean, String> buttonColumn = new Column<IBean, String>(buttonCellDelete) {
			@Override
			public String getValue(IBean object) {
				// The value to display in the button.
				return "Delete";
			}
		};
		buttonColumn.setFieldUpdater(new FieldUpdater<IBean, String>() {
			public void update(int index, IBean lexiqueBean, String value) {
				logger.log("Delete "+lexiqueBean.getName()+" id:"+lexiqueBean.getId());
				iAction.delete(""+lexiqueBean.getId(),lexiqueBean.getName());
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

	public void setLexiqueMetadata(List<? extends IBean> list) {
		dataGrid.setRowCount(list.size(), true);
		dataGrid.setRowData(0, list);
		dataGrid.redraw();
	}

	DockPanel dockPanel;

	public Widget getWidget() {
		return dockPanel;
	}

	public void setiAction(IAction iAction) {
		this.iAction = iAction;
	}



}
