package info.novatec.iban.calculator;

/**
 * Calculator for German IBAN numbers.
 */
public interface IBANCalculator {

	/**
	 * Calculates the IBAN number for Germany from the given bank code and
	 * account number. This includes the generation of the checksum digits.
	 * 
	 * @param bankCode
	 *            the bank code
	 * @param accountNumber
	 *            the account number
	 * @return the complete IBAN number in valid format for given country code
	 */
	String calculate(String bankCode, String accountNumber);

}
