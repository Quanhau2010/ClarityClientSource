#version 330 core


in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform float uTime;
uniform int   uStyle;    
uniform vec3  uColor2;
uniform float uSpin;

const float PI = 3.14159265, TAU = 6.2831853;
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise(vec2 p) {
    vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y);
}
float fbm(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 4; i++) { s += a * vnoise(p); p = p * 2.03 + 7.1; a *= 0.5; } return s / 0.9375; }
float aaStep(float edge, float x) { float w = fwidth(x) * 0.75 + 1e-4; return smoothstep(edge + w, edge - w, x); }
float sdTriangle(vec2 p, vec2 p0, vec2 p1, vec2 p2) {
    vec2 e0 = p1 - p0, e1 = p2 - p1, e2 = p0 - p2;
    vec2 v0 = p - p0, v1 = p - p1, v2 = p - p2;
    vec2 pq0 = v0 - e0 * clamp(dot(v0, e0) / dot(e0, e0), 0.0, 1.0);
    vec2 pq1 = v1 - e1 * clamp(dot(v1, e1) / dot(e1, e1), 0.0, 1.0);
    vec2 pq2 = v2 - e2 * clamp(dot(v2, e2) / dot(e2, e2), 0.0, 1.0);
    float s = sign(e0.x * e2.y - e0.y * e2.x);
    vec2 d = min(min(vec2(dot(pq0, pq0), s * (v0.x * e0.y - v0.y * e0.x)),
                     vec2(dot(pq1, pq1), s * (v1.x * e1.y - v1.y * e1.x))),
                     vec2(dot(pq2, pq2), s * (v2.x * e2.y - v2.y * e2.x)));
    return -sqrt(d.x) * sign(d.y);
}

void main() {
    vec3 c1 = vColor.rgb, c2 = uColor2;
    float op = vColor.a;
    vec3 col = vec3(0.0);
    float a = 0.0;
    vec2 uv = vUV;

    if (uStyle == 0) {                                      
        float r = length(uv);
        if (r > 1.35) discard;
        float R = 0.76;
        float core = exp(-pow((r - R) / 0.035, 2.0));
        float glow = exp(-pow((r - R) / 0.20, 2.0));
        float th = atan(uv.y, uv.x);
        float motes = 0.0;
        for (int i = 0; i < 6; i++) {
            float ang = uTime * uSpin * 2.0 + float(i) * TAU / 6.0;
            vec2 mp = vec2(cos(ang), sin(ang)) * R;
            motes += exp(-dot(uv - mp, uv - mp) / 0.0025);
        }
        float shimmer = 0.85 + 0.15 * sin(th * 12.0 - uTime * 3.0);
        col = mix(c1, vec3(1.0), 0.55) * core * 1.4 * shimmer + c1 * glow * 0.5 + c2 * motes * 1.2
            + c2 * smoothstep(R, 0.0, r) * 0.05;
        a = core * 0.6;
        col *= smoothstep(1.35, 1.12, r);
        a *= smoothstep(1.35, 1.12, r);
    } else if (uStyle == 1) {                               
        float r = length(uv);
        if (r > 1.35) discard;
        float R = 0.86;
        float th = atan(uv.y, uv.x);
        float dir = vAux.y;
        float head = uTime * uSpin * 3.0 * dir + vAux.x * 2.1;
        float dTh = mod((th - head) * dir + PI, TAU) - PI;          
        float tail = dTh < 0.0 ? exp(dTh * 1.6) : exp(-dTh * 25.0);
        float band = exp(-pow((r - R) / 0.03, 2.0));
        float halo = exp(-pow((r - R) / 0.12, 2.0));
        vec3 rc = mix(c1, c2, vAux.x);
        col = rc * band * (0.25 + 1.4 * tail) + rc * halo * tail * 0.4 + vec3(1.0) * band * pow(tail, 6.0) * 0.8;
        a = band * 0.2;
        float ef = smoothstep(1.35, 1.12, r);
        col *= ef; a *= ef;
    } else if (uStyle == 2) {                               
        float f = fract(uv.x * 8.0);
        float tri = 1.0 - abs(f - 0.5) * 2.0;
        float top = 0.42 + 0.58 * pow(tri, 2.2);
        float inside = aaStep(top, uv.y) * step(0.0, uv.y);
        float edge = top - uv.y;
        vec2 np = vec2(uv.x * 7.0, uv.y * 1.6) + vec2(uTime * 0.04, 0.0);
        float n1 = fbm(np), n2 = fbm(np * 1.7 + 3.0);
        vec3 neb = vec3(0.03, 0.01, 0.06) + c2 * pow(smoothstep(0.35, 0.9, n1), 1.4) + c1 * 0.35 * pow(smoothstep(0.5, 0.95, n2), 2.0);
        vec2 sg = vec2(uv.x * 160.0, uv.y * 24.0);
        float st = step(0.92, hash12(floor(sg))) * smoothstep(0.3, 0.0, length(fract(sg) - 0.5)) * (0.6 + 0.4 * sin(uTime * 4.0 + hash12(floor(sg)) * 40.0));
        neb += vec3(1.0) * st;
        vec3 gold = mix(c1, vec3(1.0, 0.95, 0.8), 0.3);
        float rim = exp(-edge * 40.0) + smoothstep(0.08, 0.0, uv.y) + smoothstep(0.05, 0.0, abs(uv.y - 0.22)) * 0.8;
        
        vec2 gp = vec2((f - 0.5) * 0.9, uv.y - 0.72);
        float gem = smoothstep(0.06, 0.04, length(gp));
        vec3 gemc = mix(c2, vec3(1.0), 0.4 + 0.3 * sin(uTime * 2.0 + floor(uv.x * 8.0)));
        col = mix(neb + gold * rim * 1.2, gemc * 1.3, gem) * inside;
        a = inside * 0.95;
        col += gold * exp(-max(-edge, 0.0) * 30.0) * (1.0 - inside) * 0.35 * step(0.0, uv.y);
        float topFade = smoothstep(1.15, 1.04, uv.y);
        col *= topFade; a *= topFade;
    } else {                                                
        float d = sdTriangle(uv, vec2(-0.85, -0.9), vec2(0.85, -0.9), vec2(0.15, 0.95)) - 0.06;
        float di = sdTriangle(uv, vec2(-0.45, -0.75), vec2(0.45, -0.75), vec2(0.12, 0.45)) - 0.05;
        float inside = aaStep(0.0, d);
        float inner = aaStep(0.0, di);
        float line = exp(-pow(d / 0.035, 2.0));
        float glow = exp(-max(d, 0.0) * 10.0) * (1.0 - inside);
        float pulse = exp(-pow(uv.y - (fract(uTime * 0.6) * 2.6 - 1.3), 2.0) * 8.0);
        vec3 fill = mix(c1 * 0.15, c2 * 0.55, inner);
        col = fill * inside + mix(c1, vec3(1.0), 0.4) * line * (1.0 + pulse) + c1 * glow * 0.5;
        a = inside * 0.6;
        float ef = smoothstep(1.35, 1.15, max(abs(uv.x), abs(uv.y)));
        col *= ef; a *= ef;
    }
    fragColor = vec4(col, a) * op;
}
