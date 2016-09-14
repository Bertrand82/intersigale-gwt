package bg.client.ui.importTranslate;



import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;
/**
 * 
 * 
  {
 "data": {
  "translations": [
   {
    "translatedText": "Hallo Welt"
   }
  ]
 }
}
 * 
 * @author Bertrand
 *
 */
public class Feed extends JavaScriptObject {
	   protected Feed() {}

	   public final native JavaScriptObject getData() /*-{
	     return this.feed.data;
	   }-*/;
	   
	   
	 }
	 