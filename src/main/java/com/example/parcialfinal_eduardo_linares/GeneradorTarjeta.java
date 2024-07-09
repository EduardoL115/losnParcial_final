package com.example.parcialfinal_eduardo_linares;

import java.util.Random;

public class GeneradorTarjeta {

    public static String generadorTarjeta() {
        String[] prefixes = {"4", "5", "37", "6"};
        Random random = new Random();
        String prefix = prefixes[random.nextInt(prefixes.length)];
        int length = (prefix.equals("37")) ? 15 : random.nextInt(4) + 13; // American Express tiene 15 dígitos, otros entre 13 y 16

        StringBuilder numero = new StringBuilder(prefix);

        while (numero.length() < length - 1) {
            numero.append(random.nextInt(10));
        }
        numero.append(validacion(numero.toString()));

        return numero.toString();
    }

    private static int validacion(String numero) {
        int sum = 0;
        boolean alternate = true;

        for (int i = numero.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(numero.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (10 - (sum % 10)) % 10;
    }
}
