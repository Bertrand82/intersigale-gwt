package bg.client.ui.debug;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Debug extends Composite {

	@UiField
	Label labelScreen;
	
	@UiField
	Label labelStorage;
	
	@UiField
	Label labelCanvas;
	
	@UiField
	Button buttonRefresh;
	
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
		init();
	}
	
	private void init() {
		String textScreen =" Screen "+Window.getClientWidth()+"x"+Window.getClientHeight();
		labelScreen.setText(textScreen);
		String textStorage = "LocalStorage Html5 isSupported :"+Storage.isSupported();
		labelStorage.setText(textStorage);
		String textCanvas ="Canvas html5 isSupported :"+Canvas.isSupported();
		labelCanvas.setText(textCanvas);
		buttonRefresh.setText("Refresh");
	}
    


	public static Debug getInstance() {
		if (instance == null){
			instance = new Debug();
		}
		return instance;
	}
}
