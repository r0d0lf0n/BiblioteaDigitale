package bibliotecaDigitale;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.users.Roles;

public class App {
		
	private static final int regUserNumber = 20;
	private static final int extUserNumber = 3;
	private static final String[] FIRST_NAMES = {"Mario", "Luigi", "Giovanni", "Anna", "Maria", "Luca", "Rodolfo", "Ilaria", "Alessandro", "Marta", "Giacomo", "Josè", "Martin", "David", "Luca", "Giorgio", "Kevin"};
    private static final String[] LAST_NAMES = {"Rossi", "Bianchi", "Granata", "Mayo", "Conforti", "Durante", "Verza", "Desantis", "Lupini", "Casole", "Gioia", "Terracina", "Fionda", "Barberi", "Pizzuti"};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
				
		Orchestrator orchestrator = new Orchestrator();

		orchestrator.startDB();
		
		//create users
		if(!orchestrator.usersExist()) {
			orchestrator.createUsers(Roles.ADMIN, generateUser());
	
			for(int i=0; i< regUserNumber; i++) {
				orchestrator.createUsers(Roles.REGULAR_USER, generateUser());
			}	
		}
		else {
			orchestrator.loadUsers();
		}
		for(int i=0; i< extUserNumber; i++) {
			orchestrator.createUsers(Roles.EXTERNAL_USER, generateUser());
		}
		
		orchestrator.startApp();
		
	}
	
	private static List<String> generateUser() {
        List<String> user = new ArrayList<>();
        Random random = new Random();

         String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
         String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
         String codFiscale = generateCodiceFiscale(firstName, lastName);

         user.add(firstName);
         user.add(lastName);
         user.add(codFiscale);

        return user;
    }

    private static String generateCodiceFiscale(String firstName, String lastName) {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";

        StringBuilder codiceFiscale = new StringBuilder();

        codiceFiscale.append(getThreeCharacters(lastName.toUpperCase(), characters, random));
        codiceFiscale.append(getThreeCharacters(firstName.toUpperCase(), characters, random));

        for (int i = 0; i < 2; i++) {
            codiceFiscale.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        codiceFiscale.append(characters.charAt(random.nextInt(characters.length())));

        for (int i = 0; i < 2; i++) {
            codiceFiscale.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        for (int i = 0; i < 4; i++) {
            codiceFiscale.append(characters.charAt(random.nextInt(characters.length())));
        }

        return codiceFiscale.toString();
    }

    private static String getThreeCharacters(String name, String characters, Random random) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            if (i < name.length()) {
                result.append(name.charAt(i));
            } else {
                result.append(characters.charAt(random.nextInt(characters.length())));
            }
        }

        return result.toString();
    }
}
