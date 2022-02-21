package com.fruttidino.api.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DinoTalent {
    Carnivore("Carnivore", 1),
    Herbivore("Herbivore", 2),
    Omnivore("Omnivore", 3),
    None("None", 99);

    private final String dinoTalentName;
    private final int dinoTalent;

    // Reverse lookup methods
    public static Optional<DinoTalent> getDinoTalentByValue(String value) {
        return Arrays.stream(DinoTalent.values())
                .filter(dinoTalentCode -> dinoTalentCode.dinoTalentName.equals(value))
                .findFirst();
    }

    public static Optional<DinoTalent> getDinoTalentByValue(int value) {
        return Arrays.stream(DinoTalent.values())
                .filter(dinoTalentCode -> dinoTalentCode.dinoTalent ==  value)
                .findFirst();
    }
}
