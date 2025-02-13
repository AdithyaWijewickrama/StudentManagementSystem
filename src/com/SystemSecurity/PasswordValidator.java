package com.SystemSecurity;

/**
 *
 * @author AW Developer
 */
public final class PasswordValidator {

    private int upperCount = 0;
    private int lowerCount = 0;
    private int digitCount = 0;
    private int symbolCount = 0;
    private String password;

    public PasswordValidator(String password) {
        setPassword(password);
    }

    public PasswordValidator() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        upperCount = 0;
        lowerCount = 0;
        digitCount = 0;
        symbolCount = 0;
        for (char q : password.toCharArray()) {
            if (Character.isUpperCase(q)) {
                upperCount++;
            } else if (Character.isLowerCase(q)) {
                lowerCount++;
            } else if (Character.isDigit(q)) {
                digitCount++;
            } else if (!Character.isAlphabetic(q)) {
                symbolCount++;
            }
        }
    }

    public int getStrength() {
        int i = 0;
        if (upperCount > 0) {
            i++;
        }
        if (lowerCount > 0) {
            i++;
        }
        if (digitCount > 0) {
            i++;
        }
        if (symbolCount > 0) {
            i++;
        }
        if (password.length() >= 8) {
            i++;
        }
        return i;
    }

    public boolean isValid(StrengthType type) {
        switch (type) {
            case ALL:
                return getStrength() >= 5;
            case MIXED_ALPHABATIC:
                return lowerCount > 0 && upperCount > 0;
            case MIXED_LOWER_ALPHABATIC:
                return lowerCount > 0;
            case MIXED_UPPER_ALPHABATIC:
                return upperCount > 0;
            case MIXED_NUMARIC:
                return digitCount > 0;
            case MIXED_SYMBOLIC:
                return symbolCount > 0;
            default:
                if (getStrength() == 1) {
                    switch (type) {
                        case ONLY_ALPHABATIC:
                            return lowerCount > 0 && upperCount > 0;
                        case ONLY_LOWER_ALPHABATIC:
                            return lowerCount > 0;
                        case ONLY_UPPER_ALPHABATIC:
                            return upperCount > 0;
                        case ONLY_NUMARIC:
                            return digitCount > 0;
                        case ONLY_SYMBOLIC:
                            return symbolCount > 0;
                        default:
                            throw new AssertionError();
                    }
                }
        }
        return false;
    }

    public static boolean isPasswordValid(String p) {
        return new PasswordValidator(p).isValid(StrengthType.ALL);
    }

    public enum StrengthType {
        MIXED_ALPHABATIC,
        MIXED_UPPER_ALPHABATIC,
        MIXED_LOWER_ALPHABATIC,
        MIXED_NUMARIC,
        MIXED_SYMBOLIC,
        ONLY_ALPHABATIC,
        ONLY_UPPER_ALPHABATIC,
        ONLY_LOWER_ALPHABATIC,
        ONLY_NUMARIC,
        ONLY_SYMBOLIC,
        ALL;
    }
}
