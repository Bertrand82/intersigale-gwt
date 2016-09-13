package bg.client.ui.importTranslate;



import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Feed extends JavaScriptObject {
	   protected Feed() {}

	   public final native JsArray<Entry> getEntries() /*-{
	     return this.feed.entry;
	   }-*/;
	 }
	 