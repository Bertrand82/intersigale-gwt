package bg.client.inter.sigal.beans;

import java.io.Serializable;

public class UserBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String password;
	
	public UserBean() {
		
	}

	public UserBean(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	private Long id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
