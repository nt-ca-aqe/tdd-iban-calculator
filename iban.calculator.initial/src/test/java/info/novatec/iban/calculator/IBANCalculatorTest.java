package info.novatec.iban.calculator;

import info.novatec.iban.calculator.impl.IBANCalculatorImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test to verify the correctness of the IBAN calculator.
 */
public class IBANCalculatorTest {

	/** Expected length of an IBAN number in Germany. */
	private static final int EXPECTED_IBAN_LENGTH_FOR_DE = 22;

	/** Class under Test. */
	private IBANCalculator cut = new IBANCalculatorImpl();
	
	/** JUnit rule for expected exceptions. */
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Verifies that the expected German IBAN code is calculated.
	 */
	@Test
	public final void verifyValidIBANCalculationForDE() {

		String iban = cut.calculate("10090044", "532013018");

		assertThat(iban).isNotNull();
		assertThat(iban.length()).isEqualTo(EXPECTED_IBAN_LENGTH_FOR_DE);
		assertThat(iban).isEqualTo("DE10100900440532013018");
	}

	/**
	 * Verifies expected failure in IBAN calculation for invalid bank code.
	 */
	@Test
	public final void verifyIBANCalculationErrorForInvalidBankCode() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid bank code");
		
		cut.calculate("123", "484848");
	}

	/**
	 * Verifies expected failure in IBAN calculation for empty bank code.
	 */
	@Test
	public final void verifyIBANCalculationErrorForEmptyBankCode() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid bank code");

		cut.calculate("", "484848");
	}

	/**
	 * Verifies expected failure in IBAN calculation for missing bank code.
	 */
	@Test
	public final void verifyIBANCalculationErrorForMissingBankCode() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid bank code");

		cut.calculate(null, "484848");
	}

	/**
	 * Verifies expected failure in IBAN calculation for missing account number.
	 */
	@Test
	public final void verifyIBANCalculationErrorForMissingAccountNumber() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid account number");

		cut.calculate("60050101", null);
	}

	/**
	 * Verifies expected failure in IBAN calculation for empty account number.
	 */
	@Test
	public final void verifyIBANCalculationErrorForEmptyAccountNumber() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("invalid account number");

		cut.calculate("60050101", "");
	}

}
