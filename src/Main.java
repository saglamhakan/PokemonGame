import model.Character;
import model.Player;
import service.GameService;
import service.LoadService;
import service.PlayerService;
import service.PokemonService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LoadService loadService = new LoadService();
        PlayerService playerService = new PlayerService();
        GameService gameService = new GameService();
        PokemonService pokemonService = new PokemonService();

        ArrayList<Character> characterList = loadService.loadCharacters();

        Player firstPlayer = playerService.generatePlayer("Player 1", characterList);
        Player secondPlayer = playerService.generatePlayer("Player 1", characterList);

        gameService.startFight(firstPlayer, secondPlayer);

        while (!gameService.isGameOver(firstPlayer, secondPlayer)) {
            System.out.println("\nDevam etmek için bir tuşa basın veya 'q' tuşuna basarak çıkın.");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Oyun çıkışı yapıldı.");
                break;
            }

            // Oyunun bir turunu ilerletin
            gameService.isGameOver(firstPlayer, secondPlayer);
        }

        scanner.close();
    }
}
