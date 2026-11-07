package need.clarity.modules.utility.marlow;

import net.minecraft.resources.Identifier;

public final class MarlowChannelIdentifiers {
    private static final String labelText = "marlowcrystal";

    private MarlowChannelIdentifiers() {
    }

    static Identifier evaluate(String arg0) {
        return Identifier.fromNamespaceAndPath("marlowcrystal", arg0);
    }

}
