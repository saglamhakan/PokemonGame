package service;

import model.Character;
import model.Player;
import model.Pokemon;

import java.util.List;
import java.util.Random;

public class PlayerService {
    public Player checkWinner(Player player) {
        List<Pokemon> pokemonList = player.getCharacter().getPokemonList();

        // Oyuncunun tüm Pokemonları sağlığı 0'ın altındaysa, oyunu kaybetmiş demektir.
        boolean allPokemonFainted = pokemonList.stream().allMatch(pokemon -> pokemon.getHealth() <= 0);

        if (allPokemonFainted) {
            System.out.println(player.getName() + " has lost the game!");
            // Oyuncunun karakterinin tüm Pokemonlarını iyileştirelim.
            for (Pokemon pokemon : pokemonList) {
                pokemon.setHealth(100);
            }
        }
        return player;
    }

    public void listPlayerPokemons(Player player) {
        List<Pokemon> pokemonList = player.getCharacter().getPokemonList();

        if (pokemonList.isEmpty()) {
            System.out.println(player.getName() + " has no Pokemon left!");
            return;
        }

        System.out.println(player.getName() + "'s Pokemon List:");
        for (int i = 0; i < pokemonList.size(); i++) {
            Pokemon pokemon = pokemonList.get(i);
            System.out.println((i + 1) + ". " + pokemon.getName() + " - Health: " + pokemon.getHealth());
        }
    }
    public Player generatePlayer(String s, List<Character> characterList) {
        Random random = new Random();
        int randomIndex = random.nextInt(characterList.size());
        Character randomCharacter = characterList.get(randomIndex);

        return new Player(randomCharacter);
    }
}
