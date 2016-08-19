package bg.client.register;

import bg.client.SigaleService;
import bg.client.SigaleServiceAsync;

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

public class RegisterForm extends Composite  {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final SigaleServiceAsync greetingService = GWT.create(SigaleService.class);

	private static RegisterFormUiBinder uiBinder = GWT.create(RegisterFormUiBinder.class);

	private  static RegisterForm instance ;
	
	
	public static RegisterForm getInstance() {
		if (instance == null){
			instance = new RegisterForm();
		}
		return instance;
	}
 

	interface RegisterFormUiBinder extends UiBinder<Widget, RegisterForm> {
		
	}

	private RegisterForm() {
		instance=this;	
		initWidget(uiBinder.createAndBindUi(this));
		buttonRegister.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				validAndRegister();
			}
		});
	}

	
	
	@UiField
	PasswordTextBox  password1;
	
	@UiField
	PasswordTextBox  password2;
	
	@UiField
	TextBox email;
	
	@UiField
	TextBox name;
	
	@UiField
	Button buttonRegister ;

	@UiField
	InlineLabel labelComment;
	
	private void validAndRegister() {
		boolean valid = this.valid();
		
		if (valid) {
		//	Window.Location.replace("page_home.jsp"); // break History
		/*	greetingService.register(email.getText(), name.getText(), password1.getText(), new AsyncCallback<String>(){

				@Override
				public void onFailure(Throwable e) {
					labelComment.setText("Exception "+e.getMessage());
				}

				@Override
				public void onSuccess(String result) {
					Window.Location.assign("page_home.jsp");
				}
				
			});
			*/
		}
	}

	private boolean valid() {
		boolean b = true;
		String comment ="";
		
		if (email.getText().trim().length() < 5){
			b= false;
			comment = " Email non nalide ,";
		}
		if (email.getText().indexOf("@") < 0){
			b= false;
			comment = " Email non nalide ,";
		}
		if (!password1.getText().equals(password2.getText())){
			b= false;
			comment += " Les mots de passe ne sont pas égaux,";
		}
		if (password1.getText().length()<6){
			b= false;
			comment += " Le mot de passe doit être supérieur à 6 caract�res";
		}
		
		labelComment.setText(comment);
		
		return b;
	}




	
	

}
