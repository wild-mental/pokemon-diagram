package pokemongame;

import lombok.Getter;

@Getter
public class FlyPokemon extends Pokemon implements IFlyable {
    public FlyPokemon(String pokemonName, int HP, PokeDex.PokeCategory pokeCategory) {
        super(pokemonName, HP, pokeCategory);
    }

    public FlyPokemon(String pokemonName, String customName, int HP, PokeDex.PokeCategory pokeCategory) {
        super(pokemonName, customName, HP, pokeCategory);
    }

    @Override
    public void fly(String tgCity) {

    }

    @Override
    public void crossOcean(String tgCity) {
        fly(tgCity);
    }
}