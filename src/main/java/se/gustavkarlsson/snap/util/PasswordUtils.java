package se.gustavkarlsson.snap.util;

public abstract class PasswordUtils {

	private static final String SPECIAL_CHARACTERS = ",._-+*/=;:!?\"#¤%&/\\()'<>|@£${}[]";

	public enum Strength {
		INADEQUATE, WEAK, MODERATE, STRONG, EXCEPTIONAL;
	}

	public static boolean checkValidity(String password) {
		for (int i = 0; i < password.length(); i++) {
			String c = password.substring(i, i + 1);
			if (!(c.matches("[a-z]") || c.matches("[A-Z]")
					|| c.matches("[0-9]") || SPECIAL_CHARACTERS.contains(c))) {
				return false;
			}
		}
		return true;
	}

	public static Strength checkStrength(String password) {
		int entropy = 0;
		entropy += (password.matches(".*[a-z].*")) ? 26 : 0;
		entropy += (password.matches(".*[A-Z].*")) ? 26 : 0;
		entropy += (password.matches(".*[0-9].*")) ? 10 : 0;
		entropy += (password.matches(".*[^a-zA-Z0-9].*")) ? SPECIAL_CHARACTERS
				.length() : 0;

		double strength = Math.pow(entropy, password.length());

		if (strength > Math.pow(10, 18)) {
			return Strength.EXCEPTIONAL;
		}
		if (strength > Math.pow(10, 15)) {
			return Strength.STRONG;
		}
		if (strength > Math.pow(10, 12)) {
			return Strength.MODERATE;
		}
		if (strength > Math.pow(10, 9)) {
			return Strength.WEAK;
		}
		return Strength.INADEQUATE;
	}
}
