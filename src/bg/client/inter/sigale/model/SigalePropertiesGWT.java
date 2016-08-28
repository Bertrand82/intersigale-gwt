package bg.client.inter.sigale.model;

import java.util.Properties;

import org.apache.tools.ant.taskdefs.rmic.KaffeRmic;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.storage.client.Storage;

public class SigalePropertiesGWT implements ISigalePropertes {

	
	
	private String nameLexique;
	private static final String KEY_nameLexique="lexique";
	
	public SigalePropertiesGWT() {
	}

	/* (non-Javadoc)
	 * @see bg.client.inter.sigale.model.ISigalePropertes#getNameLexique()
	 */
	@Override
	public String getNameLexique() {
		if (nameLexique == null){
			nameLexique = fetchInLocalStore(KEY_nameLexique);
		}
		return nameLexique;
	}

	private String fetchInLocalStore(String key) {
		String value = null;
		try {
			Storage storage = Storage.getLocalStorageIfSupported();
			value = storage.getItem(this.getClass().getName()+key);
		} catch (Exception e) {
			GWT.log("SigaleProperties.fetchInLocalStore error 67654", e);;
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see bg.client.inter.sigale.model.ISigalePropertes#setNameLexique(java.lang.String)
	 */
	@Override
	public void setNameLexique(String nameLexique) {
		this.nameLexique = nameLexique;
		try {
			Storage storage = Storage.getLocalStorageIfSupported();
			storage.setItem(this.getClass().getName()+KEY_nameLexique, nameLexique);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			GWT.log("setNameLexique  676344", e);;
		}
	} 
	


}
