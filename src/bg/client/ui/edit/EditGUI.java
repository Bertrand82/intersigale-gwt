package bg.client.ui.edit;

import bg.client.EntryPointSigale;
import bg.client.register.RegisterForm;

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
import com.google.gwt.user.client.ui.Widget;

public class EditGUI extends Composite {

	private static EditGUIUiBinder uiBinder = GWT.create(EditGUIUiBinder.class);

	interface EditGUIUiBinder extends UiBinder<Widget, EditGUI> {
	}

	

	@UiField
	Button button;
	
	@UiField
	Button buttonChooseLexique;
	
	@UiField
	Button buttonSaveLexique;
	
	@UiField
	Button buttonSaveLexiqueIn;
	
	@UiField
	Button buttonCreateLexique;

	private static EditGUI instance;
	public static Widget getInstance() {
		if (instance == null){
			instance = new EditGUI();
		}
		return instance;
	}


	private EditGUI() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonChooseLexique.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("No Implemented yet");
			}
		});
		buttonSaveLexique.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("No Implemented yet");
			}
		});
		buttonSaveLexiqueIn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("No Implemented yet");
			}
		});
		buttonCreateLexique.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("No Implemented yet");
			}
		});
	}
	
}
