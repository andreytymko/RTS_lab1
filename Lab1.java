package com.tymko;

import org.math.plot.Plot2DPanel;

import javax.swing.*;

import java.util.Arrays;

import java.util.stream.IntStream;

public class Lab1 {

    public static final int n = 6; // number of harmonic
    public static final int w = 2100; // cutoff frequency
    public static final int N = 256 ; // number of discrete readings


    public static void main(String[] args) {
    double [] randomSignals = getRandomSignalArray();
    double mx = calculateMx(randomSignals);

    double dx = calculateDx (randomSignals, mx);
    System.out.printf("Mx = %s; Dx = %s;", mx, dx);
    plotGraphic(randomSignals);

}

    private static void plotGraphic(double[] randomSignals) {
        int[] tmpT = IntStream.rangeClosed(0, N-1).toArray();
        double[] t = Arrays.stream(tmpT).mapToDouble(elem -> elem).toArray();
        double[] y = Arrays.copyOfRange(randomSignals, 0, 100);
        double[] x = Arrays.copyOfRange(t, 0, 100);
        Plot2DPanel plot = new Plot2DPanel();
        plot.addLinePlot("x(t) = A * sin(wt + f)", x, y);
        JFrame frame = new JFrame("Plot panel");
        frame.setContentPane(plot);
        frame.setVisible(true);

        frame.setSize(500, 500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(plot);

    }

    private static double calculateDx(double[] randomSignals, double mx) { double[] signals = randomSignals.clone();
        return Arrays.stream(signals).map(x -> Math.pow(x - mx, 2)).sum() / N -1;

    }

    private static double calculateMx(double[] randomSignals) { double[] signals = randomSignals.clone();
        return Arrays.stream(signals).sum() / N;

    }

    private static double[] getRandomSignalArray() { double[] xArray = new double[N];
        for (int t = 0; t < N; t++) {

            xArray[t] = getRandomSignal(t);

        }

        return xArray;

    }

    public static double getRandomSignal(double t) {
        double sum = 0;
        double wp = w / n;

        for (int i = 0; i < n; i++) {

            double Ap = Math.random();

            double Fp = Math.random();

            sum += Ap * Math.sin(wp * t + Fp);

            wp += w / n;

        }

        return sum;

    }

}
