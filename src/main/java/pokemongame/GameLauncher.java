package pokemongame;

import java.lang.reflect.Field;

public class GameLauncher {
    public static void main(String[] args) {
        // 트레이드용 더미 데이터 생성
        Trainer trainer1 = new Trainer();  // 특수 이벤트 1번 포켓몬 보유
        Trainer trainer2 = new Trainer();  // 특수 이벤트 2번 포켓몬 보유
        //   1-2. 트레이너의 보유 포켓몬
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
        // trainer1.trade(trainer2);  // 트레이드 한방에 처리하자!
        // TODO 1) 감춰진 신비의 요소값을 추가
        //   Mystic 포켓몬에 사용자에게는 공개되지 않은 동작 제어 요소를 추가하고 확인해보기 : Reflection 적용!
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
