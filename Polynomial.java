public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        double[] zero_polynomial = { 0 };
        coefficients = zero_polynomial;
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial polynomial) {
        double[] longer_poly_coefficients;
        double[] shorter_poly_coefficients;
        if (coefficients.length >= polynomial.coefficients.length) {
            longer_poly_coefficients = coefficients;
            shorter_poly_coefficients = polynomial.coefficients;
        } else {
            longer_poly_coefficients = polynomial.coefficients;
            shorter_poly_coefficients = coefficients;
        }
        for (int i = 0; i < shorter_poly_coefficients.length; i++) {
            longer_poly_coefficients[i] += shorter_poly_coefficients[i];
        }
        Polynomial result_polynomial = new Polynomial(longer_poly_coefficients);
        return result_polynomial;
    }

    public double evaluate(double value) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * (Math.pow(value, i));
        }
        return result;
    }

    public boolean hasRoot(double value) {
        if (evaluate(value) == 0) {
            return true;
        }
        return false;
    }

}