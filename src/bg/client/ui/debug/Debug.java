package bg.client.ui.debug;

import java.util.List;

import bg.client.LogGWT;
import bg.client.SigaleService;
import bg.client.SigaleServiceAsync;
import bg.client.inter.sigal.beans.LexiqueMetaData;
import bg.client.inter.sigale.util.ILogListener;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Debug extends Composite {

	private final SigaleServiceAsync sigaleService = GWT.create(SigaleService.class);
	private ILogListener logListener = new LogGWT();

	@UiField
	Label labelScreen;

	@UiField
	Label labelStorage;

	@UiField
	Label labelCanvas;

	@UiField
	Button buttonRefresh;

	@UiField
	Button buttonGetListLexiques;

	private static DebugUiBinder uiBinder = GWT.create(DebugUiBinder.class);

	interface DebugUiBinder extends UiBinder<Widget, Debug> {
	}

	private static Debug instance;

	public Debug() {
		initWidget(uiBinder.createAndBindUi(this));

		buttonRefresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				init();
			}
		});

		buttonGetListLexiques.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				init();
				String email ="bg@bg";
				
				AsyncCallback  <List<LexiqueMetaData>> callback = new AsyncCallback<List<LexiqueMetaData> >() {

					@Override
					public void onFailure(Throwable e) {
						logListener.log("Get List exception "+e);
					}

					@Override
					public void onSuccess(List<LexiqueMetaData>  result) {
						logListener.log("Get List "+result);
						buttonGetListLexiques.setText("list size : "+result.size());
					}
				};
				sigaleService.getListLexiquesByOwner(email, callback);
			}
		});
		init();
	}

	private void init() {
		String textScreen = " Screen " + Window.getClientWidth() + "x" + Window.getClientHeight();
		labelScreen.setText(textScreen);
		String textStorage = "LocalStorage Html5 isSupported :" + Storage.isSupported();
		labelStorage.setText(textStorage);
		String textCanvas = "Canvas html5 isSupported :" + Canvas.isSupported();
		labelCanvas.setText(textCanvas);
		buttonRefresh.setText("Refresh");
	}

	public static Debug getInstance() {
		if (instance == null) {
			instance = new Debug();
		}
		return instance;
	}
}
