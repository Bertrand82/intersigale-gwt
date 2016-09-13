package bg.client.ui.importTranslate;

import bg.client.LogGWT;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TranslateServiceGoogle {

	private static TranslateServiceGoogle instance;
	static LogGWT log = new LogGWT();

	private TranslateServiceGoogle() {

	}

	public static TranslateServiceGoogle getInstance() {
		return instance;
	}

	// String url =
	// "http://www.google.com/calendar/feeds/developer-calendar@google.com/public/full?alt=json-in-script";
	String url = "https://www.googleapis.com/language/translate/v2?key=AIzaSyDouqYxNhF8U1vfI7YM7uzEzZi9DjYzJQ4&q=hello%20world&source=en&target=de";

	public void translate_____() {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setTimeout(60000);
		AsyncCallback<JavaScriptObject> callBack = new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable e) {
				Window.alert("onFAilure " + e);
			}

			@Override
			public void onSuccess(JavaScriptObject feed) {
				Window.alert("onSuccese " + feed);
			}

		};
		jsonp.requestObject(url, callBack);
	}

	public void translate() {
		log.log("translate start");
		Callback<JavaScriptObject, Void> callback = new Callback<JavaScriptObject, Void>() {

			@Override
			public void onFailure(Void e) {
				Window.alert("OnFAilure 43333" + e);
			}

			@Override
			public void onSuccess(JavaScriptObject result) {
				Window.alert("OnFAilure 4350003344 " + result);

			}
		};
		processJSONRequest(url, callback);
		log.log("translate request done");
	}

	/**
	 * Process json request.
	 * 
	 * @param url
	 *            the url
	 * @param callback
	 *            the callback
	 */
	public static void processJSONRequest(final String url, final Callback<JavaScriptObject, Void> callback) {
		final JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.setTimeout(60000);
		@SuppressWarnings("unused")
		final JsonpRequest<JavaScriptObject> request = builder.requestObject(url, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onFailure(final Throwable e) {
				Window.alert("OnFAilure 435 " + e);
				callback.onFailure(null);
			}

			@Override
			public void onSuccess(final JavaScriptObject result) {
				Window.alert("OnFAilure 435000 " + result);
				callback.onSuccess(result);
			}
		});
		log.log("processJSONRequestdone url: " + url);
	}

}
