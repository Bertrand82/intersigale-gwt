package bg.server.inter.cicada.data;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class UserEntityFactory {

	private Logger logger = Logger.getLogger("UserEntityFactory");

	private static final PersistenceManagerFactory persistenceManagerFactory = bg.server.util.PMF.getPersistenceManagerFactory();

	private static UserEntityFactory instance = new UserEntityFactory();
	
	
	public static UserEntityFactory getInstance() {
		return instance;
	}


	
	
	
	public UserEntity getUserEntity(String email, String password){
		UserEntity user = getUserEntity(email);
		if (user == null){
			return null;
		}else {
			//TODO check passowrd
		}
		return user;
	}
	
	public UserEntity getUserEntity(String email){
		try {
			PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
			UserEntity u = pm.getObjectById(UserEntity.class, email);
			
			return u;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	}
	
	
	public boolean userExist(String email){
		UserEntity u  = getUserEntity(email);
		return (u != null);
	} 
	
	public UserEntity register(UserEntity user_SS) throws Exception{
		if (userExist(user_SS.getEmail())){
			throw new Exception("User Already exists");
		}		
		try {			
			PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
			pm.makePersistent(user_SS);
			pm.close();
			return user_SS;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
