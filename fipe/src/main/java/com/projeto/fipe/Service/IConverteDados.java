package com.projeto.fipe.Service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDado(String json, Class<T> classe);
    <T> List<T> obterLista(String json, Class<T> classe);
}
