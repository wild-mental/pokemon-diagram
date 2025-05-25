package pokemongame;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Pokemon implements IPokemon {
    private String pokemonName;  // Name of the type of Pokemon
    @Setter
    private String customName;
    private int HP;
    private PokeDex.PokeCategory pokeCategory;

    // Constructor for PokeDex only
    public Pokemon(
        String pokemonName,
        int HP, PokeDex.PokeCategory pokeCategory) {
        this.pokemonName = pokemonName;
        this.HP = HP;
        this.pokeCategory = pokeCategory;
    }

    // Constructor for player-owned Pokemon
    public Pokemon(
        String pokemonName, String customName,
        int HP, PokeDex.PokeCategory pokeCategory) {
        this.pokemonName = pokemonName;
        this.customName = customName;
        this.HP = HP;
        this.pokeCategory = pokeCategory;
    }

    @Override
    public void attack(Pokemon tgPokemon) {
        // Data and actions: tgPokemon.getHP(), tgPokemon.setHP()
        // Other additional actions and result output
    }

    @Override
    public void flee(int enemyLv) {
        // Result output
    }

    @Override
    public Pokemon evolve() {
        // Data and actions
        EvolvedPokemon evolveTo = PokeDex.getEvolveForm(this.pokemonName);
        System.out.println("== Evolve To: " + evolveTo.getPokemonName() + " ==");
        return new EvolvedPokemon(
            evolveTo.getPokemonName(), this.customName, evolveTo.getHP(), evolveTo.getPokeCategory()
        );
    }
}