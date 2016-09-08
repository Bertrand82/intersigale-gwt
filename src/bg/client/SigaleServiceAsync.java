package bg.client;

import java.util.List;

import bg.client.inter.sigal.beans.LexiqueMetaData;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SigaleServiceAsync {
	
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	
	void storeLexiqueMetadata(LexiqueMetaData lexique, AsyncCallback<Boolean> callback);


	void getListLexiquesByOwner(String email, AsyncCallback<List<LexiqueMetaData>> callback);

}
