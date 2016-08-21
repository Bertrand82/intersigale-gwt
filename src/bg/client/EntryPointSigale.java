package bg.client;

import bg.client.menu.Menu;
import bg.client.ui.register.RegisterForm;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EntryPointSigale implements EntryPoint {

	private static RootPanel rootPanelRegister;
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final SigaleServiceAsync sigaleService = GWT.create(SigaleService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel rootPanelMenu = RootPanel.get("sigaleMenu");
		if (rootPanelMenu != null) {
			final Menu menu = Menu.getInstance();
			rootPanelMenu.add(menu);
		}

		rootPanelRegister = RootPanel.get("sigalePanel");
		if (rootPanelRegister != null) {
			final RegisterForm crypto = RegisterForm.getInstance();
			rootPanelRegister.add(crypto);
		}

	}

	public static void showView(Widget composite) {
		rootPanelRegister.remove(0);
		rootPanelRegister.add(composite);
	}

}
