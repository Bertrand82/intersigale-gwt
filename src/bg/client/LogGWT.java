package bg.client;

import bg.client.inter.cicada.util.ILogListener;
import bg.client.ui.log.LogUI;

import com.google.gwt.core.client.GWT;

public class LogGWT implements ILogListener {

	public void log(String message) {
		GWT.log(message);
	}

	public void log(String message, Throwable t) {
		GWT.log(message, t);
	}

	@Override
	public void logText(String message) {
		LogUI.getInstance().setText(message);
		GWT.log(message);

	}

	@Override
	public void logTitle(String s) {
		EntryPointSigale.setTitle(s);
		GWT.log(s);
	}

}
