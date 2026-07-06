package ui;

import br.com.atlas.core.AtlasCore;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AtlasApplication extends Application {

    private final TextFlow consoleLog = new TextFlow();
    private final AtlasCore atlasCore = new AtlasCore();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        // ==========================================
        // 1. CABEÇALHO (TOP BAR) COM RELÓGIO
        // ==========================================
        HBox topBar = new HBox();
        topBar.getStyleClass().add("top-bar");
        topBar.setStyle("-fx-alignment: CENTER_LEFT;");

        Text title = new Text("ATLAS NEXT // SYSTEM ACTIVE");
        title.getStyleClass().add("hud-text");

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Text clockText = new Text();
        clockText.getStyleClass().add("hud-clock");

        Thread updateClock = new Thread(() -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            while (true) {
                String timeNow = LocalTime.now().format(formatter);
                Platform.runLater(() -> clockText.setText(timeNow));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        updateClock.setDaemon(true);
        updateClock.start();

        topBar.getChildren().addAll(title, topSpacer, clockText);

        // ==========================================
        // 2. BARRA LATERAL ESQUERDA (NAVEGAÇÃO)
        // ==========================================
        VBox sideBarLeft = new VBox(10);
        sideBarLeft.getStyleClass().add("sidebar-left");

        Text menuTitle = new Text("NAVEGAÇÃO");
        menuTitle.setStyle("-fx-font-family: 'Orbitron'; -fx-fill: #8BA1B0; -fx-font-size: 11px; -fx-font-weight: bold;");
        sideBarLeft.getChildren().add(menuTitle);

        Button btnDashboard = new Button("// Visão Geral");
        Button btnConversas = new Button("// Conversas");
        Button btnProjetos = new Button("// Projetos");
        Button btnConfig = new Button("// Configurações");

        btnDashboard.getStyleClass().add("nav-button");
        btnConversas.getStyleClass().add("nav-button");
        btnProjetos.getStyleClass().add("nav-button");
        btnConfig.getStyleClass().add("nav-button");

        sideBarLeft.getChildren().addAll(btnDashboard, btnConversas, btnProjetos, btnConfig);

        // ==========================================
        // 3. BARRA LATERAL DIREITA (SECURITY LOGS)
        // ==========================================
        VBox sideBarRight = new VBox(15);
        sideBarRight.getStyleClass().add("sidebar-right");

        Text securityTitle = new Text("SEGURANÇA E TELEMETRIA");
        securityTitle.setStyle("-fx-font-family: 'Orbitron'; -fx-fill: #8BA1B0; -fx-font-size: 11px; -fx-font-weight: bold;");
        sideBarRight.getChildren().add(securityTitle);

        VBox alertCard1 = new VBox(3);
        alertCard1.getStyleClass().add("alert-card");
        Text alertHeader1 = new Text("[!] PROTOCOLO SEGURANÇA");
        alertHeader1.getStyleClass().add("alert-header");
        Text alertBody1 = new Text("Firewall ativo. Monitorando requisições locais.");
        alertBody1.getStyleClass().add("alert-body");
        alertCard1.getChildren().addAll(alertHeader1, alertBody1);

        sideBarRight.getChildren().add(alertCard1);

        // ==========================================
        // 4. BARRA INFERIOR (STATUS BAR)
        // ==========================================
        HBox bottomBar = new HBox(20);
        bottomBar.getStyleClass().add("bottom-bar");
        bottomBar.setStyle("-fx-alignment: CENTER_LEFT;");

        Text lblSync = new Text("SINCRONIZAÇÃO:");
        lblSync.getStyleClass().add("status-label");
        Text statusSync = new Text("ONLINE");
        statusSync.getStyleClass().add("status-indicator");

        bottomBar.getChildren().addAll(lblSync, statusSync);

        // ==========================================
        // 5. ÁREA CENTRAL (DASHBOARD E INPUT DE COMANDOS)
        // ==========================================
        VBox centerArea = new VBox(15);
        centerArea.getStyleClass().add("main-dashboard");

        Text dashboardTitle = new Text("SISTEMA DE CONTROLE");
        dashboardTitle.setStyle("-fx-font-family: 'Orbitron'; -fx-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Cards de Telemetria Dinâmicos
        GridPane cardGrid = new GridPane();
        cardGrid.getStyleClass().add("dashboard-grid");

        VBox cardCore = new VBox(5);
        cardCore.getStyleClass().add("telemetry-card");
        Text titleCore = new Text("ATLAS CORE");
        titleCore.getStyleClass().add("card-title");
        Text valCore = new Text("0.1.0 // ATIVO");
        valCore.getStyleClass().add("card-value");
        cardCore.getChildren().addAll(titleCore, valCore);

        VBox cardTask = new VBox(5);
        cardTask.getStyleClass().add("telemetry-card");
        Text titleTask = new Text("APLICATIVOS ABERTOS HOJE");
        titleTask.getStyleClass().add("card-title");
        Text valTask = new Text("0");
        valTask.getStyleClass().add("card-value");
        cardTask.getChildren().addAll(titleTask, valTask);

        cardGrid.add(cardCore, 0, 0);
        cardGrid.add(cardTask, 1, 0);

        // Terminal de Mensagens Interno (ScrollPane)
        ScrollPane scrollConsole = new ScrollPane(consoleLog);
        scrollConsole.getStyleClass().add("message-log-area");
        scrollConsole.setFitToWidth(true);
        scrollConsole.setPrefHeight(250);
        scrollConsole.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Mensagem de Inicialização da ATLAS no HUD
        // ==========================================
        // SAUDAÇÃO INICIAL DINÂMICA BASEADA NO HORÁRIO
        // ==========================================
        LocalTime agoraMensagem = LocalTime.now();
        int horaMensagem = agoraMensagem.getHour();
        String hourString = agoraMensagem.format(DateTimeFormatter.ofPattern("HH:mm"));

        if (horaMensagem >= 5 && horaMensagem <= 9) {
            adicionarLog("ATLAS: Bom dia senhor!\n", "#00BFFF");
            adicionarLog("ATLAS: Tão cedo senhor?\n", "#00BFFF");
            adicionarLog("ATLAS: Agora são " + hourString + "\n\n", "#00BFFF");
        } else if (horaMensagem >= 9 && horaMensagem <= 12) {
            adicionarLog("ATLAS: Bom dia senhor!\n", "#00BFFF");
            adicionarLog("ATLAS: Bora pra mais uma senhor!\n", "#00BFFF");
            adicionarLog("ATLAS: Agora são " + hourString + "\n\n", "#00BFFF");
        } else if (horaMensagem >= 12 && horaMensagem <= 17) {
            adicionarLog("ATLAS: Boa tarde senhor!!\n", "#00BFFF");
            adicionarLog("ATLAS: Já tomou aquele cafézinho de cada dia senhor?\n", "#00BFFF");
            adicionarLog("ATLAS: Agora são " + hourString + "\n\n", "#00BFFF");
        } else if (horaMensagem >= 18 && horaMensagem <= 23 || horaMensagem >= 0 && horaMensagem < 5) {
            adicionarLog("ATLAS: Boa noite senhor!\n", "#00BFFF");
            adicionarLog("ATLAS: Já são " + hourString + "!\n", "#00BFFF");
            adicionarLog("ATLAS: Preparado senhor?\n\n", "#00BFFF");
        }

        // Campo de Entrada de Comandos (Onde o Senhor vai conversar)
        TextField inputComando = new TextField();
        inputComando.getStyleClass().add("hud-input-field");
        inputComando.setPromptText("Digite o nome do aplicativo para abrir (Ex: firefox)...");
        HBox.setHgrow(inputComando, Priority.ALWAYS);

        Button btnEnviar = new Button("// EXECUTAR");
        btnEnviar.getStyleClass().add("nav-button");
        btnEnviar.setStyle("-fx-border-color: rgba(0, 191, 255, 0.4); -fx-padding: 8px 20px;");

        // LÓGICA DE EVENTO: Acionada ao pressionar ENTER ou clicar no botão
        inputComando.setOnAction(e -> processarComando(inputComando, valTask));
        btnEnviar.setOnAction(e -> processarComando(inputComando, valTask));

        HBox inputControlBox = new HBox(10);
        inputControlBox.getChildren().addAll(inputComando, btnEnviar);

        centerArea.getChildren().addAll(dashboardTitle, cardGrid, scrollConsole, inputControlBox);

        // Acoplamento
        root.setTop(topBar);
        root.setLeft(sideBarLeft);
        root.setCenter(centerArea);
        root.setRight(sideBarRight);
        root.setBottom(bottomBar);

        Scene scene = new Scene(root, 1280, 720);
        String css = this.getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("ATLAS NEXT - Painel de Controlo HUD");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método Auxiliar para processar o aplicativo digitado na tela
    private void processarComando(TextField input, Text txtContador) {
        String appName = input.getText().trim();
        if (appName.isEmpty()) {
            adicionarLog("ATLAS: Digite um comando antes de executar.\n", "#FF4500");
            return;
        }
        try {
            String resposta = atlasCore.executarComando(appName);

            adicionarLog("ATLAS: " + resposta + "\n", "#32CD32");

            txtContador.setText(
                    String.valueOf(atlasCore.getQuantidadeAppsAbertos()));

        } catch (Exception ex) {
            adicionarLog("ATLAS: Erro crítico ao tentar iniciar o recurso.\n", "#FF4500");
        }

        input.clear();
    }

    // Método para escrever linhas coloridas personalizadas na consola HUD
    private void adicionarLog(String texto, String corHex) {
        Text t = new Text(texto);
        t.setStyle("-fx-fill: " + corHex + "; -fx-font-family: 'Consolas', monospace; -fx-font-size: 13px;");
        consoleLog.getChildren().add(t);
    }
}