package bg.client.inter.sigale.model.statistic;

import com.google.gwt.dev.json.JsonObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.storage.client.Storage;



public class PersisterStat {

	private final String KEY_UL="StatUL";

	public void saveStatistique(String xml, String name) {
		// TODO Auto-generated method stub
		
	}

	public void saveStatistique(String lexiqueName, StatistiquesUL statistiquesUL) {
		Storage storage = Storage.getLocalStorageIfSupported();
		String key = getStatULKeyStoreFromName(lexiqueName,statistiquesUL);
		JSONObject json = StatistiquesLexiqueFactory.toJSon(statistiquesUL);
		String jsonStr = json.toString();
		storage.setItem(key, jsonStr);
	}

	private String getStatULKeyStoreFromName(String lexiqueName, StatistiquesUL statistiquesUL) {
		String key = KEY_UL+":"+lexiqueName+statistiquesUL.getUniteLexicaleId();
		return key;
	}

}
