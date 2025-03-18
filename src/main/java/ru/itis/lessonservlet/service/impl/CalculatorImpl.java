package ru.itis.lessonservlet.service.impl;

import ru.itis.lessonservlet.service.Calculator;

public class CalculatorImpl implements Calculator {
    @Override
    public int sum(int a, int b) {
        return a+b;
    }

    @Override
    public int minus(int a, int b) {
        return 0;
    }

    @Override
    public int multiply(int a, int b) {
        return 0;
    }
}
