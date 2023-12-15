package mod.linguardium.zenithattributesnullfix.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 1500)
public abstract class ZenithAttributesPortingLibFixFix {
        @Unique
        private static final Identifier STEP_HEIGHT_ADDITION_ID = new Identifier("porting_lib:step_height_addition");

        @Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

        @TargetHandler(
                mixin = "dev.shadowsoffire.attributeslib.mixin.PortLibStepHeightFix",
                name = "zenith_attributes_modifyStepHeight"
        )
        @Inject(
                method = "@MixinSquared:Handler",
                at = @At(
                        value = "HEAD"
                ),
                cancellable = true
        )
        private void earlyCancelIfNullPortingLibStepHeight(float vanillaStep, CallbackInfoReturnable<Float> cir) {
            if (getAttributeInstance(Registries.ATTRIBUTE.get(STEP_HEIGHT_ADDITION_ID)) == null) {
                    cir.setReturnValue(vanillaStep);
            }
        }
}
