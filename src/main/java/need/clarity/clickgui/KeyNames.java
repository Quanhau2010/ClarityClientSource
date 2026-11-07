package need.clarity.clickgui;

public final class KeyNames {
    private KeyNames() {
    }

    public static String name(int arg0) {
        if (arg0 >= 65) {
            if (arg0 <= 90) {
                return String.valueOf(((char) arg0));
            }
            if (arg0 >= 48) {
                if (arg0 <= 57) {
                    return String.valueOf(((char) arg0));
                }
                if (arg0 >= 290) {
                    if (arg0 <= 301) {
                        return "F" + ((arg0 - 289));
                    }
                    switch (arg0) {
                        case 32 -> {}
                        case 39 -> {}
                        case 44 -> {}
                        case 45 -> {}
                        case 46 -> {}
                        case 47 -> {}
                        case 59 -> {}
                        case 61 -> {}
                        case 91 -> {}
                        case 92 -> {}
                        case 93 -> {}
                        case 96 -> {}
                        case 257 -> {}
                        case 258 -> {}
                        case 259 -> {}
                        case 260 -> {}
                        case 261 -> {}
                        case 262 -> {}
                        case 263 -> {}
                        case 264 -> {}
                        case 265 -> {}
                        case 266 -> {}
                        case 267 -> {}
                        case 268 -> {}
                        case 269 -> {}
                        case 280 -> {}
                        case 281 -> {}
                        case 282 -> {}
                        case 283 -> {}
                        case 284 -> {}
                        case 335 -> {}
                        case 340 -> {}
                        case 341 -> {}
                        case 342 -> {}
                        case 344 -> {}
                        case 345 -> {}
                        case 346 -> {}
                        default -> {}
                    }
                    return "Key " + (arg0);
                }
            }
        }
    }

}
