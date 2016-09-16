package bg.client;

import com.google.gwt.core.client.GWT;

public class SigaleUtil {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private static final SigaleServiceAsync sigaleService = GWT.create(SigaleService.class);
	public static SigaleServiceAsync getSigaleService() {
		return sigaleService;
	}
}
