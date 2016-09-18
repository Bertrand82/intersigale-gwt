package bg.client;

import java.util.List;

import bg.client.inter.sigal.beans.LexiqueMetaData;
import bg.client.inter.sigal.beans.UserBean;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SigaleServiceAsync {
	
	
	void storeLexiqueMetadata(LexiqueMetaData lexique, AsyncCallback<Long> callback);


	void getListLexiquesByOwner(String email, AsyncCallback<List<LexiqueMetaData>> callback);


	void deleteLexiqueMetadata(String lexiqueId, String email, AsyncCallback<Boolean> callback);


	void getLexiqueMetadataById(String lexiqueId, String email, AsyncCallback<LexiqueMetaData> callback);


	void register(UserBean user, AsyncCallback<UserBean> callback);


	void login(String email, String password, AsyncCallback<UserBean> callback);


	void getKey(UserBean userBean, AsyncCallback<String> callback);


	
	

}
