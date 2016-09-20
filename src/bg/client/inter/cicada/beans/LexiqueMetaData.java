package bg.client.inter.cicada.beans;

import java.io.Serializable;
import java.util.Date;





public class LexiqueMetaData implements Serializable,IBean{


	private static final long serialVersionUID = 1L;


	private Long id;
	
	
	private String name ;
	
	private String emailOwner ;
	
	private String xml ;

	private long timeRegistered;

	private long timeModified ;


	public LexiqueMetaData() {
		
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


	public String getXml() {
		return xml;
	}


	public void setXml(String xml) {
		this.xml = xml;
	}


	public long getTimeRegistered() {
		return timeRegistered;
	}


	public void setTimeRegistered(long timeRegistered) {
		this.timeRegistered = timeRegistered;
	}


	public long getTimeModified() {
		return timeModified;
	}


	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}


	public void setDateRegistered(Date dateRegistered) {
		if (dateRegistered == null){
			return;
		}
		this.timeRegistered = dateRegistered.getTime();
	}


	public void setDateModified(Date dateModified) {
		if (dateModified == null){
			return;
		}
		this.timeModified = dateModified.getTime();
	}



}
