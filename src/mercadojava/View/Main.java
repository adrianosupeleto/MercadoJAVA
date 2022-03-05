/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mercadojava.View;

import mercadojava.Controller.MercadoController;
import mercadojava.Exception.FilaEstaVaziaException;
import mercadojava.Exception.FilaJaExisteExcepetion;
import mercadojava.Exception.FilaNaoExisteException;
import mercadojava.Exception.FilaNaoPodeSerExcluidaException;
import mercadojava.Exception.PessoaJaEstaEmUmaFilaException;
import mercadojava.Exception.PessoaJaExisteException;
import mercadojava.Exception.PessoaNaoEncontradaException;
import mercadojava.Facade.Facade;
import mercadojava.Model.Fila;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        Date date;
        Fila filaAux;
        int totalFilas = 0;
        int totalPessoasSendoAtendidas = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String nome = "", cpf = "", rg = "", data = "";
        String escolha = "";
        String fila = "";
        Facade fac = new Facade();
        do {
            System.out.println("*Digite [1] para cadastrar uma nova pessoa");
            System.out.println("*Digite [2] para inserir uma pessoa em uma fila");
            System.out.println("*Digite [3] para remover uma pessoa de uma fila (desistência)");
            System.out.println("*Digite [4] para finalizar o antendimento em uma fila");
            System.out.println("*Digite [5] para cadastrar uma nova fila");
            System.out.println("*Digite [6] para encerrar uma fila");
            System.out.println("*Digite [7] para consultar uma fila");
            System.out.println("*Digite [8] para consultar todas as filas ordenadas em forma crescente!");
            System.out.println("*Digite [9] para consultar o total de filas em funcionamento e quantas pessoas ainda precisam ser atendidas");
            System.out.println("*Digite [10] para consultar as pessoas aguardando fila disponível");
            System.out.println("*Digite [0] para sair");

            System.out.println("Escolha: ");
            escolha = s.nextLine();
            switch (escolha) {
                case "1":
                    System.out.println("Digite seu nome: ");
                    nome = s.nextLine();
                  
                    System.out.println("Digite seu cpf: ");
                    cpf = s.nextLine();
                    
                    System.out.println("Digite seu rg: ");
                    rg = s.nextLine();
                   
                    System.out.println("Digite sua data de nascimento(dd/mm/aaaa): ");
                    data = s.nextLine();
                    try {
                        date = dateFormat.parse(data);
                        fac.criarPessoa(nome, cpf, rg, date);
                        System.out.println("A pessoa foi cadastrada com sucesso!");
                    } catch (ParseException ex) {
                        System.out.println("Você digitou uma data com formatação inválida!");
                    } catch (PessoaJaExisteException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("Digite o CPF para inserir uma pessoa na fila: ");
                    cpf = s.nextLine();
                    try {
                        filaAux = fac.inserirPessoaEmFila(cpf);
                        System.out.println("Pessoa foi inserida na fila: " + filaAux.getIdFila());
                    } catch (PessoaNaoEncontradaException ex) {
                        System.out.println(ex.getMessage());
                    } catch (FilaNaoExisteException ex) {
                        System.out.println(ex.getMessage());
                    } catch (PessoaJaEstaEmUmaFilaException ex) {
                        System.out.println(ex.getMessage());
                    }

                    break;
                case "3":
                    System.out.println("Digite o CPF para remover uma pessoa da sua fila atual: ");
                    cpf = s.nextLine();
                    try {
                        filaAux = fac.removerPessoaDaFila(cpf);
                        System.out.println("Pessoa[cpf]: " + cpf + " foi removida da fila: " + filaAux.getIdFila());
                    } catch (PessoaNaoEncontradaException ex) {
                        System.out.println(ex.getMessage());
                    }
                    catch (FilaNaoExisteException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "4":
                    System.out.println("Digite a fila que deseja finalizar um atendimento: ");
                    fila = s.nextLine();
                    try {
                        fac.finalizarAtendimentoDeFila(Integer.parseInt(fila));
                    } catch (FilaNaoExisteException ex) {
                        System.out.println(ex.getMessage());
                    } catch (FilaEstaVaziaException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "5":
                    System.out.println("Digite um valor inteiro para a fila que será cadastrada: ");
                    fila = s.nextLine();
                    try {
                        fac.criarFila(Integer.parseInt(fila));
                        System.out.println("A fila foi cadastrada com sucesso!");
                    } catch (FilaJaExisteExcepetion ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "6":
                    System.out.println("Digite a fila que você deseja encerrar: ");
                    fila = s.nextLine();
                    try {
                        fac.encerrarFila(Integer.parseInt(fila));
                    } catch (FilaNaoExisteException ex) {
                        System.out.println(ex.getMessage());
                    } catch (FilaNaoPodeSerExcluidaException ex) {
                        System.out.println(ex.getMessage());
                    }

                    break;
                case "7":
                    System.out.println("Digite a fila que você deseja consultar: ");
                    fila = s.nextLine();
                    try {
                        filaAux = fac.buscarFila(Integer.parseInt(fila));
                        System.out.println(filaAux.toString());
                    } catch (FilaNaoExisteException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "8":
                    try {
                        System.out.println(fac.verificarTodasAsFilas());
                    } catch (FilaNaoExisteException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "9":
                    try {
                        totalFilas = fac.totalFilas();
                        totalPessoasSendoAtendidas = fac.totalPessoasAguardandoAtendimento();
                        System.out.println("No momento existem " + totalFilas + " funcionando e " + totalPessoasSendoAtendidas
                        + " aguardando atendimento!");
                    } catch (FilaNaoExisteException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "10":
                    try{   
                        fac.exibirPessoas();
                    }catch (PessoaNaoEncontradaException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "0":
                    System.out.println("Sistema Encerrado!");
                    break;
                default:
                    System.out.println("Escolha um valor válido!");
                    break;
            }
            System.out.println("Digite enter para continuar!");
            s.nextLine();
        } while (!escolha.equals("0"));
    }

}
