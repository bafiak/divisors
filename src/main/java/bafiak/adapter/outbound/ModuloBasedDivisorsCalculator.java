package bafiak.adapter.outbound;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import bafiak.application.DivisorsCalculator;

public class ModuloBasedDivisorsCalculator implements DivisorsCalculator {
    @Override
    public List<Integer> calculate(final int number) {
        if (number < 1) {
           throw new IllegalArgumentException("Only natural numbers are accepted. Found: " + number);
        }

        return IntStream.rangeClosed(1, number).filter(iter -> number % iter == 0).boxed().collect(toList());
    }
}
