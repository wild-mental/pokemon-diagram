package pokemongame;

import java.util.HashMap;
import java.util.Map;

public class PokeDex {
    // Add data type fields to manage Pokemon information
    static Map<String, Pokemon> pokemonByName = new HashMap<>();
    static Map<PokeCategory, Map<String, Pokemon>> pokemonByCategory = new HashMap<>();
    // Index Map for searching evolved forms
    static Map<String, String> evolveMap = new HashMap<>();
    static Map<String, MysticPokemon.IMysticActionable> mysticActionDex = new HashMap<>();

    static {
        // Create dummy data for moon Pokemon & evolved forms
        // Initialize internal Map with all PokeCategory values as keys
        Pokemon[] pokemons = {
            // You can add as many Pokemon as you want to create here with new
            // Assign the created Pokemon above to the appropriate dictionary data index
            new MysticPokemon("근육몬", 10, PokeCategory.MYSTIC),
            new EvolvedPokemon("괴력몬", 100, PokeCategory.NORMAL),
            new MysticPokemon("킹스톤", 10, PokeCategory.MYSTIC),
        };

        for (PokeCategory category : PokeCategory.values()) {
            pokemonByCategory.put(category, new HashMap<>());  // 세부 카테고리에 대한 해시맵 초기화
        }

        for (Pokemon pokemon : pokemons) {
            // 위 생성 포켓몬을 적절한 사전 데이터 색인으로 할당
            pokemonByName.put(pokemon.getPokemonName(), pokemon);
            pokemonByCategory.get(pokemon.getPokeCategory()).put(pokemon.getPokemonName(), pokemon);
        }
        evolveMap.put("근육몬", "괴력몬");

        mysticActionDex.put(
            "근육몬",
            // Inline implementation of IMysticActionable
            // Use the internal implementation of the target class to reference instance variables
            // When using a functional interface, the signature
            MysticPokemon::mysticEvolve
        );
        mysticActionDex.put(
            "킹스톤",
            // IMysticActionable 의 인라인 구현체
            MysticPokemon::mysticTransform
        );
    }

    public static EvolvedPokemon getEvolveForm(String currentForm) {
        String evolveTo = evolveMap.get(currentForm);
        return (EvolvedPokemon) pokemonByName.get(evolveTo);
    }

    public enum PokeCategory {
        WATER, FIRE, EARTH, SKY, LEGENDARY, MYSTIC, NORMAL, ELECTRIC
    }

    public static Pokemon searchPokemon(String name) {
        return pokemonByName.get(name);
    }
    public static Map<String, Pokemon> searchPokemon(PokeCategory category) {
        return pokemonByCategory.get(category);
    }
    // 아래 기능 필요 없음
//    public static Pokemon searchPokemon(PokeCategory category, String name) {
//        return searchPokemon(category).get(name);
//    };
}
