/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mercadojava.Exception;

/**
 *
 * @author kayos
 */
public class PessoaJaExisteException extends Exception{
    public PessoaJaExisteException(String errorMessage) {
        super(errorMessage);
    }
}
