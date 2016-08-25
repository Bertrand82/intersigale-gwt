package bg.client.ui.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PopupDialogOption extends Composite  {

	public static final int YES_OPTION = 0;
	public static int CANCEL_OPTION = 1;

	private static PopupDialogOption instance ;
	private static PopupDialogOptionUiBinder uiBinder = GWT
			.create(PopupDialogOptionUiBinder.class);


	interface PopupDialogOptionUiBinder extends
			UiBinder<Widget, PopupDialogOption> {
	}

	IPopupListener listener;
	
	
	public PopupDialogOption() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonOk.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				listener.actionPerformed(YES_OPTION);
				
			}
		});
		buttonCancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				listener.actionPerformed(CANCEL_OPTION);
				
				
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
	DialogBox dialogBox;

	public void showConfirmDialog(String message, String buttonCancelStr, String buttonOKStr, IPopupListener listener_) {
		Window.alert(message);
		label.setText(message);
		buttonCancel.setText(buttonCancelStr);
		buttonOk.setText(buttonOKStr);
		this.listener = listener_;
		this.dialogBox.show();
	}

	public static PopupDialogOption getInstance() {
		if (instance==null){
			instance= new PopupDialogOption();
		}
		return instance;
	}

	


}
