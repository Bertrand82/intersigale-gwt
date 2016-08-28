package bg.client.inter.sigale.model.statistic;



import java.util.Date;

import bg.client.LogGWT;
import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.UniteLexicale;
import bg.client.inter.sigale.util.ILogListener;
import bg.client.inter.sigale.util.UtilXml;

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class StatistiquesLexiqueFactory {

	private static StatistiquesLexiqueFactory instance;
	private PersisterStat persister = new PersisterStat();
	
	private StatistiquesLexique statistique;
	ILogListener logListener = new LogGWT();

	public synchronized static StatistiquesLexiqueFactory getInstance() {
		if (instance == null) {
			instance = new StatistiquesLexiqueFactory();
		}
		return instance;
	}

	public static synchronized StatistiquesLexique cretateNewStatistique() {
		StatistiquesLexique statistiquesLexique = new StatistiquesLexique();
		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		for (UniteLexicale ul : lexique.getListUniteLexicale()) {
			statistiquesLexique.getListStatistiqueUL().add(ul.getStatistique());
		}
		return statistiquesLexique;
	}

	public static void setStatistiqueToLexique(StatistiquesLexique statistiqueLexique,Lexique lexique) {
		System.out.println("setStatistiqueLexique ");
		
		for (UniteLexicale ul : lexique.getListUniteLexicale()) {
			String id = ul.getId();
			StatistiquesUL statistique = statistiqueLexique.getStatistiqueULById(id);
			ul.setStatistique(statistique);
		}
	}

	
	
	public void fetchStatistique() {
		// fetchStatistiqueLocalInFile();
	}

	public void saveStatistic(String name) {
		logListener.log("saveStatistic");
		try {
			String xml = toXml(statistique);
			GWT.log("saveStatistic " + xml);
			this.persister.saveStatistique(xml, name);
			GWT.log("Statistique " + name + " saved");
		} catch (Throwable e) {
			GWT.log("saveStatistic", e);
		}
	}

	

	private String toXml(StatistiquesLexique stat) {
		
		Document document = XMLParser.createDocument();
		document.appendChild(document.createProcessingInstruction("xml", "version=\"1.0\" encoding=\"UTF-8\""));
		Element elementLexique = document.createElement(StatistiquesLexique.TAG_ROOT);
		document.appendChild(elementLexique);
		for (StatistiquesUL statUL : stat.getListStatistiqueUL()) {
			Element elementUL = document.createElement(StatistiquesUL.TAG_ROOT);
			elementUL.appendChild(document.createTextNode("" + statUL.getUniteLexicaleId()));
			for(StatistiquesItem item : statUL.getList()){
				Element elementItem = document.createElement(StatistiquesItem.TAG_ROOT);
				elementItem.setAttribute("succes", ""+item.succes);
				elementItem.setAttribute("date", ""+item.date);
				elementUL.appendChild(elementItem);
			}
			elementLexique.appendChild(elementUL);
		}
		return document.toString();
	}
	
	private StatistiquesLexique parse(String xml) {
		if (xml == null){
			logListener.log("try to parse null xml !");
			return null;
		}
		Document document = XMLParser.parse(xml);
		Node nodeStatistique = document.getElementsByTagName(StatistiquesLexique.TAG_ROOT).item(0);
		StatistiquesLexique stat = new StatistiquesLexique();
		NodeList nodeList = nodeStatistique.getChildNodes();
		for (int k = 0; k< nodeList.getLength(); k++) {
			Node nodeUL = nodeList.item(k);
			String nodeULName = nodeUL.getNodeName();
			if (StatistiquesUL.TAG_ROOT.equals(nodeULName)) {
				StatistiquesUL ul = new StatistiquesUL();
				stat.getListStatistiqueUL().add(ul);
				NodeList nodeULList = nodeUL.getChildNodes();
				for (int j = 0; j < nodeList.getLength(); j++) {
					Node nodeULItem = nodeULList.item(j);
					String nodeItemName = nodeULItem.getNodeName();
					if (StatistiquesItem.TAG_ROOT.equals(nodeItemName)) {
						StatistiquesItem item = new StatistiquesItem();
						ul.getList().add(item);
						NodeList nodeItemList = nodeULItem.getChildNodes();
						String nodeName = nodeULItem.getNodeName();
						for (int i = 0; i < nodeItemList.getLength(); i++) {
							Node node = nodeULList.item(i);
							if ("date".equals(nodeName)) {
								Date date = UtilXml.parseAsDate(node);
								item.setDate(date);
							}
							if ("succes".equals(nodeName)) {
								boolean b = UtilXml.parseAsBoolean(node,true);
								item.setSucces(b);
							}
						}
						
					}
				}
			}
		}
		return stat;
	}
	
	
	public void fetchStatistique(String name) {
		
	}

	public void createStatistique() {
		
	}

}
