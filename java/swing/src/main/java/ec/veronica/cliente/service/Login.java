/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.veronica.cliente.service;
import com.google.gson.Gson;
import ec.veronica.cliente.model.Token;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;


 
/**
 *
 * @author Xavi
 */
public class Login {
    public static String TOKEN = "";
    private final Gson gson;
    private Token token;
    
    public Login () throws IOException{
        this.gson = new Gson();
        this.token = new Token();
        this.loginVeronica();
    }
    
    public void loginVeronica () throws IOException  {
        
        String name = "AQUI_PONER_CLIENTE_ID";
        String password = "AQUI_PONER_CLIENTE_SECRET";

        String authString = name + ":" + password;
        System.out.println("auth string: " + authString);
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        System.out.println("Base64 encoded auth string: " + authStringEnc);
        
        //Creating CloseableHttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating the RequestBuilder object
        RequestBuilder reqbuilder = RequestBuilder.post();

        //Setting URI and parameters
        RequestBuilder reqbuilder1 = reqbuilder.setUri("https://api-dev.veronica.ec/api/v1.0/oauth/token?grant_type=password&username=USER_NAME&password=USER_PASSWORD");
        RequestBuilder reqbuilder2 = reqbuilder1.addHeader("Authorization", "Basic " + authStringEnc);

        //Building the HttpUriRequest object
        HttpUriRequest httppost = reqbuilder2.build();

        //Executing the request
        HttpResponse httpresponse = httpclient.execute(httppost);

        //Printing the status and the contents of the response
        String response = EntityUtils.toString(httpresponse.getEntity());
        System.out.println(response);
        System.out.println(httpresponse.getStatusLine());
        
        //Comvert data in object
         this.token = this.gson.fromJson(response, Token.class); 
     
        //Print object
        System.out.print(this.token.toString());
        
        //Tocken in local save
        TOKEN = this.token.getAccessToken();
    }
    
}
