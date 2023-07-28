package service;

import model.*;
import model.Character;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class LoadService {

    public void load() {
        Scanner scanner = new Scanner(System.in);

        PlayerService playerService = new PlayerService();
        GameService gameService = new GameService();
        PokemonService pokemonService = new PokemonService();

        ArrayList<Character> characterList = loadCharacters();
        ArrayList<Pokemon> pokemonList = loadPokemon();

        Player firstPlayer = playerService.generatePlayer("Player 1", characterList);
        Player secondPlayer = playerService.generatePlayer("Player 1", characterList);

        pokemonService.addPokemonToPlayer(firstPlayer, pokemonList);
        pokemonService.addPokemonToPlayer(secondPlayer, pokemonList);

        System.out.println("1 - Start\n2 - Exit");
        int startOption = scanner.nextInt();
        if (startOption == 1) {
            System.out.println("The battle has begun!\n");
            gameService.startFight(firstPlayer, secondPlayer);
        } else {
            System.out.println("Please wait while the game is closing...");
        }
    }

    public ArrayList<model.Character> loadCharacters() {
        List<SpecialPower> specialPowerList = loadSpecialPowers();
        List<Pokemon> pokemonList = loadPokemon(); // Assuming you have a method to load Pokemon



        model.Character ash = new Ash("Ash", specialPowerList.get(0), pokemonList);
        Brooke brooke = new Brooke("Brock", specialPowerList.get(1), pokemonList);

        ArrayList<model.Character> characterList = new ArrayList<>();
        characterList.add(ash);
        characterList.add(brooke);

        return characterList;
    }

    public ArrayList<Pokemon> loadPokemon() {
        List<SpecialPower> specialPowerList = loadSpecialPowers();

        Pokemon pikachu = new Pikachu("Pikachu", 100, 18,
                TypeEnum.ELECTRIC, specialPowerList.get(0), WeatherType.WINDY);

        Pokemon squirtle = new Squirtle("Squirtle", 100, 14,
                TypeEnum.WATER, specialPowerList.get(1), WeatherType.SUNNY);

        Pokemon charmander = new Charmender("Charmander", 100, 16,
                TypeEnum.FIRE, specialPowerList.get(2), WeatherType.RAINY);

        Pokemon balbasaur = new Balbausar("Bulbasaur", 100, 20,
                TypeEnum.GRASS, specialPowerList.get(3), WeatherType.WINDY);

        ArrayList<Pokemon> pokemonArrayList = new ArrayList<>();
        pokemonArrayList.add(pikachu);
        pokemonArrayList.add(squirtle);
        pokemonArrayList.add(charmander);
        pokemonArrayList.add(balbasaur);

        return pokemonArrayList;
    }

    public List<SpecialPower> loadSpecialPowers() {
        SpecialPower electricity = new Electricty("Lightning Rod", 3, 1);
        SpecialPower water = new Water("Aqua Tail", 1, 1);
        SpecialPower fire = new Fire("Flamethrower", 2, 1);
        SpecialPower earth = new Earth("Chlorophyll", 4, 1);

        List<SpecialPower> specialPowerList = new ArrayList<>();
        specialPowerList.add(electricity);
        specialPowerList.add(water);
        specialPowerList.add(fire);
        specialPowerList.add(earth);

        return specialPowerList;
    }
}

