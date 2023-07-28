package service;

import model.Player;
import model.Pokemon;
import model.WeatherType;

import java.util.Scanner;

import java.util.Scanner;

public class GameService {

    public void attack(Player attacker, Player defender, boolean isPokeSpecialAttack, boolean isCharacterSpecialAttack) {
        int weatherNumber = (int) (Math.random() * 4);
        WeatherType activeWeather = WeatherType.values()[weatherNumber];
        boolean specialAttack = false;

        System.out.println("#### Weather is " + activeWeather + " ####");

        // İki oyuncunun da özel saldırı kullanıp kullanmadığını kontrol edelim.
        if (isPokeSpecialAttack && isCharacterSpecialAttack) {
            specialAttack = attacker.getCharacter().getSelectedPokemon().getSpecialPower().getRemainingRights() > 0 &&
                    attacker.getCharacter().getSpecialPower().getRemainingRights() > 0;
        } else if (isPokeSpecialAttack) {
            specialAttack = attacker.getCharacter().getSelectedPokemon().getSpecialPower().getRemainingRights() > 0;
        } else if (isCharacterSpecialAttack) {
            specialAttack = attacker.getCharacter().getSpecialPower().getRemainingRights() > 0;
        }

        int damage = 0;
        if (specialAttack) {
            if (isPokeSpecialAttack && isCharacterSpecialAttack) {
                damage += attacker.getCharacter().getSelectedPokemon().specialAttack();
                damage += attacker.getCharacter().getSpecialPower().getDamage();
                attacker.getCharacter().getSpecialPower().setRemainingRights(attacker.getCharacter().getSpecialPower().getRemainingRights() - 1);
            } else if (isPokeSpecialAttack) {
                damage += attacker.getCharacter().getSelectedPokemon().specialAttack();
            } else {
                damage = attacker.getCharacter().getSelectedPokemon().getDamage() + attacker.getCharacter().getSpecialPower().getDamage();
                attacker.getCharacter().getSpecialPower().setRemainingRights(attacker.getCharacter().getSpecialPower().getRemainingRights() - 1);
            }
        } else {
            if (isPokeSpecialAttack || isCharacterSpecialAttack) {
                damage = 0;
            } else {
                damage = attacker.getCharacter().getSelectedPokemon().getDamage();
            }
        }

        if (activeWeather == defender.getCharacter().getSelectedPokemon().getWeakWeather()) {
            damage -= damage * 0.2;
        }

        defender.getCharacter().getSelectedPokemon().setHealth(defender.getCharacter().getSelectedPokemon().getHealth() - damage);
    }

    public boolean healthCheck(Player defender) {
        Pokemon defenderPokemon = defender.getCharacter().getSelectedPokemon();

        if (defenderPokemon.getHealth() > 0) {
            System.out.println("Health of " + defenderPokemon.getName() + " belongs to " + defender.getName() +
                    " is " + defenderPokemon.getHealth());
            System.out.println("Game continues...\n");
            return true;
        } else {
            System.out.println(defenderPokemon.getName() + " belongs to " + defender.getName() +
                    " is dead!");
            if (defender.getCharacter().getPokemonList().size() <= 1) {
                System.out.println(defender.getName() + " has lost the round!\n");
            }
            return false;
        }
    }

    public void startFight(Player player1, Player player2) {
        CharacterService characterService = new CharacterService();
        PlayerService playerService = new PlayerService();

        // İlk turda ilk Pokemon'u kullanalım.
        characterService.updateSelectedPokemonForCharacter(player1.getCharacter(), 0);
        characterService.updateSelectedPokemonForCharacter(player2.getCharacter(), 0);

        Scanner scanner = new Scanner(System.in);
        GameService gameService = new GameService();
        int whoStarts = (int) (Math.random() * 100);
        // whoStarts, 0-49 arasıysa player1 başlar, değilse player2 başlar.
        if (whoStarts > 49) {
            Player tempPlayer = player1;
            player1 = player2;
            player2 = tempPlayer;
            tempPlayer = null;
        }
        System.out.println("-----Round 1-----");
        System.out.println(player1.getName() + " will be the first attacker!");
        fightUntilDie(player1, player2);
        obtainPokemon(player1, player2);
        System.out.println("-----Round 2-----");
        characterService.changeSelectedPokemon(player1);
        characterService.changeSelectedPokemon(player2);
        fightUntilDie(player1, player2);

        // İlk turun galibi ise ikinci turda kaybeden Pokemon'u değiştirelim.
        changeToOtherPokemon(player1);
        changeToOtherPokemon(player2);

        // Yeni Pokemon'larla savaşalım.
        fightUntilDie(player1, player2);

        playerService.checkWinner(player1);
        playerService.checkWinner(player2);
    }

    public void changeToOtherPokemon(Player player) {
        if (player.getCharacter().getSelectedPokemon().getHealth() <= 0) {
            player.getCharacter().getPokemonList().remove(player.getCharacter().getSelectedPokemon());
            player.getCharacter().setSelectedPokemon(player.getCharacter().getPokemonList().get(0));
            System.out.println(player.getName() + " keeps fighting with " + player.getCharacter().getSelectedPokemon());
        }
    }

    public boolean isGameOver(Player player1, Player player2) {
        return player1.getCharacter().getPokemonList().isEmpty() || player2.getCharacter().getPokemonList().isEmpty();
    }
    public void fightUntilDie(Player player1, Player player2) {
        boolean checkFirstPlayer = healthCheck(player1);
        boolean checkSecondPlayer = healthCheck(player2);

        while (!isGameOver(player1, player2) && checkFirstPlayer && checkSecondPlayer) {

            System.out.println("--- " + player1.getName() + "'s turn to attack ---");
            boolean isFirstPokemonSpecial = isPokemonSpecialAttack();
            boolean isFirstCharacterSpecial = isCharacterSpecialAttack();
            attack(player1, player2, isFirstPokemonSpecial, isFirstCharacterSpecial);
            checkSecondPlayer = healthCheck(player2);

            if (!checkSecondPlayer || isGameOver(player1, player2)) {
                break;
            }

            System.out.println("--- " + player2.getName() + "'s turn to attack ---");
            boolean isSecondPokemonSpecial = isPokemonSpecialAttack();
            boolean isSecondCharacterSpecial = isCharacterSpecialAttack();
            attack(player2, player1, isSecondPokemonSpecial, isSecondCharacterSpecial);
            checkFirstPlayer = healthCheck(player1);

            if (isGameOver(player1, player2)) {
                break;
            }
        }


    }

    public boolean isPokemonSpecialAttack() {
        int randomNumber = (int) (Math.random() * 2); // 0 ya da 1
        return randomNumber == 1;
    }

    public boolean isCharacterSpecialAttack() {
        int randomNumber = (int) (Math.random() * 10); // 0-9 arası sayı
        return randomNumber == 9; // 1/10 olasılıkla true dönecektir.
    }

    public void obtainPokemon(Player player1, Player player2) {
        CharacterService characterService = new CharacterService();
        characterService.updateSelectedPokemonForCharacter(player1.getCharacter(), 1);
        characterService.updateSelectedPokemonForCharacter(player2.getCharacter(), 1);
    }
}
