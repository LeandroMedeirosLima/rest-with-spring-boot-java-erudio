package br.com.erudio.converters;

public class NumberConverter {
    
    public static Boolean isNumbersValids(String numberOne, String numberTwo) {
        Boolean isNumberValid = true;
        
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
//            throw new UnsupportedMathOperation("Please set a numeric value.");
            isNumberValid = false;
        }
        return isNumberValid;
    }

    public static Double convertToDouble(String strNumber) {
        Double invalidNumber = 0D;
        if (strNumber == null) {
            return invalidNumber;
        }

        // BR 10,25
        // US 10.25
        String number = strNumber.replaceAll(",", ".");
        if (!isNumeric(number)) {
            return invalidNumber;
        }
        return Double.parseDouble(number);
    }

    public static Boolean isNumeric(String strNumber) {
        if (strNumber == null) {
            return false;
        }

        String number = strNumber.replaceAll(",", ".");
        return number.matches("[+-]?[0-9]*\\.?[0-9]+");
    }

}
