package info.novatec.iban.calculator;

/**
 * Calculator for IBAN numbers.
 */
public interface IBANCalculator {

	/**
	 * Calculates the IBAN number from the given country code, bank code and
	 * account number. This includes the generation of the checksum digits.
	 * 
	 * @param country
	 *            the country code
	 * @param bankCode
	 *            the bank code
	 * @param accountNumber
	 *            the account number
	 * @return the complete IBAN number in valid format for given country code
	 */
	String calculate(CountryCode country, String bankCode, String accountNumber);

}
