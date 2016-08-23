package bg.client.ui.log;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LogUI extends Composite  implements HasText{

	private static LogUIUiBinder uiBinder = GWT.create(LogUIUiBinder.class);

	interface LogUIUiBinder extends UiBinder<Widget, LogUI> {
	}

	private final static LogUI instance = new LogUI();
	
	private LogUI() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label label;

	@Override
	public String getText() {
		
		return label.getText();
	}

	@Override
	public void setText(String text) {
		label.setText(text);
	}

	public static LogUI getInstance() {
		return instance;
	}

	
	
}
