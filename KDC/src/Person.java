import java.util.Random;

public class Person {
	
	private String name;
	private String key;
	private String keySession;
	int nonce;
	int newNonce;
	
	public Person(String name, String key) {
		super();
		this.name = name;
		this.key = key;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getKeySession() {
		return keySession;
	}

	public void setKeySession(String keySession) {
		this.keySession = keySession;
	}
	
	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nounce) {
		this.nonce = nounce;
	}

	public int getNewNonce() {
		return newNonce;
	}

	public void setNewNonce(int newNonce) {
		this.newNonce = newNonce;
	}

	public void createNonce() {
		Random rand = new Random();
		this.nonce = rand.nextInt(1000); //returns a random number between 0-1000
	}
	
	public int funcAuthentic(int nonce) {
		return (nonce * 27) + 9;
	}
	
}
