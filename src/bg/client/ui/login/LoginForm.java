package bg.client.ui.login;

import bg.client.ui.menu.Menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginForm extends Composite {

	private static LoginFormUiBinder uiBinder = GWT.create(LoginFormUiBinder.class);

	private static LoginForm instance = new LoginForm();
	@UiField
	Button buttonLogin;

	@UiField
	Button buttonRegister;

	interface LoginFormUiBinder extends UiBinder<Widget, LoginForm> {
	}

	public LoginForm() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonLogin.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				login();
			}
		});
		buttonRegister.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				register();
			}
		});
	}

	public static LoginForm getInstance() {
		return instance;
	}

	boolean isLogged = false;
	private void login() {
		
		isLogged= true;
		Menu.getInstance().setLogout(isLogged);
	}
	public void loginLogout() {
		isLogged= !isLogged;
	}
	public void register() {
		Menu.getInstance().register();
	}

}
