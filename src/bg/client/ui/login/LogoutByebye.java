package bg.client.ui.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class LogoutByebye extends Composite {
	private static LogoutByebye instance ;
	private static LogoutByebyeUiBinder uiBinder = GWT.create(LogoutByebyeUiBinder.class);

	interface LogoutByebyeUiBinder extends UiBinder<Widget, LogoutByebye> {
	}

	public LogoutByebye() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public static LogoutByebye getInstance() {
		if (instance == null){
			instance = new LogoutByebye();
		}
		return instance;
	}



	
}
