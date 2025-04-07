package ru.itis.lessonservlet.service.impl;

import ru.itis.lessonservlet.service.Calculator;

public class CalculatorImpl implements Calculator {


    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }

    @Override
    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return a / b;
    }


}