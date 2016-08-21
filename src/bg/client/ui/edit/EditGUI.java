package bg.client.ui.edit;

import java.util.List;

import bg.client.EntryPointSigale;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.ui.register.RegisterForm;

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
				List<String> list = LexiqueFactory.getInstance().getLexiquesInLocalStorage();
				Window.alert("Choose Lexique No Implemented yet list: "+list.size()+"  "+list);
			}
		});
		buttonSaveLexique.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				try {
					LexiqueFactory.getInstance().saveLexique();
				} catch (Exception e) {
					Window.alert("Save Lexique Exception "+e.getMessage());
					e.printStackTrace();
				}
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
