package br.com.atlas.task.impl;

import br.com.atlas.task.AtlasTask;
import java.io.IOException;

/**
 * Classe responsável por executar a ação de abrir um aplicativo.
 * Ela "assina" o contrato AtlasTask usando a palavra-chave 'implements'.
 */
public class OpenAppTask implements AtlasTask {

    private final String applicationName;

    // Construtor: Pede o nome ou caminho do aplicativo que queremos abrir
    public OpenAppTask(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void execute() {
        try {
            // O ProcessBuilder é a ferramenta nativa do Java para rodar comandos no seu PC
            ProcessBuilder processBuilder = new ProcessBuilder(applicationName);
            processBuilder.start(); // Aqui o aplicativo é disparado

        } catch (IOException e) {
            System.err.println("[ATLAS-ERROR] Não foi possível abrir: " + applicationName);
            e.printStackTrace();
        }
    }

    @Override
    public String getSystemMessage() {
        return "Aplicativo '" + applicationName + " iniciado com sucesso, Senhor.";
    }
}