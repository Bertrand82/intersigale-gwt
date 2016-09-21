package bg.server.inter.cicada.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;



@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class LexiqueEntity {

	
	
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)   
	@PrimaryKey
	private Long id;
	
	@Persistent
	private String name ;
	
	@Persistent
	private String emailOwner ;
	
	
	@Persistent
	private Text xml ;

	@Persistent
	private Date dateRegistered = new Date();

	@Persistent
	private Date dateModified = new Date();


	public LexiqueEntity() {
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmailOwner() {
		return emailOwner;
	}


	public void setEmailOwner(String emailOwner) {
		this.emailOwner = emailOwner;
	}


	public Text getXml() {
		return xml;
	}


	public void setXml(Text xml) {
		this.xml = xml;
	}


	public Date getDateRegistered() {
		return dateRegistered;
	}


	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}


	public Date getDateModified() {
		return dateModified;
	}


	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}


	public void setDateModified(long timeModified) {
		if (timeModified != 0){
			this.dateModified = new Date(timeModified);
		}
	}


	public void setDateRegistered(long timeRegistered) {
		if (timeRegistered != 0){
			this.dateRegistered= new Date(timeRegistered);
		}
	}


	public String getXmlAsStr() {
		if (xml == null){
			return null;
		}
		return xml.getValue();
	}
	
	
	

}
