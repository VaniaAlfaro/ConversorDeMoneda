//Librerias
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

//API KEY: 5419ca4de23a7835f819f8cf
//"https://v6.exchangerate-api.com/v6/5419ca4de23a7835f819f8cf/latest/USD";
public class PrincipalAPI {

    public static String buscarCambio(String selectorAbreviaturaPais){
        //Declaración de variable del URL del API.
        String url_str = "https://v6.exchangerate-api.com/v6/5419ca4de23a7835f819f8cf/latest/USD";

        //Declaración de la libreria GSON
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        try {
            //Inicialización del API.
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url_str))
                    .build();

            //Mandar llamar el contenido del API.
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            // Convertir la respuesta JSON a un objeto Java.
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            // Acceder al objeto "conversion_rates" que contiene los tipos de cambio.
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

            // Acceder al tipo de cambio para una moneda específica, por ejemplo, EUR.
            String exchangeRate = conversionRates.get(selectorAbreviaturaPais).getAsString();
            System.out.println("El tipo de cambio del pais es de: " + exchangeRate);
            System.out.println(" ");
            return exchangeRate;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        //Declaración de Variables
        int tipoDeCambio = 0;
        int paisSeleccionado = 0;
        int a = 1;
        String selectorAbreviaturaPais = "";
        String cambioRate;
        int cantidadDeCambio = 0;
        double operacionAritmetica;

        String[] paisArray = {"Argentina", "Brasil", "Colombia", "Cuba", "México."};
        String[] abreviacionArray = {"ARS", "BRL", "COP", "CUP", "MXN"};

        String menuCambio = """
                -------- MENÚ.SELECCIÓN DEL CAMBIO --------
                1. DOLAR => PESO DEL PAIS.
                2. PESO DEL PAIS => DOLAR.
                3. SALIR.""";

        String menuPaisSeleccionado = """
                -------- MENÚ.SELECCIÓN DEL PAIS --------
                1. Argentina.
                2. Brasil.
                3. Colombia.
                4. Cuba.
                5. México.""";

        //Scanner: para que el usuario pueda ingresar datos.
        Scanner lectura = new Scanner(System.in);

        System.out.println("*********** CONVERSOR DE MONEDA ALURA EN DOLARES ***********");
        System.out.println(" ");
        System.out.println("'Bienvenido al conversor de moneda Alura en Dolares'");

        while (tipoDeCambio != 3)
        {
            //Seleccionar que cambio requiere.
            System.out.println(menuCambio);
            tipoDeCambio = lectura.nextInt();

            switch (tipoDeCambio)
            {
                case 1:
                    System.out.println(" ");
                    //Seleccionar un pais de la lista para el cambio.
                    System.out.println(menuPaisSeleccionado);
                    paisSeleccionado = lectura.nextInt();
                    paisSeleccionado -= a;
                    System.out.println(" ");

                    //Imprimir el pais seleccionado
                    System.out.println("El pais seleccionado es: "+ paisArray[paisSeleccionado]);
                    selectorAbreviaturaPais = abreviacionArray[paisSeleccionado];
                    cambioRate = buscarCambio(selectorAbreviaturaPais);
                    System.out.println(" ");

                    //Calcula el cambio del dinero ingresado.
                    System.out.println("-------- INGRESE LA CANTIDAD DE DINERO A CAMBIAR --------");
                    cantidadDeCambio = lectura.nextInt();
                    operacionAritmetica = cantidadDeCambio * Double.parseDouble(cambioRate);
                    System.out.println("De Dolar a " + paisArray[paisSeleccionado] + " " + "es de: "+ operacionAritmetica);
                    break;

                case 2:
                    System.out.println(" ");
                    System.out.println(menuPaisSeleccionado);
                    paisSeleccionado = lectura.nextInt();
                    paisSeleccionado -= a;
                    System.out.println(" ");

                    //Imprimir el pais seleccionado
                    System.out.println(paisArray[paisSeleccionado]);
                    selectorAbreviaturaPais = abreviacionArray[paisSeleccionado];
                    cambioRate = buscarCambio(selectorAbreviaturaPais);

                    //Calcula el cambio del dinero ingresado.
                    System.out.println("-------- INGRESE LA CANTIDAD DE DINERO A CAMBIAR --------");
                    cantidadDeCambio = lectura.nextInt();
                    operacionAritmetica = cantidadDeCambio / Double.parseDouble(cambioRate);
                    System.out.println("De " + paisArray[paisSeleccionado] + " " + "a Dolar es de: " + operacionAritmetica);
                    break;
            }

        }
    }
}

/*
ARS: Argentina (864.75 ARS = 1 USD)
BRL: Brasil (5.1680 BRL = 1 USD)
COP: Colombia (3901.8504 COP = 1 USD)
CUP: Cuba (24.0000 CUP = 1 USD)
MXN: México (17.0172 MXN = 1 USD)
 */
