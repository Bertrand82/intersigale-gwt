package bg.server;

import java.util.List;

import bg.client.SigaleService;
import bg.client.inter.sigal.beans.LexiqueMetaData;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.server.inter.sigale.data.LexiqueEntity;
import bg.server.inter.sigale.data.LexiqueEntityFactory;
import bg.shared.FieldVerifier;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SigaleServiceImpl extends RemoteServiceServlet implements SigaleService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	@Override
	public long storeLexiqueMetadata(LexiqueMetaData lexique) throws Exception {
		long id = LexiqueEntityFactory.getInstance().makePersistent(lexique);
		return id;
	}

	@Override
	public List<LexiqueMetaData> getListLexiquesByOwner(String email) throws Exception {
		
		return LexiqueEntityFactory.getInstance().getListLexiqueLight();
	}

	@Override
	public boolean deleteLexiqueMetadata(String lexiqueId, String emailUser) throws Exception {
		LexiqueEntityFactory.getInstance().removeLexique(lexiqueId, emailUser);
		return true;
	}

	@Override
	public LexiqueMetaData getLexiqueMetadataById(String lexiqueId, String email) throws Exception {
		LexiqueMetaData lmd = LexiqueEntityFactory.getInstance().getLexique(lexiqueId);
		return lmd;
	}

	


	
}
