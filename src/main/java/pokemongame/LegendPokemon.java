package pokemongame;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class LegendPokemon extends Pokemon {
    private static Set<String> existingLegend = new HashSet<>();

    private LegendPokemon(String pokemonName, int HP, PokeDex.PokeCategory pokeCategory) {
        super(pokemonName, HP, pokeCategory);
    }

//    public LegendPokemon(String pokemonName, String customName, int HP, PokeDex.PokeCategory pokeCategory) {
//        super(pokemonName, customName, HP, pokeCategory);
//    }

    // enum 과 같은 자동 생성자 호출 방식이 아닌,
    // 커스텀 로직에 따른 Singleton 구현 코드를 작성할 수 있음!
    public static LegendPokemon getLegend(String newLegend) {
        // 기존 객체가 있는지, Dex 등재 되어있는지 검사!
        // 기존 객체 있으면 null 리턴
        if (existingLegend.contains(newLegend)) return null;
        // 사전 등재 안되어 있으면 null 리턴
        LegendPokemon dexLegend = (LegendPokemon) PokeDex.pokemonByName.get(newLegend);
        if (dexLegend == null) return null;

        // 없으면 새로운 Legend 포켓몬 리턴!
        LegendPokemon newLegendObj = new LegendPokemon(
            dexLegend.getPokemonName(), dexLegend.getHP(), dexLegend.getPokeCategory()
        );
        existingLegend.add(newLegend);
        return newLegendObj;
    }
}
