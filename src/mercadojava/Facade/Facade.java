package mercadojava.Facade;

import mercadojava.Controller.MercadoController;
import mercadojava.Exception.FilaEstaVaziaException;
import mercadojava.Exception.FilaJaExisteExcepetion;
import mercadojava.Exception.FilaNaoExisteException;
import mercadojava.Exception.FilaNaoPodeSerExcluidaException;
import mercadojava.Exception.PessoaJaEstaEmUmaFilaException;
import mercadojava.Exception.PessoaJaExisteException;
import mercadojava.Exception.PessoaNaoEncontradaException;
import mercadojava.Model.Fila;
import mercadojava.Model.Pessoa;
import java.util.Date;

/**
 *
 * @author kayos
 */
public class Facade {

    MercadoController c = new MercadoController();

    public Facade() {
    }

    public boolean criarPessoa(String nome, String cpf, String rg, Date data) throws PessoaJaExisteException {
        return c.criarPessoa(nome, cpf, rg, data);
    }

    public boolean criarFila(int id) throws FilaJaExisteExcepetion {
        return c.criarFila(id);
    }

    public Fila removerPessoaDaFila(String cpf) throws PessoaNaoEncontradaException,FilaNaoExisteException {
        return c.removerPessoaFila(cpf);
    }

    public Fila inserirPessoaEmFila(String cpf) throws PessoaNaoEncontradaException, FilaNaoExisteException, PessoaJaEstaEmUmaFilaException {
        return c.inserirPessoa(cpf);
    }

    public boolean finalizarAtendimentoDeFila(int id) throws FilaNaoExisteException, FilaEstaVaziaException {
        return c.finalizarAtendimentoFila(id);
    }

    public boolean encerrarFila(int id) throws FilaNaoExisteException, FilaNaoPodeSerExcluidaException {
        return c.encerrarFila(id);
    }

    public Fila buscarFila(int id) throws FilaNaoExisteException {
        return c.buscarFila(id);
    }

    public String verificarTodasAsFilas() throws FilaNaoExisteException {
        return c.situacaoTodasAsFilas();
    }

    public int totalFilas() throws FilaNaoExisteException {
        return c.totalFilas();
    }

    public int totalPessoasAguardandoAtendimento() throws FilaNaoExisteException {
        return c.totalPessoaEmFilas();
    }

    public void exibirPessoas() throws PessoaNaoEncontradaException {
        c.exibePessoas();
    }
}
