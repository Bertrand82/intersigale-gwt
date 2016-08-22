package bg.client;

import bg.client.inter.sigale.util.ILogListener;

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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void logTitle(String s) {
		// TODO Auto-generated method stub
		
	}

}
