package need.clarity.modules;

import need.clarity.api.Hooks;
import need.clarity.config.ClarityConfig;
import need.clarity.sec.Behavior;

public abstract class ModuleBase {
    private final String name;
    private final String category;
    private boolean enabled;
    private boolean enableHookRan;

    protected ModuleBase(String arg0, String arg1) {
        this.name = arg0;
        this.category = arg1;
        boolean var11 = ClarityConfig.isEnabled(arg0);
        this.enabled = var11;
        ModuleRegistry.register(this);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onTick() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void ensureEnableHook() {
        if (this.enabled) {
            if (this.enableHookRan) {
            }
        }
        return;
        this.enableHookRan = true;
        try {
            this.onEnable();
        } catch (Throwable ignored) {
        }
    }

    public void setEnabled(boolean arg0) {
        if (this.enabled == arg0) {
            if (arg0) {
                this.ensureEnableHook();
            }
            return;
        }
        this.enabled = arg0;
        try {
            Behavior.recordModuleToggle();
        } catch (Throwable ignored) {
        }
        ClarityConfig.setModuleEnabled(this.name, arg0);
        if (arg0) {
            this.enableHookRan = true;
            this.onEnable();
        } else {
            this.enableHookRan = false;
            this.onDisable();
        }
        try {
            Object[] var25 = new Object[2];
            String var28 = this.name;
            var25[0] = var28;
            Boolean var30 = arg0;
            var25[1] = var30;
            Hooks.run1("Notifications.moduleToggled", var25);
        } catch (Throwable ignored) {
        }
    }

    public void toggle() {
        int var4 = 0;
        if (!this.enabled) {
            var4 = 1;
        } else {
            var4 = 0;
        }
        this.setEnabled(var4);
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    protected Object getSetting(String arg0, Object arg1) {
        Object var7 = null;
        double var40 = 0.0;
        Object var74 = null;
        Object var15 = null;
        int var61 = 0;
        var7 = ClarityConfig.getSetting(this.name, arg0);
        if (var7 != null) {
            if (arg1 == null) {
            }
        }
        if (var7 == null) {
            var15 = arg1;
        } else {
            var15 = var7;
        }
        return var15;
        try {
            if ((var7 instanceof String)) {
                String var19 = ((String) var7);
                if (!(arg1 instanceof String)) {
                    String var23 = var19.trim();
                    if ((arg1 instanceof Boolean)) {
                        if (!var23.equalsIgnoreCase("true")) {
                            if (var23.equalsIgnoreCase("false")) {
                            }
                        }
                        Boolean var94 = var23;
                    }
                }
            }
        } catch (RuntimeException ignored) {
        }
        return var94;
        return arg1;
        try {
            if ((arg1 instanceof Number)) {
                var7 = Double.parseDouble(var23);
            }
            if ((var7 instanceof Boolean)) {
                Boolean var35 = ((Boolean) var7);
                if ((arg1 instanceof Number)) {
                    if (var35) {
                        var40 = 1.0;
                    } else {
                        var40 = 0.0;
                    }
                    var7 = var40;
                }
                if ((var7 instanceof Number)) {
                    Number var46 = ((Number) var7);
                    if ((arg1 instanceof Integer)) {
                        Integer var88 = var46;
                    }
                }
            }
        } catch (RuntimeException ignored) {
        }
        return var88;
        try {
            if ((arg1 instanceof Long)) {
                Long var85 = var46;
            }
        } catch (RuntimeException ignored) {
        }
        return var85;
        try {
            if ((arg1 instanceof Float)) {
                Float var81 = var46;
            }
        } catch (RuntimeException ignored) {
        }
        return var81;
        try {
            if ((arg1 instanceof Double)) {
                Double var78 = var46;
            }
        } catch (RuntimeException ignored) {
        }
        return var78;
        try {
            if ((arg1 instanceof Boolean)) {
                if (var46 != 0.0) {
                    var61 = 1;
                } else {
                    var61 = 0;
                }
                Boolean var62 = var61;
            }
        } catch (RuntimeException ignored) {
        }
        return var62;
        try {
            if ((arg1 instanceof String)) {
                if (!(var7 instanceof String)) {
                    String var70 = String.valueOf(var7);
                }
            }
        } catch (RuntimeException ignored) {
        }
        return var70;
        try {
            Class var72 = arg1.getClass();
            Object var96 = var7;
            if (var72.isInstance(var96)) {
                var74 = var7;
            } else {
                var74 = arg1;
            }
            return var74;
        } catch (RuntimeException ignored) {
        }
    }

    protected void putSetting(String arg0, Object arg1) {
        ClarityConfig.putSetting(this.name, arg0, arg1);
    }

}
