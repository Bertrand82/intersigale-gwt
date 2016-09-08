package bg.client.ui.admin;

import java.util.List;

import bg.client.LogGWT;
import bg.client.SigaleService;
import bg.client.SigaleServiceAsync;
import bg.client.inter.sigal.beans.LexiqueMetaData;
import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.util.ILogListener;
import bg.client.ui.admin.chooser.ListSimpleBeanChooser;
import bg.client.ui.admin.chooserLexique.IAction;
import bg.client.ui.admin.chooserLexique.BeanChooser;
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
	
	private IAction actionChooserRemote = new IAction() {
		
		
		public void display(String id, String name) {
			LexiqueFactory.getInstance().getLexiqueByIdInRemoteStore(id,name);
			log.log("Display "+id);
		}
		
		
		public void delete(String id, String name) {
			log.log("Delete "+id+"   "+name);
			LexiqueFactory.getInstance().deleteInRemoteStore(id,name);
		}
	};
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
	Button buttonChooseLexiqueInLocal;
	
	@UiField
	Button buttonChooseLexiqueInRemote;

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
		buttonChooseLexiqueInLocal.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				List<String> list = LexiqueFactory.getInstance().getLexiquesInLocalStorage();
				ListSimpleBeanChooser.getInstance().setLexiques(list);
				myPopup.showWidget(ListSimpleBeanChooser.getInstance().getWidget());

			}

		});
		
		buttonChooseLexiqueInRemote.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String email ="bg@bg";
				
				AsyncCallback  <List<LexiqueMetaData>> callback = new AsyncCallback<List<LexiqueMetaData> >() {

					@Override
					public void onFailure(Throwable e) {
						log.log("Get List exception "+e);
					}

					@Override
					public void onSuccess(List<LexiqueMetaData>  result) {
						log.log("Get List size :"+result.size());
						BeanChooser chooser = BeanChooser.getInstance() ;
						chooser.setLexiqueMetadata(result);
						chooser.setiAction(actionChooserRemote);
						
						myPopup.showWidget(BeanChooser.getInstance().getWidget());
					}
				};
				log.log("Send request to server ");
				sigaleService.getListLexiquesByOwner(email, callback);
			}

		});
		
		
		buttonSaveLexiqueInRemote.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				try {
					final Lexique lexique = LexiqueFactory.getInstance().getLexique();
					String usermail="bg@bg";
					String xml = LexiqueFactory.getInstance().toXml(lexique);
					AsyncCallback<Long> callback = new AsyncCallback<Long>() {

						@Override
						public void onFailure(Throwable t) {
							log.log("Fail to save Lexique", t);
						}

						@Override
						public void onSuccess(Long result) {
							log.log("Save Lexique :"+result);
							lexique.setId(result);
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
