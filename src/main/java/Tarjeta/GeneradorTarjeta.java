package Tarjeta;

import java.util.Random;

public class GeneradorTarjeta {

    /*public static String generadorTarjeta() {
        String[] prefixes = {"4", "5", "37", "6"};
        Random random = new Random();
        String prefix = prefixes[random.nextInt(prefixes.length)];
        int length = (prefix.equals("37")) ? 15 : random.nextInt(4) + 13; // American Express tiene 15 dígitos, otros entre 13 y 16

        StringBuilder number = new StringBuilder(prefix);

        while (number.length() < length - 1) {
            number.append(random.nextInt(10));
        }
        number.append(validacion(number.toString()));

        return number.toString();
    }

    /*private static int validacion(String number) {
        int sum = 0;
        boolean alternate = true;

        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
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
    }*/

    public boolean isValid(long number)
    {
        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4)
                        || prefixMatched(number, 5)
                        || prefixMatched(number, 37)
                        || prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) + sumOfOddPlace(number)) % 10 == 0);
    }

    public int getFacilitador(long number){
        if(prefixMatched(number, 4)){
            return 1;
        }
        if(prefixMatched(number, 5)){
            return 2;
        }
        if(prefixMatched(number, 37)){
            return 3;
        }
        if(prefixMatched(number, 6)){
            return 4;
        }
        return 5;
    }

    // Get the result from Step 2
    public static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    // Return this number if it is a single digit, otherwise,
    // return the sum of the two digits
    public static int getDigit(int number)
    {
        if (number < 9) {
            return number;
        }
        return number / 10 + number % 10;
    }

    // Return sum of odd-place digits in number
    public static int sumOfOddPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2) {
            sum += Integer.parseInt(num.charAt(i) + "");
        }
        return sum;
    }

    // Return true if the digit d is a prefix for number
    public static boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    // Return the number of digits in d
    public static int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }

    // Return the first k number of digits from
    // number. If the number of digits in number
    // is less than k, return number.
    public static long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}
