package pokemongame;

import java.lang.reflect.Field;

public class GameLauncher {
    public static void main(String[] args) {
        // Create dummy data for trade
        Trainer trainer1 = new Trainer();  // Owns special event Pokemon number 1
        Trainer trainer2 = new Trainer();  // Owns special event Pokemon number 2
        //   Trainer's owned Pokemon
        Pokemon[] trainer1Pokemon = {
            new MysticPokemon("근육몬", "내근육몬0", 10, PokeDex.PokeCategory.MYSTIC),
            new MysticPokemon("근육몬", "내근육몬1", 10, PokeDex.PokeCategory.MYSTIC),
            new MysticPokemon("근육몬", "내근육몬2", 10, PokeDex.PokeCategory.MYSTIC),
        };
        trainer1.getPokemon(trainer1Pokemon);
        Pokemon[] trainer2Pokemon = {
            new MysticPokemon("킹스톤", "니킹스톤0", 10, PokeDex.PokeCategory.MYSTIC),
            new MysticPokemon("킹스톤", "니킹스톤1", 10, PokeDex.PokeCategory.MYSTIC),
            new MysticPokemon("킹스톤", "니킹스톤2", 10, PokeDex.PokeCategory.MYSTIC),
        };
        trainer2.getPokemon(trainer2Pokemon);
        // Let's handle the trade in one go!
        // Add hidden mystical element value
        // Add and check an action control element that is not disclosed to the user in Mystic Pokemon: Apply Reflection!
        // In case @Getter is prohibited from being applied to that part
        // (e.g., not my code, belongs to another project/package, need to minimize code changes)
        // Perform multiple operations to test hidden values
        // Retrieve hidden field values through Reflect
        // Access prevention through encapsulation
        // There is a class called Class as a metaprogramming tool!
        // Allows access to the class blueprint as data
        // Only a separate structure from the object data is derived
        // Release private access control
        // Mystical evolution/transformation Factor number: 
        MysticPokemon myMystic = new MysticPokemon("킹스톤", "Reflection_킹스톤", 10, PokeDex.PokeCategory.MYSTIC);
        // System.out.println(myMystic.getMysticFactor());
        // 해당 부분이 @Getter 적용이 금지되어 있는 경우
        // (ex - 내 코드가 아님, 다른 프로젝트/패키지 소속임, 코드 변경점을 최소화 해야 하는 경우)
        // 감춰진 값을 테스트하기 위해서 여러번 작업 수행
        // Reflect 를 통해 가려진 필드값 조회하기
        // System.out.println(myMystic.getMysticFactor());
        // System.out.println(myMystic.mysticFactor);  // encapsulation 으로 접근 방지
        // 메타 프로그래밍 도구로서 Class 라는 class 가 있다!
        Class<?> mysticClass = myMystic.getClass();  // 클래스 설계도를 데이터로서 접근할 수 있게 해 줌
        // => 객체 데이터와는 별도의 구조도만 도출

        try {
            Field hiddenField = mysticClass.getDeclaredField("mysticFactor");
            hiddenField.setAccessible(true);  // private 접근 제어를 해제
            int hiddeValue = (int) hiddenField.get(myMystic);
            System.out.println("신비의 진화/변형 Factor 숫자: " + hiddeValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
        for (int i = 0; i < 6; i++) {
            myMystic.getMysticAction().triggerMysticAction(myMystic);
        }

        // TODO 2) 전설 포켓몬은 특수한 처리 추가 구현 필요
        //   전설 포켓몬은 게임 세계 내에서 반드시 1 개체만 발생하도록 고도화! : singleton 패턴 적용!
    }
}
