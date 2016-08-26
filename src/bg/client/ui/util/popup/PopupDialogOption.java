package bg.client.ui.util.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PopupDialogOption extends Composite {

	public static final int YES_OPTION = 0;
	public static int CANCEL_OPTION = 1;

	private static PopupDialogOption instance;
	private static PopupDialogOptionUiBinder uiBinder = GWT.create(PopupDialogOptionUiBinder.class);

	interface PopupDialogOptionUiBinder extends UiBinder<Widget, PopupDialogOption> {
	}

	IPopupListener listener;

	public PopupDialogOption() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonOk.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				listener.actionPerformed(YES_OPTION, textBox.getText());

			}
		});
		buttonCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				listener.actionPerformed(CANCEL_OPTION, textBox.getText());

			}
		});
	}

	@UiField
	Button buttonOk;

	@UiField
	Button buttonCancel;

	@UiField
	Label label;

	@UiField
	TextBox textBox;

	@UiField
	DialogBox dialogBox;

	public void showConfirmDialog(String message, String buttonCancelStr, String buttonOKStr, IPopupListener listener_) {
		this.showConfirmDialog(message, buttonCancelStr, buttonOKStr, false, listener_);
	}

	public void showConfirmDialog(String message, String buttonCancelStr, String buttonOKStr, boolean textBoxVisible, IPopupListener listener_) {
		label.setText(message);
		buttonCancel.setText(buttonCancelStr);
		buttonOk.setText(buttonOKStr);
		this.textBox.setText("");
		this.textBox.setVisible(textBoxVisible);
		;
		this.listener = listener_;
		this.dialogBox.setModal(true);
		this.dialogBox.center();
		this.dialogBox.show();
	}

	public static PopupDialogOption getInstance() {
		if (instance == null) {
			instance = new PopupDialogOption();
		}
		return instance;
	}

}
