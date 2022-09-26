package rpg;

public class UserInfo {
	private String account = null;
	private String password = null;
	public String name = null;
	public int characterType = 0;
	public int attack = 0;
	public int level = 0;
	public int exp = 0;

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return this.account;
	}

	public String getPassword() {
		return this.password;
	}
}
