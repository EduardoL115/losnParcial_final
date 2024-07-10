package Tarjeta;

import java.util.Random;

public class GeneradorTarjeta { // verifica que el numero de tarjeta sea valido y tambien devuelve el tipo Facilitador



    public boolean isValid(long number) // verifica la validez de una tarjeta
    {
        return (getSize(number) >= 13 && //busca que el numero de tarjeta sea mayor o igual a 13
                getSize(number) <= 16) && //busca que el numero de tarjeta sea menor o igual a 16
                (prefixMatched(number, 4) //revisa que el prefijo sea 4 (Visa)
                        || prefixMatched(number, 5)//revisa que el prefijo sea 5 (Master Card)
                        || prefixMatched(number, 37)//revisa que el prefijo sea 37 (Master Card)
                        || prefixMatched(number, 6))  //revisa que el prefijo sea 6 (Discover)
                         && ((sumOfDoubleEvenPlace(number) + sumOfOddPlace(number)) % 10 == 0); // y que finmalmente corre el algoritmo de luhn para la ultima verificacion
    }

    public int getFacilitador(long number){ // devuelve el facilitador para guardar en la base de datos
        if(prefixMatched(number, 4)){ // revisa si el prefijo es 4
            return 1;//devuelve el id en la base de datos para visa
        }
        if(prefixMatched(number, 5)){ //revisa que el prefijo sea 5
            return 2;//devuelve el id en la base de datos para mastercard
        }
        if(prefixMatched(number, 37)){//revisa que el prfijo sea 37
            return 3;//devuelve el id de la base de datos para american express
        }
        if(prefixMatched(number, 6)){//revisa que el prefijo sea 6
            return 4;//devuelve el id en la base de datos para discover
        }
        return 5;// no aplico ninguno (por el metodo de verificacion nunca se ocupa)
    }


    public static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0; // inicia la suma que se retona
        String num = number + ""; //cambia numero a string
        for (int i = getSize(number) - 2; i >= 0; i -= 2) // de derecha a izquierda  solamente usa los valores en pociciones par
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2); // suma los valores cuando se multiplican *dos el digito y se hace get digit

        return sum;// retorno de la suma de numeros de derecha a izquierda multiplicado por 2 y sumados
    }


    public static int getDigit(int number)
    {
        if (number < 9) { // revisa que sea menor a un digito de dos espacios (10,11,12 ect)
            return number; //regresa el numero
        }
        return number / 10 + number % 10; // separa los dos digitos y los suma
    }


    public static int sumOfOddPlace(long number) { // suma las pociciones impar
        int sum = 0;// inicio de suma a retornar
        String num = number + ""; //cambia numero a un string
        for (int i = getSize(number) - 1; i >= 0; i -= 2) {  // de derecha a izquierda busca en las pociciones inpar
            sum += Integer.parseInt(num.charAt(i) + ""); // suma el valor en la pocicion a suma
        }
        return sum;// retorna la suma total de los numeros en pociciones inpar
    }


    public static boolean prefixMatched(long number, int d) // funcion que se verifico que el prefijo era igual
    {
        return getPrefix(number, getSize(d)) == d; // regresa true si el prefijo es igual a la variable d
    }


    public static int getSize(long d)
    {
        String num = d + "";//cambia a sting
        return num.length();//regresa tamanio de digito
    }


    public static long getPrefix(long number, int k) // encuentra el prifjo
    {
        if (getSize(number) > k) { // revisa q el numero sea mas grande que lo que se busca
            String num = number + ""; // cambia num a string
            return Long.parseLong(num.substring(0, k));//  manda  la pocicion donde  se revisa y que sea igual a k
        }
        return number;// regresa numero
    }
}
