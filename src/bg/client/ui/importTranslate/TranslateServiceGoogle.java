package bg.client.ui.importTranslate;

import java.util.ArrayList;
import java.util.List;

import bg.client.LogGWT;
import bg.client.SigaleUtil;
import bg.client.inter.sigal.beans.UserBean;
import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.UniteLexicale;
import bg.client.ui.login.LoginService;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

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

	
	String url0 = "https://www.googleapis.com/language/translate/v2?";
	// String url =
	// "http://www.google.com/calendar/feeds/developer-calendar@google.com/public/full?alt=json-in-script";
	String url__ = "https://www.googleapis.com/language/translate/v2?key=AIzaSyDouqYxNhF8U1vfI7YM7uzEzZi9DjYzJQ4&q=hello%20world&source=en&target=de";
	// Pour avoir la liste des langues proposées par google :
	// https://www.googleapis.com/language/translate/v2/languages?key=AIzaSyDouqYxNhF8U1vfI7YM7uzEzZi9DjYzJQ4BG&target=en
	private String buildUrl(String langageSrc, String langageDest, String key, final List<String> list) {
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
		
		final UserBean userBean = LoginService.getInstance().getUserBean();
		if (userBean == null){
			Window.alert("No registered. For translating service, user must be registered");
			return;
		}
		AsyncCallback<String> callback = new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable e) {
				Window.alert("Exception during fetching translate key :"+e.getMessage());
			}

			@Override
			public void onSuccess(String key) {
				translate2(nameLexique, langageSrc, langageDest, srcIsQuestion, textSrc, key);

			}
		};
		SigaleUtil.getSigaleService().getKey(userBean, callback);
	}
	private void translate2(final String nameLexique, final String langageSrc, final String langageDest, final boolean srcIsQuestion, final String textSrc, String key) {
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

		String url = buildUrl(langageSrc, langageDest, key, list);
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
				String translatedEncoded = jsonStringtranslated.stringValue();
				String translated = decode(translatedEncoded);
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
		ImportTranslateGUI.getInstance().showPopupSave(nameLexique, lexique);
		LexiqueFactory.getInstance().notifyNewLexique();
		
	}
	
	private static String decode(String s){
		String r =s.replace("&#39;", "'");
		return r;
	}

	public void fetchLangages() {

		final UserBean userBean = LoginService.getInstance().getUserBean();
		if (userBean == null){
			Window.alert("No registered. For translating service, user must be registered  453");
			return;
		}
		AsyncCallback<String> callback = new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable e) {
				Window.alert("Exception during fetching translate key :"+e.getMessage());
			}

			@Override
			public void onSuccess(String key) {
				fetchLangages2(key);
			}
		};
		SigaleUtil.getSigaleService().getKey(userBean, callback);
	}
	public void fetchLangages2(String key) {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setTimeout(60000);
		AsyncCallback<JavaScriptObject> callBack = new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable e) {
				log.log("Request to google lanages Exception: ", e);
				Window.alert("getLangages onFAilure 1234 :" + e);

			}

			@Override
			public void onSuccess(JavaScriptObject jsonResponse) {
				List<Langage> list = parseJsonLangagesResponse( jsonResponse);
				ImportTranslateGUI.getInstance().setListLangages(list);
			}

		};

		
		String url ="https://www.googleapis.com/language/translate/v2/languages?key="+key+"&target=en";
		jsonp.requestObject(url, callBack);
		
	}
	
	
	public List<Langage> parseJsonLangagesResponse( JavaScriptObject jsonResponse) {
		List<Langage> listLangages = new ArrayList<Langage>();
		try {
			JSONObject json = new JSONObject(jsonResponse);
			JSONValue jsonDataValue = json.get("data");
			JSONObject jsonData = jsonDataValue.isObject();
			JSONValue jsonLangagesValue = jsonData.get("languages");
			JSONArray jsonLangagesArray = jsonLangagesValue.isArray();
			
			for (int i = 0; i < jsonLangagesArray.size(); i++) {
				JSONValue jsonItemValue = jsonLangagesArray.get(i);
				JSONObject jsonItem = jsonItemValue.isObject();
				JSONValue jsonLangage= jsonItem.get("language");
				JSONString jsonStringLangage = jsonLangage.isString();
				String langageStr = jsonStringLangage.stringValue();
				JSONValue jsonName= jsonItem.get("name");
				JSONString jsonStringName = jsonName.isString();
				String name = jsonStringName.stringValue();
				
				Langage langage = new Langage(langageStr, name);
				listLangages.add(langage);
				// String noTranslated = list.get(i);
				// Window.alert("onSuccese3 : " + i +" No Translated "
				// +noTranslated+ "|Translated : " + translated);
			}
			log.logText("Request to google langages : " + listLangages.size());
		} catch (Exception e) {
			log.log("Exception parsing translation from google", e);
		}
		return listLangages;
	}

}
