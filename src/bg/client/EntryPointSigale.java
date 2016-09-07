package bg.client;

import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.SigalePropertiesGWT;
import bg.client.ui.lesson.Lesson;
import bg.client.ui.log.LogUI;
import bg.client.ui.menu.Menu;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EntryPointSigale implements EntryPoint {

	private static RootPanel rootPanelRegister;
	private static Label labelTitle = new Label("InterSigale");
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. Please check your network connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final SigaleServiceAsync sigaleService = GWT.create(SigaleService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		/*
		 * Necessaire pour pouvoir avoir les traces des exceptions en dev (Mode
		 * super dev) dans la console.
		 */
		GWT.setUncaughtExceptionHandler(uncaughtExceptionHandler);
		new LexiqueFactory(new SigalePropertiesGWT(), new LogGWT());
		RootPanel rootPanelMenu = RootPanel.get("sigaleMenu");
		if (rootPanelMenu != null) {
			final Menu menu = Menu.getInstance();
			rootPanelMenu.add(menu);
		}

		rootPanelRegister = RootPanel.get("sigalePanel");
		if (rootPanelRegister != null) {
			final Lesson widget = Lesson.getInstance();
			rootPanelRegister.add(widget);
		}

		RootPanel rootPanelTitle = RootPanel.get("sigaleTitle");
		if (rootPanelTitle != null) {

			rootPanelTitle.add(labelTitle);
		}

		RootPanel rootPanelLog = RootPanel.get("sigaleLog");
		if (rootPanelLog != null) {
			final LogUI logUI = LogUI.getInstance();
			rootPanelLog.add(logUI);
		}

	}

	public static void showView(Widget composite) {
		rootPanelRegister.remove(0);
		rootPanelRegister.add(composite);
	}

	public static void setTitle(String message) {
		labelTitle.setText(message);
	}

	/*
	 * 
	 */
	private static GWT.UncaughtExceptionHandler uncaughtExceptionHandler = new GWT.UncaughtExceptionHandler() {

		@Override
		public void onUncaughtException(Throwable e) {

			GWT.log("Exception " + e.getMessage(), e);
		}
	};

}
