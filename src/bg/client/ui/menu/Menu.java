/**
 * 
 */
package bg.client.ui.menu;

import bg.client.EntryPointSigale;
import bg.client.ui.edit.EditGUI;
import bg.client.ui.lesson.Lesson;
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
public class Menu extends Composite {

	private static MenuUiBinder uiBinder = GWT.create(MenuUiBinder.class);

	interface MenuUiBinder extends UiBinder<Widget, Menu> {
	}

	@UiField
	Button buttonRegister;
	@UiField
	Button buttonLesson;
	@UiField
	Button buttonEdit;
	@UiField
	Button buttonDebug;
	@UiField
	Button buttonAdmin;
	@UiField
	Button buttonStat;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	private Menu() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonRegister.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				EntryPointSigale.showView(RegisterForm.getInstance());
			}
		});
		buttonLesson.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				EntryPointSigale.showView(Lesson.getInstance());
			}
		});
		buttonEdit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				EntryPointSigale.showView(EditGUI.getInstance());
			}
		});
	}

	private static Menu instance = new Menu();

	public static Menu getInstance() {

		return instance;
	}

}
