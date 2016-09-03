package bg.client.inter.sigale.model.statistic;

import java.io.FileInputStream;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.Phrase;
import bg.client.inter.sigale.model.UniteLexicale;
import bg.client.inter.sigale.model.Visible;

public class StatistiquesLexiqueFactory {

	private static StatistiquesLexiqueFactory instance;
	private PersisterStat persister = new PersisterStat();

	public synchronized static StatistiquesLexiqueFactory getInstance() {
		if (instance == null) {
			instance = new StatistiquesLexiqueFactory();
		}
		return instance;
	}

	public  synchronized StatistiquesLexique getStatistiquesLexique() {
		StatistiquesLexique statistiquesLexique = new StatistiquesLexique();
		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		for (UniteLexicale ul : lexique.getListUniteLexicale()) {
			statistiquesLexique.getListStatistiqueUL().add(ul.getStatistique());
		}
		return statistiquesLexique;
	}

	public static void setStatistiqueLexique(StatistiquesLexique statistiqueLexique) {
		System.out.println("setStatistiqueLexique ");
		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		for (UniteLexicale ul : lexique.getListUniteLexicale()) {
			String id = ul.getId();
			StatistiquesUL statistique = statistiqueLexique.getStatistiqueULById(id);
			ul.setStatistique(statistique);
		}
	}

	
	public void fetchStatistique() {
		// fetchStatistiqueLocalInFile();
	}

	public void saveStatisticCurrentLexique() {

	}

	

	public void fetchStatistique(String name) {
		// TODO Auto-generated method stub
		
	}

	public void createStatistique() {
		// TODO Auto-generated method stub
		
	}

	public String toXML(StatistiquesLexique stat) {
		Document document = XMLParser.createDocument();
		document.appendChild(document.createProcessingInstruction("xml", "version=\"1.0\" encoding=\"UTF-8\""));
		Element elementLexique = document.createElement(StatistiquesLexique.TAG_ROOT);
		document.appendChild(elementLexique);
		for (StatistiquesUL statUL : stat.getListStatistiqueUL()) {
			Element elementUL = document.createElement(StatistiquesUL.TAG_ROOT);
			elementLexique.appendChild(elementUL);
			elementUL.setAttribute(StatistiquesUL.TAG_uniteLexicaleId, "" + statUL.getUniteLexicaleId());
			for (StatistiquesItem statItem : statUL.getList()) {
				Element elementItem = document.createElement(StatistiquesItem.TAG_ROOT);
				elementUL.appendChild(elementItem);
				elementItem.setAttribute(StatistiquesItem.TAG_succes, "" + statItem.isSucces());
				elementItem.setAttribute(StatistiquesItem.TAG_date, "" + statItem.getDate().getTime());
			}
			
			
		}
		return document.toString();
		
	}

	public StatistiquesLexique parse(String xml) {
		if (xml == null){
			return null;
		}
		Document document = XMLParser.parse(xml);
		Node nodeStatLexique = document.getElementsByTagName(StatistiquesLexique.TAG_ROOT).item(0);
		StatistiquesLexique lexique = new StatistiquesLexique();
		NodeList nodeList = nodeStatLexique.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeUniteLexicale = nodeList.item(i);
			String nodeULName = nodeUniteLexicale.getNodeName();
			if (StatistiquesUL.TAG_ROOT.equals(nodeULName)) {
				StatistiquesUL ul = parseStaistiquesUL(nodeUniteLexicale);
			}
		}
		return lexique;
	}

	private StatistiquesUL parseStaistiquesUL(Node nodeUniteLexicale) {
		StatistiquesUL statUL = new StatistiquesUL();
		NodeList nodeList = nodeUniteLexicale.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeItem = nodeList.item(i);
			String nodeULName = nodeItem.getNodeName();
			if (StatistiquesItem.TAG_ROOT.equals(nodeULName)) {
				StatistiquesItem item = parseStaistiquesItem(nodeItem);
				statUL.getList().add(item);
			}
		}
		return statUL;
	}

	private StatistiquesItem parseStaistiquesItem(Node nodeItem) {
		StatistiquesItem item = new StatistiquesItem();
		String succesStr = ((Element) nodeItem).getAttribute(StatistiquesItem.TAG_succes);
		String dateStr = ((Element) nodeItem).getAttribute(StatistiquesItem.TAG_date);
		return item;
	}

}
