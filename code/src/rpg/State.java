package rpg;

public class State {
	public final static int LOGIN = 0;
	public final static int LOGUP = 1;
	public final static int HASLOGIN = 2;
	public final static int SIGN_CHARACTER = 3;
	public final static int START = 4;
	
	private int state;
	public State() {
		this.state = 0;
	}
	public boolean is(int state) {
		if(state==this.state)return true;
		return false;
	}
	public void set(int state) {
		this.state=state;
	}
}
