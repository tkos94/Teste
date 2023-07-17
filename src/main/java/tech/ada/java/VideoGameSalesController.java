package tech.ada.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class VideoGameSalesController {

    private HashMap<GameKey, VideoGameSale> games = new HashMap<>();
    private HashMap<String, Set<String>> platformsByGame = new HashMap<>();
    private Scanner userInput = new Scanner(System.in);

    public void run() {
        // Ler o arquivo CSV e criar um mapa com os dados.
        File file = new File("src/main/resources/vgsales.csv");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Pular a linha do cabeçalho.
        String header = scanner.nextLine();

        // Iterar sobre as linhas do arquivo.
        while (scanner.hasNextLine()) {
            // Obter os dados da linha.
            String[] data = scanner.nextLine().split(";");

            // Criar um novo objeto VideoGameSale.
            VideoGameSale game = new VideoGameSale();
            game.rank = Integer.parseInt(data[0]);
            game.name = data[1].replace("\"", "");
            game.platform = data[2];
            game.year = data[3];
            game.genre = data[4];
            game.publisher = data[5];
            game.naSales = Double.parseDouble(data[6].replace(",", ""));
            game.euSales = Double.parseDouble(data[7].replace(",", ""));
            game.jpSales = Double.parseDouble(data[8].replace(",", ""));
            game.otherSales = Double.parseDouble(data[9].replace(",", ""));
            game.globalSales = Double.parseDouble(data[10].replace(",", ""));

            // Criar um novo objeto GameKey.
            GameKey gameKey = new GameKey(game.name, game.platform);

            // Adicionar o VideoGameSale ao mapa usando o GameKey como chave.
            games.put(gameKey, game);

            // Armazenar as plataformas para cada jogo no mapa platformsByGame.
            if (!platformsByGame.containsKey(game.name)) {
                platformsByGame.put(game.name, new HashSet<>());
            }
            platformsByGame.get(game.name).add(game.platform);
        }

        // Imprimir o menu.
        System.out.println("Bem-vindo ao aplicativo de vendas de jogos de vídeo.");
        System.out.println("O que você gostaria de fazer?");
        System.out.println("1. Ver as vendas por região e jogo.");
        System.out.println("2. Ver as vendas de um jogo informando a plataforma e a região específica.");
        System.out.println("3. Sair.");
        System.out.print("Por favor, informe sua seleção: ");

        // Obter a seleção do usuário.
        int userSelection = userInput.nextInt();
        userInput.nextLine(); // Consumir o caractere de nova linha deixado pelo nextInt()

        do {
            switch (userSelection) {
                case 1:
                    // Ver as vendas por região e jogo.
                    viewSalesByRegionAndGame();
                    break;
                case 2:
                    // Procurar por um jogo em uma região específica.
                    searchForGame();
                    break;
                case 3:
                    // Sair do aplicativo.
                    exit();
                    break;
                default:
                    // Seleção inválida.
                    System.out.println("Seleção inválida. Por favor, tente novamente.");
            }

            // Exibir o menu novamente após o processamento da opção anterior
            System.out.println("\nO que você gostaria de fazer?");
            System.out.println("1. Ver as vendas por região e jogo.");
            System.out.println("2. Ver as vendas de um jogo informando a plataforma e a região específica.");
            System.out.println("3. Sair.");
            System.out.print("Por favor, informe sua seleção: ");
            userSelection = userInput.nextInt();
            userInput.nextLine(); // Consumir o caractere de nova linha deixado pelo nextInt()

        } while (userSelection != 3);
    }

    private void viewSalesByRegionAndGame() {
        // Ver as vendas por região e jogo.
        System.out.println("As vendas por região e jogo são:");
        System.out.println("Jogo | Plataforma | Vendas na América do Norte | Vendas na Europa | Vendas no Japão | Vendas em outras regiões | Vendas globais");
        for (VideoGameSale game : games.values()) {
            System.out.printf("%-15s | %-10s | %-10.2f | %-10.2f | %-10.2f | %-10.2f | %-10.2f\n",
                    game.name, game.platform, game.naSales, game.euSales, game.jpSales, game.otherSales, game.globalSales);
        }
    }

    private void searchForGame() {
        // Obter o nome do jogo do usuário.
        System.out.println("Qual jogo você gostaria de procurar?");
        String gameName = userInput.nextLine();

        // Verificar se o jogo existe nos dados.
        Set<String> platforms = platformsByGame.get(gameName);
        if (platforms == null) {
            System.out.println("O jogo " + gameName + " não foi encontrado nos dados.");
            return;
        }

        // Obter a plataforma do usuário.
        System.out.println("Selecione uma plataforma:");
        int platformIndex = 1;
        HashMap<Integer, String> platformIndexToPlatform = new HashMap<>();
        for (String platform : platforms) {
            System.out.println(platformIndex + ". " + platform);
            platformIndexToPlatform.put(platformIndex, platform);
            platformIndex++;
        }
        System.out.print("Por favor, informe sua seleção: ");
        int platformSelection = userInput.nextInt();
        userInput.nextLine(); // Consumir o caractere de nova linha deixado pelo nextInt()

        String platform = platformIndexToPlatform.get(platformSelection);
        if (platform == null) {
            System.out.println("Seleção de plataforma inválida.");
            return;
        }

        // Obter a região do usuário.
        System.out.println("Selecione uma região:");
        System.out.println("1. Vendas na América do Norte");
        System.out.println("2. Vendas na Europa");
        System.out.println("3. Vendas no Japão");
        System.out.println("4. Vendas em outras regiões");
        System.out.println("5. Vendas globais");
        System.out.print("Por favor, informe sua seleção: ");
        int regionSelection = userInput.nextInt();
        userInput.nextLine(); // Consumir o caractere de nova linha deixado pelo nextInt()

        double sales = 0.0;
        GameKey gameKey = new GameKey(gameName, platform);
        VideoGameSale game = games.get(gameKey);

        if (game != null) {
            switch (regionSelection) {
                case 1:
                    sales = game.naSales;
                    break;
                case 2:
                    sales = game.euSales;
                    break;
                case 3:
                    sales = game.jpSales;
                    break;
                case 4:
                    sales = game.otherSales;
                    break;
                case 5:
                    sales = game.globalSales;
                    break;
                default:
                    System.out.println("Seleção de região inválida.");
                    return;
            }

            System.out.println("As vendas de " + gameName + " na plataforma " + platform + " na região selecionada são " + sales);
        } else {
            // Se o jogo não for encontrado, imprimir uma mensagem.
            System.out.println("O jogo " + gameName + " não foi encontrado nos dados.");
        }
    }

    private void exit() {
        // Sair do aplicativo.
        System.out.println("Saindo...");
        System.exit(0);
    }

    private static class GameKey {
        private String name;
        private String platform;

        public GameKey(String name, String platform) {
            this.name = name;
            this.platform = platform;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameKey gameKey = (GameKey) o;
            return name.equals(gameKey.name) && platform.equals(gameKey.platform);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, platform);
        }
    }
}
