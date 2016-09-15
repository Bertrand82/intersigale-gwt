package bg.client.ui.importTranslate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import bg.client.LogGWT;
import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.UniteLexicale;

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
import com.ibm.icu.util.StringTokenizer;

/**
 * 
 * @author Bertrand La key doit être trouvée sur :
 *         https://console.cloud.google.com
 *         /apis/credentials/key/0?project=sigale-1267 Rechercher l'api
 *         "translate", puis sélctioner la clé, et faire les manips de sécurité
 *         qui s"imposent
 */

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

	private String buildUrl(String langageSrc, String langageDest, final List<String> list) {
		String s = url0;
		s += "key=" + key;
		for (String textSrc : list) {
			s += "&q=" + URL.encode(textSrc);
		}
		s += "&source=" + langageSrc;
		s += "&target=" + langageDest;
		return s;
	}

	public void translate(final String nameLexique, final String langageSrc, final String langageDest, final boolean srcIsQuestion, final String textSrc) {
		final List<String> list = getStrings(textSrc);
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setTimeout(60000);
		AsyncCallback<JavaScriptObject> callBack = new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable e) {
				log.log("Request to google translate Exception: ", e);
				Window.alert("translate onFAilure 1230 :" + e);

			}

			@Override
			public void onSuccess(JavaScriptObject jsonResponse) {
				parseJsonTranslateResponse(nameLexique, srcIsQuestion, list, jsonResponse);
			}

		};

		String url = buildUrl(langageSrc, langageDest, list);
		log.logText("Request to google translate : " + list.size());
		jsonp.requestObject(url, callBack);
	}

	private List<String> getStrings(String textSrc) {
		List<String> list = new ArrayList<String>();
		String[] sArray = textSrc.split("\\r?\\n");
		for (String s : sArray) {
			if (!s.trim().isEmpty()) {
				list.add(s);
			}
		}
		return list;
	}

	public void parseJsonTranslateResponse(String nameLexique, boolean srcIsQuestion, List<String> list, JavaScriptObject jsonResponse) {
		try {
			JSONObject json = new JSONObject(jsonResponse);
			JSONValue jsonDataValue = json.get("data");
			JSONObject jsonData = jsonDataValue.isObject();
			JSONValue jsonTranslationsValue = jsonData.get("translations");
			JSONArray jsonTranslationsArray = jsonTranslationsValue.isArray();
			List<String> listTranslated = new ArrayList<String>();
			for (int i = 0; i < jsonTranslationsArray.size(); i++) {
				JSONValue jsonTanslatedItemValue = jsonTranslationsArray.get(i);
				JSONObject jsonTranslatedItem = jsonTanslatedItemValue.isObject();
				JSONValue jsonTranslated = jsonTranslatedItem.get("translatedText");
				JSONString jsonStringtranslated = jsonTranslated.isString();
				String translated = jsonStringtranslated.stringValue();
				listTranslated.add(translated);
				// String noTranslated = list.get(i);
				// Window.alert("onSuccese3 : " + i +" No Translated "
				// +noTranslated+ "|Translated : " + translated);
			}
			log.logText("Request to google translated : " + listTranslated.size());
			createLexique(nameLexique, srcIsQuestion, list, listTranslated);
		} catch (Exception e) {
			log.log("Exception parsing translation from google", e);
		}

	}

	private void createLexique(String nameLexique, boolean srcIsQuestion, List<String> list, List<String> listTranslated) {
		if (nameLexique.trim().isEmpty()) {
			nameLexique = "No Name";
		}
		Lexique lexique = LexiqueFactory.getInstance().getLexiqueByNameInLocalStore(nameLexique.trim());
		for (int i = 0; i < list.size(); i++) {
			String text1 = list.get(i);
			String text2 = listTranslated.get(i);
			UniteLexicale ul;
			if (srcIsQuestion) {
				ul = new UniteLexicale(text1, text2);
			} else {
				ul = new UniteLexicale(text2, text1);
			}
			lexique.add(ul);
		}
		log.logText("create lexique done  : listsize " + list.size());
	}

}
