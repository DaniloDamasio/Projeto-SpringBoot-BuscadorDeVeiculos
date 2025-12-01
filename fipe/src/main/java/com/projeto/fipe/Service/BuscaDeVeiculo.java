package com.projeto.fipe.Service;

public class BuscaDeVeiculo {

    public String GeracaoDeLinkVeiculo(String opcao){
        String urlBase = "https://parallelum.com.br/fipe/api/v1/";
        String enderecoVeiculo;


        if(opcao.toLowerCase().contains("carro")){
            enderecoVeiculo = urlBase+"carros/marcas/";
        } else if (opcao.toLowerCase().contains("motos")) {
            enderecoVeiculo = urlBase+"motos/marcas/";
        } else {
            enderecoVeiculo = urlBase+"caminhoes/marcas/";
        }
        return enderecoVeiculo;
    }

    public String GeracaoDeLinkMarca(String enderecoVeiculo, String escolhaMarca){
        String urlBase = "/modelos/";
        String enderecoMarca = enderecoVeiculo+escolhaMarca+urlBase;
        return enderecoMarca;
    }

    public String GeracaoDeLinkModelo(String enderecoMarca, String codigoModelo){
        String enderecoModelo = enderecoMarca+codigoModelo+"/anos";
        return enderecoModelo;
    }
}
