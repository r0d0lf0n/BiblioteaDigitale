/**
 * 
 */
package models.users;

import java.security.SecureRandom;

/**
 * 
 */
public class UtenteEsterno extends Utente {

	private String randomKey = "";
	private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int LENGTH = 5;
    private final SecureRandom random = new SecureRandom();

	
	public UtenteEsterno() {
		super(Roles.EXTERNAL_USER);

		randomKey = generateRandomString();
	}

    public String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
    
    public String getRandomKey() {
    	return randomKey;
    }
	
}
