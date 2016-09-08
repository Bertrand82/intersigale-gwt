package bg.server.inter.sigale.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import bg.client.inter.sigal.beans.LexiqueMetaData;

public class LexiqueEntityFactory {

	private Logger logger = Logger.getLogger("LexiqueEntityFactory");

	private static final PersistenceManagerFactory persistenceManagerFactory = bg.server.util.PMF.getPersistenceManagerFactory();

	private LexiqueEntityFactory() {
	}

	public boolean makePersistent(LexiqueEntity bean) throws Exception {
		try {
			PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
			pm.makePersistent(bean);
			pm.close();
			logger.info("makePersistent LexiqueEntity done:" + bean);
			return true;
		} catch (Exception e) {
			logger.log(Level.WARNING, "makePersistent LexiqueEntity Exception:" + bean, e);
			throw e;
		}
	}

	/**
	 * Le xml n'est pas "chargé" , c pourquoi je l'appelle light Cette methode
	 * est utile pour afficher la liste
	 * 
	 * @return
	 */
	public List<LexiqueMetaData> getListLexiqueLight() {
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		logger.warning("getListTeleInfo start ");

		Query q = pm.newQuery(LexiqueEntity.class);

		List<LexiqueMetaData> listLexiques = new ArrayList<LexiqueMetaData>();
		try {
			logger.warning("getListLexiqueEntity results " + q);

			@SuppressWarnings("unchecked")
			List<LexiqueEntity> results = (List<LexiqueEntity>) q.execute();
			logger.warning("getListLexiqueEntity results.size " + results.size());

			for (LexiqueEntity p : results) {
				LexiqueMetaData lexique = new LexiqueMetaData();
				lexique.setId(p.getId());
				lexique.setName(p.getName());
				lexique.setDateRegistered(p.getDateRegistered());
				lexique.setDateModified(p.getDateModified());
				lexique.setEmailOwner(p.getEmailOwner());
				listLexiques.add(lexique);
			}

		} catch (Exception e) {
			logger.log(Level.WARNING, "getListLexiqueEntity results " + q, e);
			return null;
		} finally {
			q.closeAll();
		}
		return listLexiques;

	}

	private static LexiqueEntityFactory instance;

	public static LexiqueEntityFactory getInstance() {
		if (instance == null) {
			instance = new LexiqueEntityFactory();
		}
		return instance;
	}

	public boolean makePersistent(LexiqueMetaData lexique) throws Exception {
		LexiqueEntity lexiqueEntity = toLexiqueEntity(lexique);
		return makePersistent(lexiqueEntity);
	}

	private LexiqueEntity toLexiqueEntity(LexiqueMetaData lexique) {
		LexiqueEntity lexiqueEntity = new LexiqueEntity();
		lexiqueEntity.setDateModified(lexique.getTimeModified());
		lexiqueEntity.setDateRegistered(lexique.getTimeRegistered());
		lexiqueEntity.setEmailOwner(lexique.getEmailOwner());
		lexiqueEntity.setId(lexique.getId());
		lexiqueEntity.setName(lexique.getName());
		lexiqueEntity.setXml(lexique.getXml());
		return lexiqueEntity;
	}
}
