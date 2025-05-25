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
        System.out.println("야생의 포켓몬 " + wildPokemon.getPokemonName() + "을(를) 만났습니다!");
        // 야생의 포켓몬을 만나서 싸우거나 잡거나 그냥 지나가거나
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
                System.out.println("야생의 포켓몬 " + wildPokemon.getPokemonName() + "을(를) 그냥 지나갑니다.");
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
        System.out.println("포켓몬 트레이드를 시작합니다!");
        // 1) 소지 포켓몬 옵션 출력 및 선택
        // 내 포켓몬
        int idx = 0;
        for (Pokemon pokemon: this.getCapturedPokemonList()) {
            System.out.println(idx + ": " + pokemon);
            idx++;
        }
        System.out.print("교환할 내 포켓몬을 선택하세요:");
        int myPokemonPickIdx = inputReader.nextInt();
        Pokemon myPickedPokemon = this.capturedPokemonList.get(myPokemonPickIdx);

        // 상대 포켓몬
        idx = 0;
        for (Pokemon pokemon: tgTrainer.getCapturedPokemonList()) {
            System.out.println(idx + ": " + pokemon);
            idx++;
        }
        System.out.print("교환할 상대 포켓몬을 선택하세요:");
        int tgPokemonPickIdx = inputReader.nextInt();
        Pokemon tgPickedPokemon = tgTrainer.capturedPokemonList.get(tgPokemonPickIdx);

        // 2) 교환 대상 포켓몬 확인 출력
        System.out.println(
            "내 포켓몬 " + myPickedPokemon + " 과\n" +
            "상대 포켓몬 " + tgPickedPokemon + " 을 교환합니다!"
        );

        // 3) 교환 수행
        tgTrainer.capturedPokemonList.set(tgPokemonPickIdx, myPickedPokemon);
        this.capturedPokemonList.set(myPokemonPickIdx, tgPickedPokemon);

        // 4) MysticPokemon 한정 교환 후 이벤트 발생
        Pokemon result = null;
        if (tgPickedPokemon instanceof MysticPokemon myMysticPokemon) {
            System.out.println("트레이딩의 결과 신비의 포켓몬 " + myMysticPokemon.getPokemonName() + "이 반응합니다!");
            result = myMysticPokemon.getMysticAction().triggerMysticAction(myMysticPokemon);
            this.capturedPokemonList.set(myPokemonPickIdx, result);
        }
        if (myPickedPokemon instanceof MysticPokemon tgMysticPokemon) {
            System.out.println("트레이딩의 결과 신비의 포켓몬 " + tgMysticPokemon.getPokemonName() + "이 반응합니다!");
            result = tgMysticPokemon.getMysticAction().triggerMysticAction(tgMysticPokemon);
            tgTrainer.capturedPokemonList.set(tgPokemonPickIdx, result);
        }

        // 5) 교환 후 결과 출력
        System.out.println(
            "교환 결과:\n" +
                "\t내 포켓몬:\n" +
                "\t\t" + this.capturedPokemonList + "\n" +
                "\t상대 포켓몬:\n" +
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