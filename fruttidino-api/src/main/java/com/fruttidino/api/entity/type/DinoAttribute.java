package com.fruttidino.api.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DinoAttribute {
    Day("Day", 1),
    Night("Night", 2),
    Dawn("Dawn", 3),
    Eclipse("Eclipse", 4),
    None("None", 99);


    private final String dinoAttributeName;
    private final int dinoAttribute;

    // Reverse lookup methods
    public static Optional<DinoAttribute> getDinoTypeByValue(String value) {
        return Arrays.stream(DinoAttribute.values())
                .filter(dinoAttributeCode -> dinoAttributeCode.dinoAttributeName.equals(value))
                .findFirst();
    }

    public static Optional<DinoAttribute> getDinoTypeByValue(int value) {
        return Arrays.stream(DinoAttribute.values())
                .filter(dinoAttributeCode -> dinoAttributeCode.dinoAttribute ==  value)
                .findFirst();
    }
}
