#version 330 core





out vec4 fragColor;

uniform vec2  uRes;
uniform float uTime;
uniform int   uStyle;    
uniform vec3  uA, uB;
uniform sampler2D uLogo;

float hash(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float noise(vec2 p) {
    vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash(i), hash(i + vec2(1, 0)), f.x), mix(hash(i + vec2(0, 1)), hash(i + vec2(1, 1)), f.x), f.y);
}
float fbm3(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 3; i++) { s += a * noise(p); p = p * 2.03 + 17.1; a *= 0.5; } return s / 0.875; }
float fbm(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 5; i++) { s += a * noise(p); p = p * 2.03 + 17.1; a *= 0.5; } return s; }
vec3 hsv(float h, float s, float v) { vec3 k = clamp(abs(mod(h * 6.0 + vec3(0, 4, 2), 6.0) - 3.0) - 1.0, 0.0, 1.0); return v * mix(vec3(1), k, s); }


vec3 design(vec2 f, float t) {
    vec2 p = vec2(f.x * 0.625, f.y);                 
    vec3 dark = mix(uA, uB, 0.5) * 0.06 + vec3(0.012, 0.012, 0.02);

    if (uStyle == 0) {                                
        vec3 c = dark;
        for (int i = 0; i < 3; i++) {
            float fi = float(i);
            float x = p.x * (2.0 + fi) + fbm(vec2(p.y * 1.5 + t * 0.15, fi * 3.1)) * 1.6 + t * (0.12 + 0.05 * fi);
            float band = pow(0.5 + 0.5 * sin(x * 6.2831), 6.0);
            float fade = smoothstep(1.05, 0.15, f.y) * (0.6 + 0.4 * noise(vec2(x * 3.0, t)));
            c += mix(uA, uB, 0.5 + 0.5 * sin(fi * 2.3 + f.y * 3.0 + t * 0.3)) * band * fade * 0.75;
        }
        c += mix(uA, uB, f.y) * 0.08;
        return c;
    }
    if (uStyle == 1) {                                
        vec2 q = p * 3.0;
        float n1 = fbm(q + vec2(t * 0.05, -t * 0.08));
        float n2 = fbm(q * 1.6 + n1 * 1.8 - vec2(0.0, t * 0.06));
        vec3 c = dark;
        c += uA * smoothstep(0.35, 0.9, n1) * 0.9;
        c += uB * smoothstep(0.45, 0.95, n2) * 0.8;
        c += mix(uA, vec3(1.0), 0.6) * pow(smoothstep(0.62, 0.95, n1 * n2 * 1.6), 2.0) * 0.6;
        vec2 g = floor(p * 26.0); vec2 fr = fract(p * 26.0) - 0.5;
        float h = hash(g);
        float star = step(0.86, h) * smoothstep(0.22, 0.0, length(fr)) * (0.5 + 0.5 * sin(t * 3.0 + h * 40.0));
        return c + vec3(star);
    }
    if (uStyle == 2) {                                
        vec2 q = p * 1.1;
        vec2 w = vec2(fbm3(q + vec2(t * 0.07, 0.0)), fbm3(q + vec2(5.2, -t * 0.09)));
        float n = fbm3(q + w * 1.6 + vec2(0.0, -t * 0.06));
        vec3 c = mix(dark, uB * 0.85, smoothstep(0.15, 0.6, n));
        c = mix(c, uA, smoothstep(0.45, 0.85, n));
        
        float ridge = 1.0 - abs(2.0 * fract(n * 2.5 - t * 0.12) - 1.0);
        c += mix(uA, vec3(1.0), 0.6) * pow(ridge, 10.0) * 0.35 * smoothstep(0.3, 0.7, n);
        return c;
    }
    if (uStyle == 3) {                                
        vec2 q = p * 9.0;
        vec2 cell = floor(q), fq = fract(q) - 0.5;
        float h = hash(cell);
        
        float tr = h < 0.4 ? abs(fq.y) : (h < 0.8 ? abs(fq.x) : 1.0);
        float line = smoothstep(0.07, 0.02, tr);
        float node = smoothstep(0.17, 0.11, length(fq)) * step(0.82, hash(cell + 7.0));
        float along = h < 0.4 ? q.x : q.y;
        float pulse = pow(fract(along * 0.18 - t * (0.5 + h) + h * 3.0), 12.0);
        vec3 c = dark + uB * 0.06;
        c += mix(uB, uA, 0.4) * line * 0.28;
        c += mix(uA, vec3(1.0), 0.35) * line * pulse * 1.6;
        c += uA * node * (0.5 + 0.5 * sin(t * 4.0 + h * 20.0));
        return c;
    }
    
    float n = fbm(p * 2.5 + vec2(0.0, t * 0.05));
    float k = p.x * 1.2 + p.y * 0.8 + n * 0.9 + t * 0.12;
    vec3 foil = hsv(fract(k), 0.55, 1.0);
    vec3 c = mix(foil, mix(uA, uB, fract(k * 2.0)), 0.45);
    float glint = pow(max(0.0, sin((p.x - p.y) * 9.0 - t * 1.8)), 40.0);
    return c * 0.72 + vec3(glint) * 0.22;
}

void main() {
    vec2 px = gl_FragCoord.xy / uRes * vec2(64.0, 32.0);   
    bool outer = px.x >= 1.0 && px.x < 11.0 && px.y >= 1.0 && px.y < 17.0;
    bool inner = px.x >= 12.0 && px.x < 22.0 && px.y >= 1.0 && px.y < 17.0;
    vec2 f = outer ? vec2((px.x - 1.0) / 10.0, (px.y - 1.0) / 16.0)
           : inner ? vec2((px.x - 12.0) / 10.0, (px.y - 1.0) / 16.0)
           : fract(px / vec2(10.0, 16.0));
    float t = uTime;
    vec3 col = design(f, t);

    if (outer) {
        
        vec2 lp = (f - vec2(0.5, 0.40)) * vec2(1.0, 1.6) / 0.62 + 0.5;
        float a = 0.0;
        if (all(greaterThanEqual(lp, vec2(0.0))) && all(lessThanEqual(lp, vec2(1.0)))) a = texture(uLogo, lp).a;
        float breathe = 0.85 + 0.15 * sin(t * 1.6);
        vec3 lc = mix(vec3(1.0), uA, 0.25);
        col = col * (1.0 - a * 0.85) + lc * a * breathe * 1.15;
        float d = length((f - vec2(0.5, 0.40)) * vec2(1.0, 1.6));
        col += uA * exp(-d * d * 18.0) * 0.18 * breathe;           
        
        float edge = min(min(f.x, 1.0 - f.x), min(f.y, 1.0 - f.y) * 1.6);
        col *= 0.75 + 0.25 * smoothstep(0.0, 0.18, edge);
        col = mix(col, uA * 1.1, smoothstep(0.045, 0.0, edge) * 0.75);
    } else if (inner) {
        col *= 0.38;
    } else {
        col = mix(col, uA, 0.35) * 0.7;
    }
    fragColor = vec4(clamp(col, 0.0, 1.0), 1.0);
}
