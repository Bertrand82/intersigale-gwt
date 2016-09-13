package bg.client.ui.importTranslate;

import com.google.gwt.core.client.JavaScriptObject;

public class Entry extends JavaScriptObject {
	   public Entry() {}

	   public final native String getTranslatedText() /*-{
	     return this.translatedText.$t;
	   }-*/;

	 
	 }
