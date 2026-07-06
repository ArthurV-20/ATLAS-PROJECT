package br.com.atlas.core;
import br.com.atlas.task.AtlasTask;
import br.com.atlas.task.impl.OpenAppTask;
import java.util.ArrayList;
import java.util.List;

public class AtlasCore {
    private final List<String> appsAbertos = new ArrayList<>();

    public String executarComando(String comando) throws Exception {
        if (comando == null || comando.isBlank()) {
            throw new IllegalArgumentException("O comando não pode estar vazio, senhor.");
        }
        AtlasTask task = new OpenAppTask(comando);

        task.execute();

        appsAbertos.add(comando);

        return task.getSystemMessage();
    }
    public int getQuantidadeAppsAbertos() {
        return appsAbertos.size();
    }
}

