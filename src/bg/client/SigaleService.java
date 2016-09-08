package bg.client;

import java.util.List;

import bg.client.inter.sigal.beans.LexiqueMetaData;
import bg.client.inter.sigale.model.Lexique;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface SigaleService extends RemoteService {
	
	String greetServer(String name) throws IllegalArgumentException;
	
	boolean storeLexiqueMetadata(LexiqueMetaData lexique) throws Exception;
	
	List<LexiqueMetaData> getListLexiquesByOwner(String email) throws Exception;
}
