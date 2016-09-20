package bg.server.inter.cicada.data;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ConstanteEntity {
	
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent
	private String key;
	
	@Persistent
	private String value;	

	
	
	public ConstanteEntity(){
		
	}



	public String getKey() {
		return key;
	}



	public void setKey(String key) {
		this.key = key;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}

	

}
