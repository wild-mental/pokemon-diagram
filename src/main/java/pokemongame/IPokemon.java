package pokemongame;

public interface IPokemon {
    // attack
    public void attack(Pokemon tgPokemon);
    // flee
    public void flee(int enemyLv);
    // evolve: overloading possible in the future
    public Pokemon evolve();
}
