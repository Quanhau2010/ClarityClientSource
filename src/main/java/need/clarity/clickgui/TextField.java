package need.clarity.clickgui;

public final class TextField {
    public String text;
    public int caret;
    public boolean allSelected;
    public final int maxLen;

    public TextField(int arg0) {
        this.text = "";
        this.caret = 0;
        this.allSelected = false;
        this.maxLen = arg0;
    }

    public void set(String arg0) {
        if (arg0 == null) {
        } else {
        }
        "".text = arg0;
        this.caret = this.text.length();
        this.allSelected = false;
    }

    public void type(String arg0) {
        char[] var3 = null;
        if (arg0 != null) {
            if (arg0.isEmpty()) {
            }
        }
        return;
        if (this.allSelected) {
            this.text = "";
            this.caret = 0;
            this.allSelected = false;
        }
        StringBuilder var2 = new StringBuilder();
        var3 = arg0.toCharArray();
        int var4 = var3.length;
        int var5 = 0;
        while (true) {
            if (var5 >= var4) break;
            int var6 = var3[var5];
            if (var6 >= 32) {
                if (var6 != 127) {
                    var2.append(var6);
                }
                var5++;
            }
        }
        var3 = var2.toString();
        if ((this.text.length() + var3.length()) > this.maxLen) {
            var3 = var3.substring(0, Math.max(0, (this.maxLen - this.text.length())));
        }
        this.text = (this.text.substring(0, this.caret)) + (var3) + (this.text.substring(this.caret));
        this.caret = (this.caret + var3.length());
    }

    public boolean key(int arg0, boolean arg1, String arg2) {
        int var4 = 0;
        switch (arg0) {
            case 65 -> {}
            case 86 -> {}
            case 259 -> {}
            case 261 -> {}
            case 262 -> {}
            case 263 -> {}
            case 268 -> {}
            case 269 -> {}
            default -> {}
        }
        if (this.allSelected) {
            this.text = "";
            this.caret = 0;
            this.allSelected = false;
            return true;
        }
        if (this.caret == 0) {
            return true;
        }
        var4 = (this.caret - 1);
        if (arg1) {
            var4 = this.caret;
            while (true) {
                if (var4 <= 0) break;
                if (this.text.charAt((var4 - 1)) != 32) break;
                var4--;
            }
            while (true) {
                if (var4 <= 0) break;
                if (this.text.charAt((var4 - 1)) == 32) break;
                var4--;
            }
            this.text = (this.text.substring(0, var4)) + (this.text.substring(this.caret));
            this.caret = var4;
            return true;
            if (this.allSelected) {
                this.text = "";
                this.caret = 0;
                this.allSelected = false;
                return true;
            }
            if (this.caret < this.text.length()) {
                this.text = (this.text.substring(0, this.caret)) + (this.text.substring((this.caret + 1)));
            }
            return true;
            this.caret = Math.max(0, (this.caret - 1));
            this.allSelected = false;
            return true;
            this.caret = Math.min(this.text.length(), (this.caret + 1));
            this.allSelected = false;
            return true;
            this.caret = 0;
            this.allSelected = false;
            return true;
            this.caret = this.text.length();
            this.allSelected = false;
            return true;
            if (arg1) {
                this.allSelected = true;
                this.caret = this.text.length();
                return true;
            }
            return false;
            if (arg1) {
                if (arg2 != null) {
                    this.type(arg2.replace("\n", " "));
                    return true;
                }
                return false;
                return false;
            }
        }
    }

}
