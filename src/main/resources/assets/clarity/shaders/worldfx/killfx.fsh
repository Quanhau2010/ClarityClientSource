#version 330 core


in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform float uTime;
uniform int   uStyle;   
uniform vec3  uColor2;

const float PI = 3.14159265, TAU = 6.2831853;
float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise(vec2 p) { vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y); }
float fbm(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 4; i++) { s += a * vnoise(p); p = p * 2.03 + 7.1; a *= 0.5; } return s / 0.9375; }
float jag(float x, float seed) { float i = floor(x), f = fract(x); return mix(hash11(i + seed), hash11(i + 1.0 + seed), f) - 0.5; }
float ring(float r, float R, float w) { float x = (r - R) / w; return exp(-x * x); }

void main() {
    vec2  uv = vUV;
    float p = clamp(vAux.x, 0.0, 1.0);
    float part = floor(vAux.y + 1e-3);
    float seed = fract(vAux.y);
    vec3  c1 = vColor.rgb, c2 = uColor2;
    float r = length(uv);
    vec3  col = vec3(0.0);
    float a = 0.0;
    float e = 1.0 - pow(1.0 - p, 3.0);

    if (uStyle == 0) {                                      
        if (part < 0.5) {
            if (r > 1.3) discard;
            float ang = atan(uv.y, uv.x);
            float R = 0.12 + 0.88 * e;
            float n = vnoise(vec2(ang * 4.0 + seed * 20.0, p * 3.0));
            float shell = ring(r, R + (n - 0.5) * 0.06, 0.035 + 0.12 * p) * pow(1.0 - p, 1.5);
            float core = exp(-r * r * 16.0) * pow(1.0 - p, 4.0) * 3.0;
            float glow = exp(-r * 3.0) * pow(1.0 - p, 2.0) * 0.5;
            col = vec3(1.0, 0.97, 0.9) * core + mix(vec3(1.0), c1, 0.6) * shell * 1.6 + c2 * glow;
            col *= smoothstep(1.3, 1.1, r);
        } else {
            if (r > 1.25) discard;
            float R = 0.08 + 0.9 * e;
            float front = ring(r, R, 0.02 + 0.05 * p);
            float trail = smoothstep(R, R - 0.4, r) * step(r, R) * (1.0 - p) * 0.35;
            col = (mix(c1, vec3(1.0), 0.5) * front * 1.5 + c1 * trail) * (1.0 - p);
            col *= smoothstep(1.25, 1.08, r);
        }
    } else if (uStyle == 1) {                               
        float vis = step(p, 0.5) * (0.55 + 0.45 * step(0.5, fract(p * 9.0 + seed)));   
        float fadeOut = 1.0 - smoothstep(0.3, 0.55, p);
        if (part < 0.5) {
            float y = uv.y, x = uv.x;
            float off = jag(y * 14.0, seed * 31.0) * 0.55 + jag(y * 55.0, seed * 17.0) * 0.14;
            off *= smoothstep(0.0, 0.06, y);
            float bx = x - off;
            float core = exp(-bx * bx / 0.0012);
            float glow = exp(-abs(bx) * 6.0) * 0.35;
            
            for (int k = 0; k < 2; k++) {
                float y0 = 0.35 + 0.25 * float(k) + 0.1 * hash11(seed * 9.0 + float(k));
                if (y < y0 && y > y0 - 0.22) {
                    float t = (y0 - y) / 0.22;
                    float bo = off + (float(k) * 2.0 - 1.0) * t * 0.35 + jag(y * 60.0, seed + float(k) * 5.0) * 0.05;
                    float bb = x - bo;
                    core += exp(-bb * bb / 0.0006) * (1.0 - t) * 0.7;
                }
            }
            col = (mix(c1, vec3(1.0), 0.7) * core * 2.0 + c1 * glow) * vis * fadeOut;
            col *= smoothstep(1.0, 0.85, y) * smoothstep(1.0, 0.8, abs(x));
        } else {
            if (r > 1.25) discard;
            float flash = exp(-r * r * 3.0) * (1.0 - smoothstep(0.0, 0.35, p)) * 1.2;
            float scorch = ring(r, 0.25 + 0.6 * e, 0.06) * (1.0 - p);
            col = mix(c1, vec3(1.0), 0.5) * flash + c2 * scorch * 0.8;
            col *= smoothstep(1.25, 1.05, r);
        }
    } else if (uStyle == 2) {                               
        float y = uv.y, x = uv.x;
        float rise = e;
        float n = fbm(vec2(x * 4.0 + seed * 10.0, y * 3.0 - uTime * 1.8 - p * 3.0));
        float n2 = fbm(vec2(x * 9.0, y * 6.0 - uTime * 3.0));
        float colW = 0.28 + 0.12 * n;
        float wisp = smoothstep(colW, 0.0, abs(x + (n2 - 0.5) * 0.25)) * smoothstep(0.0, 0.1, y) * (1.0 - smoothstep(rise * 0.9, rise * 0.9 + 0.15, y));
        wisp *= 0.35 + 0.9 * n2;
        float orbY = 0.15 + 0.7 * rise;
        vec2 oq = vec2(x * 1.6, (y - orbY) * 4.0);
        float orb = exp(-dot(oq, oq) * 3.0) * 1.4 + exp(-dot(oq, oq) * 1.5) * 0.2;
        float life = smoothstep(0.0, 0.08, p) * (1.0 - smoothstep(0.7, 1.0, p));
        col = (mix(c2, c1, n2) * wisp * 0.9 + mix(c1, vec3(1.0), 0.6) * orb) * life;
        col *= smoothstep(1.0, 0.85, abs(x)) * smoothstep(1.0, 0.9, y);
    } else if (uStyle == 3) {                               
        if (r > 1.2) discard;
        float ang = atan(uv.y, uv.x);
        if (p < 0.78) {
            float q = p / 0.78;
            float R = mix(1.0, 0.3, q * q);
            float hole = mix(0.05, 0.22, smoothstep(0.0, 0.4, q));
            float sw = ang + log(max(r, 0.02)) * 3.5 - uTime * 6.0 - q * 10.0;
            float arms = pow(0.5 + 0.5 * sin(sw * 3.0), 3.0);
            float disk = smoothstep(hole, hole + 0.06, r) * smoothstep(R, R * 0.4, r);
            float n = vnoise(vec2(sw * 2.0, r * 8.0));
            float rim = ring(r, hole + 0.02, 0.02) * 1.6;
            col = (mix(c2, c1, arms) * (arms * 0.9 + n * 0.3) * disk + mix(c1, vec3(1.0), 0.6) * rim) * smoothstep(0.0, 0.1, q);
            a = smoothstep(hole + 0.01, hole - 0.01, r) * 0.95 * smoothstep(0.0, 0.15, q);    
        } else {
            float q = (p - 0.78) / 0.22;
            float R = 0.05 + 1.0 * q;
            col = vec3(1.0, 0.97, 0.9) * exp(-r * r * 30.0) * (1.0 - q) * 3.0 + mix(c1, vec3(1.0), 0.4) * ring(r, R, 0.03 + 0.06 * q) * (1.0 - q) * 1.6;
        }
        col *= smoothstep(1.2, 1.05, r);
    } else if (uStyle == 4) {                               
        float life = smoothstep(0.0, 0.08, p) * (1.0 - smoothstep(0.6, 1.0, p));
        if (part < 0.5) {
            float y = uv.y, x = uv.x;
            float core = exp(-x * x * 40.0);
            float glow = exp(-x * x * 3.5) * 0.45;
            float streak = pow(vnoise(vec2(x * 12.0 + seed * 9.0, y * 10.0 - uTime * 6.0)), 3.0) * exp(-x * x * 6.0);
            col = (mix(c1, vec3(1.0), 0.75) * core * 1.4 + c1 * glow + c2 * streak * 1.2) * life;
            col *= smoothstep(1.0, 0.55, y) * smoothstep(0.0, 0.03, y) * smoothstep(1.0, 0.8, abs(x));
        } else {
            if (r > 1.25) discard;
            col = (c1 * ring(r, 0.55, 0.1) * 0.9 + mix(c1, vec3(1.0), 0.5) * exp(-r * r * 6.0) * 0.6) * life;
            col *= smoothstep(1.25, 1.05, r);
        }
    } else {                                                
        if (r > 1.0) discard;
        col = (vec3(1.0) * exp(-r * r * 10.0) * 2.0 + c1 * ring(r, 0.2 + 0.7 * e, 0.05) * 1.2) * pow(1.0 - p, 2.0);
        col *= smoothstep(1.0, 0.85, r);
    }
    fragColor = vec4(col, a) * vColor.a;
}
