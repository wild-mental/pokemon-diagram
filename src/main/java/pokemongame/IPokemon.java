package pokemongame;

public interface IPokemon {
    // attack
    public void attack(Pokemon tgPokemon);
    // flee
    public void flee(int enemyLv);
    // evolve : 향후 오버로딩 가능
    public Pokemon evolve();
}
