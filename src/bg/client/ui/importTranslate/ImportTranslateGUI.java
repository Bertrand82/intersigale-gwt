package bg.client.ui.importTranslate;







import bg.client.inter.sigale.model.Lexique;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ImportTranslateGUI extends Composite {

	private static ImportTranslateUiBinder uiBinder = GWT.create(ImportTranslateUiBinder.class);

	interface ImportTranslateUiBinder extends UiBinder<Widget, ImportTranslateGUI> {
	}

	@UiField
	Button buttonImportTranslate;
	@UiField
	TextBox textBoxLexiqueNAme;
	@UiField
	TextArea textAeraSource;
	@UiField
	ListBox listBoxLangageSource;
	@UiField
	ListBox listBoxLangageDest;
	@UiField
	RadioButton radioQuestion;

	@UiField
	RadioButton radioResponse;

	MyPopup myPopup = new MyPopup();
	LexiqueSaveInPopup lexiqueSaveIn = new LexiqueSaveInPopup();
	public ImportTranslateGUI() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonImportTranslate.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				process();

			}
		});
	}

	private static ImportTranslateGUI instance;

	public static ImportTranslateGUI getInstance() {
		if (instance == null) {
			instance = new ImportTranslateGUI();
		}
		return instance;
	}

	private void process() {
		boolean srcIsQuestion =radioQuestion.getValue();
		int selectedIndexLanageSrc = this.listBoxLangageSource.getSelectedIndex();
		String langageSrc = this.listBoxLangageSource.getValue(selectedIndexLanageSrc);
		int selectedIndexLanageDest = this.listBoxLangageDest.getSelectedIndex();
		String langageDest = this.listBoxLangageDest.getValue(selectedIndexLanageDest);
		String lexiqueName = textBoxLexiqueNAme.getText();
		if (lexiqueName.trim().isEmpty()) {
			lexiqueName = "No Name";
		}
		String textSrc = this.textAeraSource.getText();
		if((""+langageSrc).equals(langageDest)){
			Window.alert("Langage src must be different than langage target");
			return;
		}
		String s = "src : " + langageSrc + " | dest : " + langageDest + " | lexique Name :" + lexiqueName + " | textSrc :" + textSrc;
		
		GWT.log("process Translate "+lexiqueName+" "+langageSrc+"  "+langageDest+"  "+srcIsQuestion);
		TranslateServiceGoogle.getInstance().translate(lexiqueName, langageSrc, langageDest,srcIsQuestion, textSrc);
		
	}
	
	public void showPopupSave(String lexiqueName, Lexique lexique) {
		lexiqueSaveIn.getTextBox().setText(lexiqueName);
		myPopup.showWidget(lexiqueSaveIn);
	}
	
	public void hidePopup() {
		this.myPopup.hide();
	}
	
	private static class MyPopup extends PopupPanel {

		public MyPopup() {

			super(true);
		}

		Widget wOld = null;

		public void showWidget(Widget w) {
			if (wOld != null) {
				this.remove(wOld);
			}
			wOld = w;
			MyPopup.this.add(w);
			MyPopup.this.center();
			MyPopup.this.show();
		}

	}

}
