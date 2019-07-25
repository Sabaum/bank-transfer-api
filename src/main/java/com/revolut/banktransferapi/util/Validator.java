package com.revolut.banktransferapi.util;

import com.revolut.banktransferapi.exception.InvalidOperationException;

import java.math.BigDecimal;
import java.util.Optional;

public enum Validator {

    NOT_NULL {
        @Override
        public void validate(Object obj, String fieldName) {
            if (obj == null) {
                throw new InvalidOperationException(String.format(Constants.NULL_OBJECT_MESSAGE, fieldName));
            }
        }
    },
    POSITIVE_NUMBER {
        @Override
        public void validate(Object obj, String fieldName) {
            BigDecimal number = (BigDecimal) obj;
            if (number.compareTo(BigDecimal.ZERO) < 1) {
                throw new InvalidOperationException(String.format(Constants.NOT_POSITIVE_NUMBER_MESSAGE, fieldName));
            }
        }
    },
    NOT_EMPTY_OPTIONAL {
        @Override
        public void validate(Object obj, String fieldName) {
            Optional<?> optional = (Optional) obj;
            if (!optional.isPresent()) {
                throw new InvalidOperationException(String.format(Constants.EMPTY_OPTIONAL_MESSAGE, fieldName));
            }
        }
    };

    public abstract void validate(Object obj, String fieldName);

    public static void validateNotEquals(Object first, Object second) {
        if (first.equals(second)) {
            throw new InvalidOperationException(Constants.SAME_ACCOUNT_TRANSFER_MESSAGE);
        }
    }

}
