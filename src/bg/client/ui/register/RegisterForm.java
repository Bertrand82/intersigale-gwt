package bg.client.ui.register;

import bg.client.SigaleUtil;
import bg.client.inter.sigal.beans.UserBean;
import bg.client.ui.login.LoginService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RegisterForm extends Composite {

	
	private static RegisterFormUiBinder uiBinder = GWT.create(RegisterFormUiBinder.class);

	private static RegisterForm instance;

	public static RegisterForm getInstance() {
		if (instance == null) {
			instance = new RegisterForm();
		}
		return instance;
	}

	interface RegisterFormUiBinder extends UiBinder<Widget, RegisterForm> {

	}

	private RegisterForm() {
		instance = this;
		initWidget(uiBinder.createAndBindUi(this));
		buttonRegister.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				validAndRegister();
			}
		});
	}

	@UiField
	PasswordTextBox password1;

	@UiField
	PasswordTextBox password2;

	@UiField
	TextBox textBoxEmail;

	@UiField
	TextBox textBoxName;

	@UiField
	Button buttonRegister;

	@UiField
	InlineLabel labelComment;

	private void validAndRegister() {
		boolean valid = this.valid();

		if (valid) {
			String password = password1.getText();
			String name = textBoxName.getText();
			String email = textBoxEmail.getText();
			final UserBean user = new UserBean(name, email, password);
			SigaleUtil.getSigaleService().register(user, new AsyncCallback<UserBean>() {

				@Override
				public void onSuccess(UserBean userBean) {
					
					LoginService.getInstance().login(user);
				}

				@Override
				public void onFailure(Throwable e) {
					Window.alert("Failure Registering " + user.getEmail());
				}

			});
		}
	}

	private boolean valid() {
		boolean b = true;
		String comment = "";

		if (textBoxEmail.getText().trim().length() < 5) {
			b = false;
			comment = " e-mail is not Valid  ,";
		}
		if (textBoxEmail.getText().indexOf("@") < 0) {
			b = false;
			comment = " e-mail isnot Valid  ,";
		}
		if (!password1.getText().equals(password2.getText())) {
			b = false;
			comment += "passwords are not equals ,";
		}

		labelComment.setText(comment);

		return b;
	}

}
