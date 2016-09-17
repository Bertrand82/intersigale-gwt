package bg.client.ui.login;

import com.google.gwt.storage.client.Storage;

import bg.client.LogGWT;
import bg.client.inter.sigal.beans.UserBean;
import bg.client.ui.menu.Menu;

public class LoginService {

	private LogGWT log = new LogGWT();

	private UserBean userBean;

	private static LoginService instance = new LoginService();

	public LoginService() {
		this.userBean=fetchUserInLocalStorage();
		
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

	public void login(UserBean user) {
		this.userBean = user;
		if (user == null) {
			log.log("No Login");
		} else {
			log.log("Login " + user.getName() + " " + user.getEmail());
			Menu.getInstance().setLogout(userBean);

		}
		this.save(user);
		WelcomeUI.getInstance().update(userBean);
	}

	public void logout() {
		if (isLogged()) {
			this.userBean = null;
			Menu.getInstance().logoutByebye();

		}
	}

	private static final String KEY = "inter.sigale.bg.client.ui.login";

	public UserBean fetchUserInLocalStorage() {
		Storage storage = Storage.getLocalStorageIfSupported();
		if (storage == null) {
			return null;
		} else {
			String userStr = storage.getItem(KEY);
			UserBean user = parseUser(userStr);
			return user;
		}
	}

	public void save(UserBean userBean) {
		Storage storage = Storage.getLocalStorageIfSupported();
		if (storage != null) {
			String value;
			if (userBean == null) {
				value = "";
			} else {
				value = userBean.toString();
			}
			storage.setItem(KEY, value);
		}
	}

	public static UserBean parseUser(String s) {
		if (s == null) {
			return null;
		}
		if (s.trim().length() == 0) {
			return null;
		}
		String email = null;
		String name = null;
		String[] sArray = s.split(":");
		for (String item : sArray) {
			item = item.trim();
			String[] itemArray = item.split("=");
			if (itemArray == null) {

			} else if (itemArray.length == 2) {
				String key = itemArray[0];
				String value = itemArray[1];
				if (key.equalsIgnoreCase("email")) {
					email = value;
				}
				if (key.equalsIgnoreCase("name")) {
					name = value;
				}
			}
		}
		UserBean userBean = new UserBean(name, email, null);

		return userBean;
	}

	public void logoutByebye() {
		userBean=null;
		save(userBean);
	}
}
