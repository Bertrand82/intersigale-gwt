/**
 * 
 */
package bg.client.ui.menu;

import bg.client.EntryPointSigale;
import bg.client.LogGWT;
import bg.client.inter.sigale.util.ILogListener;
import bg.client.ui.debug.Debug;
import bg.client.ui.edit.EditLexiqueGUI;
import bg.client.ui.importTranslate.ImportTranslateGUI;
import bg.client.ui.register.RegisterForm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Bertrand
 *
 */
public class MenuTools extends Composite {

	private static MenuUiBinder uiBinder = GWT.create(MenuUiBinder.class);
	private ILogListener logListener = new LogGWT();
	interface MenuUiBinder extends UiBinder<Widget, MenuTools> {
	}

	// @UiField
	Button buttonRegister = new Button();
	@UiField
	Button buttonEdit;
	@UiField
	Button buttonDebug;
	@UiField
	Button buttonImportTranslate;
	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	private MenuTools() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonRegister.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonRegister);
				EntryPointSigale.showView(RegisterForm.getInstance());
				logListener.logText("Register");
			}
		});

		buttonEdit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonEdit);
				EntryPointSigale.showView(EditLexiqueGUI.getInstance());
				logListener.logText("Edit");
			}
		});

		buttonDebug.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonDebug);
				EntryPointSigale.showView(Debug.getInstance());
				logListener.logText("Debug");
			}
		});
		
		buttonImportTranslate.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonImportTranslate);
				EntryPointSigale.showView(ImportTranslateGUI.getInstance());
				logListener.logText("Debug");
			}
		});
	}
	
	
	public void setBorders() {
		buttonDebug.getElement().getStyle().setProperty("borderWidth", "1px");
		buttonEdit.getElement().getStyle().setProperty("borderWidth", "1px");
		buttonRegister.getElement().getStyle().setProperty("borderWidth", "1px");
		
	}
	private void setBorders(Button buttonSelected) {
		setBorders();
		MenuUtil.selectButton(buttonSelected);
		Menu.getInstance().setBorders();

	}

	private static MenuTools instance = new MenuTools();

	public static MenuTools getInstance() {

		return instance;
	}

}
