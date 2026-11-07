#version 330 core

in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform float uTime;
uniform int   uStyle;    
uniform vec3  uColor2;
uniform vec3  uRight, uUp, uFwd;

const float PI = 3.14159265, TAU = 6.2831853;
float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
vec3 hsv2rgb(vec3 c) { vec3 p = abs(fract(c.xxx + vec3(1.0, 2.0 / 3.0, 1.0 / 3.0)) * 6.0 - 3.0); return c.z * mix(vec3(1.0), clamp(p - 1.0, 0.0, 1.0), c.y); }
float aa(float d) { float w = fwidth(d) * 0.8 + 1e-4; return smoothstep(w, -w, d); }

float sdHeart(vec2 p) {
    p.y = -p.y * 1.1 + 0.55; p.x = abs(p.x) * 1.1;
    if (p.y + p.x > 1.0) return sqrt(dot(p - vec2(0.25, 0.75), p - vec2(0.25, 0.75))) - 0.3535534;
    return sqrt(min(dot(p - vec2(0.0, 1.0), p - vec2(0.0, 1.0)), dot(p - 0.5 * max(p.x + p.y, 0.0), p - 0.5 * max(p.x + p.y, 0.0)))) * sign(p.x - p.y);
}
float star4(vec2 uv, float sharp) { vec2 a = abs(uv); return max(exp(-a.x * sharp) * exp(-a.y * 2.5), exp(-a.y * sharp) * exp(-a.x * 2.5)); }
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise(vec2 p) { vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y); }
float fbm(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 4; i++) { s += a * vnoise(p); p = p * 2.03 + 7.1; a *= 0.5; } return s / 0.9375; }
float vnoise3(vec3 p) { vec3 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    float n = i.x + i.y * 57.0 + i.z * 113.0;
    return mix(mix(mix(hash11(n), hash11(n + 1.0), f.x), mix(hash11(n + 57.0), hash11(n + 58.0), f.x), f.y),
               mix(mix(hash11(n + 113.0), hash11(n + 114.0), f.x), mix(hash11(n + 170.0), hash11(n + 171.0), f.x), f.y), f.z); }

void main() {
    vec2 uv = vUV;
    float r = length(uv);
    vec3 c1 = vColor.rgb, c2 = uColor2;
    vec3 col = vec3(0.0); float a = 0.0;

    if (uStyle == 0) {                                   
        if (r > 1.0) discard;
        float nz = sqrt(1.0 - r * r);
        vec3 n = normalize(uv.x * uRight + uv.y * uUp - nz * uFwd);
        float fres = pow(1.0 - nz, 2.0);
        float p1 = vnoise3(n * 2.5 + vec3(0.0, -uTime * 0.9, uTime * 0.3));
        float p2 = vnoise3(n * 6.0 + vec3(uTime * 0.7, 0.0, 0.0));
        float veins = smoothstep(0.08, 0.0, abs(p1 - 0.5)) * (0.5 + 0.5 * p2);
        col = c1 * (fres * 1.1 + veins * (0.35 + 0.9 * fres)) + c2 * fres * fres * 0.7 + c2 * veins * 0.3;
        col *= smoothstep(1.0, 0.96, r);
        a = fres * 0.2;
    } else if (uStyle == 1) {                            
        
        float y = uv.y, x = uv.x;
        float bodyHalf = 0.36 * (1.0 - 0.3 * y);
        float n  = fbm(vec2(x * 3.5, y * 2.5 - uTime * 2.6));
        float n2 = fbm(vec2(x * 8.0 + 3.0, y * 5.0 - uTime * 4.2));
        float d = abs(x) - bodyHalf;                                   
        float outer = 1.0 - smoothstep(0.0, 0.32 + 0.4 * n, d);
        float hollow = mix(0.25, 1.0, smoothstep(-0.32, -0.02, d));
        float top = 0.72 + 0.3 * n2;
        float vfade = smoothstep(0.0, 0.07, y) * (1.0 - smoothstep(top - 0.3, top, y));
        float heat = outer * hollow * vfade * (0.45 + 1.0 * pow(n2, 1.4) + 0.3 * n);
        vec3 fire = mix(c2, c1, smoothstep(0.2, 0.8, heat));
        fire = mix(fire, vec3(1.0, 0.97, 0.9), smoothstep(0.9, 1.3, heat) * 0.7);
        col = fire * heat * 1.25;
        col += c2 * exp(-max(d, 0.0) * 4.0) * vfade * 0.22;            
        col *= smoothstep(1.0, 0.85, abs(x));
    } else {                                             
        if (r > 1.2) discard;
        float th = atan(uv.y, uv.x);
        float arms = 0.5 + 0.5 * sin(th * 2.0 - log(max(r, 0.05)) * 5.0 + uTime * 0.8);
        arms = pow(arms, 3.0);
        float disk = smoothstep(0.32, 0.5, r) * smoothstep(1.15, 0.7, r);
        float dust = fbm(vec2(th * 2.0 + uTime * 0.2, r * 5.0));
        vec2 sg = uv * 40.0; vec2 sid = floor(sg);
        float st = step(0.94, hash12(sid)) * smoothstep(0.3, 0.0, length(fract(sg) - 0.5)) * (0.6 + 0.4 * sin(uTime * 3.0 + hash12(sid) * 30.0));
        vec3 g = mix(c2, c1, arms) * (arms * 0.9 + dust * 0.35) + vec3(1.0) * st;
        g += mix(c1, vec3(1.0), 0.5) * exp(-pow((r - 0.42) / 0.04, 2.0)) * 0.8;   
        col = g * disk + g * exp(-pow((r - 0.42) / 0.04, 2.0)) * 0.6;
        col *= smoothstep(1.2, 1.05, r);
    }
    fragColor = vec4(col, a) * vColor.a;
}
