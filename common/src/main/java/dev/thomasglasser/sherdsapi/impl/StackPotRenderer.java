package dev.thomasglasser.sherdsapi.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import net.minecraft.client.renderer.SubmitNodeCollector;
import org.joml.Vector3f;

public interface StackPotRenderer {
    void sherdsapi$submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, StackPotDecorations decorations, int outlineColor);

    void getExtents(Set<Vector3f> output);
}
