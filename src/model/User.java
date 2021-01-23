
package model;

import java.io.Serializable;

public class User implements Serializable {
	private int id;
	private String name;//ユーザー名
	private String pass;//パスワード

	public User() {
	}

	public User(int id, String name, String pass) {
		this.name = name;
		this.pass = pass;
		this.id = id;
	}

	public User(String name, String pass) {
		this.name = name;
		this.pass = pass;
	}

	public User(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPass() {
		return pass;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
