package pokemongame;

import lombok.Getter;

import java.util.Random;

public class MysticPokemon extends Pokemon {
    // 트레이드 되었는가?
    // -> 비동기 처리 등
    // 트레이드 즉시 수행할 수 있는 동작

    // 1) 필드 추가
    // Mystic 이기 때문에 가지고 있는 속성을 넣어주자! // 주사위 눈을 하나 가진다!
    private final int mysticFactor = new Random().nextInt(1, 7);
    // 2) 메서드 추가
    @Getter
    private IMysticActionable mysticAction;

    // Dex 전용 생성자
    public MysticPokemon(String pokemonName, int HP, PokeDex.PokeCategory pokeCategory) {
        super(pokemonName, HP, pokeCategory);
    }

    // 유저 포켓몬 전용 생성자
    // (Mystic Action 사전에서 검색해 초기화)
    public MysticPokemon(String pokemonName, String customName, int HP, PokeDex.PokeCategory pokeCategory) {
        super(pokemonName, customName, HP, pokeCategory);
        this.mysticAction = PokeDex.mysticActionDex.get(pokemonName);
    }

    @FunctionalInterface
    public interface IMysticActionable {
        Pokemon triggerMysticAction(MysticPokemon mysticPokemon);
        // 인터페이스 자체에 현재 객체와 동일한 객체를 부여하면,
        // static 한 동작이 아니라, 객체 기준 동작으로 수행 가능 (instance 맥락)
    }

    // Mystic Action 유형 1  (인스턴스 멤버 참조)
    public Pokemon mysticEvolve() {
        int diceValueForEvent = new Random().nextInt(1, 7);
        if (diceValueForEvent != mysticFactor) {
            System.out.println(this.getPokemonName()+ "의 Mystic Action 진화가 실패했습니다!: " + diceValueForEvent);
            return this;  // 진화 안한 객체 그대로 리턴
        }
        // 인스턴스 메서드이기 때문에 반드시 기준 인스턴스 파라미터가 존재하는 경우에만 호출 가능
        // 인스턴스 참조를 포함하는 로직 구현 가능
        System.out.println(this.getPokemonName()+ "의 Mystic Action 으로 진화가 일어납니다!: " + diceValueForEvent);
        // 상속구조 등을 통한 this, super 기반 메서드 호출 가능
        return this.evolve();
    }

    public Pokemon mysticTransform() {
        int diceValueForEvent = new Random().nextInt(1, 7);
        if (diceValueForEvent != mysticFactor) {
            System.out.println(this.getPokemonName()+ "의 Mystic Action 변형이 실패했습니다!: " + diceValueForEvent);
            return this;  // 변형 안한 객체 그대로 리턴
        }
        System.out.println(this.getPokemonName()+ "의 Mystic Action 으로 변형이 일어납니다!: " + diceValueForEvent);

        // 생성자 호출이 반드시 한 번만 유효하게 일어날 것을 보장해야 한다
        //   ->
        //   생성자 호출을 외부에서 수행하는 것을 금지하고,
        //   한번 수행되고 나면 실패하게 만드는 내부 로직을 구현해야 한다.
//        return new LegendPokemon(
//            this.getPokemonName(), this.getCustomName(), this.getHP(),
//            PokeDex.PokeCategory.LEGENDARY
//        );

        // 외부에서 생성자를 호출하는 대신, 아래와 같이 정해진 getLegend 방식으로만 Legend 객체를 얻을 수 있다!
        // Singleton 패턴을 구현한 결과가 됨
        String transformTo = "";
        LegendPokemon newLegend = LegendPokemon.getLegend(transformTo);
        if (newLegend == null) {
            return this;
        }
        newLegend.setCustomName(this.getCustomName());
        return newLegend;
    }
}
