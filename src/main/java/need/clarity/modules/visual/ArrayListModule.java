package need.clarity.modules.visual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import need.clarity.nvg.ClarityDraw;
import need.clarity.nvg.HudElements;
import need.clarity.nvg.HudLayout;
import need.clarity.util.ThemeColors;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

public class ArrayListModule extends ModuleBase {
    public static ArrayListModule INSTANCE;
    private static final float floatValue = 0.8F;
    private static final NVGColor fieldVal;
    private static final NVGColor fieldVal1;
    private static final Map entriesMap;
    private static final Map entriesMap1;
    private static final Map entriesMap2;
    private static float floatValue1;
    private static long timestampMs;
    private static String labelText;
    private static final Map entriesMap3;
    private static final float floatValue2 = 0.4F;

    public ArrayListModule() {
        super("ArrayList", "Visual");
        INSTANCE = this;
    }

    public static boolean isBlur() {
        int var11 = 0;
        ArrayListModule var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Boolean var12 = false;
                if (((Boolean) var0.getSetting("Blur", var12))) {
                    var11 = 1;
                } else {
                }
            }
            var11 = 0;
            return var11;
        }
    }

    public static int getBlurStrength() {
        ArrayListModule var0 = INSTANCE;
        if (var0 == null) {
            return 2;
        }
        Integer var14 = 2;
        int var13 = ((Integer) var0.getSetting("Blur Strength", var14));
        int var10 = Math.min(4, var13);
        return Math.max(1, var10);
    }

    public static void applyStep(long arg0, float arg1, float arg2) {
        int var71 = 0;
        int var72 = 0;
        int var74 = 0;
        int var83 = 0;
        float var88 = 0.0F;
        float var89 = 0.0F;
        float var90 = 0.0F;
        float var91 = 0.0F;
        int var397 = 0;
        float var445 = 0.0F;
        float var453 = 0.0F;
        float var534 = 0.0F;
        String var562 = null;
        float var617 = 0.0F;
        float var627 = 0.0F;
        float var630 = 0.0F;
        float var633 = 0.0F;
        float var634 = 0.0F;
        float var654 = 0.0F;
        int var655 = 0;
        float var666 = 0.0F;
        float var675 = 0.0F;
        String var707 = null;
        int var728 = 0;
        int var729 = 0;
        float var730 = 0.0F;
        int var731 = 0;
        float var732 = 0.0F;
        String var733 = null;
        float var734 = 0.0F;
        float var735 = 0.0F;
        int var736 = 0;
        String var737 = null;
        int var738 = 0;
        float var817 = 0.0F;
        float var1159 = 0.0F;
        int var1160 = 0;
        int var1338 = 0;
        float var1345 = 0.0F;
        int var1348 = 0;
        float var1349 = 0.0F;
        float var1356 = 0.0F;
        float var1357 = 0.0F;
        float var1380 = 0.0F;
        ArrayListModule var4 = INSTANCE;
        if (var4 == null) {
            return;
        }
        int var111 = ThemeColors.getAccentRgb();
        int var112 = ThemeColors.getAccent2Rgb();
        int var113 = ThemeColors.getSurfaceRgb();
        int var114 = computeColorOrIndex(var113, var111, 0.02F);
        float var116 = computeFloatMetric(var111);
        String var739 = "Side";
        String var976 = "Right";
        Object var118 = var4.getSetting(var739, var976);
        String var119 = ((String) var118);
        String var740 = var119;
        boolean var121 = "Left".equalsIgnoreCase(var740);
        if (!var121) {
            var728 = 1;
        } else {
            var728 = 0;
        }
        int var26 = var728;
        ArrayListModule var741 = var4;
        String var977 = "Scale";
        Float var1161 = 1.0F;
        Object var742 = var741.getSetting(var977, var1161);
        Float var743 = ((Float) var742);
        float var744 = var743;
        float var123 = Math.max(0.1F, var744);
        String var745 = "Margin X";
        int var978 = 6;
        Integer var979 = var978;
        Object var125 = var4.getSetting(var745, var979);
        Integer var126 = ((Integer) var125);
        int var127 = var126;
        String var746 = "Margin Y";
        int var980 = 12;
        Integer var981 = var980;
        Object var129 = var4.getSetting(var746, var981);
        Integer var130 = ((Integer) var129);
        int var131 = var130;
        String var747 = "Row Height";
        float var982 = 22.0F;
        Float var983 = var982;
        Object var133 = var4.getSetting(var747, var983);
        Float var134 = ((Float) var133);
        float var135 = var134;
        String var748 = "H Padding";
        float var984 = 18.0F;
        Float var985 = var984;
        Object var137 = var4.getSetting(var748, var985);
        Float var138 = ((Float) var137);
        float var139 = var138;
        String var749 = "Text Size";
        float var986 = 12.0F;
        Float var987 = var986;
        Object var141 = var4.getSetting(var749, var987);
        Float var142 = ((Float) var141);
        float var143 = var142;
        String var750 = "Anim Speed";
        float var988 = 0.12F;
        Float var989 = var988;
        Object var145 = var4.getSetting(var750, var989);
        Float var146 = ((Float) var145);
        float var147 = var146;
        float var751 = 0.01F;
        float var990 = 1.0F;
        float var148 = computeFloatMetric(var147, var751, var990);
        String var752 = "Corner Radius";
        float var991 = 4.0F;
        Float var992 = var991;
        Object var150 = var4.getSetting(var752, var992);
        Float var151 = ((Float) var150);
        float var152 = var151;
        String var753 = "Row Gap";
        float var993 = 0.0F;
        Float var994 = var993;
        Object var154 = var4.getSetting(var753, var994);
        Float var155 = ((Float) var154);
        float var156 = var155;
        float var754 = 0.0F;
        float var995 = 8.0F;
        float var157 = computeFloatMetric(var156, var754, var995);
        String var755 = "Flush Edges";
        int var996 = 0;
        Boolean var997 = var996;
        Object var159 = var4.getSetting(var755, var997);
        Boolean var160 = ((Boolean) var159);
        boolean var161 = var160;
        String var756 = "Border";
        int var998 = 1;
        Boolean var999 = var998;
        Object var163 = var4.getSetting(var756, var999);
        Boolean var164 = ((Boolean) var163);
        boolean var165 = var164;
        int var166 = ThemeColors.getBorderRgb();
        int var757 = var111;
        float var1000 = 0.1F;
        int var167 = computeColorOrIndex(var166, var757, var1000);
        String var758 = "Border Color";
        int var1001 = var167;
        int var169 = computeColorOrIndex(var4, var758, var1001);
        String var759 = "Border Width";
        float var1002 = 1.0F;
        Float var1003 = var1002;
        Object var171 = var4.getSetting(var759, var1003);
        Float var172 = ((Float) var171);
        float var173 = var172;
        float var760 = 0.25F;
        float var1004 = 4.0F;
        float var174 = computeFloatMetric(var173, var760, var1004);
        String var761 = "Border Highlight";
        int var1005 = 1;
        Boolean var1006 = var1005;
        Object var176 = var4.getSetting(var761, var1006);
        Boolean var177 = ((Boolean) var176);
        boolean var178 = var177;
        String var762 = "Highlight Color";
        boolean var1007 = ThemeColors.isLight();
        if (var1007) {
            var1160 = 0;
        } else {
            var1160 = 16777215;
        }
        int var180 = computeColorOrIndex(var4, var762, var1160);
        String var763 = "Highlight Opacity";
        int var1008 = 20;
        Integer var1009 = var1008;
        Object var182 = var4.getSetting(var763, var1009);
        Integer var183 = ((Integer) var182);
        int var184 = var183;
        float var185 = ((float) var184);
        float var764 = 0.0F;
        float var1010 = 100.0F;
        float var186 = computeFloatMetric(var185, var764, var1010);
        float var765 = 100.0F;
        float var187 = (var186 / var765);
        String var766 = "Background";
        String var1011 = "Solid";
        Object var189 = var4.getSetting(var766, var1011);
        String var190 = ((String) var189);
        String var767 = "BG Color";
        int var1012 = 0;
        int var192 = computeColorOrIndex1(var4, var767, var1012);
        String var768 = "BG Opacity";
        int var1013 = 100;
        Integer var1014 = var1013;
        Object var194 = var4.getSetting(var768, var1014);
        Integer var195 = ((Integer) var194);
        int var196 = var195;
        float var197 = ((float) var196);
        float var769 = 0.0F;
        float var1015 = 100.0F;
        float var198 = computeFloatMetric(var197, var769, var1015);
        float var770 = 100.0F;
        float var199 = (var198 / var770);
        String var771 = var190;
        boolean var201 = "Gradient".equalsIgnoreCase(var771);
        String var772 = "Gradient Speed";
        float var1016 = 1.0F;
        Float var1017 = var1016;
        Object var203 = var4.getSetting(var772, var1017);
        Float var204 = ((Float) var203);
        float var205 = var204;
        String var773 = "Gradient Saturation";
        float var1018 = 0.8F;
        Float var1019 = var1018;
        Object var207 = var4.getSetting(var773, var1019);
        Float var208 = ((Float) var207);
        float var209 = var208;
        float var774 = 0.0F;
        float var1020 = 1.0F;
        float var210 = computeFloatMetric(var209, var774, var1020);
        String var775 = "Outline Color";
        int var1021 = var167;
        int var212 = computeColorOrIndex(var4, var775, var1021);
        String var776 = "Outline Width";
        float var1022 = 1.0F;
        Float var1023 = var1022;
        Object var214 = var4.getSetting(var776, var1023);
        Float var215 = ((Float) var214);
        float var216 = var215;
        String var777 = "Text Color";
        int var1024 = ThemeColors.getTextRgb();
        int var218 = computeColorOrIndex(var4, var777, var1024);
        String var778 = "Text Opacity";
        int var1025 = 100;
        Integer var1026 = var1025;
        Object var220 = var4.getSetting(var778, var1026);
        Integer var221 = ((Integer) var220);
        int var222 = var221;
        float var223 = ((float) var222);
        float var779 = 0.0F;
        float var1027 = 100.0F;
        float var224 = computeFloatMetric(var223, var779, var1027);
        float var780 = 100.0F;
        float var225 = (var224 / var780);
        String var781 = "Shadow";
        int var1028 = 0;
        Boolean var1029 = var1028;
        Object var227 = var4.getSetting(var781, var1029);
        Boolean var228 = ((Boolean) var227);
        boolean var229 = var228;
        String var782 = "Shadow Color";
        int var1030 = 0;
        int var231 = computeColorOrIndex(var4, var782, var1030);
        String var783 = "Shadow Opacity";
        int var1031 = 60;
        Integer var1032 = var1031;
        Object var233 = var4.getSetting(var783, var1032);
        Integer var234 = ((Integer) var233);
        int var235 = var234;
        float var236 = ((float) var235);
        float var784 = 0.0F;
        float var1033 = 100.0F;
        float var237 = computeFloatMetric(var236, var784, var1033);
        float var785 = 100.0F;
        float var238 = (var237 / var785);
        String var786 = "Accent Bar";
        int var1034 = 0;
        Boolean var1035 = var1034;
        Object var240 = var4.getSetting(var786, var1035);
        Boolean var241 = ((Boolean) var240);
        boolean var242 = var241;
        String var787 = "Accent Color";
        Object var1036 = null;
        Object var244 = var4.getSetting(var787, var1036);
        int var788 = -16777216;
        int var1037 = var111;
        int var789 = (var788 | var1037);
        int var1038 = 0;
        int var245 = ThemeColors.resolveArgb(var244, var789, var1038);
        String var790 = "Accent Width";
        float var1039 = 2.0F;
        Float var1040 = var1039;
        Object var247 = var4.getSetting(var790, var1040);
        Float var248 = ((Float) var247);
        float var249 = var248;
        String var791 = "Show Mode";
        int var1041 = 1;
        Boolean var1042 = var1041;
        Object var251 = var4.getSetting(var791, var1042);
        Boolean var252 = ((Boolean) var251);
        boolean var253 = var252;
        String var792 = "Mode Style";
        String var1043 = "Plain";
        Object var255 = var4.getSetting(var792, var1043);
        String var256 = ((String) var255);
        String var793 = "Mode Color";
        String var1044 = "#8A8F98";
        Object var258 = var4.getSetting(var793, var1044);
        String var259 = ((String) var258);
        int var794 = 9080728;
        int var260 = computeColorOrIndex(var259, var794);
        int var795 = 16777215;
        int var261 = (var260 & var795);
        String var796 = "Mode Opacity";
        int var1045 = 100;
        Integer var1046 = var1045;
        Object var263 = var4.getSetting(var796, var1046);
        Integer var264 = ((Integer) var263);
        int var265 = var264;
        float var266 = ((float) var265);
        float var797 = 0.0F;
        float var1047 = 100.0F;
        float var267 = computeFloatMetric(var266, var797, var1047);
        float var798 = 100.0F;
        float var268 = (var267 / var798);
        String var799 = "Mode Scale";
        float var1048 = 1.0F;
        Float var1049 = var1048;
        Object var270 = var4.getSetting(var799, var1049);
        Float var271 = ((Float) var270);
        float var272 = var271;
        float var800 = 0.5F;
        float var1050 = 1.5F;
        float var273 = computeFloatMetric(var272, var800, var1050);
        String var801 = "Show Suffix";
        int var1051 = 0;
        Boolean var1052 = var1051;
        Object var275 = var4.getSetting(var801, var1052);
        Boolean var276 = ((Boolean) var275);
        boolean var277 = var276;
        String var802 = "Suffix Opacity";
        int var1053 = 55;
        Integer var1054 = var1053;
        Object var279 = var4.getSetting(var802, var1054);
        Integer var280 = ((Integer) var279);
        int var281 = var280;
        float var282 = ((float) var281);
        float var803 = 0.0F;
        float var1055 = 100.0F;
        float var283 = computeFloatMetric(var282, var803, var1055);
        float var804 = 100.0F;
        float var284 = (var283 / var804);
        long var285 = System.currentTimeMillis();
        if (var201) {
            long var288 = timestampMs;
            long var1056 = 0L;
            int var290 = Long.compare(var288, var1056);
            if (var290 > 0) {
                long var1058 = timestampMs;
                long var293 = (var285 - var1058);
                float var295 = ((float) var293);
                float var805 = 1000.0F;
                float var296 = (var295 / var805);
                float var806 = 0.1F;
                float var297 = Math.min(var296, var806);
                float var298 = floatValue1;
                float var807 = var205;
                float var1060 = 54.0F;
                float var808 = (var807 * var1060);
                float var1061 = var297;
                float var809 = (var808 * var1061);
                float var299 = (var298 + var809);
                float var810 = 360.0F;
                float var300 = (var299 % var810);
                floatValue1 = var300;
            }
            timestampMs = var285;
            List var303 = ModuleRegistry.all();
            List var1335 = var303;
            List var1062 = var1335;
            int var1063 = var1062.size();
            ArrayList var304 = new ArrayList(var1063);
            var71 = 0;
            while (true) {
                int var306 = var71;
                List var811 = var1335;
                int var812 = var811.size();
                if (var306 >= var812) break;
                List var719 = var1335;
                int var974 = var71;
                Object var720 = var719.get(var974);
                ModuleBase var721 = ((ModuleBase) var720);
                ModuleBase var1341 = var721;
                ModuleBase var722 = var1341;
                boolean var723 = var722.isEnabled();
                if (var723) {
                    ModuleBase var724 = var1341;
                    int var725 = (var724 instanceof ArrayListModule);
                    if (var725 == 0) {
                        ArrayList var726 = var304;
                        ModuleBase var975 = var1341;
                        boolean var727 = var726.add(var975);
                    }
                    var71 = (var71 + 1);
                }
            }
            boolean var308 = var304.isEmpty();
            if (var308) {
                Map var309 = entriesMap;
                float var813 = var148;
                applyStep(var309, var813);
                boolean var310 = HudLayout.isEditing();
                if (!var310) {
                } else {
                    float var814 = var123;
                    float var312 = (90.0F * var814);
                    float var1336 = var312;
                    int var815 = var26;
                    if (var815 == 0) {
                    } else {
                        float var818 = arg1;
                        int var1066 = var127;
                        float var1067 = ((float) var1066);
                        float var819 = (var818 - var1067);
                        float var1068 = var1336;
                        var817 = (var819 - var1068);
                    }
                }
            } else {
                String var1069 = "inter-sb";
                NanoVG.nvgFontFace(arg0, var1069);
                float var1070 = var143;
                NanoVG.nvgFontSize(arg0, var1070);
                float var1071 = 0.0F;
                NanoVG.nvgTextLetterSpacing(arg0, var1071);
                int var1072 = 9;
                NanoVG.nvgTextAlign(arg0, var1072);
                int var820 = var277;
                int var1073 = var253;
                String var1163 = var256;
                float var1196 = var273;
                String var323 = (var143) + "|" + (var820) + "|" + (var1073) + "|" + (var1163) + "|" + (var1196);
                String var1337 = var323;
                String var821 = labelText;
                boolean var325 = var1337.equals(var821);
                if (!var325) {
                    Map var326 = entriesMap1;
                    var326.clear();
                    labelText = var1337;
                }
                var72 = 0;
                while (true) {
                    int var329 = var72;
                    ArrayList var822 = var304;
                    int var823 = var822.size();
                    if (var329 >= var823) break;
                    ArrayList var700 = var304;
                    int var967 = var72;
                    Object var701 = var700.get(var967);
                    ModuleBase var702 = ((ModuleBase) var701);
                    ModuleBase var1344 = var702;
                    ModuleBase var703 = var1344;
                    String var704 = var703.getName();
                    String var1347 = var704;
                    int var705 = var253;
                    if (var705 != 0) {
                        String var706 = var1347;
                        var707 = ModuleSuffixFormatter.resolveLabel(var706);
                    } else {
                        var707 = null;
                    }
                    String var1350 = var707;
                    Map var708 = entriesMap1;
                    String var968 = var1347;
                    boolean var709 = var708.containsKey(var968);
                    if (var709) {
                        String var710 = var1350;
                        Map var969 = entriesMap3;
                        String var1153 = var1347;
                        Object var970 = var969.get(var1153);
                        boolean var711 = Objects.equals(var710, var970);
                        if (!var711) {
                        }
                    }
                    Map var712 = entriesMap1;
                    String var971 = var1347;
                    long var1154 = arg0;
                    String var1225 = var1347;
                    String var1246 = var1350;
                    float var1259 = var143;
                    int var1276 = var277;
                    String var1291 = var256;
                    float var1302 = var273;
                    float var1156 = computeFloatMetric(var1154, var1225, var1246, var1259, var1276, var1291, var1302);
                    Float var1157 = var1156;
                    Object var713 = var712.put(var971, var1157);
                    String var714 = var1350;
                    if (var714 == null) {
                        Map var715 = entriesMap3;
                        String var972 = var1347;
                        Object var716 = var715.remove(var972);
                    } else {
                        Map var717 = entriesMap3;
                        String var973 = var1347;
                        String var1158 = var1350;
                        Object var718 = var717.put(var973, var1158);
                    }
                    var72 = (var72 + 1);
                }
                Comparator var824 = (p0, p1) -> ArrayListModule.lambda$a(p0, p1);
                var304.sort(var824);
                var1338 = 0;
                while (true) {
                    int var332 = var1338;
                    ArrayList var825 = var304;
                    int var826 = var825.size();
                    if (var332 >= var826) break;
                    ArrayList var676 = var304;
                    int var959 = var1338;
                    Object var677 = var676.get(var959);
                    ModuleBase var678 = ((ModuleBase) var677);
                    String var679 = var678.getName();
                    String var1343 = var679;
                    Map var680 = entriesMap1;
                    String var960 = var1343;
                    float var1141 = 0.0F;
                    Float var1142 = var1141;
                    Object var681 = var680.getOrDefault(var960, var1142);
                    Float var682 = ((Float) var681);
                    float var683 = var682;
                    float var961 = var139;
                    float var684 = (var683 + var961);
                    float var1346 = var684;
                    Map var685 = entriesMap;
                    String var962 = var1343;
                    float var1143 = 0.0F;
                    Float var1144 = var1143;
                    Object var686 = var685.getOrDefault(var962, var1144);
                    Float var687 = ((Float) var686);
                    float var688 = var687;
                    var1349 = var688;
                    float var689 = var1349;
                    float var963 = 0.0F;
                    int var690 = Float.compare(var689, var963);
                    if (var690 == 0) {
                        float var691 = var1346;
                        var1349 = var691;
                    }
                    Map var692 = entriesMap;
                    String var964 = var1343;
                    float var1145 = var1349;
                    float var1189 = var1346;
                    float var1221 = var1349;
                    float var1190 = (var1189 - var1221);
                    float var1222 = var148;
                    float var1191 = (var1190 * var1222);
                    float var1146 = (var1145 + var1191);
                    Float var1147 = var1146;
                    Object var693 = var692.put(var964, var1147);
                    Map var694 = entriesMap2;
                    String var965 = var1343;
                    float var1148 = 0.0F;
                    Float var1149 = var1148;
                    Object var695 = var694.getOrDefault(var965, var1149);
                    Float var696 = ((Float) var695);
                    float var697 = var696;
                    float var1352 = var697;
                    Map var698 = entriesMap2;
                    String var966 = var1343;
                    float var1150 = var1352;
                    float var1192 = 1.0F;
                    float var1223 = var1352;
                    float var1193 = (var1192 - var1223);
                    float var1224 = var148;
                    float var1194 = (var1193 * var1224);
                    float var1151 = (var1150 + var1194);
                    Float var1152 = var1151;
                    Object var699 = var698.put(var966, var1152);
                    var1338 = (var1338 + 1);
                }
                Map var333 = entriesMap;
                Set var334 = var333.keySet();
                int var827 = 0;
                String[] var828 = new String[var827];
                Object[] var335 = var334.toArray(var828);
                String[] var336 = ((String[]) var335);
                String[] var1339 = var336;
                int var338 = var1339.length;
                var74 = 0;
                while (true) {
                    int var340 = var74;
                    int var829 = var338;
                    if (var340 >= var829) break;
                    int var830 = var74;
                    String var342 = var1339[var830];
                    Map var343 = entriesMap1;
                    String var831 = var342;
                    boolean var344 = var343.containsKey(var831);
                    if (var344) {
                        String var832 = var342;
                        boolean var346 = isEligible(var304, var832);
                        if (!var346) {
                        }
                    }
                    Map var347 = entriesMap;
                    String var833 = var342;
                    Object var348 = var347.get(var833);
                    Float var349 = ((Float) var348);
                    float var350 = var349;
                    float var834 = 1.0F;
                    float var1074 = var148;
                    float var835 = (var834 - var1074);
                    float var352 = (var350 * var835);
                    Map var353 = entriesMap2;
                    String var836 = var342;
                    float var1075 = 1.0F;
                    Float var1076 = var1075;
                    Object var354 = var353.getOrDefault(var836, var1076);
                    Float var355 = ((Float) var354);
                    float var356 = var355;
                    float var837 = 1.0F;
                    float var1077 = var148;
                    float var838 = (var837 - var1077);
                    float var357 = (var356 * var838);
                    Map var358 = entriesMap2;
                    String var839 = var342;
                    float var1078 = var357;
                    Float var1079 = var1078;
                    Object var359 = var358.put(var839, var1079);
                    float var840 = 0.5F;
                    int var361 = Float.compare(var352, var840);
                    if (var361 < 0) {
                        float var841 = 0.03F;
                        int var363 = Float.compare(var357, var841);
                        if (var363 < 0) {
                            Map var364 = entriesMap;
                            String var842 = var342;
                            Object var365 = var364.remove(var842);
                            Map var366 = entriesMap1;
                            String var843 = var342;
                            Object var367 = var366.remove(var843);
                            Map var368 = entriesMap2;
                            String var844 = var342;
                            Object var369 = var368.remove(var844);
                            Map var370 = entriesMap3;
                            String var845 = var342;
                            Object var371 = var370.remove(var845);
                        } else {
                            Map var372 = entriesMap;
                            String var846 = var342;
                            float var1080 = var352;
                            Float var1081 = var1080;
                            Object var373 = var372.put(var846, var1081);
                        }
                        var74 = (var74 + 1);
                    }
                }
                NanoVG.nvgSave(arg0);
                float var1082 = var123;
                float var1164 = var123;
                NanoVG.nvgScale(arg0, var1082, var1164);
                float var1083 = 0.0F;
                int var1165 = var131;
                float var1166 = ((float) var1165);
                float var1197 = var123;
                float var1167 = (var1166 / var1197);
                NanoVG.nvgTranslate(arg0, var1083, var1167);
                float var847 = var123;
                float var381 = (arg1 / var847);
                float var1340 = var381;
                if (var26 != 0) {
                    float var674 = var1340;
                    int var956 = var127;
                    float var957 = ((float) var956);
                    float var1140 = var123;
                    float var958 = (var957 / var1140);
                    var675 = (var674 - var958);
                } else {
                    float var384 = ((float) var127);
                    float var848 = var123;
                    var675 = (var384 / var848);
                }
                float var1342 = var675;
                var1345 = 0.0F;
                var1348 = 0;
                String var849 = var190;
                boolean var388 = "None".equalsIgnoreCase(var849);
                int var1351 = var388;
                if (var1351 == 0) {
                    String var850 = var190;
                    boolean var391 = "Outline Only".equalsIgnoreCase(var850);
                    if (!var391) {
                        String var851 = "Blur";
                        int var1084 = 0;
                        Boolean var1085 = var1084;
                        Object var393 = var4.getSetting(var851, var1085);
                        Boolean var394 = ((Boolean) var393);
                        boolean var395 = var394;
                        if (var395) {
                            boolean var396 = HudElements.backdropReady();
                            if (var396) {
                                var397 = 1;
                            }
                        }
                    } else {
                    }
                }
                var397 = 0;
                int var1353 = var397;
                String var852 = "Blur Tint";
                float var1086 = 0.5F;
                Float var1087 = var1086;
                Object var399 = var4.getSetting(var852, var1087);
                Float var400 = ((Float) var399);
                float var401 = var400;
                float var853 = 0.0F;
                float var1088 = 1.0F;
                float var402 = computeFloatMetric(var401, var853, var1088);
                float var1354 = var402;
                ArrayList var403 = new ArrayList();
                HashMap var404 = new HashMap();
                Iterator var406 = var304.iterator();
                while (true) {
                    boolean var408 = var406.hasNext();
                    if (!var408) break;
                    Iterator var667 = var406;
                    Object var668 = var667.next();
                    ModuleBase var669 = ((ModuleBase) var668);
                    ModuleBase var1358 = var669;
                    ArrayList var670 = var403;
                    ModuleBase var952 = var1358;
                    String var953 = var952.getName();
                    boolean var671 = var670.add(var953);
                    HashMap var672 = var404;
                    ModuleBase var954 = var1358;
                    String var955 = var954.getName();
                    ModuleBase var1139 = var1358;
                    Object var673 = var672.put(var955, var1139);
                }
                Map var409 = entriesMap;
                Set var410 = var409.keySet();
                Iterator var411 = var410.iterator();
                Iterator var1355 = var411;
                while (true) {
                    boolean var413 = var1355.hasNext();
                    if (!var413) break;
                    Object var415 = var1355.next();
                    String var416 = ((String) var415);
                    String var854 = var416;
                    boolean var418 = var404.containsKey(var854);
                    if (var418) continue;
                    String var855 = var416;
                    boolean var420 = var403.add(var855);
                }
                var1356 = 0.0F;
                var1357 = 0.0F;
                var83 = 0;
                while (true) {
                    int var424 = var83;
                    ArrayList var856 = var403;
                    int var857 = var856.size();
                    if (var424 >= var857) break;
                    int var858 = var83;
                    Object var426 = var403.get(var858);
                    String var427 = ((String) var426);
                    Map var428 = entriesMap;
                    String var859 = var427;
                    float var1089 = 0.0F;
                    Float var1090 = var1089;
                    Object var429 = var428.getOrDefault(var859, var1090);
                    Float var430 = ((Float) var429);
                    float var431 = var430;
                    float var860 = 1.0F;
                    int var433 = Float.compare(var431, var860);
                    if (var433 < 0) {
                        float var656 = var1345;
                        float var946 = var135;
                        float var1135 = var157;
                        float var947 = (var946 + var1135);
                        float var657 = (var656 + var947);
                        var1345 = var657;
                    } else {
                        float var434 = var1356;
                        float var861 = var431;
                        float var435 = Math.max(var434, var861);
                        var1356 = var435;
                        float var436 = var1345;
                        float var862 = var135;
                        float var437 = (var436 + var862);
                        var1357 = var437;
                        Map var438 = entriesMap2;
                        String var863 = var427;
                        float var1091 = 1.0F;
                        Float var1092 = var1091;
                        Object var439 = var438.getOrDefault(var863, var1092);
                        Float var440 = ((Float) var439);
                        float var441 = var440;
                        float var864 = 0.0F;
                        float var1093 = 1.0F;
                        float var442 = computeFloatMetric(var441, var864, var1093);
                        if (var26 != 0) {
                            float var865 = var431;
                            var445 = (var1342 - var865);
                        } else {
                            var445 = var1342;
                        }
                        float var87 = var445;
                        float var866 = 1.0F;
                        float var1094 = var442;
                        float var867 = (var866 - var1094);
                        int var1095 = var26;
                        if (var1095 != 0) {
                            var1159 = 12.0F;
                        } else {
                            var1159 = -12.0F;
                        }
                        float var868 = (var867 * var1159);
                        float var447 = (var87 + var868);
                        float var1360 = var447;
                        var91 = var152;
                        var90 = var152;
                        var89 = var152;
                        var88 = var152;
                        if (var161) {
                            if (var26 != 0) {
                                float var869 = var431;
                                var453 = (var1342 - var869);
                            } else {
                                var453 = var1342;
                            }
                            float var93 = var453;
                            float var454 = var1345;
                            int var870 = var131;
                            float var871 = ((float) var870);
                            float var1096 = var123;
                            float var872 = (var871 / var1096);
                            float var455 = (var454 + var872);
                            float var873 = 0.5F;
                            int var457 = Float.compare(var93, var873);
                            if (var457 <= 0) {
                                var729 = 1;
                            } else {
                                var729 = 0;
                            }
                            int var95 = var729;
                            float var874 = var431;
                            float var459 = (var93 + var874);
                            float var875 = var1340;
                            float var1097 = 0.5F;
                            float var876 = (var875 - var1097);
                            int var460 = Float.compare(var459, var876);
                            if (var460 >= 0) {
                                var738 = 1;
                            } else {
                                var738 = 0;
                            }
                            int var96 = var738;
                            float var877 = 0.5F;
                            int var462 = Float.compare(var455, var877);
                            if (var462 <= 0) {
                                var655 = 1;
                            } else {
                                var655 = 0;
                            }
                            int var97 = var655;
                            float var878 = var135;
                            float var464 = (var455 + var878);
                            float var879 = arg2;
                            float var1098 = var123;
                            float var880 = (var879 / var1098);
                            float var1099 = 0.5F;
                            float var881 = (var880 - var1099);
                            int var465 = Float.compare(var464, var881);
                            if (var465 >= 0) {
                                var736 = 1;
                            } else {
                                var736 = 0;
                            }
                            int var98 = var736;
                            if (var97 == 0) {
                                if (var95 != 0) {
                                }
                            }
                            var88 = 0.0F;
                            if (var97 == 0) {
                                if (var96 != 0) {
                                }
                            }
                            var89 = 0.0F;
                            if (var98 == 0) {
                                if (var96 != 0) {
                                }
                            }
                            var90 = 0.0F;
                            if (var98 == 0) {
                                if (var95 != 0) {
                                }
                            }
                            var91 = 0.0F;
                        }
                        if (var1353 != 0) {
                            float var1100 = var1360;
                            float var1168 = var1345;
                            float var1198 = var431;
                            float var1226 = var135;
                            float var10 = var88;
                            float var11 = var89;
                            float var12 = var90;
                            float var13 = var91;
                            int var1314 = -(var131);
                            float var1315 = ((float) var1314);
                            float var1316 = (var1315 / var123);
                            float var1324 = arg1;
                            float var1325 = (var1324 / var123);
                            float var1328 = arg2;
                            float var1329 = (var1328 / var123);
                            float var1331 = var442;
                            boolean var481 = HudElements.fillBlurRRect(arg0, var1100, var1168, var1198, var1226, var10, var11, var12, var13, 0.0F, var1316, var1325, var1329, var1331);
                            if (var481) {
                                var731 = 1;
                            } else {
                            }
                        }
                        var731 = 0;
                        int var1361 = var731;
                        if (var1361 != 0) {
                            var654 = var1354;
                        } else {
                            var654 = 1.0F;
                        }
                        float var1362 = var654;
                        if (var1351 == 0) {
                            String var882 = var190;
                            boolean var485 = "Outline Only".equalsIgnoreCase(var882);
                            if (var485) {
                                NVGColor var643 = fieldVal;
                                int var945 = var212;
                                float var1130 = 0.9F;
                                float var1185 = var442;
                                float var1131 = (var1130 * var1185);
                                applyStep(var643, var945, var1131);
                                long var644 = arg0;
                                NanoVG.nvgBeginPath(var644);
                                long var646 = arg0;
                                float var1132 = var1360;
                                float var1186 = var1345;
                                float var1217 = var431;
                                float var1244 = var135;
                                float var1258 = var88;
                                float var1275 = var89;
                                float var1290 = var90;
                                float var1301 = var91;
                                NanoVG.nvgRoundedRectVarying(var646, var1132, var1186, var1217, var1244, var1258, var1275, var1290, var1301);
                                long var648 = arg0;
                                NVGColor var1133 = fieldVal;
                                NanoVG.nvgStrokeColor(var648, var1133);
                                long var650 = arg0;
                                float var1134 = var216;
                                NanoVG.nvgStrokeWidth(var650, var1134);
                                long var652 = arg0;
                                NanoVG.nvgStroke(var652);
                            } else {
                                int var486 = var201;
                                if (var486 != 0) {
                                    float var487 = var116;
                                    float var883 = floatValue1;
                                    float var488 = (var487 + var883);
                                    int var884 = var1348;
                                    float var885 = ((float) var884);
                                    float var1101 = 22.0F;
                                    float var886 = (var885 * var1101);
                                    float var489 = (var488 + var886);
                                    float var887 = 360.0F;
                                    float var490 = (var489 % var887);
                                    float var1363 = var490;
                                    float var491 = var1363;
                                    float var888 = 38.0F;
                                    float var492 = (var491 + var888);
                                    float var889 = 360.0F;
                                    float var493 = (var492 % var889);
                                    float var1367 = var493;
                                    NVGColor var494 = fieldVal;
                                    float var890 = var1363;
                                    float var1102 = var210;
                                    float var1169 = 0.55F;
                                    float var1199 = var199;
                                    float var1227 = var442;
                                    float var1200 = (var1199 * var1227);
                                    float var1228 = var1362;
                                    float var1201 = (var1200 * var1228);
                                    applyStep(var494, var890, var1102, var1169, var1201);
                                    NVGColor var495 = fieldVal1;
                                    float var891 = var1367;
                                    float var1103 = var210;
                                    float var1170 = 0.48F;
                                    float var1202 = var199;
                                    float var1229 = var442;
                                    float var1203 = (var1202 * var1229);
                                    float var1230 = var1362;
                                    float var1204 = (var1203 * var1230);
                                    applyStep(var495, var891, var1103, var1170, var1204);
                                    int var496 = var26;
                                    if (var496 != 0) {
                                        float var635 = var1360;
                                        float var941 = var431;
                                        var730 = (var635 + var941);
                                    } else {
                                        var730 = var1360;
                                    }
                                    float var1370 = var730;
                                    int var497 = var26;
                                    if (var497 != 0) {
                                        var634 = var1360;
                                    } else {
                                        float var498 = var1360;
                                        float var892 = var431;
                                        var634 = (var498 + var892);
                                    }
                                    float var1373 = var634;
                                    long var499 = arg0;
                                    float var1104 = var1360;
                                    float var1171 = var1345;
                                    float var1205 = var431;
                                    float var1231 = var135;
                                    float var1247 = var88;
                                    float var1260 = var89;
                                    float var1277 = var90;
                                    float var1292 = var91;
                                    float var1303 = var1370;
                                    float var1317 = var1345;
                                    float var1326 = var1373;
                                    float var1330 = var1345;
                                    NVGColor var1332 = fieldVal;
                                    int var1333 = computeColorOrIndex(var1332);
                                    NVGColor var19 = fieldVal1;
                                    int var1334 = computeColorOrIndex(var19);
                                    ClarityDraw.fillRRectVaryingLinear(var499, var1104, var1171, var1205, var1231, var1247, var1260, var1277, var1292, var1303, var1317, var1326, var1330, var1333, var1334);
                                } else {
                                    float var636 = var199;
                                    float var942 = var442;
                                    float var637 = (var636 * var942);
                                    float var943 = var1362;
                                    float var638 = (var637 * var943);
                                    float var944 = 255.0F;
                                    float var639 = (var638 * var944);
                                    int var640 = Math.round(var639);
                                    int var1366 = var640;
                                    long var641 = arg0;
                                    float var1129 = var1360;
                                    float var1184 = var1345;
                                    float var1216 = var431;
                                    float var1243 = var135;
                                    float var1257 = var88;
                                    float var1274 = var89;
                                    float var1289 = var90;
                                    float var1300 = var91;
                                    int var1311 = var1366;
                                    int var1321 = 24;
                                    int var1312 = (var1311 << var1321);
                                    int var1322 = var192;
                                    int var1327 = 16777215;
                                    int var1323 = (var1322 & var1327);
                                    int var1313 = (var1312 | var1323);
                                    ClarityDraw.fillRRectVarying(var641, var1129, var1184, var1216, var1243, var1257, var1274, var1289, var1300, var1313);
                                }
                                int var501 = var165;
                                if (var501 != 0) {
                                    String var502 = "Outline Only";
                                    String var893 = var190;
                                    boolean var503 = var502.equalsIgnoreCase(var893);
                                    if (!var503) {
                                        NVGColor var504 = fieldVal;
                                        int var894 = var169;
                                        float var1105 = 1.0F;
                                        float var1172 = var442;
                                        float var1106 = (var1105 * var1172);
                                        applyStep(var504, var894, var1106);
                                        long var505 = arg0;
                                        NanoVG.nvgBeginPath(var505);
                                        long var507 = arg0;
                                        float var1107 = var1360;
                                        float var1173 = var1345;
                                        float var1206 = var431;
                                        float var1232 = var135;
                                        float var1248 = var88;
                                        float var1261 = var89;
                                        float var1278 = var90;
                                        float var1293 = var91;
                                        NanoVG.nvgRoundedRectVarying(var507, var1107, var1173, var1206, var1232, var1248, var1261, var1278, var1293);
                                        long var509 = arg0;
                                        NVGColor var1108 = fieldVal;
                                        NanoVG.nvgStrokeColor(var509, var1108);
                                        long var511 = arg0;
                                        float var1109 = var174;
                                        NanoVG.nvgStrokeWidth(var511, var1109);
                                        long var513 = arg0;
                                        NanoVG.nvgStroke(var513);
                                        int var515 = var178;
                                        if (var515 != 0) {
                                            float var516 = var187;
                                            float var895 = 0.0F;
                                            int var517 = Float.compare(var516, var895);
                                            if (var517 > 0) {
                                                long var518 = arg0;
                                                float var1110 = var1360;
                                                float var1174 = var1345;
                                                float var1207 = var431;
                                                float var1233 = var135;
                                                float var1249 = var88;
                                                float var1262 = var89;
                                                float var1250 = Math.max(var1249, var1262);
                                                float var1263 = var187;
                                                float var1279 = var442;
                                                float var1264 = (var1263 * var1279);
                                                float var1280 = 255.0F;
                                                float var1265 = (var1264 * var1280);
                                                int var1266 = Math.round(var1265);
                                                int var1281 = 24;
                                                int var1267 = (var1266 << var1281);
                                                int var1282 = var180;
                                                int var1294 = 16777215;
                                                int var1283 = (var1282 & var1294);
                                                int var1268 = (var1267 | var1283);
                                                ClarityDraw.topHighlight(var518, var1110, var1174, var1207, var1233, var1250, var1268);
                                            }
                                            int var520 = var242;
                                            if (var520 != 0) {
                                                int var521 = var1351;
                                                if (var521 == 0) {
                                                    int var522 = var26;
                                                    if (var522 != 0) {
                                                        var633 = var1360;
                                                    } else {
                                                        float var631 = var1360;
                                                        float var939 = var431;
                                                        float var632 = (var631 + var939);
                                                        float var940 = var249;
                                                        var633 = (var632 - var940);
                                                    }
                                                    float var1364 = var633;
                                                    NVGColor var523 = fieldVal;
                                                    int var896 = var245;
                                                    float var1111 = 0.92F;
                                                    float var1175 = var442;
                                                    float var1112 = (var1111 * var1175);
                                                    applyStep(var523, var896, var1112);
                                                    long var524 = arg0;
                                                    NanoVG.nvgBeginPath(var524);
                                                    float var526 = var249;
                                                    float var897 = 2.0F;
                                                    float var527 = (var526 / var897);
                                                    float var1368 = var527;
                                                    int var528 = var26;
                                                    if (var528 != 0) {
                                                        float var529 = var88;
                                                        float var898 = 0.0F;
                                                        int var530 = Float.compare(var529, var898);
                                                        if (var530 == 0) {
                                                            var630 = 0.0F;
                                                        } else {
                                                            var630 = var1368;
                                                        }
                                                    } else {
                                                        var630 = var1368;
                                                    }
                                                    float var1371 = var630;
                                                    int var531 = var26;
                                                    if (var531 != 0) {
                                                        float var532 = var91;
                                                        float var899 = 0.0F;
                                                        int var533 = Float.compare(var532, var899);
                                                        if (var533 == 0) {
                                                            var534 = 0.0F;
                                                        } else {
                                                            var534 = var1368;
                                                        }
                                                    } else {
                                                        var534 = var1368;
                                                    }
                                                    float var1374 = var534;
                                                    int var535 = var26;
                                                    if (var535 != 0) {
                                                        var732 = var1368;
                                                    } else {
                                                        float var628 = var89;
                                                        float var938 = 0.0F;
                                                        int var629 = Float.compare(var628, var938);
                                                        if (var629 == 0) {
                                                            var732 = 0.0F;
                                                        } else {
                                                            var732 = var1368;
                                                        }
                                                        float var1376 = var732;
                                                        int var536 = var26;
                                                        if (var536 != 0) {
                                                            var627 = var1368;
                                                        } else {
                                                            float var625 = var90;
                                                            float var937 = 0.0F;
                                                            int var626 = Float.compare(var625, var937);
                                                            if (var626 == 0) {
                                                                var627 = 0.0F;
                                                            } else {
                                                                var627 = var1368;
                                                            }
                                                            float var99 = var627;
                                                            long var537 = arg0;
                                                            float var1113 = var1364;
                                                            float var1176 = var1345;
                                                            float var1208 = var249;
                                                            float var1234 = var135;
                                                            float var1251 = var1371;
                                                            float var1269 = var1376;
                                                            float var1284 = var99;
                                                            float var1295 = var1374;
                                                            NanoVG.nvgRoundedRectVarying(var537, var1113, var1176, var1208, var1234, var1251, var1269, var1284, var1295);
                                                            long var539 = arg0;
                                                            NVGColor var1114 = fieldVal;
                                                            NanoVG.nvgFillColor(var539, var1114);
                                                            long var541 = arg0;
                                                            NanoVG.nvgFill(var541);
                                                        }
                                                    }
                                                }
                                                float var543 = var1345;
                                                float var900 = var135;
                                                float var1115 = 0.5F;
                                                float var901 = (var900 * var1115);
                                                float var544 = (var543 + var901);
                                                int var545 = Math.round(var544);
                                                float var546 = ((float) var545);
                                                float var1365 = var546;
                                                float var547 = var225;
                                                float var902 = var442;
                                                float var548 = (var547 * var902);
                                                float var903 = 255.0F;
                                                float var549 = (var548 * var903);
                                                int var550 = Math.round(var549);
                                                int var904 = 24;
                                                int var551 = (var550 << var904);
                                                int var905 = var218;
                                                int var1116 = 16777215;
                                                int var906 = (var905 & var1116);
                                                int var552 = (var551 | var906);
                                                int var1369 = var552;
                                                float var553 = var238;
                                                float var907 = var225;
                                                float var554 = (var553 * var907);
                                                float var908 = var442;
                                                float var555 = (var554 * var908);
                                                float var1372 = var555;
                                                int var556 = var253;
                                                if (var556 != 0) {
                                                    Map var557 = entriesMap3;
                                                    String var909 = var427;
                                                    Object var558 = var557.get(var909);
                                                    var733 = ((String) var558);
                                                } else {
                                                    var733 = null;
                                                }
                                                String var1375 = var733;
                                                int var559 = var277;
                                                if (var559 == 0) {
                                                    String var560 = var1375;
                                                    if (var560 != null) {
                                                    }
                                                }
                                                int var561 = var277;
                                                if (var561 != 0) {
                                                    String var618 = var427;
                                                    var562 = resolveLabel(var618);
                                                } else {
                                                    var562 = null;
                                                }
                                                String var1377 = var562;
                                                String var563 = var1375;
                                                if (var563 == null) {
                                                    var737 = null;
                                                } else {
                                                    String var564 = var1375;
                                                    String var910 = var256;
                                                    var737 = resolveLabel(var564, var910);
                                                }
                                                String var1379 = var737;
                                                float var565 = var143;
                                                float var911 = 0.8F;
                                                float var566 = (var565 * var911);
                                                float var567 = var143;
                                                float var912 = var273;
                                                float var568 = (var567 * var912);
                                                long var569 = arg0;
                                                String var1117 = "inter-sb";
                                                float var1177 = var143;
                                                float var1209 = 0.0F;
                                                String var1235 = var427;
                                                float var571 = ClarityDraw.textWidth(var569, var1117, var1177, var1209, var1235);
                                                String var572 = var1377;
                                                if (var572 == null) {
                                                    var617 = 0.0F;
                                                } else {
                                                    long var573 = arg0;
                                                    String var1118 = "inter-sb";
                                                    float var1178 = var566;
                                                    float var1210 = 0.0F;
                                                    String var1236 = var1377;
                                                    var617 = ClarityDraw.textWidth(var573, var1118, var1178, var1210, var1236);
                                                }
                                                float var103 = var617;
                                                String var575 = var1379;
                                                if (var575 == null) {
                                                    var734 = 0.0F;
                                                } else {
                                                    long var576 = arg0;
                                                    String var1119 = "inter-sb";
                                                    float var1179 = var568;
                                                    float var1211 = 0.0F;
                                                    String var1237 = var1379;
                                                    var734 = ClarityDraw.textWidth(var576, var1119, var1179, var1211, var1237);
                                                }
                                                float var104 = var734;
                                                String var578 = var1379;
                                                if (var578 == null) {
                                                    var735 = 0.0F;
                                                } else {
                                                    float var616 = var143;
                                                    float var934 = 0.4F;
                                                    var735 = (var616 * var934);
                                                }
                                                float var105 = var735;
                                                float var579 = var571;
                                                float var913 = var103;
                                                float var580 = (var579 + var913);
                                                float var914 = var105;
                                                float var581 = (var580 + var914);
                                                float var915 = var104;
                                                float var582 = (var581 + var915);
                                                float var583 = var1360;
                                                float var916 = var431;
                                                float var1120 = 0.5F;
                                                float var917 = (var916 * var1120);
                                                float var584 = (var583 + var917);
                                                float var918 = var582;
                                                float var1121 = 0.5F;
                                                float var919 = (var918 * var1121);
                                                float var585 = (var584 - var919);
                                                int var586 = Math.round(var585);
                                                float var587 = ((float) var586);
                                                int var588 = 17;
                                                long var589 = arg0;
                                                float var1122 = var143;
                                                int var1180 = var1369;
                                                int var1212 = var588;
                                                float var1238 = var587;
                                                float var1252 = var1365;
                                                String var1270 = var427;
                                                int var1285 = var229;
                                                int var1296 = var231;
                                                float var1304 = var1372;
                                                applyStep(var589, var1122, var1180, var1212, var1238, var1252, var1270, var1285, var1296, var1304);
                                                float var591 = var587;
                                                float var920 = var571;
                                                float var592 = (var591 + var920);
                                                var1380 = var592;
                                                String var593 = var1377;
                                                if (var593 != null) {
                                                    float var594 = var225;
                                                    float var921 = var284;
                                                    float var595 = (var594 * var921);
                                                    float var922 = var442;
                                                    float var596 = (var595 * var922);
                                                    float var923 = 255.0F;
                                                    float var597 = (var596 * var923);
                                                    int var598 = Math.round(var597);
                                                    int var924 = 24;
                                                    int var599 = (var598 << var924);
                                                    int var925 = var218;
                                                    int var1123 = 16777215;
                                                    int var926 = (var925 & var1123);
                                                    int var600 = (var599 | var926);
                                                    long var601 = arg0;
                                                    float var1124 = var566;
                                                    int var1181 = var600;
                                                    int var1213 = var588;
                                                    float var1239 = var1380;
                                                    float var1253 = var1365;
                                                    String var1271 = var1377;
                                                    int var1286 = var229;
                                                    int var1297 = var231;
                                                    float var1305 = var1372;
                                                    float var1318 = var284;
                                                    float var1306 = (var1305 * var1318);
                                                    applyStep(var601, var1124, var1181, var1213, var1239, var1253, var1271, var1286, var1297, var1306);
                                                    float var603 = var1380;
                                                    float var927 = var103;
                                                    float var604 = (var603 + var927);
                                                    var1380 = var604;
                                                }
                                                String var605 = var1379;
                                                if (var605 != null) {
                                                    float var606 = var268;
                                                    float var928 = var442;
                                                    float var607 = (var606 * var928);
                                                    float var929 = 255.0F;
                                                    float var608 = (var607 * var929);
                                                    int var609 = Math.round(var608);
                                                    int var930 = 24;
                                                    int var610 = (var609 << var930);
                                                    int var931 = var261;
                                                    int var611 = (var610 | var931);
                                                    int var1381 = var611;
                                                    long var612 = arg0;
                                                    float var1125 = var568;
                                                    int var1182 = var1381;
                                                    int var1214 = var588;
                                                    float var1240 = var1380;
                                                    float var1254 = var105;
                                                    float var1241 = (var1240 + var1254);
                                                    float var1255 = var1365;
                                                    String var1272 = var1379;
                                                    int var1287 = var229;
                                                    int var1298 = var231;
                                                    float var1307 = var238;
                                                    float var1319 = var268;
                                                    float var1308 = (var1307 * var1319);
                                                    float var1320 = var442;
                                                    float var1309 = (var1308 * var1320);
                                                    applyStep(var612, var1125, var1182, var1214, var1241, var1255, var1272, var1287, var1298, var1309);
                                                    float var619 = var1360;
                                                    float var935 = var431;
                                                    float var1127 = 0.5F;
                                                    float var936 = (var935 * var1127);
                                                    float var620 = (var619 + var936);
                                                    int var621 = Math.round(var620);
                                                    float var622 = ((float) var621);
                                                    float var1378 = var622;
                                                    long var623 = arg0;
                                                    float var1128 = var143;
                                                    int var1183 = var1369;
                                                    int var1215 = 18;
                                                    float var1242 = var1378;
                                                    float var1256 = var1365;
                                                    String var1273 = var427;
                                                    int var1288 = var229;
                                                    int var1299 = var231;
                                                    float var1310 = var1372;
                                                    applyStep(var623, var1128, var1183, var1215, var1242, var1256, var1273, var1288, var1299, var1310);
                                                }
                                                float var614 = var1345;
                                                float var932 = var135;
                                                float var1126 = var157;
                                                float var933 = (var932 + var1126);
                                                float var615 = (var614 + var933);
                                                var1345 = var615;
                                                var1348 = (var1348 + 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    var83 = (var83 + 1);
                }
                float var658 = var1356;
                float var948 = 0.0F;
                int var659 = Float.compare(var658, var948);
                if (var659 > 0) {
                    boolean var660 = HudLayout.isEditing();
                    if (var660) {
                        int var661 = var26;
                        if (var661 != 0) {
                            float var662 = var1342;
                            float var949 = var1356;
                            var666 = (var662 - var949);
                        } else {
                            var666 = var1342;
                        }
                        float var1359 = var666;
                        String var663 = "ArrayList";
                        float var950 = var1359;
                        float var1136 = var123;
                        float var951 = (var950 * var1136);
                        int var1137 = var131;
                        float var1138 = ((float) var1137);
                        float var1187 = var1356;
                        float var1218 = var123;
                        float var1188 = (var1187 * var1218);
                        float var1219 = var1357;
                        float var1245 = var123;
                        float var1220 = (var1219 * var1245);
                        HudLayout.report(var663, var951, var1138, var1188, var1220);
                    }
                    long var664 = arg0;
                    NanoVG.nvgRestore(var664);
                    return;
                    int var816 = var127;
                    var817 = ((float) var816);
                }
            }
            int var1064 = var131;
            float var1065 = ((float) var1064);
            float var1162 = var1336;
            float var1195 = (var135 * var123);
            HudLayout.report("ArrayList", var817, var1065, var1162, var1195);
            return;
        }
    }

    private static String resolveLabel(String arg0, String arg1) {
        if ("Brackets".equalsIgnoreCase(arg1)) {
            return "[" + (arg0) + "]";
        }
        if ("Dash".equalsIgnoreCase(arg1)) {
            return "- " + (arg0);
        }
        return arg0;
    }

    private static float computeFloatMetric(long arg0, String arg1, String arg2, float arg3, boolean arg4, String arg5, float arg6) {
        float var19 = 0.0F;
        var19 = ClarityDraw.textWidth(arg0, "inter-sb", arg3, 0.0F, arg1);
        if (arg4) {
            float var22 = var19;
            float var38 = (arg3 * 0.8F);
            String var44 = resolveLabel(arg1);
            float var28 = ClarityDraw.textWidth(arg0, "inter-sb", var38, 0.0F, var44);
            var19 = (var22 + var28);
        }
        if (arg2 != null) {
            float var25 = var19;
            float var30 = (arg3 * 0.4F);
            float var43 = (arg3 * arg6);
            String var47 = resolveLabel(arg2, arg5);
            float var35 = ClarityDraw.textWidth(arg0, "inter-sb", var43, 0.0F, var47);
            float var31 = (var30 + var35);
            var19 = (var25 + var31);
        }
        return var19;
    }

    private static String resolveLabel(String arg0) {
        int var9 = arg0.length();
        int var8 = Math.min(2, var9);
        return " [" + (arg0.substring(0, var8).toUpperCase()) + "]";
    }

    private static void applyStep(long arg0, float arg1, int arg2, int arg3, float arg4, float arg5, String arg6, boolean arg7, int arg8, float arg9) {
        if (arg7) {
            if (Float.compare(arg9, 0.01F) > 0) {
                int var34 = (Math.round((computeFloatMetric(arg9, 0.0F, 1.0F) * 255.0F)) << 24);
                int var38 = (arg8 & 16777215);
                int var35 = (var34 | var38);
                float var42 = (arg4 + 1.0F);
                float var45 = (arg5 + 1.0F);
                ClarityDraw.hudText(arg0, arg1, var35, arg3, var42, var45, arg6);
            }
            ClarityDraw.hudText(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
            return;
        }
    }

    private static int computeColorOrIndex(ArrayListModule arg0, String arg1, int arg2) {
        int var25 = 0;
        String var15 = null;
        Object var8 = arg0.getSetting(arg1, null);
        if (ThemeColors.isThemeValue(var8)) {
            if (var8.toString().trim().equalsIgnoreCase("theme2")) {
                var25 = ThemeColors.getAccent2Rgb();
            } else {
                var25 = ThemeColors.getAccentRgb();
            }
            return (var25 & 16777215);
        }
        if (ThemeColors.isEnabled()) {
            return (ThemeColors.getAccentRgb() & 16777215);
        }
        if (var8 == null) {
            var15 = null;
        } else {
            var15 = var8.toString();
        }
        return (computeColorOrIndex(var15, arg2) & 16777215);
    }

    private static int computeColorOrIndex1(ArrayListModule arg0, String arg1, int arg2) {
        String var21 = null;
        int var22 = 0;
        Object var8 = arg0.getSetting(arg1, null);
        if (ThemeColors.isThemeValue(var8)) {
            if (var8.toString().trim().equalsIgnoreCase("theme2")) {
                var22 = ThemeColors.getAccent2Rgb();
            }
        } else {
            if (var8 == null) {
                var21 = null;
            } else {
                var21 = var8.toString();
            }
            return (computeColorOrIndex(var21, arg2) & 16777215);
            var22 = ThemeColors.getAccentRgb();
        }
        return (var22 & 16777215);
    }

    public static boolean isEligible() {
        int var84 = 0;
        Iterator var9 = entriesMap2.entrySet().iterator();
        while (true) {
            if (!var9.hasNext()) break;
            float var18 = ((Float) ((Map.Entry) var9.next()).getValue());
            if (Float.compare(var18, 0.985F) >= 0) continue;
            if (Float.compare(var18, 0.015F) <= 0) continue;
        }
        return false;
        List var24 = ModuleRegistry.all();
        var84 = 0;
        while (true) {
            int var26 = var84;
            int var68 = var24.size();
            if (var26 >= var68) break;
            int var69 = var84;
            ModuleBase var29 = ((ModuleBase) var24.get(var69));
            if (!(var29 instanceof ArrayListModule)) {
                if (var29.isEnabled()) {
                    Map var34 = entriesMap1;
                    String var71 = var29.getName();
                    Float var80 = 0.0F;
                    float var37 = ((Float) var34.getOrDefault(var71, var80));
                    if (var37 == 0.0F) {
                    } else {
                        Map var40 = entriesMap;
                        String var74 = var29.getName();
                        Float var82 = 0.0F;
                        float var43 = ((Float) var40.getOrDefault(var74, var82));
                        if (Float.compare(Math.abs((var37 - var43)), 0.05F) > 0) {
                            return false;
                        }
                    }
                }
                var84 = (var84 + 1);
            }
        }
        Iterator var51 = entriesMap.entrySet().iterator();
        while (true) {
            if (!var51.hasNext()) break;
            Map.Entry var56 = ((Map.Entry) var51.next());
            Map var57 = entriesMap1;
            Object var78 = var56.getKey();
            if (var57.containsKey(var78)) continue;
            if (Float.compare(((Float) var56.getValue()), 0.5F) <= 0) continue;
        }
        return false;
        return true;
    }

    private static boolean isEligible(ArrayList arg0, String arg1) {
        int var4 = 0;
        var4 = 0;
        while (true) {
            int var5 = var4;
            int var13 = arg0.size();
            if (var5 >= var13) break;
            int var14 = var4;
            if (((ModuleBase) arg0.get(var14)).getName().equals(arg1)) {
                return true;
            }
            var4 = (var4 + 1);
        }
        return false;
    }

    private static void applyStep(Map arg0, float arg1) {
        int var7 = 0;
        Set var11 = arg0.keySet();
        String[] var46 = new String[0];
        String[] var13 = ((String[]) var11.toArray(var46));
        int var15 = var13.length;
        var7 = 0;
        while (true) {
            if (var7 < var15) {
                int var48 = var7;
                String var19 = var13[var48];
                float var23 = ((Float) arg0.get(var19));
                float var51 = (1.0F - arg1);
                float var24 = (var23 * var51);
                Map var25 = entriesMap2;
                Float var64 = 1.0F;
                float var28 = ((Float) var25.getOrDefault(var19, var64));
                float var54 = (1.0F - arg1);
                float var29 = (var28 * var54);
                Map var30 = entriesMap2;
                Float var67 = var29;
                Object var31 = var30.put(var19, var67);
                if (Float.compare(var24, 0.5F) < 0) {
                    if (Float.compare(var29, 0.03F) < 0) {
                        Object var37 = arg0.remove(var19);
                        Object var39 = entriesMap1.remove(var19);
                        Object var41 = entriesMap2.remove(var19);
                        Object var43 = entriesMap3.remove(var19);
                    }
                }
            } else {
                return;
                Float var69 = var24;
                Object var45 = arg0.put(var19, var69);
            }
            var7 = (var7 + 1);
        }
    }

    private static float computeFloatMetric(float arg0, float arg1, float arg2) {
        float var7 = 0.0F;
        if (Float.compare(arg0, arg1) < 0) {
            var7 = arg1;
        } else {
            var7 = Math.min(arg0, arg2);
        }
        return var7;
    }

    private static int computeColorOrIndex(int arg0, int arg1, float arg2) {
        float var13 = computeFloatMetric(arg2, 0.0F, 1.0F);
        int var16 = ((arg0 >> 16) & 255);
        int var19 = ((arg0 >> 8) & 255);
        int var21 = (arg0 & 255);
        int var24 = ((arg1 >> 16) & 255);
        int var27 = ((arg1 >> 8) & 255);
        int var29 = (arg1 & 255);
        float var31 = ((float) var16);
        float var50 = (((float) (var24 - var16)) * var13);
        int var34 = (Math.round((var31 + var50)) << 16);
        float var53 = ((float) var19);
        float var66 = (((float) (var27 - var19)) * var13);
        int var56 = (Math.round((var53 + var66)) << 8);
        int var35 = (var34 | var56);
        float var58 = ((float) var21);
        float var71 = (((float) (var29 - var21)) * var13);
        int var60 = Math.round((var58 + var71));
        return (var35 | var60);
    }

    private static float computeFloatMetric(int arg0) {
        float var10 = 0.0F;
        float var45 = 0.0F;
        float var15 = (((float) ((arg0 >> 16) & 255)) / 255.0F);
        float var20 = (((float) ((arg0 >> 8) & 255)) / 255.0F);
        float var24 = (((float) (arg0 & 255)) / 255.0F);
        float var64 = Math.max(var20, var24);
        float var26 = Math.max(var15, var64);
        float var66 = Math.min(var20, var24);
        float var28 = Math.min(var15, var66);
        float var30 = (var26 - var28);
        if (Float.compare(var30, 1.0E-4F) <= 0) {
            return 0.0F;
        }
        if (var26 == var15) {
            var10 = (((var20 - var24) / var30) % 6.0F);
        } else {
            if (var26 == var20) {
                var10 = (((var24 - var15) / var30) + 2.0F);
            } else {
                var10 = (((var15 - var20) / var30) + 4.0F);
            }
            float var41 = (var10 * 60.0F);
            if (Float.compare(var41, 0.0F) < 0) {
                var45 = (var41 + 360.0F);
            } else {
                var45 = var41;
            }
            return var45;
        }
    }

    private static int computeColorOrIndex(String arg0, int arg1) {
        String var18 = null;
        if (arg0 != null) {
            if (arg0.isEmpty()) {
            }
        }
        return arg1;
        if (arg0.startsWith("#")) {
            var18 = arg0.substring(1);
        } else {
            var18 = arg0;
        }
        String var4 = var18;
        try {
            int var13 = ((int) Long.parseLong(var4, 16));
        } catch (NumberFormatException ignored) {
        }
        return var13;
    }

    private static void applyStep(NVGColor arg0, int arg1, float arg2) {
        float var14 = (((float) ((arg1 >> 16) & 255)) / 255.0F);
        float var19 = (((float) ((arg1 >> 8) & 255)) / 255.0F);
        float var23 = (((float) (arg1 & 255)) / 255.0F);
        NVGColor var25 = NanoVG.nvgRGBAf(var14, var19, var23, arg2, arg0);
    }

    private static void applyStep(NVGColor arg0, float arg1, float arg2, float arg3, float arg4) {
        float var58 = 0.0F;
        float var59 = 0.0F;
        float var60 = 0.0F;
        float var30 = 0.0F;
        if (arg2 == 0.0F) {
            var60 = arg3;
            var59 = arg3;
            var58 = arg3;
        } else {
            if (Float.compare(arg3, 0.5F) < 0) {
                float var42 = (1.0F + arg2);
                var30 = (arg3 * var42);
            } else {
                float var16 = (arg3 + arg2);
                float var34 = (arg3 * arg2);
                var30 = (var16 - var34);
            }
            float var10 = var30;
            float var19 = ((2.0F * arg3) - var10);
            float var45 = ((arg1 / 360.0F) + 0.33333334F);
            var58 = computeFloatMetric1(var19, var10, var45);
            float var47 = (arg1 / 360.0F);
            var59 = computeFloatMetric1(var19, var10, var47);
            float var50 = ((arg1 / 360.0F) - 0.33333334F);
            var60 = computeFloatMetric1(var19, var10, var50);
        }
        float var26 = var58;
        float var40 = var59;
        float var51 = var60;
        NVGColor var27 = NanoVG.nvgRGBAf(var26, var40, var51, arg4, arg0);
    }

    private static float computeFloatMetric1(float arg0, float arg1, float arg2) {
        if (Float.compare(arg2, 0.0F) < 0) {
            arg2 = (arg2 + 1.0F);
        }
        if (Float.compare(arg2, 1.0F) > 0) {
            arg2 = (arg2 - 1.0F);
        }
        if (Float.compare(arg2, 0.16666667F) < 0) {
            float var38 = ((arg1 - arg0) * 6.0F);
            float var45 = arg2;
            float var39 = (var38 * var45);
            return (arg0 + var39);
        }
        if (Float.compare(arg2, 0.5F) < 0) {
            return arg1;
        }
        if (Float.compare(arg2, 0.6666667F) < 0) {
            float var33 = (arg1 - arg0);
            float var6 = arg2;
            float var41 = (0.6666667F - var6);
            float var35 = ((var33 * var41) * 6.0F);
            return (arg0 + var35);
        }
        return arg0;
    }

    private static int computeColorOrIndex(NVGColor arg0) {
        int var11 = (((int) (arg0.r() * 255.0F)) & 255);
        int var16 = (((int) (arg0.g() * 255.0F)) & 255);
        int var21 = (((int) (arg0.b() * 255.0F)) & 255);
        int var28 = ((((int) (arg0.a() * 255.0F)) & 255) << 24);
        int var41 = (var11 << 16);
        int var29 = (var28 | var41);
        int var43 = (var16 << 8);
        return ((var29 | var43) | var21);
    }

    static {
        fieldVal = NVGColor.create();
        fieldVal1 = NVGColor.create();
        HashMap var2 = new HashMap();
        entriesMap = var2;
        HashMap var3 = new HashMap();
        entriesMap1 = var3;
        HashMap var4 = new HashMap();
        entriesMap2 = var4;
        floatValue1 = 0.0F;
        timestampMs = 0L;
        labelText = "";
        HashMap var9 = new HashMap();
        entriesMap3 = var9;
    }

}
