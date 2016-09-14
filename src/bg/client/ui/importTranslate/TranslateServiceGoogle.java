package bg.client.ui.importTranslate;

import java.net.URLEncoder;

import bg.client.LogGWT;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dev.json.JsonString;
import com.google.gwt.json.client.*;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

//import com.google.gwt.language.client.translation.Translation;

public class TranslateServiceGoogle {

	private static TranslateServiceGoogle instance;
	static LogGWT log = new LogGWT();

	private TranslateServiceGoogle() {

	}

	public static TranslateServiceGoogle getInstance() {
		if (instance == null) {
			instance = new TranslateServiceGoogle();
		}
		return instance;
	}

	String key = "AIzaSyDouqYxNhF8U1vfI7YM7uzEzZi9DjYzJQ4BG";
	String url0 = "https://www.googleapis.com/language/translate/v2?";
	// String url =
	// "http://www.google.com/calendar/feeds/developer-calendar@google.com/public/full?alt=json-in-script";
	String url__ = "https://www.googleapis.com/language/translate/v2?key=AIzaSyDouqYxNhF8U1vfI7YM7uzEzZi9DjYzJQ4&q=hello%20world&source=en&target=de";
    private String buildUrl(String langageSrc, String langageDest, final String textSrc){
    	String s = url0;
    	s +="key="+key;
    	s +="&q="+URL.encode(textSrc) ;
    	s +="&source="+langageSrc;
    	s += "&target="+langageDest;
    	return s;
    }
	public void translate(String langageSrc, String langageDest, final String textSrc) {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setTimeout(60000);
		AsyncCallback<JavaScriptObject> callBack = new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable e) {
				Window.alert("onFAilure0 :" + e);
			}

			@Override
			public void onSuccess(JavaScriptObject jsonResponse) {
				parseJsonTranslateResponse(textSrc, jsonResponse);
			}

		};
		String url = buildUrl(langageSrc, langageDest, textSrc);
		jsonp.requestObject(url, callBack);
	}

	public void parseJsonTranslateResponse(String textSrc, JavaScriptObject jsonResponse) {
		JSONObject json = new JSONObject(jsonResponse);
		if (json.containsKey("data")) {
			JSONValue jsonDataValue = json.get("data");
			JSONObject jsonData = jsonDataValue.isObject();
			JSONValue jsonTranslationsValue = jsonData.get("translations");
			JSONArray jsonTranslationsArray = jsonTranslationsValue.isArray();
			for (int i = 0; i < jsonTranslationsArray.size(); i++) {
				JSONValue jsonTanslatedItemValue = jsonTranslationsArray.get(i);
				JSONObject jsonTranslatedItem = jsonTanslatedItemValue.isObject();
				JSONValue jsonTranslated = jsonTranslatedItem.get("translatedText");
				JSONString jsonStringtranslated = jsonTranslated.isString();
				String translated = jsonStringtranslated.stringValue();
				Window.alert("onSuccese3 : " + i + "|Translated : " + translated);
			}
		}
	}

}
