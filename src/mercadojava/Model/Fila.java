package mercadojava.Model;

import java.util.LinkedList;

public class Fila implements Comparable<Fila> {

    private LinkedList<Pessoa> filaPessoas;
    private int idFila;
    private String cpfAux = "";

    public Fila(int idFila) {
        filaPessoas = new LinkedList<>();
        this.idFila = idFila;
    }

    public int quantidadePessoas() {
        return filaPessoas.size();
    }

    public int getIdFila() {
        return idFila;
    }

    public void setIdFila(int idFila) {
        this.idFila = idFila;
    }

    public String getCpfAux() {
        return cpfAux;
    }

    public String setCpfAux(String cpf) {
        for (int i = 0; i < filaPessoas.size(); i++) {
            if (filaPessoas.get(i).getCpf().equals(cpf)) {
                this.cpfAux = filaPessoas.get(i).getCpf();
            }
        }
        return this.cpfAux;
    }

    public void addPessoas(Pessoa pessoaNova) {
        filaPessoas.add(pessoaNova);
    }

    public Pessoa getPessoaPosicao(int pos) {
        Pessoa pessoaAux = null;
        pessoaAux = filaPessoas.get(pos);
        return pessoaAux;
    }

    public Pessoa removerPessoaDaFila() {
        return filaPessoas.remove();
    }

    public Pessoa buscarPessoaAleatoria(String cpf) {
        Pessoa pessoaAux = null;
        for (int i = 0; i < filaPessoas.size(); i++) {
            if (filaPessoas.get(i).getCpf().equals(cpf)) {
                pessoaAux = filaPessoas.get(i);
            }
        }
        return pessoaAux;
    }

    public boolean removerPessoaAleatoria(Pessoa pessoa) {
        return filaPessoas.remove(pessoa);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fila other = (Fila) obj;
        if (this.idFila != other.idFila) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Fila f) {
        if (this.quantidadePessoas() < f.quantidadePessoas()) {
            return -1;
        } else if (this.quantidadePessoas() == f.quantidadePessoas()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        String fila = "ID da Fila: " + idFila + ", Quantidade de Pessoas na fila: " + filaPessoas.size();
        String pessoas = "";
        if (filaPessoas.size() < 1) {
            return fila;
        } else {
            String listaP = filaPessoas.toString();
            pessoas += listaP;
        }
        return fila + "\n" + "\t" + pessoas;
    }
}
