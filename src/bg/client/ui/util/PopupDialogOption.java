package bg.client.ui.util;

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

public class PopupDialogOption extends Composite  {

	public static final int YES_OPTION = 0;
	public static int CANCEL_OPTION = 1;

	private static PopupDialogOptionUiBinder uiBinder = GWT
			.create(PopupDialogOptionUiBinder.class);


	interface PopupDialogOptionUiBinder extends
			UiBinder<Widget, PopupDialogOption> {
	}

	public PopupDialogOption() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button buttonOk;

	@UiField
	Button buttonCancel;

	public static int showConfirmDialog(String message) {
		Window.alert(message);;
		return 0;
	}

	


}
