package bg.client.inter.sigale.model;

import java.util.Date;
import java.util.List;

import bg.client.LogGWT;
import bg.client.SigaleService;
import bg.client.SigaleServiceAsync;
import bg.client.inter.sigal.beans.LexiqueMetaData;
import bg.client.inter.sigale.model.statistic.StatistiquesLexiqueFactory;
import bg.client.inter.sigale.util.ILogListener;
import bg.client.ui.edit.EditLexiqueGUI;
import bg.client.ui.lesson.Lesson;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class LexiqueFactory {

	private PersisterLexique persister = new PersisterLexique();

	private final SigaleServiceAsync sigaleService = GWT.create(SigaleService.class);

	public static final String KEY_LexiqueName = "intersigale.lexique.name";

	private ISigalePropertes sigaleProperties;

	private Lexique lexique;

	private static ILogListener logListener = new LogGWT();

	private static LexiqueFactory instance;

	public LexiqueFactory(ISigalePropertes sigaleProperties2, ILogListener logListener2) {
		this.sigaleProperties = sigaleProperties2;
		this.logListener = logListener2;
		instance = this;
		try {
			String name = sigaleProperties.getNameLexique();
			GWT.log("LexiqueFactory constructeur name: "+name);
			if (name == null) {
				logListener.logText("No file in LocalStorage !");
			} else {
				this.lexique = fetchLexiqueInLocalStorage(name);
				this.logListener.logTitle(name);
				this.logListener.logText("Display former context. Lexique :" + lexique.getName());
			}
			if (this.lexique == null){
				this.lexique=this.getLexiqueDefault();
			}
		} catch (Exception e) {
			this.logListener.log("Exception Constructor LexiqueFactory", e);
		}

	}

	public static LexiqueFactory getInstance() {
		return instance;
	}

	public Lexique getLexique() {
		if (lexique == null) {
			lexique = getLexiqueDefault();
		}
		return lexique;
	}

	public Lexique getLexiqueDefault() {
		Lexique lexique = new Lexique();
		lexique.setName("Welcome Lexique");
		lexique.add(new UniteLexicale(new Phrase("Inte ?"), new Phrase("Bertrand")));
		lexique.add(new UniteLexicale(new Phrase("Pourquoi il a fait ça ?"), new Phrase("Pour apprendre")));
		lexique.add(new UniteLexicale(new Phrase("Pour apprendre quoi ?"), new Phrase("Tout")));
		Phrase p1 = new Phrase("La capitale du Quercy est ?");
		Phrase p2 = new Phrase("Montpezat de Quercy ");
		p2.setSelected(0, 9);
		lexique.add(new UniteLexicale(p1, p2));
		logListener.logText("Load welcome Listener");
		logListener.logTitle(lexique.getName());
		StatistiquesLexiqueFactory.getInstance().fetchStatitistiqueInLocaleStorage(lexique);
		return lexique;
	}

	/**
	 * 
	 * @param name
	 * @throws Exception
	 */
	public Lexique createLexique(String name) {
		this.lexique = new Lexique();
		lexique.setName(name);
		this.saveLexiqueInLocal();
		this.logListener.logText("Create Lexique " + name);
		this.logListener.logTitle(name);
		UtilInterSigale.saveProperties(KEY_LexiqueName, name);
		StatistiquesLexiqueFactory.getInstance().createNewStatistique();
		return lexique;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void saveLexiqueInLocal() {
		saveLexiqueInLocal(this.getLexique());
	}
	public  void saveLexiqueInLocal(Lexique lexique_) {
		logListener.log("saveLexique");
		try {
			String name = "" + lexique_.getName();
			String xml = toXml(lexique_);
			GWT.log("saveLexique " + xml);
			this.persister.save(xml, name);
			this.logListener.logText("Lexique " + name + " saved");
		} catch (Throwable e) {
			this.logListener.logText("Save Lexique In Local Exception 201 " + e.getMessage());
			GWT.log("saveLexique", e);
			throw e;
		}
	}

	/**
	 * 
	 * @param item
	 */
	public void saveItem(UniteLexicale item) {
		this.saveLexiqueInLocal();
	}

	/*
	 * 
	 */
	public static String toXml(Lexique lexique2) {
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

	private static Element toXML(Phrase phrase, Document document) {
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

	public void saveLexiqueInLocal(String newName) {
		this.getLexique().setName(newName);
		saveLexiqueInLocal();
		logListener.logTitle(newName);
		logListener.logText("Save Lexique " + newName + " done");

	}

	public Lexique getLexiqueByNameInLocalStore(String name) {
		this.logListener.logText("get Lexique in local storage by Name : " + name);
		this.sigaleProperties.setNameLexique(name);
		this.lexique = fetchLexiqueInLocalStorage(name);
		if (this.lexique == null) {
			this.lexique = new Lexique();
		}
		this.logListener.logTitle(name);
		this.lexique.setName(name);
		return lexique;
	}

	private Lexique fetchLexiqueInLocalStorage(String name) {
		if (name == null) {
			return null;
		}
		String xml = this.persister.getLexiqueXMLFromName(name);
		GWT.log("fetchLexiqueInLocalStorage xml :"+xml);
		if (xml == null) {
			logListener.log("No xml in localstore for name :"+name);
			return null;
		} else {
			Lexique lexiqueParsed = parse(xml);
			if (lexiqueParsed== null){
				return null;
			}else {
			GWT.log("Parse xml done nb ul : " + lexiqueParsed.getListUniteLexicale().size());

			logListener.logTitle(name);
			logListener.logText("Fetch and display Display " + lexiqueParsed.getName());
			
			return lexiqueParsed;
			}
		}
	}

	public void deleteLexiqueByName(String name) {
		this.logListener.logText("Delete Lexique in local storage by Name : " + name);
		this.persister.delete(name);
		logListener.logText("Deleted : " + name);
	}

	public static Lexique parse(String xml) {
		if (xml == null) {
			logListener.logText("try to parse null xml !");
			return null;
		}
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

	private static UniteLexicale parseUniteLexicale(Node node) {
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

	private static Phrase parsePhrase(Node nodePhrase) {
		Phrase phrase = new Phrase();
		String text = nodePhrase.getFirstChild().getNodeValue();
		phrase.setText(text);
		NodeList nodeList = nodePhrase.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeChild = nodeList.item(i);
			String name = nodeChild.getNodeName();
			if (Visible.TAG_ROOT.equals(name)) {
				Visible visible = parseVisible(nodeChild);
				phrase.getListVisible().add(visible);
			}
		}
		return phrase;
	}

	private static Visible parseVisible(Node nodeVisible) {
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

	public LexiqueMetaData getLexiqueMetaData(Lexique lexique2) {
		LexiqueMetaData lmd = new LexiqueMetaData();
		lmd.setEmailOwner("bg@bg");
		lmd.setDateModified(new Date());
		lmd.setDateRegistered(new Date());
		lmd.setName(lexique2.getName());
		lmd.setXml(toXml(lexique2));
		return lmd;
	}

	public void getLexiqueByIdInRemoteStore(final String lexiqueId, String name) {
		AsyncCallback<LexiqueMetaData> callback = new AsyncCallback<LexiqueMetaData>() {

			@Override
			public void onFailure(Throwable e) {
				logListener.log("getLexiqueByIdInRemoteStore : " + lexiqueId + " Exception : " + e);
			}

			@Override
			public void onSuccess(LexiqueMetaData result) {
				logListener.log("getLexiqueByIdInRemoteStore : " + lexiqueId + " LexiqueMetaData : " + result);
				displayLexiqueMetaData(result);
			}
		};
		sigaleService.getLexiqueMetadataById(lexiqueId, "bg@bg", callback);
	}

	public void deleteInRemoteStore(final String id, String name) {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable e) {
				logListener.log("Delete : " + id + " Exception : " + e);
			}

			@Override
			public void onSuccess(Boolean result) {
				logListener.log("Delete : " + id + " result : " + result);
			}
		};
		sigaleService.deleteLexiqueMetadata(id, "bg@bg", callback);
	}

	private void displayLexiqueMetaData(LexiqueMetaData lmd) {
		Lexique lexique2 = parse(lmd.getXml());
		lexique2.setId(lmd.getId());
		lexique2.setName(lmd.getName());
		this.lexique = lexique2;

	}

	public void notifyNewLexique() {
		Lesson.getInstance().notifyNewLexique();
		EditLexiqueGUI.getInstance().notifyNewLexique();
		
	}

}
