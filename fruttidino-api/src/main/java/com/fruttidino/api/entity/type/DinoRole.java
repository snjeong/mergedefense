package com.fruttidino.api.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DinoRole {
    Tank("Tank", 1),
    Dealer("Dealer", 2),
    Supporter("Supporter", 3),
    None("None", 99);

    private final String dinoRoleName;
    private final int dinoRole;

    // Reverse lookup methods
    public static Optional<DinoRole> getDinoRoleByValue(String value) {
        return Arrays.stream(DinoRole.values())
                .filter(dinoRoleCode -> dinoRoleCode.dinoRoleName.equals(value))
                .findFirst();
    }

    public static Optional<DinoRole> getDinoRoleByValue(int value) {
        return Arrays.stream(DinoRole.values())
                .filter(dinoRoleCode -> dinoRoleCode.dinoRole ==  value)
                .findFirst();
    }
}
