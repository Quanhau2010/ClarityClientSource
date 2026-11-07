#version 330 core



in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform float uTime;
uniform int   uStyle;    
uniform vec3  uColor2;

const float PI = 3.14159265, TAU = 6.2831853;
const vec2  ROOT = vec2(0.0, 0.62);


const float U0 = -0.10, U1 = 1.25, V0 = -0.45, V1 = 1.28;

float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise(vec2 p) {
    vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y);
}
float fbm(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 4; i++) { s += a * vnoise(p); p = p * 2.03 + 7.1; a *= 0.5; } return s / 0.9375; }
vec3 hsv2rgb(vec3 c) { vec3 p = abs(fract(c.xxx + vec3(1.0, 2.0 / 3.0, 1.0 / 3.0)) * 6.0 - 3.0); return c.z * mix(vec3(1.0), clamp(p - 1.0, 0.0, 1.0), c.y); }
float aaStep(float edge, float x) { float w = fwidth(x) * 0.75 + 1e-4; return smoothstep(edge + w, edge - w, x); }


float gPx = 0.002;



vec3 blade(vec2 q, float ang, float L, float W) {
    vec2 d = vec2(cos(ang), sin(ang));
    float a = dot(q, d), b = q.x * d.y - q.y * d.x;
    float t = a / L;
    float prof = W * sqrt(clamp(1.0 - t * t, 0.0, 1.0)) * (0.55 + 0.45 * smoothstep(0.0, 0.3, t));
    float e = abs(b) / max(prof, 1e-4);
    float w = gPx * 0.8 / max(prof, 1e-4);
    float cov = smoothstep(1.0 + w, 1.0 - w, e) * smoothstep(-0.02, 0.0, t) * smoothstep(1.0, 0.99, t);
    return vec3(cov, e, clamp(t, 0.0, 1.0));
}



vec4 featherLayer(vec2 q, float a0, float a1, int n, float lenMin, float lenMax, float W, float jitter) {
    float th = atan(q.y, q.x);
    float stepA = (a1 - a0) / float(n - 1);
    float fi = clamp(floor((th - a0) / stepA + 0.5), 0.0, float(n - 1));
    vec4 best = vec4(0.0, 1.0, 0.0, 0.0);
    for (int k = -1; k <= 1; k++) {
        float i = clamp(fi + float(k), 0.0, float(n - 1));
        float ang = a0 + stepA * i;
        float bell = sin(clamp((i + 0.5) / float(n), 0.0, 1.0) * PI * 0.85 + 0.25);
        float L = mix(lenMin, lenMax, bell) * (1.0 + (hash11(i + jitter) - 0.5) * 0.08);
        vec3 bl = blade(q, ang, L, W);
        
        if (bl.x > 0.01 && (best.x < 0.01 || i > best.w)) best = vec4(bl, i);
    }
    return best;
}


float membraneR(float th, float jag) {
    float a0 = -1.35, a1 = 0.55;
    float s = clamp((th - a0) / (a1 - a0), 0.0, 1.0);
    float base = mix(0.62, 1.0, sin(s * PI * 0.75 + 0.2));
    float sc = abs(sin(s * PI * 4.0));
    float scallop = jag > 0.5 ? (1.0 - 0.28 * (1.0 - abs(fract(s * 4.0) * 2.0 - 1.0)))
                              : (1.0 - 0.3 * pow(sc, 0.7));
    return base * scallop;
}

void main() {
    gPx = length(fwidth(vUV));
    vec2  q = vUV - ROOT;
    float r = length(q);
    if (r > 1.32) discard;                                  
    float border = min(min(vUV.x - U0, U1 - vUV.x), min(vUV.y - V0, V1 - vUV.y));
    float edgeFade = smoothstep(0.0, 0.06, border);
    float th = atan(q.y, q.x);
    float seed = vAux.y;
    vec3  c1 = vColor.rgb, c2 = uColor2;
    float op = vColor.a;
    vec3  col = vec3(0.0);
    float a = 0.0;

    if (uStyle == 0 || uStyle == 3) {                       
        vec2 qq = q;
        if (uStyle == 3) {                                  
            float n = vnoise(q * 9.0 - vec2(uTime * 2.4, uTime * 1.2));
            qq += (vec2(n, vnoise(q * 9.0 + 5.0 - uTime * 2.0)) - 0.5) * 0.06 * smoothstep(0.1, 0.8, r);
        }
        vec4 prim = featherLayer(qq, -1.45, 0.42, 8, 0.62, 0.98, 0.085, 1.0);
        vec4 cov2 = featherLayer(qq, -1.20, 0.45, 7, 0.34, 0.52, 0.095, 5.0);
        vec4 scap = featherLayer(qq, -0.95, 0.50, 6, 0.17, 0.26, 0.08, 9.0);
        vec4 f = scap.x > 0.01 ? scap : (cov2.x > 0.01 ? cov2 : prim);
        float layer = scap.x > 0.01 ? 2.0 : (cov2.x > 0.01 ? 1.0 : 0.0);
        float cov = max(max(prim.x, cov2.x), scap.x);

        if (uStyle == 0) {
            vec3 fc = mix(c1, c2, f.z * (1.0 - layer * 0.3));
            fc = mix(fc, vec3(1.0), 0.18 * layer);
            float rim = smoothstep(0.55, 1.0, f.y);                     
            float rachis = smoothstep(0.10, 0.0, f.y) * (1.0 - f.z * 0.6);
            float barbs = 0.9 + 0.1 * sin((f.z * 70.0 + f.y * 9.0));
            fc = fc * (0.75 + 0.25 * (1.0 - f.y)) * barbs + vec3(1.0) * (rim * 0.35 + rachis * 0.45);
            
            float sp = fract(uTime * 0.35 + hash11(f.w + layer * 13.0 + seed));
            float mote = exp(-pow((f.z - sp) * 18.0, 2.0) - pow(f.y * 3.0, 2.0));
            fc += mix(c1, vec3(1.0), 0.7) * mote * 1.2;
            col = fc * cov;
            a = cov * 0.9;
        } else {                                                
            float n = vnoise(qq * 12.0 - vec2(uTime * 3.0, uTime * 1.5));
            float n2 = vnoise(qq * 26.0 - vec2(uTime * 5.0, 0.0));
            cov *= smoothstep(0.0, 0.3, n * 0.8 + n2 * 0.4 + (1.0 - f.z) * 0.7 - 0.2);   
            float heat = cov * pow(1.0 - f.z, 1.1) * (0.5 + 0.8 * n + 0.3 * n2) + cov * (1.0 - f.y) * 0.18;
            heat = clamp(heat, 0.0, 1.3);
            vec3 fire = mix(c2 * 0.45, c2, smoothstep(0.05, 0.3, heat));
            fire = mix(fire, c1, smoothstep(0.3, 0.65, heat));
            fire = mix(fire, vec3(1.0, 0.96, 0.85), smoothstep(0.75, 1.05, heat));
            col = fire * heat * 1.7;
            a = cov * heat * 0.25;
            
            vec2 eg = vec2(q.x * 14.0 - uTime * 1.6, q.y * 14.0 - uTime * 0.8);
            vec2 id = floor(eg), fp = fract(eg) - 0.5;
            float hh = hash12(id + seed * 7.0);
            float em = step(0.88, hh) * smoothstep(0.18, 0.0, length(fp - (vec2(hh, fract(hh * 7.3)) - 0.5) * 0.5));
            col += mix(c1, vec3(1.0, 0.9, 0.6), 0.5) * em * smoothstep(1.15, 0.3, r) * 1.5;
        }
    } else if (uStyle == 1 || uStyle == 2) {                
        float R = membraneR(th, uStyle == 2 ? 1.0 : 0.0);
        float inside = aaStep(R, r) * step(-1.35, th) * step(th, 0.55);
        float edgeD = (R - r);
        
        float bones = 0.0;
        for (int k = 0; k < 5; k++) {
            float ang = mix(-1.35, 0.55, float(k) / 4.0);
            vec2 d = vec2(cos(ang), sin(ang));
            float along = dot(q, d), perp = abs(q.x * d.y - q.y * d.x);
            float L = membraneR(ang, uStyle == 2 ? 1.0 : 0.0);
            bones = max(bones, smoothstep(0.012, 0.0, perp - 0.004) * step(0.0, along) * step(along, L));
        }
        if (uStyle == 1) {
            vec2 np = q * 3.2 + vec2(uTime * 0.05, -uTime * 0.03);
            float n1 = fbm(np), n2 = fbm(np * 1.8 + 4.0);
            vec3 neb = vec3(0.02, 0.0, 0.04) + c2 * pow(smoothstep(0.4, 0.9, n1), 1.5) * 0.9
                     + c1 * pow(smoothstep(0.5, 0.95, n2), 2.0) * 0.6;
            vec2 sg = q * 60.0; vec2 sid = floor(sg);
            float st = step(0.93, hash12(sid + 3.0)) * smoothstep(0.25, 0.0, length(fract(sg) - 0.5))
                     * (0.6 + 0.4 * sin(uTime * 3.0 + hash12(sid) * 30.0));
            neb += vec3(1.0) * st;
            float rim = exp(-max(edgeD, 0.0) * 55.0);
            col = (neb + c1 * (rim * 1.2 + bones * 0.8)) * inside;
            a = inside * 0.92;
            col += c1 * exp(-max(-edgeD, 0.0) * 30.0) * (1.0 - inside) * 0.3 * step(-1.35, th) * step(th, 0.55);
        } else {
            
            vec2 hp = q * 13.0;
            vec2 h1 = vec2(1.0, 1.7320508);
            vec2 ga = mod(hp, h1) - h1 * 0.5, gb = mod(hp - h1 * 0.5, h1) - h1 * 0.5;
            vec2 gv = dot(ga, ga) < dot(gb, gb) ? ga : gb;
            vec2 ab = abs(gv);
            float hexD = 0.5 - max(dot(ab, normalize(vec2(1.0, 1.7320508))), ab.x);
            float seam = smoothstep(0.06, 0.0, hexD);
            float pulse = exp(-pow((r - fract(uTime * 0.45) * 1.3) * 9.0, 2.0));
            vec2 cellId = hp - gv;
            float lit = step(0.8, hash12(floor(cellId * 10.0) + floor(uTime * 1.5)));
            vec3 panel = c2 * (0.10 + 0.25 * lit) ;
            float rim = exp(-max(edgeD, 0.0) * 70.0);
            col = (panel + c1 * (seam * (0.45 + 1.2 * pulse) + rim * 1.4 + bones * 1.0)) * inside;
            a = inside * 0.45;
        }
    } else {                                                 
        vec2 p = vUV;
        vec2 u1 = p - vec2(0.47, 0.80); float cr1 = cos(0.38), sr1 = sin(0.38);
        u1 = mat2(cr1, -sr1, sr1, cr1) * u1;
        float e1 = length(u1 / vec2(0.50, 0.29));
        vec2 u2 = p - vec2(0.30, 0.34); float cr2 = cos(-0.55), sr2 = sin(-0.55);
        u2 = mat2(cr2, -sr2, sr2, cr2) * u2;
        float e2 = length(u2 / vec2(0.34, 0.24));
        float e = min(e1, e2 * 1.0);
        float inside = aaStep(1.0, e) * step(0.0, q.x + 0.02);
        float border = smoothstep(0.80, 0.86, e);
        
        float veins = smoothstep(0.05, 0.0, abs(fract(th * 2.2 + 0.3) - 0.5) - 0.44) * smoothstep(0.05, 0.25, r);
        veins = max(veins, smoothstep(0.012, 0.0, abs(r - 0.42)) * 0.7);
        float film = 0.5 + 0.5 * sin(r * 9.0 - uTime * 1.2 + th * 2.0);
        vec3 irid = hsv2rgb(vec3(fract(0.55 + r * 0.6 + 0.1 * sin(uTime * 0.7 + th * 3.0)), 0.55, 1.0));
        vec3 base = mix(mix(c1, c2, film), irid, 0.35);
        base *= 0.75 + 0.25 * smoothstep(1.0, 0.2, e);
        
        float edgeLine = smoothstep(0.035, 0.0, abs(e - 0.95)) * inside;
        vec3 wc = mix(base, vec3(0.02), border * 0.85) + mix(c1, vec3(1.0), 0.6) * edgeLine * 0.9;
        wc = mix(wc, vec3(0.05), veins * 0.55);
        float sheen = pow(max(0.0, sin((p.x + p.y) * 7.0 - uTime * 1.5)), 12.0) * 0.35;
        col = (wc + sheen) * inside;
        a = inside * (0.72 + 0.25 * border);
    }

    fragColor = vec4(col, a) * op * edgeFade;
}
