package info.novatec.iban.calculator;

import info.novatec.iban.calculator.impl.IBANCalculatorImpl;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test to verify the correctness of the IBAN calculator.
 */
public class IBANCalculatorTest {

	/** Expected length of an IBAN number in Switzerland. */
	private static final int EXPECTED_IBAN_LENGTH_FOR_CH = 21;

	/** Expected length of an IBAN number in Austria. */
	private static final int EXPECTED_IBAN_LENGTH_FOR_AT = 20;

	/** Expected length of an IBAN number in Germany. */
	private static final int EXPECTED_IBAN_LENGTH_FOR_DE = 22;

	/** Class under Test. */
	private IBANCalculator cut = new IBANCalculatorImpl();
	
	/** JUnit rule for expected exceptions. */
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Verifies that the expected IBAN code is calculated from the given country
	 * code.
	 */
	@Test
	public final void verifyValidIBANCalculationForDE() {
		String IBAN = cut.calculate(CountryCode.DE, "10090044", "532013018");
		Assertions.assertThat(IBAN).isNotNull();
		Assertions.assertThat(IBAN.length()).isEqualTo(EXPECTED_IBAN_LENGTH_FOR_DE);
		Assertions.assertThat(IBAN).isEqualTo("DE10100900440532013018");
	}

	/**
	 * Verifies that the expected IBAN code is calculated from the given country
	 * code.
	 */
	@Test
	public final void verifyValidIBANCalculationForAT() {
		// Account at Salzburger Sparkasse Bank AG
		String IBAN = cut.calculate(CountryCode.AT, "20404", "12345");
		Assertions.assertThat(IBAN).isNotNull();
		Assertions.assertThat(IBAN.length()).isEqualTo(EXPECTED_IBAN_LENGTH_FOR_AT);
		Assertions.assertThat(IBAN).isEqualTo("AT612040400000012345");
	}

	/**
	 * Verifies that the expected IBAN code is calculated from the given country
	 * code.
	 */
	@Test
	public final void verifyValidIBANCalculationForCH() {
		// Account at Postfinance
		String IBAN = cut.calculate(CountryCode.CH, "9000", "175691261");
		Assertions.assertThat(IBAN).isNotNull();
		Assertions.assertThat(IBAN.length()).isEqualTo(EXPECTED_IBAN_LENGTH_FOR_CH);
		Assertions.assertThat(IBAN).isEqualTo("CH5609000000175691261");
	}

	/**
	 * Verifies expected failure in IBAN calculation for invalid bank code.
	 */
	@Test
	public final void verifyIBANCalculationErrorForInvalidBankCode() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid bank code");
		
		cut.calculate(CountryCode.DE, "123", "484848");
	}

	/**
	 * Verifies expected failure in IBAN calculation for empty bank code.
	 */
	@Test
	public final void verifyIBANCalculationErrorForEmptyBankCode() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid bank code");

		cut.calculate(CountryCode.DE, "", "484848");
	}

	/**
	 * Verifies expected failure in IBAN calculation for missing bank code.
	 */
	@Test
	public final void verifyIBANCalculationErrorForMissingBankCode() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid bank code");

		cut.calculate(CountryCode.DE, null, "484848");
	}

	/**
	 * Verifies expected failure in IBAN calculation for missing account number.
	 */
	@Test
	public final void verifyIBANCalculationErrorForMissingAccountNumber() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid account number");

		cut.calculate(CountryCode.DE, "60050101", null);
	}

	/**
	 * Verifies expected failure in IBAN calculation for empty account number.
	 */
	@Test
	public final void verifyIBANCalculationErrorForEmptyAccountNumber() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid account number");

		cut.calculate(CountryCode.DE, "60050101", "");
	}

	/**
	 * Verifies expected failure in IBAN calculation for missing country code.
	 */
	@Test
	public final void verifyIBANCalculationErrorForMissingCountryCode() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid country code");
		
		cut.calculate(null, "60050101", "484848");
	}
}
