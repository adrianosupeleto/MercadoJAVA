package mercadojava.Model;

import java.util.Date;
import java.util.Objects;

public class Pessoa {

    private String nome;
    private String cpf;
    private String rg;
    private Date data;
    private boolean alocada;

    public Pessoa(String nome, String cpf, String rg, Date data) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.data = data;
        this.alocada = false;
    }

    public boolean getAlocada() {
        return alocada;
    }

    public void setAlocada(boolean alocada) {
        this.alocada = alocada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", CPF: " + cpf + ", RG: " + rg + ", Data de Nacimento: " + data + "\n" + "\t";
    }
}
