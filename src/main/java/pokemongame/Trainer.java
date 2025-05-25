package pokemongame;

import lombok.Getter;

import java.util.*;

@Getter
public class Trainer implements ITrainer {
    List<Pokemon> capturedPokemonList = new ArrayList<>();
    Map<String, Pokemon> capturedPokemonByName = new HashMap<>();
    Scanner inputReader = new Scanner(System.in);

    @Override
    public void hunt(Pokemon wildPokemon) {
        System.out.println("Encounter a wild Pokemon " + wildPokemon.getPokemonName() + "!");
        // Encounter a wild Pokemon and either fight, capture, or pass by
        System.out.println("1: battle / 2: capture / else: pass");
        int battleOrCapture = inputReader.nextInt();
        switch (battleOrCapture) {
            case 1:
                battle(wildPokemon);
                break;
            case 2:
                capture(wildPokemon);
                Pokemon capturedPokemon = capture(wildPokemon);
                if (capturedPokemon != null) {
                    capturedPokemonList.add(capturedPokemon);
                    capturedPokemonByName.put(
                        capturedPokemon.getPokemonName(), capturedPokemon
                    );
                }
                break;
            default:
                System.out.println("Encounter a wild Pokemon " + wildPokemon.getPokemonName() + " and pass by.");
                break;
        }
    }

    @Override
    public Pokemon capture(Pokemon wildPokemon) {
        // 확률적 포획 성공 (가중치를 적용)
        // 몬스터볼 소진 등은 TBD
        return wildPokemon; //  100% 포획
    }

    @Override
    public void battle(Pokemon wildPokemon) {
        // Pokemon 에 대한 getter(), setter() 호출
        // this.getCapturedPokemonList() 등 호출
        List<Pokemon> myLineUp = this.getCapturedPokemonList();
        for (Pokemon pokemon: myLineUp) {
            while (((pokemon.getHP()!=0) && (wildPokemon.getHP()!=0))) {
                pokemon.attack(wildPokemon);
                wildPokemon.attack(pokemon);
            }
        }
        // 결과 출력
        if (wildPokemon.getHP()==0) {
            System.out.println("Win!");
        } else {
            System.out.println("Lost!");
        }
    }

    @Override
    public void battle(ITrainer enemyTrainer) {
        //
    }

    @Override
    public Pokemon searchDex(String pokemonName) {
        return PokeDex.searchPokemon(pokemonName);
    }

    @Override
    public Map<String, Pokemon> searchDex(PokeDex.PokeCategory category) {
        return PokeDex.searchPokemon(category);
    }

    @Override
    public void trade(Trainer tgTrainer) {
        System.out.println("Starting Pokemon trade!");
        // Print and select owned Pokemon options
        // My Pokemon
        int idx = 0;
        for (Pokemon pokemon: this.getCapturedPokemonList()) {
            System.out.println(idx + ": " + pokemon);
            idx++;
        }
        System.out.print("Select the Pokemon to trade:");
        int myPokemonPickIdx = inputReader.nextInt();
        Pokemon myPickedPokemon = this.capturedPokemonList.get(myPokemonPickIdx);

        // Opponent's Pokemon
        idx = 0;
        for (Pokemon pokemon: tgTrainer.getCapturedPokemonList()) {
            System.out.println(idx + ": " + pokemon);
            idx++;
        }
        System.out.print("Select the opponent's Pokemon to trade:");
        int tgPokemonPickIdx = inputReader.nextInt();
        Pokemon tgPickedPokemon = tgTrainer.capturedPokemonList.get(tgPokemonPickIdx);

        // Print confirmation of Pokemon to be traded
        System.out.println(
            "Trading my Pokemon " + myPickedPokemon + " with\n" +
            "Opponent's Pokemon " + tgPickedPokemon + "!"
        );

        // Perform trade
        tgTrainer.capturedPokemonList.set(tgPokemonPickIdx, myPickedPokemon);
        this.capturedPokemonList.set(myPokemonPickIdx, tgPickedPokemon);

        // Event occurs after trading MysticPokemon only
        Pokemon result = null;
        if (tgPickedPokemon instanceof MysticPokemon myMysticPokemon) {
            System.out.println("As a result of trading, the mystical Pokemon reacts!");
            result = myMysticPokemon.getMysticAction().triggerMysticAction(myMysticPokemon);
            this.capturedPokemonList.set(myPokemonPickIdx, result);
        }
        if (myPickedPokemon instanceof MysticPokemon tgMysticPokemon) {
            System.out.println("As a result of trading, the mystical Pokemon reacts!");
            result = tgMysticPokemon.getMysticAction().triggerMysticAction(tgMysticPokemon);
            tgTrainer.capturedPokemonList.set(tgPokemonPickIdx, result);
        }

        // Print result after trade
        System.out.println(
            "Trade result:\n" +
                "\tMy Pokemon:\n" +
                "\t\t" + this.capturedPokemonList + "\n" +
                "\tOpponent's Pokemon:\n" +
                "\t\t" + tgTrainer.capturedPokemonList
        );
    }

    public void crossOcean(String tgCity) {
        for (Pokemon pokemon: this.getCapturedPokemonList()) {
            if (pokemon instanceof IOceanCrossable) {
                ((IOceanCrossable) pokemon).crossOcean(tgCity);
            }
        }
    }

    public void getPokemon(Pokemon[] trainer1Pokemon) {
        capturedPokemonList.addAll(Arrays.asList(trainer1Pokemon));
    }
}