package bg.client.ui.importTranslate;

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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ImportTranslate extends Composite {

	private static ImportTranslateUiBinder uiBinder = GWT.create(ImportTranslateUiBinder.class);

	interface ImportTranslateUiBinder extends UiBinder<Widget, ImportTranslate> {
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

	public ImportTranslate() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonImportTranslate.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				process();
				
			}
		});
	}

	private static ImportTranslate instance;

	public static ImportTranslate getInstance() {
		if (instance == null) {
			instance = new ImportTranslate();
		}
		return instance;
	}
	
	private void process() {
		int selectedIndexLanageSrc = this.listBoxLangageSource.getSelectedIndex();
		String langageSrc = this.listBoxLangageSource.getValue(selectedIndexLanageSrc);
		int selectedIndexLanageDest = this.listBoxLangageDest.getSelectedIndex();
		String langageDest = this.listBoxLangageDest.getValue(selectedIndexLanageDest);
		String lexiqueName = textBoxLexiqueNAme.getText();
		String textSrc = this.textAeraSource.getText();
		String s = "src : "+langageSrc+" | dest : "+langageDest+" | lexique Name :"+lexiqueName+" | textSrc :"+textSrc;
		GWT.log("process Translate");
		TranslateServiceGoogle.getInstance().translate(lexiqueName,langageSrc,langageDest,textSrc);
	}

}
