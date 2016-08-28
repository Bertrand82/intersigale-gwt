package bg.client.inter.sigale.util;

import java.util.Date;

import com.google.gwt.xml.client.Node;

public class UtilXml {

	public static Date parseAsDate(Node node) {
		return parseAsDate(node, null);
	}

	public static Date parseAsDate(Node node, Date dateDefault) {
		try {
			String dateStr = node.getNodeValue();
			if (dateStr == null) {
				return dateDefault;
			}
			long dateLong = Long.parseLong(dateStr);
			Date d = new Date(dateLong);
			return d;
		} catch (NumberFormatException e) {
			return dateDefault;
		}
	}

	public static boolean parseAsBoolean(Node node, boolean bDefault) {
		String bStr = node.getNodeValue();
		if (bStr == null) {
			return bDefault;
		} else if (bStr.trim().equalsIgnoreCase("true")) {
			return true;
		} else if (bStr.trim().equalsIgnoreCase("false")) {
			return false;
		} else {
			return bDefault;
		}
	}

}
