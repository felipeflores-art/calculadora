import java.util.Scanner;
import java.util.Stack;

public class CalculadoraPolacaInversa1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pedir al usuario que ingrese la expresión en notación infija
        System.out.println("Ingrese la expresión en notación infija (por ejemplo, '2 + 3 * (4 + 5)'):");
        String expresion = scanner.nextLine();

        // Convertir la expresión de notación infija a notación polaca inversa (RPN)
        String rpnExpresion = convertirInfijaARPN(expresion);

        // Evaluar la expresión en notación polaca inversa
        double resultadoFinal = evaluarRPN(rpnExpresion);

        // Mostrar el resultado
        System.out.println("El resultado es: " + resultadoFinal);
    }

    // Método para convertir una expresión infija a notación polaca inversa (RPN)
    private static String convertirInfijaARPN(String expresion) {
        StringBuilder rpn = new StringBuilder();
        Stack<Character> operadores = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);
            if (esNumero(caracter)) {
                rpn.append(caracter);
            } else if (esOperador(caracter)) {
                while (!operadores.isEmpty() && precedencia(caracter) <= precedencia(operadores.peek())) {
                    rpn.append(operadores.pop());
                }
                operadores.push(caracter);
            } else if (caracter == '(') {
                operadores.push(caracter);
            } else if (caracter == ')') {
                while (!operadores.isEmpty() && operadores.peek() != '(') {
                    rpn.append(operadores.pop());
                }
                operadores.pop(); // Quitar el '('
            }
        }

        while (!operadores.isEmpty()) {
            rpn.append(operadores.pop());
        }

        return rpn.toString();
    }

    // Método para evaluar una expresión en notación polaca inversa (RPN)
    private static double evaluarRPN(String expresion) {
        Stack<Double> pila = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);
            if (esNumero(caracter)) {
                pila.push((double)(caracter - '0'));
            } else if (esOperador(caracter)) {
                double segundoNumero = pila.pop();
                double primerNumero = pila.pop();
                double resultado = aplicarOperador(caracter, primerNumero, segundoNumero);
                pila.push(resultado);
            }
        }

        return pila.pop();
    }

    // Método para verificar si un carácter es un operador
    private static boolean esOperador(char caracter) {
        return caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/';
    }

    // Método para aplicar el operador a los números dados
    private static double aplicarOperador(char operador, double a, double b) {
        switch (operador) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b != 0) {
                    return a / b;
                } else {
                    throw new ArithmeticException("División por cero");
                }
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }

    // Método para verificar si un carácter es un número
    private static boolean esNumero(char caracter) {
        return caracter >= '0' && caracter <= '9';
    }

    // Método para obtener la precedencia de un operador
    private static int precedencia(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}
