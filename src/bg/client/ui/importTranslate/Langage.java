package bg.client.ui.importTranslate;

public class Langage {

	
	private String language;
	private String name;
	
	public Langage() {
	}

	public Langage(String language, String name) {
		super();
		this.language = language;
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
