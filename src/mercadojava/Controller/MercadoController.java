package mercadojava.Controller;

import mercadojava.Exception.FilaEstaVaziaException;
import mercadojava.Exception.FilaJaExisteExcepetion;
import mercadojava.Exception.FilaNaoExisteException;
import mercadojava.Exception.FilaNaoPodeSerExcluidaException;
import mercadojava.Exception.PessoaJaEstaEmUmaFilaException;
import mercadojava.Exception.PessoaJaExisteException;
import mercadojava.Exception.PessoaNaoEncontradaException;
import mercadojava.Model.Fila;
import mercadojava.Model.Pessoa;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

public class MercadoController {

    public LinkedList<Pessoa> ListaPessoas;
    public LinkedList<Fila> ListaFilas;

    public MercadoController() {
        ListaPessoas = new LinkedList<>();
        ListaFilas = new LinkedList<>();
    }

    public boolean criarPessoa(String nome, String cpf, String rg, Date data) throws PessoaJaExisteException {
        Pessoa novaP = new Pessoa(nome, cpf, rg, data);
        if (ListaPessoas.contains(novaP)) {
            throw new PessoaJaExisteException("Pessoa já existente no sistema!");
        }
        ListaPessoas.add(novaP);
        return true;
    }

    public boolean criarFila(int id) throws FilaJaExisteExcepetion {
        Fila novaF = new Fila(id);
        if (ListaFilas.contains(novaF)) {
            throw new FilaJaExisteExcepetion("Fila com id: " + id + "já existente no sistema!");
        }
        ListaFilas.add(novaF);
        return true;
    }

    public Fila inserirPessoa(String cpf) throws PessoaNaoEncontradaException, FilaNaoExisteException, PessoaJaEstaEmUmaFilaException {
        Pessoa pessoaPesq = null;

        if (ListaFilas.size() < 1) {
            throw new FilaNaoExisteException("Não existe fila cadastrada no sistema");
        }

        for (int i = 0; i < ListaPessoas.size(); i++) {
            if (ListaPessoas.get(i).getCpf().equals(cpf)) {
                if (ListaPessoas.get(i).getAlocada() == false) {
                    pessoaPesq = ListaPessoas.get(i);
                    ListaPessoas.get(i).setAlocada(true);
                } else {
                    throw new PessoaJaEstaEmUmaFilaException("A pessoa com cpf: " + cpf + " já está locada em uma fila!");
                }
            }
        }

        if (pessoaPesq == null) {
            throw new PessoaNaoEncontradaException("Não existe pessoa cadastrada com esse cpf: " + cpf + "!");
        }

        Collections.sort(ListaFilas);
        Fila filaAux = ListaFilas.get(0);
        filaAux.addPessoas(pessoaPesq);

        return filaAux;
    }

    public Fila removerPessoaFila(String cpf) throws PessoaNaoEncontradaException,FilaNaoExisteException {
        
        if(ListaFilas.isEmpty()){
            throw new FilaNaoExisteException("Não há filas cadastradas no sistema!");
        }
        
        Pessoa pessoaPesq = null;
        Pessoa pessoaAux = null;
        Fila filaAux = null;
        String cpfAux = "";

        for (int i = 0; i < ListaPessoas.size(); i++) {
            if (ListaPessoas.get(i).getCpf().equals(cpf)) {
                pessoaPesq = ListaPessoas.get(i);
                ListaPessoas.get(i).setAlocada(true);
            }
        }
        if (pessoaPesq == null) {
            throw new PessoaNaoEncontradaException("Não existe pessoa cadastrada com esse cpf: " + cpf + "!");
        } else if (pessoaPesq.getAlocada() == false) {
            throw new PessoaNaoEncontradaException("Esta pessoa não se encontra em uma fila de atendimento!");
        } else {
            for (int i = 0; i < ListaFilas.size(); i++) {
                cpfAux = ListaFilas.get(i).setCpfAux(cpf);
                if (ListaFilas.get(i).getCpfAux().equals(cpfAux)) {
                    filaAux = ListaFilas.get(i);
                    pessoaAux = ListaFilas.get(i).buscarPessoaAleatoria(cpf);
                    ListaFilas.get(i).removerPessoaAleatoria(pessoaAux);
                    ListaPessoas.remove(pessoaAux);
                }
            }
        }
        return filaAux;
    }

    public boolean encerrarFila(int id) throws FilaNaoExisteException, FilaNaoPodeSerExcluidaException {
        Pessoa pessoaAux = null;
        Fila filaAux = null;

        if (ListaFilas.size() < 1) {
            throw new FilaNaoExisteException("Não há filas cadastradas no sistema!");
        }

        for (int i = 0; i < ListaFilas.size(); i++) {
            if (ListaFilas.get(i).getIdFila() == id) {
                if (ListaFilas.size() == 1 && ListaFilas.get(i).quantidadePessoas() > 0) {
                    throw new FilaNaoPodeSerExcluidaException("ERRO! Há somente uma fila em funcionanmento no momento!");
                }
                if (ListaFilas.size() > 1 && ListaFilas.get(i).quantidadePessoas() > 0) {
                    System.out.println("Há pessoas inseridas na fila no momento! Realocando pessoas");
                    filaAux = ListaFilas.get(i);
                    int tamanhoFila = filaAux.quantidadePessoas();
                    ListaFilas.remove(filaAux);

                    for (int j = 0; j < tamanhoFila; j++) {

                        pessoaAux = filaAux.getPessoaPosicao(j);
                        Collections.sort(ListaFilas);
                        Fila filaAux2 = ListaFilas.get(0);
                        filaAux2.addPessoas(pessoaAux);
                    }
                    break;
                }
                if (ListaFilas.size() >= 1 && ListaFilas.get(i).quantidadePessoas() == 0) {
                    filaAux = ListaFilas.get(i);
                    ListaFilas.remove(filaAux);
                    break;
                }
            }
        }

        if (filaAux == null) {
            throw new FilaNaoExisteException("Fila com id: " + id + " não existe no sistema!");
        }

        return true;
    }

    public Fila buscarFila(int id) throws FilaNaoExisteException {
        
        if(ListaFilas.isEmpty()){
            throw new FilaNaoExisteException("Não há fila cadastrada no sistema!");
        }
        
        Fila filaAux = null;
        for (int i = 0; i < ListaFilas.size(); i++) {
            if (ListaFilas.get(i).getIdFila() == id) {
                filaAux = ListaFilas.get(i);
            }
        }
        if (filaAux == null) {
            throw new FilaNaoExisteException("Fila não cadastrada no sistema!");
        }
        return filaAux;
    }

    public String situacaoTodasAsFilas() throws FilaNaoExisteException {
        if (ListaFilas.size() < 1) {
            throw new FilaNaoExisteException("Não há fila cadastrada no sistema!");
        }

        String dadosDasFilas = "";
        Collections.sort(ListaFilas);
        for (int i = 0; i < ListaFilas.size(); i++) {
            dadosDasFilas += ListaFilas.get(i).toString() + "\n" + "\n";
        }

        return dadosDasFilas;
    }

    public int totalFilas() throws FilaNaoExisteException {
        if (ListaFilas.size() < 1) {
            throw new FilaNaoExisteException("Não há fila cadastrada no sistema!");
        } else {
            return ListaFilas.size();
        }
    }

    public int totalPessoaEmFilas() throws FilaNaoExisteException {
        int cont = 0;

        if (ListaFilas.size() < 1) {
            throw new FilaNaoExisteException("Fila não cadastrada no sistema!");
        } else {
            for (int i = 0; i < ListaFilas.size(); i++) {
                cont += ListaFilas.get(i).quantidadePessoas();
            }
        }
        return cont;
    }

    public boolean finalizarAtendimentoFila(int id) throws FilaNaoExisteException, FilaEstaVaziaException {
        if (ListaFilas.size() < 1) {
            throw new FilaNaoExisteException("Não há filas cadastradas no sistema!");
        }

        Fila filaAux = null;
        for (int i = 0; i < ListaFilas.size(); i++) {
            if (ListaFilas.get(i).getIdFila() == id) {
                filaAux = ListaFilas.get(i);
            }
        }
        if (filaAux == null) {
            throw new FilaNaoExisteException("Fila com id: " + id + " não existe no sistema!");
        } else if (filaAux.quantidadePessoas() < 1) {
            throw new FilaEstaVaziaException("ERRO! Não há pessoas aguardadando atendimento nesta fila!");
        } else {
            for (int i = 0; i < ListaFilas.size(); i++) {
                if (ListaFilas.get(i).equals(filaAux)) {
                    Pessoa pessoaAux = ListaFilas.get(i).removerPessoaDaFila();
                    ListaPessoas.remove(pessoaAux);
                }
            }
            return true;
        }
    }

    public void exibePessoas()throws PessoaNaoEncontradaException{
        if (ListaPessoas.isEmpty()) {
            throw new PessoaNaoEncontradaException("Não exite pessoas cadastradas");
        }
        for (int i = 0; i < ListaPessoas.size(); i++) {
            if(ListaPessoas.get(i).getAlocada() == false){
            System.out.println(ListaPessoas.get(i).toString());
            }
        }
    }
}
