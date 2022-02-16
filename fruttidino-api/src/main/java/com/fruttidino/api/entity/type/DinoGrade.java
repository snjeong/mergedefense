package com.fruttidino.api.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DinoGrade {
    Normal("Normal", 1),
    Rare("Rare", 2),
    Legend("Legend", 3),
    None("None", 99);

    private final String dinoGradeName;
    private final int dinoGrade;

    // Reverse lookup methods
    public static Optional<DinoGrade> getDinoTypeByValue(String value) {
        return Arrays.stream(DinoGrade.values())
                .filter(dinoGradeCode -> dinoGradeCode.dinoGradeName.equals(value))
                .findFirst();
    }

    public static Optional<DinoGrade> getDinoTypeByValue(int value) {
        return Arrays.stream(DinoGrade.values())
                .filter(dinoGradeCode -> dinoGradeCode.dinoGrade ==  value)
                .findFirst();
    }
}
