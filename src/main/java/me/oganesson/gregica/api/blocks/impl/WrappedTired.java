package me.oganesson.gregica.api.blocks.impl;

import me.oganesson.gregica.api.blocks.ITired;
import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public class WrappedTired implements ITired {
    
    private final IStringSerializable inner;
    
    public WrappedTired(IStringSerializable inner) {
        this.inner = inner;
    }
    
    @Override
    @NotNull
    public String getName() {
        return inner.getName();
    }
}
