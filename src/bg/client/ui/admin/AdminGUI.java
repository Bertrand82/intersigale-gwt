package bg.client.ui.edit;

import java.util.List;

import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.ui.edit.chooser.LexiqueChooser;

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

	private static class MyPopup extends PopupPanel {

		public MyPopup() {

			super(true);
		}

		Widget wOld = null;

		public void showWidget(Widget w) {
			if (wOld != null) {
				this.remove(wOld);
			}
			wOld=w;
			MyPopup.this.add(w);
			MyPopup.this.center();
			MyPopup.this.show();
		}

	}

	MyPopup myPopup = new MyPopup();
	LexiqueSaveIn lexiqueSaveIn = new LexiqueSaveIn();
	
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
		if (instance == null) {
			instance = new EditGUI();
		}
		return instance;
	}

	private EditGUI() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonChooseLexique.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				List<String> list = LexiqueFactory.getInstance()
						.getLexiquesInLocalStorage();
				LexiqueChooser.getInstance().setLexiques(list);
				myPopup.showWidget(LexiqueChooser.getInstance().getWidget());

			}

		});
		buttonSaveLexique.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				try {
					LexiqueFactory.getInstance().saveLexique();
					Window.alert("Save Lexique done");
				} catch (Exception e) {
					Window.alert("Save Lexique Exception " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
		buttonSaveLexiqueIn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				myPopup.showWidget(lexiqueSaveIn);;
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
		if (instance == null) {
			instance = new EditGUI();
		}
		return instance;
	}

	public void hidePopup() {
		this.myPopup.hide();
	}



}
