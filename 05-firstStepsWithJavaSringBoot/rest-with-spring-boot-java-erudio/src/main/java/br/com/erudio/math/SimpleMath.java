package br.com.erudio.math;

public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo) {
        return Double.sum(numberOne, numberTwo);
    }

    public Double minus(Double numberOne, Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double multiplication(Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double division(Double dividendo, Double divisor) {
        return dividendo / divisor;
    }

    public Double mean(Double numberOne, Double numberTwo) {
        return (Double.sum(numberOne, numberTwo)) / 2;
    }

    public Double sqrt(Double number) {
        return Math.sqrt(number);
    }

}
