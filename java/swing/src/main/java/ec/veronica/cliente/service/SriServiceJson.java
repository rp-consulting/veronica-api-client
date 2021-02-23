/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.veronica.cliente.service;

 
import com.google.gson.Gson;
import ec.veronica.cliente.model.Status;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 *
 * @author Xavi
 */
public class SriServiceJson {
    public static String CLAVE_ACCESO = "";
    private final Gson gson;

    public SriServiceJson() {
        this.gson = new Gson();
    }
    
    public boolean crearSRIXml (String factura) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-dev.veronica.ec/api/v1.0/comprobantes"))
                .setHeader("Content-type", "application/atom+xml")
                .setHeader("Authorization", "Bearer "  + Login.TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(factura))
                .build();
        
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.body());
        Status status  = gson.fromJson(response.body(), Status.class);
        if (status.getSuccess()){
            SriServiceJson.CLAVE_ACCESO = status.getResult().getClaveAcceso();
        }
      return status.getSuccess() ; 
    }
    
    public boolean crearSRI (String factura) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-dev.veronica.ec/api/v1.0/comprobantes/facturas"))
                .setHeader("Content-type", "application/json")
                .setHeader("Authorization", "Bearer "  + Login.TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(factura))
                .build();
        
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.body());
        Status status  = gson.fromJson(response.body(), Status.class);
        if (status.getSuccess()){
            CLAVE_ACCESO = status.getResult().getClaveAcceso();
        }
      return status.getSuccess() ; 
    }
    
        public boolean enviarSRI () throws IOException, InterruptedException{
         if(CLAVE_ACCESO.equals("")){
             return false;
         }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-dev.veronica.ec/api/v1.0/comprobantes/"+CLAVE_ACCESO+"/enviar"))
                .setHeader("Content-type", "application/json")
                .setHeader("Authorization", "Bearer "  + Login.TOKEN)
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();
        
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.body());
        System.out.println(response.statusCode());

      return response.statusCode() == 200 ; 
    }
        
     public boolean autorizarSRI () throws IOException, InterruptedException{
         if(CLAVE_ACCESO.equals("")){
             return false;
         }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-dev.veronica.ec/api/v1.0/comprobantes/"+CLAVE_ACCESO+"/autorizar"))
                .setHeader("Content-type", "application/json")
                .setHeader("Authorization", "Bearer "  + Login.TOKEN)
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();
        
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.body());
        System.out.println(response.statusCode());

      return response.statusCode() == 200 ; 
    }
}
