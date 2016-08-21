package bg.client.inter.sigale.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.storage.client.Storage;



public class PersisterLexique {

	
	private static final String TAG_Lexique ="Lexique_";

	public void save(String xml, String name) {
		Storage storage = Storage.getLocalStorageIfSupported();
		storage.setItem(getLexiqueKeyStoreFromName(name), xml);
	}
	
	
	
	public List<String> getListLexiqueInStorage(){
		List<String> list = new ArrayList<String>();
		Storage storage = Storage.getLocalStorageIfSupported();
		if (storage!= null){
			for (int i = 0; i < storage.getLength(); i++){
			    String key = storage.key(i);
			    if(key.startsWith(TAG_Lexique)){
			    	list.add(getLexiqueNameFromKey(key));
			    }
			  }
		}
		return list;
	}
	
	public static String getLexiqueKeyStoreFromName(String name){
		return TAG_Lexique+name;
	}
	
	public static String getLexiqueNameFromKey(String key){
		return key.substring(TAG_Lexique.length());
	}

}
