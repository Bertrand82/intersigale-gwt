package bg.client.ui.login;

import bg.client.inter.cicada.beans.UserBean;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class WelcomeUI extends Composite {

	private static WelcomeUIUiBinder uiBinder = GWT.create(WelcomeUIUiBinder.class);

	interface WelcomeUIUiBinder extends UiBinder<Widget, WelcomeUI> {
	}

	public WelcomeUI() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonLogout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				logout();
				
			}
		});
	}

	@UiField
	Label labelName;

	@UiField
	Label labelEMail;
	
	@UiField
	Button buttonLogout;

	private static WelcomeUI instance;

	public static WelcomeUI getInstance() {
		if (instance == null) {
			instance = new WelcomeUI();
		}
		return instance;
	}

	public void update(UserBean user) {
		if (user == null) {
			this.labelName.setText(" ");
			this.labelEMail.setText(" ");
		} else {
			this.labelName.setText(" "+user.getName());
			this.labelEMail.setText(" "+user.getEmail());
		}
	}
	
	private void logout(){
		LoginService.getInstance().logout();
	}

}
