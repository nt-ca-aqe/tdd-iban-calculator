package info.novatec.iban.calculator.impl;

import info.novatec.iban.calculator.IBANCalculator;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation class for {@link IBANCalculator}.
 * 
 * This is the first (unoptimized) iteration of an implementation that conforms
 * to the corresponding unit tests.
 */
public class IBANCalculatorImpl implements IBANCalculator {

	@Override
	public String calculate(String bankCode, String accountNumber) {
		String ibanNumber = null;

		if (bankCode == null) {
			throw new IllegalArgumentException("An invalid bank code given");
		}
		if (accountNumber == null || accountNumber.trim().length() == 0) {
			throw new IllegalArgumentException(
					"An invalid account number given");
		}

		if (bankCode.trim().length() < 8) {
			throw new IllegalArgumentException("An invalid bank code given");
		}
		String paddedAccountNumber = StringUtils.leftPad(accountNumber, 10,
				'0');
		String ibanForCheckCode = bankCode + paddedAccountNumber + "131400";
		BigInteger checkCodeBase = new BigInteger(ibanForCheckCode);
		long checkCode = 98L - checkCodeBase.mod(BigInteger.valueOf(97L))
				.longValue();
		ibanNumber = "DE" + Long.valueOf(checkCode).toString()
				+ bankCode + paddedAccountNumber;

		return ibanNumber;
	}

}
