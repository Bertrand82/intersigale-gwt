package bg.client.ui.login;

import bg.client.LogGWT;
import bg.client.inter.sigal.beans.UserBean;
import bg.client.ui.menu.Menu;

public class LoginService {

	private LogGWT log = new LogGWT();

	private UserBean userBean;
	
	private static LoginService instance = new LoginService();
	
	public LoginService() {
		
	}
	public static LoginService getInstance() {
		return instance;
	}
	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	public boolean isLogged() {
		return (this.userBean != null);
	}
	
	
	public void login(UserBean user){
		this.userBean =user;
		if(user== null){			
			log.log("No Login");
		}else {
			log.log("Login "+user.getName()+" "+user.getEmail());
			Menu.getInstance().setLogout(isLogged());
		}
		WelcomeUI.getInstance().update(userBean);
	}
	
	public void logout() {
		if (isLogged()){
			this.userBean=null;
			Menu.getInstance().logoutByebye();
			
		}		
	}
}
