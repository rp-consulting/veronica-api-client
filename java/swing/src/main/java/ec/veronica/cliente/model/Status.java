/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.veronica.cliente.model;

/**
 *
 * @author PC GAMER
 */
public class Status {
    
    private boolean success;
    private Result result;

    public boolean getSuccess() { return success; }
    public void setSuccess(boolean value) { this.success = value; }

    public Result getResult() { return result; }
    public void setResult(Result value) { this.result = value; }

    public Status() {
    }

    @Override
    public String toString() {
        return "Status{" + "success=" + success + ", result=" + result.getClaveAcceso() + '}';
    }
    
}
