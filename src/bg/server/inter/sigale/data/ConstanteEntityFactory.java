package bg.server.inter.sigale.data;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class ConstanteEntityFactory {

	private Logger logger = Logger.getLogger("ConstantesEntityFactory");

	private static final PersistenceManagerFactory persistenceManagerFactory = bg.server.util.PMF.getPersistenceManagerFactory();

	private static ConstanteEntityFactory instance = new ConstanteEntityFactory();
	
	
	public static ConstanteEntityFactory getInstance() {
		return instance;
	}


	
	
	public ConstanteEntity getConstantesEntity(String key){
		try {
			PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
			ConstanteEntity u = pm.getObjectById(ConstanteEntity.class, key);
			
			return u;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public ConstanteEntity persisteValue(ConstanteEntity constante) throws Exception{
			
		try {			
			PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
			pm.makePersistent(constante);
			pm.close();
			return constante;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
