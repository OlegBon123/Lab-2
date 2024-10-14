import org.apache.commons.math3.complex.Complex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ExpressionCalculator extends JFrame {
    public ExpressionCalculator(String title) {
        super(title);
        // Создание набора данных
        XYSeriesCollection dataset = createDataset();
        // Создание графика
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Графік функції",
                "x",
                "f(x)",
                dataset
        );
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(800, 600));
        setContentPane(panel);
    }

    private XYSeriesCollection createDataset() {
        XYSeries series = new XYSeries("f(x)");
        Random rand = new Random();
        double a = rand.nextDouble() * 10;
        double b = rand.nextDouble() * 10;
        double c = rand.nextDouble() * 10 - 5;

        for (double x = -5; x <= 5; x += 0.1) {
            Complex numerator = calculateNumerator(a, x, b);
            double denominator = calculateDenominator(x, c);
            double sinValue = calculateSin(x, b);
            Complex result = numerator.divide(denominator).subtract(sinValue);
            series.add(x, result.getReal());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    private static Complex calculateNumerator(double a, double x, double b) {
        double realPart = a * Math.pow(x, 3) - b;
        if (realPart < 0) {
            return new Complex(0, Math.pow(-realPart, 0.25));
        } else {
            return new Complex(Math.pow(realPart, 0.25), 0);
        }
    }

    private static double calculateDenominator(double x, double c) {
        double value = x - c;
        double cubeRoot = Math.cbrt(value);
        return Math.log(cubeRoot) / Math.log(2);
    }

    private static double calculateSin(double x, double b) {
        double sinArgument = (Math.pow(x, 2) - (b / x)) / x;
        return Math.sin(sinArgument);
    }

    private static void findExtremums(double a, double b, double c) {
        double minValue = Double.MAX_VALUE;
        double maxValue = Double.MIN_VALUE;
        double minX = 0, maxX = 0;

        for (double x = -5; x <= 5; x += 0.1) {
            Complex numerator = calculateNumerator(a, x, b);
            double denominator = calculateDenominator(x, c);
            double sinValue = calculateSin(x, b);
            Complex result = numerator.divide(denominator).subtract(sinValue);

            if (result.getReal() < minValue) {
                minValue = result.getReal();
                minX = x;
            }
            if (result.getReal() > maxValue) {
                maxValue = result.getReal();
                maxX = x;
            }
        }

        System.out.println("Мінімум: f(" + minX + ") = " + minValue);
        System.out.println("Максимум: f(" + maxX + ") = " + maxValue);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpressionCalculator example = new ExpressionCalculator("Графік функції");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);

            findExtremums(3.1, 5.2, -11.4);
        });
    }
}
