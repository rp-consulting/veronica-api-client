/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.veronica.cliente.model;

/**
 *
 * @author Xavi
 */
public class Token {
    
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;

    public String getAccessToken() { return access_token; }
    public void setAccessToken(String value) { this.access_token = value; }

    public String getTokenType() { return token_type; }
    public void setTokenType(String value) { this.token_type = value; }

    public String getRefreshToken() { return refresh_token; }
    public void setRefreshToken(String value) { this.refresh_token = value; }

    public long getExpiresIn() { return expires_in; }
    public void setExpiresIn(long value) { this.expires_in = value; }

    public String getScope() { return scope; }
    public void setScope(String value) { this.scope = value; }

    @Override
    public String toString() {
        return "Token{" + "access_token=" + access_token + ", token_type=" + token_type + ", refresh_token=" + refresh_token + ", expires_in=" + expires_in + ", scope=" + scope + '}';
    }

    public Token() {
    }

    public Token(String access_token, String token_type, String refresh_token, long expires_in, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
    }
    
   
    
}
