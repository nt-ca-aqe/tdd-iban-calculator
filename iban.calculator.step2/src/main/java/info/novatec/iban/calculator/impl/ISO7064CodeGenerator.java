package info.novatec.iban.calculator.impl;

import java.math.BigInteger;

/**
 * ISO 7064 check code generator.
 */
class ISO7064CodeGenerator {
	private static final String DE_ALPHA_CONVERSION = "1314";
	
	/**
	 * Calculates the check code for given input data according to ISO 7064.
	 * 
	 * @param bankCode
	 *            the bank code
	 * @param accountNumber
	 *            the account number
	 * @return the calculated check code
	 */
	long calculateISO7064CheckCode(String bankCode, String accountNumber) {
		String ibanForCheckCode = String.format("%s%s%s00", 
				bankCode,accountNumber, DE_ALPHA_CONVERSION);
		BigInteger checkCodeBase = new BigInteger(ibanForCheckCode);
		return 98L - checkCodeBase.mod(BigInteger.valueOf(97L)).longValue();
	}
	
}
