/**
 * 
 */
package bg.client.ui.menu;

import bg.client.EntryPointSigale;
import bg.client.LogGWT;
import bg.client.inter.sigale.util.ILogListener;
import bg.client.ui.admin.AdminGUI;
import bg.client.ui.lesson.Lesson;
import bg.client.ui.login.LoginForm;
import bg.client.ui.login.LoginService;
import bg.client.ui.login.LogoutByebye;
import bg.client.ui.login.WelcomeUI;
import bg.client.ui.register.RegisterForm;
import bg.client.ui.stat.Statistiques;

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
	private ILogListener logListener = new LogGWT();

	interface MenuUiBinder extends UiBinder<Widget, Menu> {
	}

	@UiField
	Button buttonLogin;
	@UiField
	Button buttonLesson;
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
		buttonLogin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonLogin);
				EntryPointSigale.showView(LoginForm.getInstance());
				logListener.logText("Login");
			}
		});
		buttonLesson.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonLesson);
				EntryPointSigale.showView(Lesson.getInstance());
				logListener.logText("Lesson");
			}
		});
		buttonAdmin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonAdmin);
				EntryPointSigale.showView(AdminGUI.getInstance());
				logListener.logText("Admin");
			}
		});

		buttonStat.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonStat);
				EntryPointSigale.showView(Statistiques.getInstance());
				logListener.logText("Stat");
			}
		});

		buttonLogin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setBorders(buttonLogin);
				EntryPointSigale.showView(LoginForm.getInstance());
				LoginService.getInstance().logout();
				logListener.logText(" "+buttonLogin.getText());
			}
		});
	}

	public void setBorders() {
		buttonAdmin.getElement().getStyle().setProperty("borderWidth", "1px");
		buttonLesson.getElement().getStyle().setProperty("borderWidth", "1px");
		buttonLogin.getElement().getStyle().setProperty("borderWidth", "1px");
		buttonStat.getElement().getStyle().setProperty("borderWidth", "1px");

	}

	private void setBorders(Button buttonSelected) {
		setBorders();
		MenuUtil.selectButton(buttonSelected);
		MenuTools.getInstance().setBorders();

	}

	private static Menu instance = new Menu();

	public static Menu getInstance() {

		return instance;
	}

	public void setLogout(boolean isLogged) {
		if (isLogged) {
			this.buttonLogin.setText("Logout");
			EntryPointSigale.showView(WelcomeUI.getInstance());
		} else {
			this.buttonLogin.setText("Login");
		}
	}

	public void register() {
		setBorders(null);
		EntryPointSigale.showView(RegisterForm.getInstance());
	}

	public void logoutByebye() {
		setLogout(false);
		EntryPointSigale.showView(LogoutByebye.getInstance());
	}

}
