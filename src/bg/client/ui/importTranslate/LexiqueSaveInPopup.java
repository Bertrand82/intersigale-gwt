package bg.client.ui.importTranslate;

import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LexiqueSaveInPopup extends Composite {

	private static LexiqueSaveInUiBinder uiBinder = GWT.create(LexiqueSaveInUiBinder.class);

	interface LexiqueSaveInUiBinder extends UiBinder<Widget, LexiqueSaveInPopup> {
	}

	public LexiqueSaveInPopup() {
		initWidget(uiBinder.createAndBindUi(this));
		textBox.setText("");
		buttonDontSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				hidePopup();
			}
		});
		buttonSaveInLocal.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				LexiqueFactory.getInstance().saveLexiqueInLocal(LexiqueSaveInPopup.this.lexique);
				hidePopup();
			}
		});
		buttonSaveInRemote.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("No Implemented yet");
				hidePopup();
			}
		});
	}

	@UiField
	TextBox textBox;

	@UiField
	Button buttonSaveInLocal;
	@UiField
	Button buttonSaveInRemote;
	@UiField
	Button 	buttonDontSave;
	private Lexique lexique;
	

	public TextBox getTextBox() {
		return textBox;
	}

	public Lexique getLexique() {
		return lexique;
	}

	public void setLexique(Lexique lexique) {
		this.lexique = lexique;
	}
	private void hidePopup(){
		ImportTranslateGUI.getInstance().hidePopup();
		this.lexique=null;
	}

}
