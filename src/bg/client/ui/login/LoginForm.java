package bg.client.ui.login;

import bg.client.LogGWT;
import bg.client.SigaleUtil;
import bg.client.inter.sigal.beans.UserBean;
import bg.client.ui.menu.Menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginForm extends Composite {

	private static LoginFormUiBinder uiBinder = GWT.create(LoginFormUiBinder.class);

	private static LoginForm instance = new LoginForm();
	
	private LogGWT log = new LogGWT();
	
	@UiField
	Button buttonLogin2;

	@UiField
	Button buttonRegister;
	
	@UiField
	TextBox textBoxEmail;
	
	@UiField
	PasswordTextBox password;
	
	interface LoginFormUiBinder extends UiBinder<Widget, LoginForm> {
	}

	public LoginForm() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonLogin2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				buttonLogin2.setEnabled(false);
				loginRequest();
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

	
	private void loginRequest() {
		
		final String email = this.textBoxEmail.getText();
		String passwordStr = this.password.getText();
		this.log.log("contact server ...");
		SigaleUtil.getSigaleService().login(email,passwordStr, new AsyncCallback<UserBean>() {
			
			@Override
			public void onSuccess(UserBean user) {
				LoginService.getInstance().login(user);
				buttonLogin2.setEnabled(true);
			}
			
			@Override
			public void onFailure(Throwable e) {
				buttonLogin2.setEnabled(true);
				Window.alert("Login Excption "+e);
			}
		});
		
	}
	
	
	
	public void register() {
		Menu.getInstance().register();
	}

}
