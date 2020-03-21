package com.tymko;
import org.math.plot.Plot2DPanel;

import javax.swing.*;

import java.util.Arrays;

import java.util.stream.IntStream;

public class Lab2 {

    private static double[] randomSignalsX;

    private static double[] randomSignalsY;


    private static double[] rxx;

    private static double[] rxy;

    public static final int n = 6; // number of harmonic
    public static final int w = 2100; // cutoff frequency
    public static final int N = 256 ; // number of discrete readings

    static {
        double [] randomSignals = getRandomSignalArray();

        double mx = calculateMx(randomSignals);

//        double mx = Lab1.mx;

        double my = mx;
        randomSignalsX = randomSignals;

        randomSignalsY = randomSignals;


        rxx = calculateKorelationFunction(randomSignalsX, mx);
        rxy = calculateKorelationFunction(randomSignalsY, my);

    }

    public static void main(String[] args) {

        plotGraphic(rxx);

        plotGraphic(rxy);

    }

    private static void plotGraphic(double[] array) {

        int[] tmpTau = IntStream.rangeClosed(0, N/2).toArray();

        double[] tau = Arrays.stream(tmpTau).mapToDouble(t -> t).toArray();
        double[] x = Arrays.copyOfRange(tau, 0, 100);
        double[] y = Arrays.copyOfRange(array, 0, 100);
        Plot2DPanel plot = new Plot2DPanel(); plot.addLinePlot("Rxx function" , x, y);
        JFrame frame = new JFrame("Plot panel");
        frame.setContentPane(plot);
        frame.setVisible(true);

        frame.setSize(500, 500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    public static double getRandomSignal(double t) { double sum = 0;
        double wp = w / n;

        for (int i = 0; i < n; i++) {

            double Ap = Math.random();

            double Fp = Math.random();

            sum += Ap * Math.sin(wp * t + Fp);

            wp += w / n;

        }

        return sum;

    }

    private static double[] calculateKorelationFunction(double[] randomSignals, double mx) {

        double[] rxx = new double[N / 2];

        for (int tau = 0; tau < N / 2; tau++) {

            double sum = 0;

            for (int t = 0; t < N - tau; t++) {

                sum += (randomSignals[t] - mx) * (randomSignals[t + tau] - mx);

            }

            rxx[tau] = sum;

        }

        return rxx;
    }

}

