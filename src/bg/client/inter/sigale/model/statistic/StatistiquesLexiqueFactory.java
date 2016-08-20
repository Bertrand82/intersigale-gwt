package bg.client.inter.sigale.model.statistic;

import java.io.File;
import java.io.FileInputStream;

import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.UniteLexicale;

public class StatistiquesLexiqueFactory {

	private static StatistiquesLexiqueFactory instance;
	private PersisterStat persister = new PersisterStat();

	public synchronized static StatistiquesLexiqueFactory getInstance() {
		if (instance == null) {
			instance = new StatistiquesLexiqueFactory();
		}
		return instance;
	}

	public static synchronized StatistiquesLexique getStatistiquesLexique() {
		System.out.println("StatistiquesLexique getStatistiquesLexique ");
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

	public static File getFileStatistiqueFromName(String name) {

		return new File(name + "_statistic.xml");
	}

	public static File getFileStatistique() {

		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		File f = getFileStatistiqueFromName(lexique.getName());
		System.out.println("getFileStatistique " + f.getAbsolutePath() + " exists :" + f.exists());
		return f;
	}

	private PersisterStat getXmlMarshaller() throws Exception {

		return persister;
	}

	private PersisterStat getXmlUnMarshaller() throws Exception {

		return persister;
	}

	public void readStatistics(File selectedFile) throws Exception {
		FileInputStream is = new FileInputStream(selectedFile);
		Object o = getXmlUnMarshaller().readStatistique(is);
		StatistiquesLexique statistiqueLexique = (StatistiquesLexique) o;
		StatistiquesLexiqueFactory.setStatistiqueLexique(statistiqueLexique);
		System.out.println("StatistiquesLexique : " + statistiqueLexique);
		System.out.println("StatistiquesLexique : " + StatistiquesLexiqueFactory.getStatistiquesLexique());
	}

	public void saveStatistic(File selectedFile) throws Exception {
		System.out.println("saveStatistic start");
		System.out.println("saveStatistic size : " + StatistiquesLexiqueFactory.getStatistiquesLexique().getListStatistiqueUL().size());

		// jaxbMarshaller.marshal(LexiqueFactory.getLexique(), file);
		getXmlMarshaller().write(StatistiquesLexiqueFactory.getStatistiquesLexique(), System.out);
		getXmlMarshaller().write(StatistiquesLexiqueFactory.getStatistiquesLexique(), selectedFile);

		System.out.println("saveStatistic done " + selectedFile.getAbsolutePath());

	}

	public void fetchStatistique() {
		fetchStatistiqueLocalInFile();
	}

	public void fetchStatistiqueLocalInFile() {
		File file = StatistiquesLexiqueFactory.getFileStatistique();
		System.out.println("readStatistic " + file.getAbsolutePath() + " exists " + file.exists());
		try {
			StatistiquesLexiqueFactory.getInstance().readStatistics(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveStatisticCurrentLexique() {

		File file = StatistiquesLexiqueFactory.getFileStatistique();
		System.out.println("saveStatistice You chose to open this file: " + file.getAbsolutePath() + "  exists : " + file.exists());
		try {
			StatistiquesLexiqueFactory.getInstance().saveStatistic(file);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
