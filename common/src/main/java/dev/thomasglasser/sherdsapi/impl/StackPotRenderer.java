package dev.thomasglasser.sherdsapi.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import org.joml.Vector3fc;

public interface StackPotRenderer {
    void sherdsapi$submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, StackPotDecorations decorations, int outlineColor);

    void getExtents(Consumer<Vector3fc> consumer);
}
