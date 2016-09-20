package bg.client.inter.cicada.util;

public class UtilDivers {

	public static String removeAccents(String s) {
		if (s == null) {
			return "";
		}
		s = s.replace('�', 'e').replace('�', 'a').replace('�', 'u').replace('�', 'e').replace('�', 'e');
		return s;
	}

}
