package bg.client;

import java.util.List;

import bg.client.inter.cicada.beans.LexiqueMetaData;
import bg.client.inter.cicada.beans.UserBean;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface SigaleService extends RemoteService {
	
		
	long storeLexiqueMetadata(LexiqueMetaData lexique) throws Exception;
	
	boolean deleteLexiqueMetadata(String lexiqueId, String email) throws Exception;
	
	LexiqueMetaData getLexiqueMetadataById(String lexiqueId, String email) throws Exception;
	
	List<LexiqueMetaData> getListLexiquesByOwner(String email) throws Exception;
	
	UserBean register(UserBean user) throws Exception;
	
	UserBean login(String email, String password) throws Exception;
	
	String getKey(UserBean userBean) throws Exception;
}
