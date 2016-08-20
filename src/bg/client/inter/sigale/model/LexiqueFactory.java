package bg.client.inter.sigale.model;



import bg.client.inter.sigale.model.statistic.StatistiquesLexiqueFactory;
import bg.client.inter.sigale.util.ILogListener;

public class LexiqueFactory implements ILogListener {
	private PersisterLexique persister = new PersisterLexique();
	public static final String KEY_LexiqueName = "intersigale.lexique.name";

	private Lexique lexique;

	private static LexiqueFactory instance = new LexiqueFactory();

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
		this.lexique = null;
		lexique = getLexiqueDefault();
		log("No file Lexique");

	}

	private String getLexiqueName() {
		//return System.getProperty(KEY_LexiqueName);
		return "defaultLexique";
	}

	private static Lexique getLexiqueDefault() {
		Lexique lexique = new Lexique();
		lexique.add(new UniteLexicale(new Phrase("Qui a créé intersigale ?"), new Phrase("Bertrand")));
		lexique.add(new UniteLexicale(new Phrase("Pourquoi il a fait ça ?"), new Phrase("Pour apprendre")));
		lexique.add(new UniteLexicale(new Phrase("Pour apprendre quoi ?"), new Phrase("Tout")));
		return lexique;
	}

	


	

	private void fetchStatistique() {
		StatistiquesLexiqueFactory.getInstance().fetchStatistique();

	}

	/**
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void createLexique(String name) throws Exception {
		this.lexique = new Lexique();
		lexique.setName(name);
		this.saveLexique();
		UtilInterSigale.saveProperties(KEY_LexiqueName, name);
	}



	/**
	 * 
	 * @throws Exception
	 */
	public void saveLexique() throws Exception {
		String name = this.lexique.getName();
		
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

	private ILogListener logListener;

	public void setLogListener(ILogListener logListener_) {
		this.logListener = logListener_;
	}

	public void log(String s) {
		System.out.println(s);
		if (logListener != null) {
			logListener.log(s);
		}

	}

	public void logTitle(String s) {
		if (logListener != null) {
			logListener.logTitle(s);
		}
	}

}
