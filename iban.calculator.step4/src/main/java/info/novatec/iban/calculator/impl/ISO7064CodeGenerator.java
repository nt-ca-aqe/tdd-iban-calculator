package info.novatec.iban.calculator.impl;

import info.novatec.iban.calculator.CountryCode;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ISO7064CodeGenerator {
	private static final Map<CountryCode, String> ALPHA_CONVERSION_TABLE = new HashMap<>();

	static {
		ALPHA_CONVERSION_TABLE.put(CountryCode.AT, "1029");
		ALPHA_CONVERSION_TABLE.put(CountryCode.CH, "1217");
		ALPHA_CONVERSION_TABLE.put(CountryCode.DE, "1314");
	}
	
	/**
	 * Calculates the check code for given input data according to ISO 7064.
	 * @param country
	 *            The {@link CountryCode}
	 * @param bankCode
	 *            the bank code
	 * @param accountNumber
	 *            the account number
	 * @return the calculated check code
	 */
	public long calculateISO7064CheckCode(CountryCode country, String bankCode, String accountNumber) {
		String ibanForCheckCode = String.format("%s%s%s00", 
				bankCode,accountNumber, ALPHA_CONVERSION_TABLE.get(country));
		BigInteger checkCodeBase = new BigInteger(ibanForCheckCode);
		long checkCode = 98L - checkCodeBase.mod(BigInteger.valueOf(97L))
				.longValue();
		return checkCode;
	}
	
}
