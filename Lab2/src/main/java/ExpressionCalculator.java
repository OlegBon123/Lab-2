import org.apache.commons.math3.complex.Complex;

public class ExpressionCalculator {
    public static void main(String[] args) {
        // Заданные параметры
        double x = -1;
        double a = 3.1;
        double b = 5.2;
        double c = -11.4;

        // Вычисление числителя
        Complex numerator = calculateNumerator(a, x, b);

        // Вычисление знаменателя
        double denominator = calculateDenominator(x, c);

        // Выражение внутри синуса
        double sinValue = calculateSin(x, b);

        // Полное выражение
        Complex result = numerator.divide(denominator).subtract(sinValue);

        // Вывод результата
        System.out.println("Результат выражения: " + result);
    }

    private static Complex calculateNumerator(double a, double x, double b) {
        // ax^3 - b
        double realPart = a * Math.pow(x, 3) - b;

        // Проверяем, не отрицательное ли значение
        if (realPart < 0) {
            // Возвращаем корень четной степени как комплексное число
            return new Complex(0, Math.pow(-realPart, 0.25));
        } else {
            return new Complex(Math.pow(realPart, 0.25), 0);
        }
    }

    private static double calculateDenominator(double x, double c) {
        double value = x - c;
        // Вычисляем кубический корень и логарифм
        double cubeRoot = Math.cbrt(value);
        return Math.log(cubeRoot) / Math.log(2); // Логарифм по основанию 2
    }

    private static double calculateSin(double x, double b) {
        double sinArgument = (Math.pow(x, 2) - (b / x)) / x;
        return Math.sin(sinArgument);
    }
}