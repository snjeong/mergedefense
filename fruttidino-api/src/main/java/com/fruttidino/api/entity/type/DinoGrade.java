package com.fruttidino.api.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DinoGrade {
    Rookie("Rookie", 100),
    Normal("Normal", 200),
    Rare("Rare", 300),
    Legend("Legend", 400),
    None("None", 99);

    private final String dinoGradeName;
    private final int dinoGrade;

    // Reverse lookup methods
    public static Optional<DinoGrade> getDinoGradeByValue(String value) {
        return Arrays.stream(DinoGrade.values())
                .filter(dinoGradeCode -> dinoGradeCode.dinoGradeName.equals(value))
                .findFirst();
    }

    public static Optional<DinoGrade> getDinoGradeByValue(int value) {
        return Arrays.stream(DinoGrade.values())
                .filter(dinoGradeCode -> dinoGradeCode.dinoGrade ==  value)
                .findFirst();
    }
}
