package bg.server.inter.sigale.data;

import java.util.ArrayList;

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

	public long makePersistent(LexiqueEntity bean) throws Exception {
		try {
			PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
			bean = pm.makePersistent(bean);
			long id = bean.getId();
			pm.close();
			logger.info("makePersistent LexiqueEntity done:" + bean);
			return id;
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

	public long makePersistent(LexiqueMetaData lexique) throws Exception {
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
	
	
	public void removeLexique(String idStr, String emailUser) throws Exception {
		logger.log(Level.WARNING, "removeLexique id " + idStr+" mail "+ emailUser);
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			long id = Long.parseLong(idStr);
			LexiqueEntity c = pm.getObjectById(LexiqueEntity.class, id);

			/*if (c.getEmailOwner().equals(emailUser)) {
				c = null;
				logger.log(Level.WARNING, "removeLexique id " + idStr+"email no matching");
			}*/

			if (c == null) {
				throw new Exception("No Lexique  id :" + id + "  " + emailUser);
			}
			pm.deletePersistent(c);
			logger.log(Level.WARNING, "removeLexique id " + idStr+" done ");
		} finally {
			pm.close();
		}

	}
	public LexiqueMetaData getLexique(String idStr) {
		logger.log(Level.WARNING, "getLexique id " + idStr);
		
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			long id = Long.parseLong(idStr);
			LexiqueEntity c = pm.getObjectById(LexiqueEntity.class, id);
			if (c == null){
				return null;
			}else {
				LexiqueMetaData lmd = toLexiqueMetadata(c);
				return lmd;
			}
			
		} catch (Exception e) {
			return null;
		} finally {
			pm.close();
		}
	}
	
	private LexiqueMetaData toLexiqueMetadata(LexiqueEntity le) {
		LexiqueMetaData lmd = new LexiqueMetaData();
		lmd.setName(le.getName());
		lmd.setDateModified(le.getDateModified());
		lmd.setDateRegistered(le.getDateRegistered());
		lmd.setEmailOwner(le.getEmailOwner());
		lmd.setId(le.getId());
		lmd.setXml(le.getXml());
		return lmd;
	}

	
}
