package bg.client.inter.sigale.model;

import java.util.List;

import bg.client.Log;
import bg.client.inter.sigale.model.statistic.StatistiquesLexiqueFactory;
import bg.client.inter.sigale.util.ILogListener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.*;

public class LexiqueFactory {

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

	private Lexique getLexiqueDefault() {
		Lexique lexique = new Lexique();
		lexique.setName("Welcome Lexique");
		lexique.add(new UniteLexicale(new Phrase("Qui a créé intersigale ?"), new Phrase("Bertrand")));
		lexique.add(new UniteLexicale(new Phrase("Pourquoi il a fait ça ?"), new Phrase("Pour apprendre")));
		lexique.add(new UniteLexicale(new Phrase("Pour apprendre quoi ?"), new Phrase("Tout")));
		Phrase p1 = new Phrase("La capitale du Quercy est ?");
		Phrase p2 = new Phrase("Montpezat de Quercy ");
		p2.setSelected(0, 9);
		lexique.add(new UniteLexicale(p1, p2));
		logListener.logText("Load welcome Listener");
		logListener.logTitle(lexique.getName());
		return lexique;
	}

	private void fetchStatistique(String dname) {
		StatistiquesLexiqueFactory.getInstance().fetchStatistique();

	}

	/**
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void createLexique(String name) {
		this.lexique = new Lexique();
		lexique.setName(name);
		this.saveLexique();
		this.logListener.logText("Create Lexique " + name);
		this.logListener.logTitle(name);
		UtilInterSigale.saveProperties(KEY_LexiqueName, name);
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void saveLexique() {
		Log.log("saveLexique");
		try {
			String name = "" + this.getLexique().getName();
			String xml = toXml(lexique);
			GWT.log("saveLexique " + xml);
			this.persister.save(xml, name);
			this.logListener.logText("Lexique " + name + " saved");
		} catch (Throwable e) {
			this.logListener.logText("Exception 201 " + e.getMessage());
			GWT.log("saveLexique", e);
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

	public String toXml(Lexique lexique2) {
		Document document = XMLParser.createDocument();
		document.appendChild(document.createProcessingInstruction("xml", "version=\"1.0\" encoding=\"UTF-8\""));
		Element elementLexique = document.createElement(Lexique.TAG_ROOT);
		elementLexique.setAttribute(Lexique.TAG_name, "" + lexique2.getName());
		document.appendChild(elementLexique);
		for (UniteLexicale uniteLexicale : lexique2.getListUniteLexicale()) {
			Element elementUL = document.createElement(UniteLexicale.TAG_ROOT);
			Phrase phrase_0 = uniteLexicale.getPhrase_0();
			Phrase phrase_1 = uniteLexicale.getPhrase_1();
			Element elementPhrase_0 = toXML(phrase_0, document);
			Element elementPhrase_1 = toXML(phrase_1, document);
			elementUL.appendChild(elementPhrase_0);
			elementUL.appendChild(elementPhrase_1);
			elementLexique.appendChild(elementUL);
		}
		return document.toString();
	}

	private Element toXML(Phrase phrase, Document document) {
		Element elementPhrase = document.createElement(Phrase.TAG_ROOT);
		elementPhrase.appendChild(document.createTextNode("" + phrase.getText()));
		for (Visible visible : phrase.getListVisible()) {
			Element elementVisible = document.createElement(Visible.TAG_ROOT);
			elementVisible.setAttribute(Visible.TAG_start, "" + visible.getStart());
			elementVisible.setAttribute(Visible.TAG_end, "" + visible.getEnd());
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
		logListener.logText("Save Lexique " + newName + " done");

	}

	public void getLexiqueByName(String name) {
		this.logListener.logText("get Lexique in local storage by Name : " + name);

		String xml = this.persister.getLexiqueXMLFromName(name);
		GWT.log(xml);
		Lexique lexiqueParsed = parse(xml);
		GWT.log("Parse xml done nb ul : " + lexiqueParsed.getListUniteLexicale().size());
		this.lexique = lexiqueParsed;
		logListener.logTitle(name);
		logListener.logText("Fetch and display Display " + lexique.getName());
	}

	public void deleteLexiqueByName(String name) {
		this.logListener.logText("Delete Lexique in local storage by Name : " + name);
		this.persister.delete(name);
	}

	private Lexique parse(String xml) {
		Document document = XMLParser.parse(xml);
		Node nodeLexique = document.getElementsByTagName(Lexique.TAG_ROOT).item(0);
		String name = ((Element) nodeLexique).getAttribute(Lexique.TAG_name);
		Lexique lexique = new Lexique();
		lexique.setName(name);
		NodeList nodeList = nodeLexique.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeUniteLexicale = nodeList.item(i);
			String nodeULName = nodeUniteLexicale.getNodeName();
			if (UniteLexicale.TAG_ROOT.equals(nodeULName)) {
				UniteLexicale ul = parseUniteLexicale(nodeUniteLexicale);
				lexique.add(ul);
			}
		}
		return lexique;
	}

	private UniteLexicale parseUniteLexicale(Node node) {
		UniteLexicale ul = new UniteLexicale();
		NodeList nodeList = node.getChildNodes();
		int k = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodePhrase = nodeList.item(i);
			String name = nodePhrase.getNodeName();
			if (Phrase.TAG_ROOT.equals(name)) {
				Phrase phrase = parsePhrase(nodePhrase);
				ul.setPhrase(phrase, k);
				k++;
			}
		}
		return ul;
	}

	private Phrase parsePhrase(Node nodePhrase) {
		Phrase phrase = new Phrase();
		String text = nodePhrase.getFirstChild().getNodeValue();
		phrase.setText(text);
		NodeList nodeList = nodePhrase.getChildNodes();
		int k = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeChild = nodeList.item(i);
			String name = nodeChild.getNodeName();
			if (Visible.TAG_ROOT.equals(name)) {
				Visible visible = parseVisible(nodeChild);
				phrase.getListVisible().add(visible);
				k++;
			}
		}
		return phrase;
	}

	private Visible parseVisible(Node nodeVisible) {
		Visible visible = new Visible();
		NodeList nodeList = nodeVisible.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeChild = nodeList.item(i);
			String name = nodeChild.getNodeName();
			if (Visible.TAG_start.equals(name)) {
				String start = nodeChild.getNodeValue();
				visible.setStart(start);
			}
			if (Visible.TAG_end.equals(name)) {
				String end = nodeChild.getNodeValue();
				visible.setEnd(end);
			}
		}
		return visible;
	}

}
