package need.clarity.mixin;

import net.minecraft.CrashReportCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CrashReportCategory.class)
public class CrashReportCategoryMixin {
    public CrashReportCategoryMixin() {
    }

    @Redirect(method = "validateStackTrace", at = @At(value = "INVOKE", target = "Ljava/lang/StackTraceElement;getFileName()Ljava/lang/String;"))
    private String clarity$safeFileName(StackTraceElement arg0) {
        String var2 = arg0.getFileName();
        if (var2 == null) {
        } else {
        }
        return var2;
    }

}
