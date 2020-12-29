import java.util.Random;
import java.util.Scanner;

public class Principal {

	public static String generateRandomString(int length) {
		 String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		 StringBuilder sb = new StringBuilder();
		    Random random = new Random();
		    for(int i = 0; i < length; i++) {
		      int index = random.nextInt(alphabet.length());
		      char randomChar = alphabet.charAt(index);
		      sb.append(randomChar);
		    }
		    return sb.toString();
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		try {	
			// Sets the keys of two users
			String k_PersonB 	= generateRandomString(16);
			String k_PersonA	= generateRandomString(16);
			
			// Check if the keys are 16 or 32 characters long
			if(!(k_PersonB.length() == 16 && k_PersonA.length() == 16) || (k_PersonB.length() == 32 && k_PersonA.length() == 32)){ 
				System.out.println("Keys must be at least 16 characters long \\ n Shutting down the program");
				System.exit(0);
			}
			
			//Create the users
			Person personB = new Person("Ram", k_PersonB);
			Person personA = new Person("Shyam", k_PersonA);
			
			//Registering the users into the KDC
			System.out.println(personB.getName() + " and " + personA.getName() + " are registered into the KDC.");
			KDC kdc = new KDC(personB, personA);
			
			// PersonB sends message to KDC, asking to talk to PersonA
			System.out.println(personB.getName() + " sends a message to the KDC, asking to speak to " + personA.getName());
			kdc.generateKeySession(personB.getName(), //identification of PersonB
								AES.cipher(personB.getName(), personB.getKey()), // PersonB's identification ciphered in k_personB
								AES.cipher(personA.getName(), personB.getKey())); //PersonA's identification ciphered in k_perosnA
			
			//PersonB saves the session key
			personB.setKeySession( AES.decipher(kdc.getKs_PersonB(), personB.getKey()) );
			
			//PersonA saves the session key
			personA.setKeySession( AES.decipher(kdc.getKs_PersonA(), personA.getKey()) );
			System.out.println(personB.getName() + " and " + personA.getName() + " have the session key: " + personB.getKeySession());
			
			//PersonA creates a nonce
			personA.createNonce();
			
			//PersonA encrypts nonce in the session key and sends it to PersonB
			byte[] nonceCript = AES.cipher("" + personA.getNonce(), personA.getKeySession());
			System.out.println(personA.getName() + " generates a nonce, encrypts the session key and sends it to " + personB.getName());
			
			// PersonB decrypts nonce with the session key
			personB.setNonce( Integer.parseInt( AES.decipher( nonceCript, personB.getKeySession() ) ) );
			System.out.println(personB.getName() + " decrypts nonce with the session key.");
			
			// PersonB applies the nonce in the authentication function and sends it to PersonA, encrypted in the session key
			personB.setNewNonce( personB.funcAuthentic( personB.getNonce() ) );
			byte[] newNonceCript = AES.cipher("" + personB.getNewNonce(), personB.getKeySession());
			System.out.println(personB.getName() + " applies the nonce in the authentication function and sends it to " + personA.getName() +", encrypted in the session key ");
			
			// PersonA decrypts new received message and applies the original nonce in its authentication function after which it compares the two
			personA.setNewNonce( Integer.parseInt( AES.decipher( newNonceCript, personA.getKeySession() ) ) );
			System.out.println(personA.getName() + " decrypts the new received nonce, applies the original nonce in the authentication function and compares the two values.");
			if(personA.getNewNonce() == personA.funcAuthentic(personA.getNonce())) {
				System.out.println("The message received is from " + personB.getName());
				System.out.println("Now " + personB.getName() + " e " + personA.getName() + " can exchange secure messages with each other \n ");
				// Authentication process for conidential communication ends here
				
				// Simulation of message exchange
				Scanner keyboard = new Scanner(System.in);
				String msg = "", msgEnd = "end";
				int cont = 0;
				System.out.println("\tSend \"" + msgEnd + "\" to end the message exchange." );
				while(!(msg.equals(msgEnd))) {
					if((cont % 2) == 0) {
						System.out.print(personB.getName() + ": ");
					} else {
						System.out.print(personA.getName() + ": ");
					}
					msg = keyboard.nextLine();
					cont++;
				}
			} else {
				System.out.println("Message is not from " + personB.getName() + "\nShutting down the code");
				System.exit(0);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
