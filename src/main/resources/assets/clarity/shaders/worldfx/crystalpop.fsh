#version 330 core


in vec3 vPos;
in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform vec3  uA, uB;
uniform float uIntensity;

const float TAU = 6.2831853;
float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
float n1(float x, float s) { float i = floor(x), f = fract(x); f = f * f * (3.0 - 2.0 * f); return mix(hash11(i + s), hash11(i + 1.0 + s), f); }
float outCubic(float t) { t = 1.0 - clamp(t, 0.0, 1.0); return 1.0 - t * t * t; }

void main() {
    float t = clamp(vAux.x, 0.0, 1.0);
    float mode = floor(vAux.y + 1e-3), seed = fract(vAux.y) * 97.0;
    vec2 uv = vUV;
    float r = length(uv);
    vec3 col = vec3(0.0);

    if (mode < 0.5) {
        float ang = atan(uv.y, uv.x);
        float fade = 1.0 - t;
        
        col += mix(vec3(1.0), uA, 0.35) * exp(-r * r * 16.0) * pow(fade, 3.0) * 1.8;
        
        float Rs = outCubic(t) * 0.82;
        float w = 0.035 + 0.09 * t;
        float shell = exp(-pow((r - Rs) / w, 2.0)) * pow(fade, 1.6) * (0.65 + 0.35 * n1(ang * 6.0 / TAU * 8.0, seed));
        col += mix(uA, uB, smoothstep(0.0, 0.8, r)) * shell * 1.3;
        
        float streak = pow(n1(ang / TAU * 26.0, seed + 11.0), 5.0) * smoothstep(Rs + 0.15, Rs * 0.3, r) * smoothstep(0.02, 0.12, r);
        col += mix(uA, vec3(1.0), 0.4) * streak * pow(fade, 2.0) * 1.2;
        
        for (int i = 0; i < 12; i++) {
            float fi = float(i);
            float a = hash11(fi * 3.7 + seed) * TAU;
            float dist = outCubic(t) * (0.45 + 0.5 * hash11(fi * 1.3 + seed));
            vec2 sp = vec2(cos(a), sin(a)) * dist - vec2(0.0, t * t * 0.25);   
            float d = length(uv - sp);
            col += mix(vec3(1.0), uA, 0.3) * exp(-d * d * 1800.0) * fade * 1.4;
        }
    } else {
        
        float Rg = outCubic(t);
        float ring = exp(-pow((r - Rg) / 0.05, 2.0)) * pow(1.0 - t, 2.0);
        float scorch = exp(-r * r * 7.0) * pow(1.0 - t, 4.0) * 0.35;
        col += mix(uA, uB, 0.4) * (ring * 1.2 + scorch);
    }

    col *= uIntensity;
    if (max(col.r, max(col.g, col.b)) < 0.002) discard;
    fragColor = vec4(col, 0.0);
}
