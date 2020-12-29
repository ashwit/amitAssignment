import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public class KDC {
	
	private Person personB;
	private Person personA;
	private byte[] ks_personB;
	private byte[] ks_personA;
	
	public KDC(Person personB, Person personA) {
		super();
		this.personB = personB;
		this.personA = personA;
	}

	public Person getPersonB() {
		return personB;
	}
	
	public void setPersonB(Person personB) {
		this.personB = personB;
	}

	public Person getPersonA() {
		return personA;
	}

	public void setPersonA(Person personA) {
		this.personA = personA;
	}
	
	public byte[] getKs_PersonB() {
		return ks_personB;
	}

	public void setKs_PersonB(byte[] ks_PersonB) {
		ks_personB = ks_PersonB;
	}

	public byte[] getKs_PersonA() {
		return ks_personA;
	}

	public void setKs_PersonA(byte[] ks_PersonA) {
		ks_personA = ks_PersonA;
	}

	public String createKS() {
		System.out.println("Session Key Generated");		
		return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
	}
	
	public void generateKeySession(String origin, byte[] originCrypt, byte[] destCrypt) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		// decipher originCript on k_personB + compare result with origin
		// if equal -> generate a session key + fill in fields with personB and personA's session keys
		if(AES.decipher(originCrypt, personB.getKey()).equals(origin)) {
			System.out.println("Authentication done successfully for the user" + personB.getName());
			if(AES.decipher(destCrypt, personB.getKey()).equals(personA.getName())){
				System.out.println("Verification that " + personB.getName() + " wants to talk to " + personA.getName());
				String keySession = createKS();
				this.ks_personB = AES.cipher(keySession, personB.getKey());
				this.ks_personA = AES.cipher(keySession, personA.getKey());
			}
		} else {
			System.out.println("Could not authenticate user!");
		}
	}
}
