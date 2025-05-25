package pokemongame;

import java.util.HashMap;
import java.util.Map;

public class PokeDex {
    // 포켓몬 정보 관리할 데이터 타입 필드 추가
    static Map<String, Pokemon> pokemonByName = new HashMap<>();
    static Map<PokeCategory, Map<String, Pokemon>> pokemonByCategory = new HashMap<>();
    // 진화형 검색용 인덱스 Map
    static Map<String, String> evolveMap = new HashMap<>();
    static Map<String, MysticPokemon.IMysticActionable> mysticActionDex = new HashMap<>();

    static {
        // 달 포켓몬 & 진화형 더미데이터 생성
        // 모든 PokeCategory 값을 키로 하여 내부 Map 을 초기화
        Pokemon[] pokemons = {
            // 생성하고자 하는 포켓몬을 여기에 new 로 얼마든지 추가 가능
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
            // IMysticActionable 의
            // 인라인 구현체 -> 클래스로 선언하지 않고, 매번 구현체 생성
            // 익명 클래스 형태로 생성 -> 함수형 인터페이스 객체화
//            () -> {
//                System.out.println("근육몬의 Mystic Action 으로 진화가 일어납니다!");
//                // this.evolve();
//                //   -> 외부에서 람다식을 정의할 때, 현재의 인스턴스 객체를 참조할 수 없는 문제 발생
//                return null;
//            }  // 구현체 부분을 인스턴스 멤버로 포함시킨 후 참조
            // 인스턴스 변수를 참조하기 위해서 대상 클래스 내부 구현체를 활용
            MysticPokemon::mysticEvolve
            // 함수형 인터페이스 활용 시 시그니처를
        );
        mysticActionDex.put(
            "킹스톤",
            // IMysticActionable 의 인라인 구현체
//            () -> {
//                System.out.println("킹스톤이 Mystic Action 으로 전설 포켓몬이 됩니다!");
//                return null;
//            }
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
