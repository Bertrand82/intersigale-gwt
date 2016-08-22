package bg.client.ui.edit;

import java.util.List;

import bg.client.inter.sigale.model.LexiqueFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditGUI extends Composite {

	private static EditGUIUiBinder uiBinder = GWT.create(EditGUIUiBinder.class);

	interface EditGUIUiBinder extends UiBinder<Widget, EditGUI> {
	}


	private  static class MyPopup extends PopupPanel {

	    public MyPopup() {
	      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
	      // If this is set, the panel closes itself automatically when the user
	      // clicks outside of it.
	      super(true);

	      // PopupPanel is a SimplePanel, so you have to set it's widget property to
	      // whatever you want its contents to be.
	      setWidget(LexiqueChooser.getInstance());
	    }

		public void setLexiques(List<String> list) {
			LexiqueChooser.getInstance().setLexiques(list);
		}
	  }

	MyPopup myPopup = new MyPopup();
		
	@UiField
	Button buttonChooseLexique;
	
	@UiField
	Button buttonSaveLexique;
	
	@UiField
	Button buttonSaveLexiqueIn;
	
	@UiField
	Button buttonCreateLexique;

	private static EditGUI instance;
	public static Widget getInstancMyPopupe() {
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
				myPopup.setLexiques(list);
				myPopup.center();
				myPopup.show();
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


	public static EditGUI getInstance() {
		if(instance==null){
			instance = new EditGUI();
		}
		return instance;
	}
	
}
