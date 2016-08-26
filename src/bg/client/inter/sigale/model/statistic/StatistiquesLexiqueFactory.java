package bg.client.inter.sigale.model.statistic;

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

	private PersisterStat getXmlMarshaller() throws Exception {

		return persister;
	}

	private PersisterStat getXmlUnMarshaller() throws Exception {

		return persister;
	}

	public void fetchStatistique() {
		// fetchStatistiqueLocalInFile();
	}

	public void saveStatisticCurrentLexique() {

	}

}
