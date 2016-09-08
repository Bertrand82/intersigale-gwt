package bg.client.ui.admin;

import java.util.List;

import bg.client.LogGWT;
import bg.client.SigaleService;
import bg.client.SigaleServiceAsync;
import bg.client.inter.sigal.beans.LexiqueMetaData;
import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.util.ILogListener;
import bg.client.ui.admin.chooser.LexiqueChooser;
import bg.client.ui.util.popup.IPopupListener;
import bg.client.ui.util.popup.PopupDialogOption;








import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class AdminGUI extends Composite {

	private static AdminGUIUiBinder uiBinder = GWT.create(AdminGUIUiBinder.class);

	private ILogListener log = new LogGWT();
	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final SigaleServiceAsync sigaleService = GWT.create(SigaleService.class);

	interface AdminGUIUiBinder extends UiBinder<Widget, AdminGUI> {
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

	MyPopup myPopup = new MyPopup();
	LexiqueSaveIn lexiqueSaveIn = new LexiqueSaveIn();

	@UiField
	Button buttonChooseLexique;

	@UiField
	Button buttonSaveLexiqueInRemote;

	@UiField
	Button buttonSaveLexiqueInLocal;

	@UiField
	Button buttonCreateLexique;

	private static AdminGUI instance;

	public static Widget getInstancMyPopupe() {
		if (instance == null) {
			instance = new AdminGUI();
		}
		return instance;
	}

	private AdminGUI() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonChooseLexique.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				List<String> list = LexiqueFactory.getInstance().getLexiquesInLocalStorage();
				LexiqueChooser.getInstance().setLexiques(list);
				myPopup.showWidget(LexiqueChooser.getInstance().getWidget());

			}

		});
		buttonSaveLexiqueInRemote.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				try {
					Lexique lexique = LexiqueFactory.getInstance().getLexique();
					String usermail="bg@bg";
					String xml = LexiqueFactory.getInstance().toXml(lexique);
					AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable t) {
							log.log("Fail to save Lexique", t);
						}

						@Override
						public void onSuccess(Boolean result) {
							log.log("Save Lexique :"+result);
						}
						
					};
					LexiqueMetaData lmd = LexiqueFactory.getInstance().getLexiqueMetaData(lexique);
					
					sigaleService.storeLexiqueMetadata(lmd, callback);
				} catch (Exception e) {
					log.log("Fail to save Lexique", e);
					Window.alert("Save Lexique Exception " + e.getMessage());
					
				}
			}
		});
		buttonSaveLexiqueInLocal.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Lexique lexique = LexiqueFactory.getInstance().getLexique();
				lexiqueSaveIn.getTextBox().setText(lexique.getName().trim());
				myPopup.showWidget(lexiqueSaveIn);
			}
		});
		buttonCreateLexique.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				IPopupListener listener = new IPopupListener() {

					public void actionPerformed(int option, String name) {
						if (option == PopupDialogOption.YES_OPTION) {
							LexiqueFactory.getInstance().createLexique(name);
						}

					}
				};
				PopupDialogOption.getInstance().showConfirmDialog("New Name ?", "Cancel", "OK", true, listener);
			}
		});
	}

	public static AdminGUI getInstance() {
		if (instance == null) {
			instance = new AdminGUI();
		}
		return instance;
	}

	public void hidePopup() {
		this.myPopup.hide();
	}

}
