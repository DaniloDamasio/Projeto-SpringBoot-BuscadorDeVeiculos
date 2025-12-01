package com.projeto.fipe.Main;

import com.projeto.fipe.Model.DadosMarcas;
import com.projeto.fipe.Model.DadosVeiculos;
import com.projeto.fipe.Model.Veiculo;
import com.projeto.fipe.Service.BuscaDeVeiculo;
import com.projeto.fipe.Service.ConsumoAPI;
import com.projeto.fipe.Service.ConverteDados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    ConsumoAPI consumoAPI = new ConsumoAPI();
    BuscaDeVeiculo buscaDeVeiculo = new BuscaDeVeiculo();
    ConverteDados conversor = new ConverteDados();
    Scanner input = new Scanner(System.in);

    public void exibeMenu(){
        var menu = """
                -----------------------
                        Opções
                * Carros
                * Motos
                * Caminhões
                -----------------------
                Escolha uma opção!""";

        System.out.println(menu);
        var escolhaVeiculo = input.nextLine();
        var enderecoVeiculo = buscaDeVeiculo.GeracaoDeLinkVeiculo(escolhaVeiculo);
        var jsonVeiculo = consumoAPI.BuscaAPI(enderecoVeiculo);
        var veiculos = conversor.obterLista(jsonVeiculo, DadosVeiculos.class);
        veiculos.stream()
                .sorted(Comparator.comparing(DadosVeiculos::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código ou nome da marca para consulta:");
        var escolhaMarca = input.nextLine();
        var enderecoMarca = buscaDeVeiculo.GeracaoDeLinkMarca(enderecoVeiculo,escolhaMarca);
        var jsonMarca = consumoAPI.BuscaAPI(enderecoMarca);
        var marcas = conversor.obterDado(jsonMarca, DadosMarcas.class);
        System.out.println("Modelos da marca:");
        marcas.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculos::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o nome do modelo que deseja buscar:");
        var escolhaModelo = input.nextLine();
        List<DadosVeiculos> modelosFiltrados = marcas.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(escolhaModelo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Modelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite agora o código do modelo:");
        var escolhaModeloFiltrado = input.nextLine();

        var enderecoModelos = buscaDeVeiculo.GeracaoDeLinkModelo(enderecoMarca,escolhaModeloFiltrado);

        jsonVeiculo = consumoAPI.BuscaAPI(enderecoModelos);
        List<DadosVeiculos> anos = conversor.obterLista(jsonVeiculo, DadosVeiculos.class);
        List<Veiculo> veiculoList = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = enderecoModelos+"/"+anos.get(i).codigo();
            jsonVeiculo = consumoAPI.BuscaAPI(enderecoAnos);
            Veiculo veiculo = conversor.obterDado(jsonVeiculo, Veiculo.class);
            veiculoList.add(veiculo);
        }

        System.out.println("Todos os veiculos do modelo filtrado por ano!");
        veiculoList.forEach(System.out::println);

    }
}
