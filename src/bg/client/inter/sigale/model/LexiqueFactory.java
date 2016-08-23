package bg.client.inter.sigale.model;



import java.util.List;

import bg.client.Log;
import bg.client.inter.sigale.model.statistic.StatistiquesLexiqueFactory;
import bg.client.inter.sigale.util.ILogListener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

public class LexiqueFactory  {
	
	
	private PersisterLexique persister = new PersisterLexique();
	public static final String KEY_LexiqueName = "intersigale.lexique.name";

	private Lexique lexique;

	private final ILogListener logListener = new Log();

	private final static LexiqueFactory instance = new LexiqueFactory();

	public static LexiqueFactory getInstance() {
		return instance;
	}

	public Lexique getLexique() {
		if (lexique == null) {
			initLexique();
		}
		return lexique;
	}

	private void initLexique() {
		Log.log("initLexique");
		this.lexique = null;
		lexique = getLexiqueDefault();
		

	}


	private  Lexique getLexiqueDefault() {
		Lexique lexique = new Lexique();
		lexique.setName("Welcome Lexique");
		lexique.add(new UniteLexicale(new Phrase("Qui a créé intersigale ?"), new Phrase("Bertrand")));
		lexique.add(new UniteLexicale(new Phrase("Pourquoi il a fait ça ?"), new Phrase("Pour apprendre")));
		lexique.add(new UniteLexicale(new Phrase("Pour apprendre quoi ?"), new Phrase("Tout")));
		Phrase p1 = new Phrase("La capitale du Quercy est ?");
		Phrase p2 = new Phrase("Montpezat de Quercy ");
		p2.setSelected(0, 9);
		lexique.add(new UniteLexicale(p1,p2));
		logListener.logText("Load welcome Listener");
		logListener.logTitle(lexique.getName());
		return lexique;
	}

	


	

	private void fetchStatistique() {
		StatistiquesLexiqueFactory.getInstance().fetchStatistique();

	}

	/**
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void createLexique(String name) throws Exception {
		this.lexique = new Lexique();
		lexique.setName(name);
		this.saveLexique();
		this.logListener.logText("Create Lexique "+name);
		this.logListener.logTitle(name);
		UtilInterSigale.saveProperties(KEY_LexiqueName, name);
	}



	/**
	 * 
	 * @throws Exception
	 */
	public void saveLexique()  {
		Log.log("saveLexique");
		try {
			String name = ""+this.getLexique().getName();
			String xml = toXml(lexique);
			GWT.log("saveLexique "+xml);
			this.persister.save(xml, name);
			this.logListener.logText("Lexique "+name+" saved");
		} catch (Throwable e) {
			this.logListener.logText("Exception 201 "+e.getMessage());
			GWT.log("saveLexique",e);
			throw e;
		}
	}


	/**
	 * 
	 * @param item
	 */
	public void saveItem(UniteLexicale item) {
		try {
			this.saveLexique();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	

	

	

	public  String toXml(Lexique lexique2) {
		Document document = XMLParser.createDocument();
		document.appendChild(document.createProcessingInstruction("xml", "version=\"1.0\" encoding=\"UTF-8\""));
		Element elementLexique = document.createElement(Lexique.TAG_ROOT);
		elementLexique.setAttribute(Lexique.TAG_name, ""+lexique2.getName());
		document.appendChild(elementLexique);
		for (UniteLexicale uniteLexicale: lexique2.getListUniteLexicale()){
			Element elementUL = document.createElement(UniteLexicale.TAG_ROOT);
			Phrase phrase_0 = uniteLexicale.getPhrase_0();
			Phrase phrase_1 = uniteLexicale.getPhrase_1();
			Element elementPhrase_0 =toXML(phrase_0, document);
			Element elementPhrase_1 =toXML(phrase_1, document);
			elementUL.appendChild(elementPhrase_0);
			elementUL.appendChild(elementPhrase_1);
			elementLexique.appendChild(elementUL);
		}
		return document.toString();
	}
	
	private   Element toXML(Phrase phrase, Document document){
		Element elementPhrase = document.createElement(Phrase.TAG_ROOT);
		elementPhrase.appendChild(document.createTextNode(""+phrase.getText()));
		for(Visible visible : phrase.getListVisible()){
			Element elementVisible = document.createElement(Visible.TAG_ROOT);
			elementVisible.setAttribute(Visible.TAG_start, ""+visible.getStart());
			elementVisible.setAttribute(Visible.TAG_end, ""+visible.getEnd());
			elementPhrase.appendChild(elementVisible);
		}
		return elementPhrase;
	}

	public List<String> getLexiquesInLocalStorage() {
		
		return this.persister.getListLexiqueInStorage();
	}

	public void saveLexique(String newName) {
		this.getLexique().setName(newName);
		saveLexique();
		logListener.logTitle(newName);
		logListener.logText("Save Lexique "+newName+" done");
		
	}

	public void getLexiqueByName(String name) {
		this.logListener.logText("get Lexique in local storage by Name : "+name);
	}

	public void deleteLexiqueByName(String name) {
		this.logListener.logText("Delete Lexique in local storage by Name : "+name);
		this.persister.delete(name);
	}
}
