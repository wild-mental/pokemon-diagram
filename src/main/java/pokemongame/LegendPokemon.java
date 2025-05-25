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

    // Not an automatic constructor call method like enum,
    // Can write Singleton implementation code according to custom logic!
    public static LegendPokemon getLegend(String newLegend) {
        // Check if there is an existing object, if it is registered in Dex!
        // Return null if there is an existing object
        if (existingLegend.contains(newLegend)) return null;
        // Return null if not registered in advance
        LegendPokemon dexLegend = (LegendPokemon) PokeDex.pokemonByName.get(newLegend);
        if (dexLegend == null) return null;

        // If not, return a new Legend Pokemon!
        LegendPokemon newLegendObj = new LegendPokemon(
            dexLegend.getPokemonName(), dexLegend.getHP(), dexLegend.getPokeCategory()
        );
        existingLegend.add(newLegend);
        return newLegendObj;
    }
}
