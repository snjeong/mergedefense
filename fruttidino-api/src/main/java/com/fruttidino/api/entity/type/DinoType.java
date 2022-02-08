package com.fruttidino.api.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DinoType {
    Oreus("Oreus", 1, "Oreus dsc"),
    Waterus("Waterus", 2, "Waterus dsc"),
    Durius("Durius", 3, "Durius dsc"),
    Cocoura("Cocoura", 4, "Cocoura dsc"),
    Berryura("Berryura", 5, "Berryura dsc"),
    Meloura("Meloura", 6, "Meloura dsc"),
    Piecus("Piecus", 7, "Piecus dsc"),
    Banacus("Banacus", 8, "Banacus dsc"),
    Ramcus("Ramcus", 9, "Ramcus dsc"),
    Tigon("Tigon", 10, "Tigon dsc");

    private final String dinoName;
    private final int dinoType;
    private final String dinoDesc;

    // Reverse lookup methods
    public static Optional<DinoType> getDinoTypeByValue(String value) {
        return Arrays.stream(DinoType.values())
                .filter(dinoTypeCode -> dinoTypeCode.dinoName.equals(value))
                .findFirst();
    }

    public static Optional<DinoType> getDinoTypeByValue(int value) {
        return Arrays.stream(DinoType.values())
                .filter(dinoTypeCode -> dinoTypeCode.dinoType ==  value)
                .findFirst();
    }
}
