package dev.thomasglasser.sherdsapi.impl;

import org.jetbrains.annotations.Nullable;

public interface StackPotDecorationsHolder {
    @Nullable
    StackPotDecorations sherdsapi$getDecorations();

    static StackPotDecorationsHolder of(Object object) {
        return (StackPotDecorationsHolder) object;
    }
}
