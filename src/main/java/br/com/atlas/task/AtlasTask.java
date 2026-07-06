package br.com.atlas.task;

/**
 * Interface base para todas as tarefas da ATLAS.
 */
public interface AtlasTask {
    void execute();
    String getSystemMessage();
}