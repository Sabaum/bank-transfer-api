package com.revolut.banktransferapi.util;

import com.revolut.banktransferapi.exception.InvalidOperationException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test(expected = InvalidOperationException.class)
    public void validateNotNull_withNullObject_shouldFail() {
        Validator.NOT_NULL.validate(null, "null");
    }

    @Test(expected = InvalidOperationException.class)
    public void validatePositiveNumber_withNegativeNumber_shouldFail() {
        Validator.POSITIVE_NUMBER.validate(BigDecimal.valueOf(-1), "negative");
    }

    @Test(expected = InvalidOperationException.class)
    public void validatePositiveNumber_withZero_shouldFail() {
        Validator.POSITIVE_NUMBER.validate(BigDecimal.ZERO, "zero");
    }

    @Test(expected = InvalidOperationException.class)
    public void validateNotEmptyOptional_withOptionalEmpty_shouldFail() {
        Validator.NOT_EMPTY_OPTIONAL.validate(Optional.empty(), "empty");
    }

    @Test
    public void validateNotNull_shouldValidate() {
        Validator.NOT_NULL.validate(new Object(), "notNull");
    }

    @Test
    public void validatePositiveNumber_shouldValidate() {
        Validator.POSITIVE_NUMBER.validate(BigDecimal.TEN, "poditive");
    }

    @Test
    public void validateNotEmptyOptional_shouldValidate() {
        Validator.NOT_EMPTY_OPTIONAL.validate(Optional.of(new Object()), "notEmpty");
    }

}