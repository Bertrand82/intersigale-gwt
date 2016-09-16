package bg.client.ui.register;

public class ServiceLogin {

	private static ServiceLogin instance;
	private ServiceLogin() {
		
	}
	
	
	public static ServiceLogin getInstance() {
		if (instance== null){
			instance = new ServiceLogin();
		}
		return instance;
	}
}
