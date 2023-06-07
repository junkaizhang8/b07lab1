import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
    	double[] zero_coefficient = {0};
    	int[] zero_exponent = {0};
        coefficients = zero_coefficient;
        exponents = zero_exponent;
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }
    
    public Polynomial(File file) {
    	try {
    		double coefficient;
    		int exponent;
    		String[] separated_term;
    		Scanner input = new Scanner(file);
    		String line = input.nextLine();
    		input.close();
    		
    		line = line.replaceAll("-", "+-");
    		if (line.charAt(0) == '+') {
    			line = line.substring(1);
    		}
    		String[] list_of_terms = line.split("\\+");
    		coefficients = new double[list_of_terms.length];
    		exponents = new int[list_of_terms.length];
    		for (int i = 0; i < list_of_terms.length; i++) {
    			separated_term = list_of_terms[i].split("x");
    			coefficient = Double.parseDouble(separated_term[0]);
    			if (separated_term.length > 1) {
    				exponent = Integer.parseInt(separated_term[1]);
    			} else {
    				exponent = 0;
    			}
    			coefficients[i] = coefficient;
    			exponents[i] = exponent;
    		}
    		
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    private int getHighestDeg(Polynomial polynomial) {
    	int highest_deg = 0;
    	for (int exponent : polynomial.exponents) {
    		if (exponent > highest_deg) {
    			highest_deg = exponent;
    		}
    	}
    	return highest_deg;
    }
    
    private int nonZeroCoefficientCount(double[] coefficients) {
    	int count = 0;
    	for (double coefficient : coefficients) {
    		if (coefficient != 0) {
    			count++;
    		}
    	}
    	return count;
    }
    
    private Polynomial removeZeroCoefficients(double[] coefficients) {
    	int i, j, non_zero_coefficients;
    	i = 0;
    	j = 0;
    	non_zero_coefficients = nonZeroCoefficientCount(coefficients);
    	double[] result_coefficients = new double[Math.max(non_zero_coefficients, 1)];
    	int[] result_exponents = new int[Math.max(non_zero_coefficients, 1)];
    	while (i < coefficients.length) {
    		if (coefficients[i] != 0) {
    			result_coefficients[j] = coefficients[i];
    			result_exponents[j] = i;
    			j++;
    		}
    		i++;
    	}
    	Polynomial result_polynomial = new Polynomial(result_coefficients, result_exponents);
    	return result_polynomial;
    }

    public Polynomial add(Polynomial polynomial) {
    	int highest_deg, exponent, i;
    	highest_deg = Math.max(getHighestDeg(polynomial), getHighestDeg(this));
    	double[] sum_coefficients = new double[highest_deg + 1];
    	i = 0;
    	while (i < exponents.length) {
    		exponent = exponents[i];
    		sum_coefficients[exponent] += coefficients[i];
    		i++;
    	}
    	
    	i = 0;
    	while (i < polynomial.exponents.length) {
    		exponent = polynomial.exponents[i];
    		sum_coefficients[exponent] += polynomial.coefficients[i];
    		i++;
    	}
        return removeZeroCoefficients(sum_coefficients);
    }

    public double evaluate(double value) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * (Math.pow(value, exponents[i]));
        }
        return result;
    }

    public boolean hasRoot(double value) {
        if (evaluate(value) == 0) {
            return true;
        }
        return false;
    }
    
    public Polynomial multiply(Polynomial polynomial) {
    	int highest_deg, exponent;
    	double coefficient;
    	highest_deg = getHighestDeg(polynomial) + getHighestDeg(this);
    	double[] result_coefficients = new double[highest_deg + 1];
    	for (int i = 0; i < polynomial.coefficients.length; i++) {
    		for (int j = 0; j < coefficients.length; j++) {
    			exponent = polynomial.exponents[i] + exponents[j];
    			coefficient = polynomial.coefficients[i] * coefficients[j];
    			result_coefficients[exponent] += coefficient;
    		}
    	}
    	return removeZeroCoefficients(result_coefficients);
    }
    
    public void saveToFile(String filename) {
    	try {
    		FileWriter output = new FileWriter(filename, false);
    		for (int i = 0; i < coefficients.length; i++) {
    			if (coefficients[i] >= 0 && i != 0) {
    				output.write("+");
    			}
    			output.write(Double.toString(coefficients[i]));
    			if (exponents[i] != 0) {
    				output.write("x" + exponents[i]);
    			}
    		}
    		output.close();
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }

}