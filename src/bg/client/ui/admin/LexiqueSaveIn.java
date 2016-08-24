package bg.client.ui.admin;

import bg.client.inter.sigale.model.LexiqueFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LexiqueSaveIn extends Composite  {

	private static LexiqueSaveInUiBinder uiBinder = GWT
			.create(LexiqueSaveInUiBinder.class);

	interface LexiqueSaveInUiBinder extends UiBinder<Widget, LexiqueSaveIn> {
	}

	public LexiqueSaveIn() {
		initWidget(uiBinder.createAndBindUi(this));
		textBox.setText("");
	}
	@UiField
	TextBox textBox;
	
	@UiField
	Button buttonSaveIn;

	
	@UiHandler("buttonSaveIn")
	void onClick(ClickEvent e) {
		String newName = this.textBox.getText();
		AdminGUI.getInstance().hidePopup();
		LexiqueFactory.getInstance().saveLexique(newName);
	}

	
}
