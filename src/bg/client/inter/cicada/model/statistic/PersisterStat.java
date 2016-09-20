package bg.client.inter.cicada.model.statistic;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.storage.client.Storage;

public class PersisterStat {

	private final String KEY_UL = "StatUL";

	public void saveStatistique(String xml, String name) {
		// TODO Auto-generated method stub

	}

	public void saveStatistique(String lexiqueName, StatistiquesUL statistiquesUL) {
		Storage storage = Storage.getLocalStorageIfSupported();
		String key = getStatULKeyStoreFromName(lexiqueName, statistiquesUL.getUniteLexicaleId());
		JSONObject json = StatistiquesLexiqueFactory.toJSon(statistiquesUL);
		String jsonStr = json.toString();
		storage.setItem(key, jsonStr);
	}

	public String getJsonUL(String lexiqueName, String id) {
		Storage storage = Storage.getLocalStorageIfSupported();
		String key = getStatULKeyStoreFromName(lexiqueName, id);
		String s = storage.getItem(key);
		return s;
	}

	private String getStatULKeyStoreFromName(String lexiqueName, String uniteLexicaleId) {
		String key = KEY_UL + ":" + lexiqueName + ":" + uniteLexicaleId;
		return key;
	}

}
