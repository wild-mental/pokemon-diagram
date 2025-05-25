package pokemongame;

import lombok.Getter;

import java.util.Random;

public class MysticPokemon extends Pokemon {
    // Has it been traded?
    // -> Asynchronous processing, etc.
    // Actions that can be performed immediately upon trade

    // 1) Add field
    // Let's add the attribute it has because it's Mystic! // It has a dice face!
    private final int mysticFactor = new Random().nextInt(1, 7);
    // 2) Add method
    @Getter
    private IMysticActionable mysticAction;

    // Dex 전용 생성자
    public MysticPokemon(String pokemonName, int HP, PokeDex.PokeCategory pokeCategory) {
        super(pokemonName, HP, pokeCategory);
    }

    // 유저 포켓몬 전용 생성자
    // (Initialize by searching in the Mystic Action dictionary)
    public MysticPokemon(String pokemonName, String customName, int HP, PokeDex.PokeCategory pokeCategory) {
        super(pokemonName, customName, HP, pokeCategory);
        this.mysticAction = PokeDex.mysticActionDex.get(pokemonName);
    }

    @FunctionalInterface
    public interface IMysticActionable {
        Pokemon triggerMysticAction(MysticPokemon mysticPokemon);
        // If you assign the same object as the current object to the interface itself,
        // It can be performed as an object-based action, not a static action (instance context)
    }

    // Mystic Action Type 1 (Instance member reference)
    // Because it's an instance method, it can only be called when there is a base instance parameter
    // Logic implementation including instance reference is possible
    // Method calls based on this, super through inheritance structure, etc. are possible
    // Must ensure that the constructor call occurs only once
    // Prohibit performing constructor calls externally,
    // Must implement internal logic that causes failure once performed.
    public Pokemon mysticEvolve() {
        int diceValueForEvent = new Random().nextInt(1, 7);
        if (diceValueForEvent != mysticFactor) {
            System.out.println(this.getPokemonName()+ "의 Mystic Action 진화가 실패했습니다!: " + diceValueForEvent);
            return this;  // 진화 안한 객체 그대로 리턴
        }
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
        // Resulting in the implementation of the Singleton pattern
        String transformTo = "";
        LegendPokemon newLegend = LegendPokemon.getLegend(transformTo);
        if (newLegend == null) {
            return this;
        }
        newLegend.setCustomName(this.getCustomName());
        return newLegend;
    }
}
