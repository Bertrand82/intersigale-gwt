package bg.client.inter.sigale.model.statistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bg.client.LogGWT;
import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.UniteLexicale;
import bg.client.inter.sigale.util.ILogListener;
import bg.client.inter.sigale.util.UtilXml;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
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

	public synchronized StatistiquesLexique createNewStatistique() {
		this.statistique = new StatistiquesLexique();
		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		for (UniteLexicale ul : lexique.getListUniteLexicale()) {
			statistique.getListStatistiqueUL().add(ul.getStatistique());
		}
		return statistique;
	}

	public static void setStatistiqueToLexique(StatistiquesLexique statistiqueLexique, Lexique lexique) {
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

	public StatistiquesLexique parse(String xml) {
		if (xml == null) {
			logListener.log("try to parse null xml !");
			return null;
		}
		Document document = XMLParser.parse(xml);
		Node nodeStatistique = document.getElementsByTagName(StatistiquesLexique.TAG_ROOT).item(0);
		StatistiquesLexique stat = new StatistiquesLexique();
		NodeList nodeList = nodeStatistique.getChildNodes();
		for (int k = 0; k < nodeList.getLength(); k++) {
			Node nodeUL = nodeList.item(k);
			String nodeULName = nodeUL.getNodeName();

			if (StatistiquesUL.TAG_ROOT.equals(nodeULName)) {
				StatistiquesUL ul = new StatistiquesUL();
				stat.getListStatistiqueUL().add(ul);
				NodeList nodeULList = nodeUL.getChildNodes();
				for (int j = 0; j < nodeULList.getLength(); j++) {
					Node nodeULChild = nodeULList.item(j);
					String nodeItemName = nodeULChild.getNodeName();
					if (StatistiquesUL.TAG_ID.equals(nodeItemName)) {
						String id = nodeULChild.getFirstChild().getNodeValue();
						ul.setUniteLexicaleId(id);
					}
					if (StatistiquesItem.TAG_ROOT.equals(nodeItemName)) {
						StatistiquesItem item = new StatistiquesItem();
						ul.getList().add(item);
						NodeList nodeItemList = nodeULChild.getChildNodes();

						for (int i = 0; i < nodeItemList.getLength(); i++) {
							Node node = nodeULList.item(i);
							String nodeName = node.getNodeName();
							if ("date".equals(nodeName)) {
								Date date = UtilXml.parseAsDate(node);
								item.setDate(date);
							}
							if ("succes".equals(nodeName)) {
								boolean b = UtilXml.parseAsBoolean(node, true);
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

	public void saveStatisticUL(StatistiquesUL statistiquesUL) {
		String lexiqueName = LexiqueFactory.getInstance().getLexique().getName();
		this.persister.saveStatistique(lexiqueName, statistiquesUL);
	}

	public String toXml(StatistiquesLexique stat) {

		Document document = XMLParser.createDocument();
		document.appendChild(document.createProcessingInstruction("xml", "version=\"1.0\" encoding=\"UTF-8\""));
		Element elementLexique = document.createElement(StatistiquesLexique.TAG_ROOT);
		document.appendChild(elementLexique);
		for (StatistiquesUL statUL : stat.getListStatistiqueUL()) {
			Element elementUL = document.createElement(StatistiquesUL.TAG_ROOT);
			Element elementId = document.createElement(StatistiquesUL.TAG_ID);
			elementUL.appendChild(elementId);
			elementId.appendChild(document.createTextNode("" + statUL.getUniteLexicaleId()));
			for (StatistiquesItem item : statUL.getList()) {
				Element elementItem = document.createElement(StatistiquesItem.TAG_ROOT);
				elementItem.setAttribute("succes", "" + item.succes);
				elementItem.setAttribute("date", "" + item.date);
				elementUL.appendChild(elementItem);
			}
			elementLexique.appendChild(elementUL);
		}
		return document.toString();
	}

	public static JSONObject toJSon(StatistiquesUL ul) {

		JSONObject json = new JSONObject();
		json.put(StatistiquesUL.TAG_ID, new JSONString(ul.getUniteLexicaleId()));
		JSONArray jsonArray = new JSONArray();
		json.put(StatistiquesUL.TAG_ITEMS, jsonArray);
		int i = 0;
		for (StatistiquesItem item : ul.getList()) {
			JSONObject jsonItem = new JSONObject();
			jsonItem.put(StatistiquesItem.TAG_Date, new JSONNumber(item.getDate().getTime()));
			jsonItem.put(StatistiquesItem.TAG_succes, JSONBoolean.getInstance(item.isSucces()));
			jsonArray.set(i, jsonItem);
			i++;
		}
		return json;
	}

	public static StatistiquesUL parseJSon(String str) {
		StatistiquesUL ul2 = new StatistiquesUL();
		JSONValue jsonValue_ = JSONParser.parseStrict(str);
		JSONObject jsonObject = jsonValue_.isObject();
		JSONValue jsonValueId = jsonObject.get(StatistiquesUL.TAG_ID);
		JSONString jsonString = jsonValueId.isString();
		JSONValue jsonValueItems = jsonObject.get(StatistiquesUL.TAG_ITEMS);
		JSONArray jsonArrayItems = jsonValueItems.isArray();
		for (int i = 0; i < jsonArrayItems.size(); i++) {
			JSONValue jsonValueItem = jsonArrayItems.get(i);
			JSONObject jsonObjectItem = jsonValueItem.isObject();
			JSONValue jsonValueItemSucces = jsonObjectItem.get(StatistiquesItem.TAG_succes);
			JSONValue jsonValueItemTime = jsonObjectItem.get(StatistiquesItem.TAG_Date);
			boolean succes = jsonValueItemSucces.isBoolean().booleanValue();
			double timeDouble = jsonValueItemTime.isNumber().doubleValue();
			long time = (long) timeDouble;
			StatistiquesItem item = new StatistiquesItem(succes, time);
			List<StatistiquesItem> list = ul2.getList();
			list.add(item);
		}
		return ul2;
	}

}
