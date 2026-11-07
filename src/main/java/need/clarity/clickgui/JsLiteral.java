package need.clarity.clickgui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JsLiteral {
    private final String s;
    private int i;

    private JsLiteral(String arg0, int arg1) {
        this.s = arg0;
        this.i = arg1;
    }

    public static Object after(String arg0, String arg1) {
        int var2 = arg0.indexOf(arg1);
        if (var2 < 0) {
            return null;
        }
        return new JsLiteral(arg0, (var2 + arg1.length())).value();
    }

    private Object value() {
        this.ws();
        if (this.i >= this.s.length()) {
            return null;
        }
        int var1 = this.s.charAt(this.i);
        if (var1 == 123) {
            return this.object();
        }
        if (var1 == 91) {
            return this.array();
        }
        if (var1 != 34) {
            if (var1 != 39) {
                if (var1 == 96) {
                }
            }
            return this.string();
            if (var1 != 45) {
                if (var1 != 46) {
                    if (Character.isDigit(var1)) {
                    }
                }
                return this.number();
                if (this.s.startsWith("true", this.i)) {
                    this.i = (this.i + 4);
                    return Boolean.TRUE;
                }
                if (this.s.startsWith("false", this.i)) {
                    this.i = (this.i + 5);
                    return Boolean.FALSE;
                }
                if (this.s.startsWith("null", this.i)) {
                    this.i = (this.i + 4);
                    return null;
                }
                if (this.s.startsWith("undefined", this.i)) {
                    this.i = (this.i + 9);
                    return null;
                }
                this.skipExpression();
                return null;
            }
        }
    }

    private Map object() {
        String var3 = null;
        LinkedHashMap var1 = new LinkedHashMap();
        this.i = (this.i + 1);
        while (true) {
            this.ws();
            if (this.i >= this.s.length()) {
                return var1;
            }
            int var2 = this.s.charAt(this.i);
            if (var2 == 125) {
                this.i = (this.i + 1);
                return var1;
            }
            if (var2 == 44) {
                this.i = (this.i + 1);
            }
            if (var2 != 34) {
                if (var2 != 39) {
                    if (var2 == 96) {
                    }
                }
                var3 = this.string();
                int var4 = this.i;
                while (true) {
                    if (this.i >= this.s.length()) break;
                    if (!Character.isLetterOrDigit(this.s.charAt(this.i))) {
                        if (this.s.charAt(this.i) != 95) {
                            if (this.s.charAt(this.i) != 36) break;
                        }
                        this.i = (this.i + 1);
                    }
                }
                var3 = this.s.substring(var4, this.i);
                if (var3.isEmpty()) {
                    this.i = (this.i + 1);
                }
                this.ws();
                if (this.i < this.s.length()) {
                    if (this.s.charAt(this.i) == 58) {
                        this.i = (this.i + 1);
                    }
                    var1.put(var3, this.value());
                }
            }
        }
    }

    private List array() {
        ArrayList var1 = new ArrayList();
        this.i = (this.i + 1);
        while (true) {
            this.ws();
            if (this.i >= this.s.length()) {
                return var1;
            }
            int var2 = this.s.charAt(this.i);
            if (var2 == 93) {
                this.i = (this.i + 1);
                return var1;
            }
            if (var2 == 44) {
                this.i = (this.i + 1);
            }
            var1.add(this.value());
        }
    }

    private String string() {
        this.i = (this.i + 1);
        int var1 = this.charAt(this.s);
        StringBuilder var2 = new StringBuilder();
        while (true) {
            if (this.i >= this.s.length()) break;
            this.i = (this.i + 1);
            int var3 = this.charAt(this.s);
            if (var3 == var1) {
            } else {
                if (var3 == 92) {
                    if (this.i < this.s.length()) {
                        this.i = (this.i + 1);
                        int var4 = this.charAt(this.s);
                        switch (var4) {
                            case 110 -> {}
                            case 111 -> {}
                            case 112 -> {}
                            case 113 -> {}
                            case 114 -> {}
                            case 115 -> {}
                            case 116 -> {}
                            case 117 -> {}
                            default -> {}
                        }
                        var2.append(10);
                        var2.append(9);
                        var2.append(13);
                        if ((this.i + 4) <= this.s.length()) {
                            try {
                                var2.append(((char) Integer.parseInt(this.s.substring(this.i, (this.i + 4)), 16)));
                            } catch (NumberFormatException ignored) {
                            }
                            this.i = (this.i + 4);
                            var2.append(var4);
                        }
                    } else {
                        var2.append(var3);
                    }
                }
            }
            return var2.toString();
        }
    }

    private Double number() {
        int var1 = this.i;
        while (true) {
            if (this.i >= this.s.length()) break;
            if ("+-.0123456789eExX".indexOf(this.s.charAt(this.i)) < 0) break;
            this.i = (this.i + 1);
        }
        try {
        } catch (NumberFormatException ignored) {
        }
        return Double.parseDouble(this.s.substring(var1, this.i));
    }

    private void ws() {
        while (true) {
            if (this.i >= this.s.length()) break;
            int var1 = this.s.charAt(this.i);
            if (Character.isWhitespace(var1)) {
                this.i = (this.i + 1);
            }
            if (var1 == 47) {
                if ((this.i + 1) < this.s.length()) {
                    if (this.s.charAt((this.i + 1)) == 47) {
                        while (true) {
                            if (this.i >= this.s.length()) continue;
                            if (this.s.charAt(this.i) == 10) continue;
                            this.i = (this.i + 1);
                        }
                        if (var1 != 47) break;
                        if ((this.i + 1) >= this.s.length()) break;
                        if (this.s.charAt((this.i + 1)) != 42) break;
                        int var2 = this.s.indexOf("*/", (this.i + 2));
                        if (var2 < 0) {
                        } else {
                        }
                        this.s.length().i = (var2 + 2);
                    }
                }
            }
        }
    }

    private void skipExpression() {
        int var1 = 0;
        while (true) {
            if (this.i >= this.s.length()) break;
            int var2 = this.s.charAt(this.i);
            if (var2 != 34) {
                if (var2 != 39) {
                    if (var2 == 96) {
                    }
                }
                this.string();
                if (var2 != 40) {
                    if (var2 != 91) {
                        if (var2 == 123) {
                        }
                    }
                    var1++;
                    if (var2 != 41) {
                        if (var2 != 93) {
                            if (var2 == 125) {
                            }
                        }
                        if (var1 == 0) {
                            return;
                        }
                        var1--;
                        if (var2 == 44) {
                            if (var1 == 0) {
                                return;
                            }
                            this.i = (this.i + 1);
                        }
                    }
                }
            }
        }
    }

}
