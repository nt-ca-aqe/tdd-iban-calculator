package info.novatec.iban.calculator.impl;

import info.novatec.iban.calculator.IBANCalculator;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation class for {@link IBANCalculator}.
 * 
 * This is the second (refactored) iteration of an implementation that conforms
 * to the corresponding unit tests.
 */
public class IBANCalculatorImpl implements IBANCalculator {

	private static final int DE_ACCOUNT_NUMBER_MAX_LENGTH = 10;
	private static final int DE_BANK_CODE_MIN_LENGTH = 8;
	private static final int DE_BANK_CODE_MAX_LENGTH = DE_BANK_CODE_MIN_LENGTH;

	private ISO7064CodeGenerator iso7064CodeGenerator;
	
	public IBANCalculatorImpl() {
		super();
		this.iso7064CodeGenerator = new ISO7064CodeGenerator();
	}

	@Override
	public String calculate(String bankCode, String accountNumber) {
		if (bankCode == null) {
			throw new IllegalArgumentException("An invalid bank code given");
		}
		if (accountNumber == null || accountNumber.trim().length() == 0) {
			throw new IllegalArgumentException(
					"An invalid account number given");
		}

		return createIBANForDE(bankCode, accountNumber);
	}

	/**
	 * Calculates the IBAN for Germany.
	 * 
	 * @param bankCode
	 *            the bank code
	 * @param accountNumber
	 *            the account number
	 * @return generated IBAN for Germany (including the check code)
	 */
	private String createIBANForDE(String bankCode,	String accountNumber) {
		if (bankCode.trim().length() < DE_BANK_CODE_MIN_LENGTH
				|| bankCode.trim().length() > DE_BANK_CODE_MAX_LENGTH) {
			throw new IllegalArgumentException("An invalid bank code given");
		}
		String paddedAccountNumber = StringUtils.leftPad(accountNumber, DE_ACCOUNT_NUMBER_MAX_LENGTH,'0');
		long checkCode = iso7064CodeGenerator.calculateISO7064CheckCode(bankCode, paddedAccountNumber);
		return "DE" + checkCode + bankCode + paddedAccountNumber;
	}

}
