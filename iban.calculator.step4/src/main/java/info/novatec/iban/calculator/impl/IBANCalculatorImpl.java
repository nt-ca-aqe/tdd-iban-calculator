package info.novatec.iban.calculator.impl;

import info.novatec.iban.calculator.CountryCode;
import info.novatec.iban.calculator.IBANCalculator;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation class for {@link IBANCalculator}.
 * 
 * This is the second (refactored) iteration of an implementation that conforms
 * to the corresponding unit tests.
 */
public class IBANCalculatorImpl implements IBANCalculator {

	private static final int CH_ACCOUNT_NUMBER_MAX_LENGTH = 12;
	private static final int AT_ACCOUNT_NUMBER_MAX_LENGTH = 11;
	private static final int DE_ACCOUNT_NUMBER_MAX_LENGTH = 10;
	private static final int AT_BANK_CODE_MIN_LENGTH = 3;
	private static final int AT_BANK_CODE_MAX_LENGTH = 5;
	private static final int CH_BANK_CODE_MIN_LENGTH = 3;
	private static final int CH_BANK_CODE_MAX_LENGTH = 5;
	private static final int DE_BANK_CODE_MIN_LENGTH = 8;
	private static final int DE_BANK_CODE_MAX_LENGTH = DE_BANK_CODE_MIN_LENGTH;

	private ISO7064CodeGenerator iso7064CodeGenerator;
	
	public IBANCalculatorImpl() {
		super();
		this.iso7064CodeGenerator = new ISO7064CodeGenerator();
	}

	@Override
	public String calculate(CountryCode country, String bankCode,
			String accountNumber) {
		String ibanNumber;

		if (country == null) {
			throw new IllegalArgumentException("An invalid country code given");
		}
		if (bankCode == null) {
			throw new IllegalArgumentException("An invalid bank code given");
		}
		if (accountNumber == null || accountNumber.trim().length() == 0) {
			throw new IllegalArgumentException(
					"An invalid account number given");
		}

		if (CountryCode.DE == country) {
			ibanNumber = createIBANForDE(country, bankCode, accountNumber);
		} else if (CountryCode.AT == country) {
			ibanNumber = createIBANForAT(country, bankCode, accountNumber);
		} else {
			ibanNumber = createIBANForCH(country, bankCode, accountNumber);
		}

		return ibanNumber;
	}

	/**
	 * Calculates the IBAN for country code 'AT'.
	 * 
	 * @param country
	 *            The {@link CountryCode}
	 * @param bankCode
	 *            the bank code
	 * @param accountNumber
	 *            the account number
	 * @return generated IBAN for 'AT' (including the check code)
	 */
	private String createIBANForAT(CountryCode country, String bankCode,
			String accountNumber) {
		if (bankCode.trim().length() < AT_BANK_CODE_MIN_LENGTH
				|| bankCode.trim().length() > AT_BANK_CODE_MAX_LENGTH) {
			throw new IllegalArgumentException("An invalid bank code given");
		}
		String paddedBankCode = StringUtils.leftPad(bankCode,
				AT_BANK_CODE_MAX_LENGTH, '0');
		String paddedAccountNumber = StringUtils.leftPad(accountNumber,
				AT_ACCOUNT_NUMBER_MAX_LENGTH,
				'0');
		long checkCode = iso7064CodeGenerator.calculateISO7064CheckCode(country, paddedBankCode, paddedAccountNumber);

		return CountryCode.AT.name() + checkCode + bankCode + paddedAccountNumber;
	}

	/**
	 * Calculates the IBAN for country code 'CH'.
	 * 
	 * @param country
	 *            The country code
	 * @param bankCode
	 *            the bank code
	 * @param accountNumber
	 *            the account number
	 * @return generated IBAN for 'CH' (including the check code)
	 */
	private String createIBANForCH(CountryCode country, String bankCode, String accountNumber) {
		if (bankCode.trim().length() < CH_BANK_CODE_MIN_LENGTH
				|| bankCode.trim().length() > CH_BANK_CODE_MAX_LENGTH) {
			throw new IllegalArgumentException("An invalid bank code given");
		}
		String paddedAccountNumber = StringUtils.leftPad(accountNumber,
				CH_ACCOUNT_NUMBER_MAX_LENGTH, '0');
		String paddedBankCode = StringUtils.leftPad(bankCode,
				CH_BANK_CODE_MAX_LENGTH, '0');
		long checkCode = iso7064CodeGenerator.calculateISO7064CheckCode(country, paddedBankCode, paddedAccountNumber);
		return CountryCode.CH.name() + checkCode + paddedBankCode	+ paddedAccountNumber;
	}

	/**
	 * Calculates the IBAN for country code 'DE'.
	 * 
	 * @param country
	 *            The {@link CountryCode}
	 * @param bankCode
	 *            the bank code
	 * @param accountNumber
	 *            the account number
	 * @return generated IBAN for 'DE' (including the check code)
	 */
	private String createIBANForDE(CountryCode country, String bankCode, String accountNumber) {
		if (bankCode.trim().length() < DE_BANK_CODE_MIN_LENGTH
				|| bankCode.trim().length() > DE_BANK_CODE_MAX_LENGTH) {
			throw new IllegalArgumentException("An invalid bank code given");
		}
		String paddedAccountNumber = StringUtils.leftPad(accountNumber, DE_ACCOUNT_NUMBER_MAX_LENGTH,
				'0');
		long checkCode = iso7064CodeGenerator.calculateISO7064CheckCode(country, bankCode, paddedAccountNumber);
		return CountryCode.DE.name() + checkCode + bankCode + paddedAccountNumber;
	}

}
