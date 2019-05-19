/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blinkleds;

/**
 *
 * @author adrianna
 */
public class SignalProcessing {

    public static Complex[] fft(Complex[] complexNumbers) {
        int n = complexNumbers.length;

        if (n == 1) {
            return new Complex[]{complexNumbers[0]};
        }

        if (n % 2 != 0) {
            throw new IllegalArgumentException("n is not a power of 2");
        }

        // fft of complexEvenIndexes terms
        Complex[] complexEvenIndexes = new Complex[n / 2];
        for (int k = 0; k < n / 2; k++) {
            complexEvenIndexes[k] = complexNumbers[2 * k];
        }
        Complex[] q = fft(complexEvenIndexes);

        // fft of complexOddIndexes terms
        Complex[] complexOddIndexes = complexEvenIndexes;
        for (int k = 0; k < n / 2; k++) {
            complexOddIndexes[k] = complexNumbers[2 * k + 1];
        }
        Complex[] r = fft(complexOddIndexes);

        // combine
        Complex[] y = new Complex[n];
        for (int k = 0; k < n / 2; k++) {
            double term = -2 * k * Math.PI / n;
            Complex exp = new Complex(Math.cos(term), Math.sin(term));

            y[k] = q[k].add(exp.multiply(r[k]));
            y[k + n / 2] = q[k].subtract(exp.multiply(r[k]));
        }
        return y;
    }
   

    public static double[] calculateFFT(byte[] signal, int numberOfSamples) {
        double temp;
        Complex[] y;
        Complex[] complexSignal = new Complex[numberOfSamples];
        double[] absoluteSignal = new double[numberOfSamples / 2];

        for (int i = 0; i < numberOfSamples; i++) {
            temp = (double) ((signal[2 * i] & 0xFF) | (signal[2 * i + 1] << 8)) / 32768.0F;
            complexSignal[i] = new Complex(temp, 0.0);
        }

        y = fft(complexSignal);
        
        double maxFFTSample;
        maxFFTSample = 0.0;
        int peakPos = 0;

        for (int i = 0; i < (numberOfSamples / 2); i++) {
            absoluteSignal[i] = Math.sqrt(Math.pow(y[i].re(), 2) + Math.pow(y[i].im(), 2));

            if (absoluteSignal[i] > maxFFTSample) {
                maxFFTSample = absoluteSignal[i];
                peakPos = i;
            }
        }

        return absoluteSignal;
    }
}
