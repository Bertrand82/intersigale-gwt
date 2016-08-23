package bg.client;

import bg.client.inter.sigale.util.ILogListener;
import bg.client.ui.log.LogUI;

import com.google.gwt.core.shared.GWT;

public class Log implements ILogListener{
	
	

	public static void log(String message) {
		GWT.log(message);
	}
	public static void log(String message, Throwable t) {
		GWT.log(message,t);
	}
	
	
	@Override
	public void logText(String message) {
		LogUI.getInstance().setText(message);
		
	}
	@Override
	public void logTitle(String s) {
		EntryPointSigale.setTitle(s);
	}

}
