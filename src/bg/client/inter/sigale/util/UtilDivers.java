package bg.client.inter.sigale.util;



public class UtilDivers {

	public static String removeAccents(String s) {
		if (s == null) {
			return "";
		}
		s = s.replace('é', 'e').replace('à', 'a').replace('ù', 'u').replace('è', 'e').replace('ê','e');
		return s;
	}

}
