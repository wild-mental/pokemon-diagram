DROP DATABASE IF EXISTS pokemon_game;
CREATE DATABASE IF NOT EXISTS pokemon_game;
USE pokemon_game;

-- auto-generated definition
CREATE TABLE poke_dex
(
    monster_id      int                  NOT NULL
        PRIMARY KEY,
    monster_name    varchar(10)          NOT NULL,
    monster_type    varchar(10)          NOT NULL,
    max_hp          int                  NOT NULL,
    evolution_stage int        DEFAULT 1 NOT NULL,
    evolves_from    int                  NULL,
    is_legendary    tinyint(1) DEFAULT 0 NULL,
    CONSTRAINT poke_dex_ibfk_1
        FOREIGN KEY (evolves_from) REFERENCES poke_dex (monster_id),
    CONSTRAINT evolutiontage_range
        CHECK (`evolution_stage` BETWEEN 1 AND 3)
)
;

CREATE INDEX fk_evolvesfrom
    ON poke_dex (evolves_from)
;

CREATE INDEX idx_monstertype
    ON poke_dex (monster_type)
;

-- auto-generated definition
CREATE TABLE pokemon_skills
(
    id           int AUTO_INCREMENT
        PRIMARY KEY,
    skill_name   varchar(20) NOT NULL,
    skill_effect varchar(20) NOT NULL,
    skill_type   varchar(10) NOT NULL,
    skill_damage varchar(10) NOT NULL
)
;

-- auto-generated definition
CREATE TABLE pokemon_trainer
(
    id           int AUTO_INCREMENT
        PRIMARY KEY,
    name         varchar(20) NULL,
    trainer_type varchar(20) NULL
)
;

CREATE INDEX fk_trainertype_monstertype
    ON pokemon_trainer (trainer_type)
;


-- auto-generated definition
CREATE TABLE pokemon
(
    id          int AUTO_INCREMENT
        PRIMARY KEY,
    monster_id  int                  NOT NULL,
    skill1      int                  NOT NULL,
    skill2      int                  NULL,
    owner       int                  NULL,
    nickname    varchar(20)          NOT NULL,
    hp          int                  NOT NULL,
    is_surfable tinyint(1) DEFAULT 0 NULL,
    is_flyable  tinyint(1) DEFAULT 0 NULL,
    CONSTRAINT pokemon_ibfk_1
        FOREIGN KEY (monster_id) REFERENCES poke_dex (monster_id),
    CONSTRAINT pokemon_ibfk_2
        FOREIGN KEY (skill1) REFERENCES pokemon_skills (id),
    CONSTRAINT pokemon_ibfk_3
        FOREIGN KEY (skill2) REFERENCES pokemon_skills (id),
    CONSTRAINT pokemon_ibfk_4
        FOREIGN KEY (owner) REFERENCES pokemon_trainer (id)
)
;

CREATE INDEX fk_monsterid2
    ON pokemon (monster_id)
;

CREATE INDEX fk_pokemon_skill1
    ON pokemon (skill1)
;

CREATE INDEX fk_pokemon_skill2
    ON pokemon (skill2)
;

CREATE INDEX fk_pokemon_trainer
    ON pokemon (owner)
;

CREATE TABLE battle_result
(
    id           int AUTO_INCREMENT
        PRIMARY KEY,
    pokemon_id_1 int         NOT NULL,
    pokemon_id_2 int         NOT NULL,
    winner_id    int         NULL,
    result_memo  varchar(50) NULL,
    CONSTRAINT battle_result_ibfk_1
        FOREIGN KEY (pokemon_id_1) REFERENCES pokemon (id),
    CONSTRAINT battle_result_ibfk_2
        FOREIGN KEY (pokemon_id_2) REFERENCES pokemon (id)
)
;

CREATE INDEX fk_battleresult_pokemon_1
    ON battle_result (pokemon_id_1)
;

CREATE INDEX fk_battleresult_pokemon_2
    ON battle_result (pokemon_id_2)
;

