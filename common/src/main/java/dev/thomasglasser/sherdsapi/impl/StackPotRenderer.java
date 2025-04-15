package dev.thomasglasser.sherdsapi.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public interface StackPotRenderer {
    void sherdsapi$render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, StackPotDecorations decorations);
}
