package info.novatec.iban.calculator.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ISO7064CodeGeneratorTest {

    private ISO7064CodeGenerator cut = new ISO7064CodeGenerator();

    @Test
    public void calculateISO7064CheckCode() throws Exception {
        String paddedAccountNumber = StringUtils.leftPad("532013018", DE_ACCOUNT_NUMBER_MAX_LENGTH,'0');
        long checkCode = cut.calculateISO7064CheckCode("10090044", paddedAccountNumber);
        assertThat(checkCode).isNotNull();
        assertThat(checkCode).isEqualTo(10);
    }

}