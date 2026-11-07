#version 330 core
in vec2 vUV;
in vec4 vData;
out vec4 fragColor;

uniform vec3  uColor1, uColor2;
uniform int   uRainbow;
uniform float uBass, uTime, uOpacity, uRadius;
uniform float uBeatAges[4];
uniform int   uGround;

vec3 hsv2rgb(vec3 c) { vec3 p = abs(fract(c.xxx + vec3(1.0, 2.0 / 3.0, 1.0 / 3.0)) * 6.0 - 3.0); return c.z * mix(vec3(1.0), clamp(p - 1.0, 0.0, 1.0), c.y); }
float ring(float r, float R, float w) { float x = (r - R) / w; return exp(-x * x); }

void main() {
    if (vData.w < 0.0) {                                  
        if (uGround == 0) discard;
        float r = length(vUV);
        if (r > 1.7) discard;
        float ang = atan(vUV.y, vUV.x) / 6.2831853 + 0.5;
        vec3 rc = uRainbow == 1 ? hsv2rgb(vec3(fract(ang + uTime * 0.1), 0.75, 1.0)) : mix(uColor1, uColor2, 0.5 + 0.5 * sin(ang * 6.2831853 * 2.0 + uTime));
        float core = ring(r, 1.0, 0.03 + 0.03 * uBass) * (0.6 + 1.2 * uBass);
        float glow = ring(r, 1.0, 0.18 + 0.2 * uBass) * (0.25 + 0.6 * uBass);
        float waves = 0.0;
        for (int i = 0; i < 4; i++) {
            float t = uBeatAges[i];
            if (t < 0.0 || t > 0.8) continue;
            float R = 1.0 + t * 0.9;
            waves += ring(r, R, 0.03 + 0.05 * t) * (1.0 - t / 0.8);
        }
        vec3 col = rc * (core + glow) + mix(rc, vec3(1.0), 0.4) * waves * 1.2;
        col *= smoothstep(1.7, 1.5, r);
        fragColor = vec4(col * uOpacity, 0.0);
        return;
    }
    float y = vUV.y, x = abs(vUV.x);
    float lvl = vData.x, peak = vData.z;
    float bar = step(y, lvl) * smoothstep(1.0, 0.7, x);
    
    float top = smoothstep(0.02, 0.0, y - lvl + 0.0) ;
    vec3 base = uRainbow == 1 ? hsv2rgb(vec3(fract(vData.y + uTime * 0.1), 0.75, 1.0)) : uColor1;
    vec3 tip  = uRainbow == 1 ? mix(base, vec3(1.0), 0.4) : uColor2;
    vec3 bc = mix(base, tip, smoothstep(0.0, max(lvl, 0.05), y));
    float capLine = exp(-pow((y - peak) * vData.w / 0.018, 2.0)) * smoothstep(1.0, 0.6, x);
    float glow = exp(-max(y - lvl, 0.0) * vData.w * 14.0) * (1.0 - bar) * smoothstep(1.0, 0.2, x) * 0.35;
    vec3 col = bc * bar * (0.75 + 0.5 * uBass) + mix(tip, vec3(1.0), 0.5) * capLine * 0.9 + tip * glow;
    fragColor = vec4(col * uOpacity, bar * 0.35 * uOpacity);
}
