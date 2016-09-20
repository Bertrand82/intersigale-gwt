package bg.client.inter.cicada.model;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.storage.client.Storage;

public class CicadaPropertiesGWT implements ICicadaPropertes {

	private String nameLexique;
	private static final String KEY_nameLexique = "nameLexiqueCurrent";

	public CicadaPropertiesGWT() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.client.inter.sigale.model.ISigalePropertes#getNameLexique()
	 */
	@Override
	public String getNameLexique() {
		if (nameLexique == null) {
			nameLexique = fetchInLocalStore(KEY_nameLexique);
		}
		return nameLexique;
	}

	private String fetchInLocalStore(String key) {
		String value = null;
		try {
			Storage storage = Storage.getLocalStorageIfSupported();
			value = storage.getItem(this.getClass().getName() + key);
		} catch (Exception e) {
			GWT.log("SigaleProperties.fetchInLocalStore error 67654", e);
			;
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * bg.client.inter.sigale.model.ISigalePropertes#setNameLexique(java.lang
	 * .String)
	 */
	@Override
	public void setNameLexique(String nameLexique) {
		this.nameLexique = nameLexique;
		try {
			Storage storage = Storage.getLocalStorageIfSupported();
			storage.setItem(this.getClass().getName() + KEY_nameLexique, nameLexique);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			GWT.log("setNameLexique  676344", e);
			;
		}
	}

}
