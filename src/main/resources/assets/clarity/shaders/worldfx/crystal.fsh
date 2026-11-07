#version 330 core



in vec3 vPos;
in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform mat4  uViewProj;
uniform vec3  uRight, uUp, uFwd;
uniform float uTime;
uniform int   uStyle;        
uniform vec3  uA, uB;
uniform float uGlow;

float hash31(vec3 p) { p = fract(p * 0.1031); p += dot(p, p.zyx + 31.32); return fract((p.x + p.y) * p.z); }
float vnoise3(vec3 p) {
    vec3 i = floor(p), f = fract(p);
    f = f * f * (3.0 - 2.0 * f);
    float n000 = hash31(i),                   n100 = hash31(i + vec3(1.0, 0.0, 0.0));
    float n010 = hash31(i + vec3(0.0, 1.0, 0.0)), n110 = hash31(i + vec3(1.0, 1.0, 0.0));
    float n001 = hash31(i + vec3(0.0, 0.0, 1.0)), n101 = hash31(i + vec3(1.0, 0.0, 1.0));
    float n011 = hash31(i + vec3(0.0, 1.0, 1.0)), n111 = hash31(i + vec3(1.0, 1.0, 1.0));
    float x00 = mix(n000, n100, f.x), x10 = mix(n010, n110, f.x);
    float x01 = mix(n001, n101, f.x), x11 = mix(n011, n111, f.x);
    return mix(mix(x00, x10, f.y), mix(x01, x11, f.y), f.z);
}
float fbm3(vec3 p) { return 0.5 * vnoise3(p) + 0.25 * vnoise3(p * 2.03 + 7.1) + 0.125 * vnoise3(p * 4.1 + 3.3); }

mat3 rotY(float a) { float c = cos(a), s = sin(a); return mat3(c, 0, -s, 0, 1, 0, s, 0, c); }
mat3 rotX(float a) { float c = cos(a), s = sin(a); return mat3(1, 0, 0, 0, c, s, 0, -s, c); }
mat3 rotZ(float a) { float c = cos(a), s = sin(a); return mat3(c, s, 0, -s, c, 0, 0, 0, 1); }

float sdOcta(vec3 p, float s) { p = abs(p); return (p.x + p.y + p.z - s) * 0.57735027; }
float sdBoxFrame(vec3 p, vec3 b, float e) {
    p = abs(p) - b; vec3 q = abs(p + e) - e;
    return min(min(length(max(vec3(p.x, q.y, q.z), 0.0)) + min(max(p.x, max(q.y, q.z)), 0.0),
                   length(max(vec3(q.x, p.y, q.z), 0.0)) + min(max(q.x, max(p.y, q.z)), 0.0)),
                   length(max(vec3(q.x, q.y, p.z), 0.0)) + min(max(q.x, max(q.y, p.z)), 0.0));
}
float sdRoundBox(vec3 p, vec3 b, float r) { vec3 q = abs(p) - b + r; return length(max(q, 0.0)) + min(max(q.x, max(q.y, q.z)), 0.0) - r; }

mat3 R1, R2;   


float map(vec3 p, out float id) {
    id = 0.0;
    if (uStyle == 0) {                                     
        vec3 q = R1 * p;
        float d = sdOcta(vec3(q.x, q.y * 0.70, q.z), 0.40) / 0.70 * 0.70;
        return d;
    } else if (uStyle == 1) {                              
        return length(p) - 0.34;
    } else if (uStyle == 2) {                              
        float a = sdBoxFrame(R1 * p, vec3(0.33), 0.022);
        float b = sdBoxFrame(R2 * p, vec3(0.23), 0.018);
        float c = sdOcta(R1 * p, 0.17);
        float d = min(a, b);
        if (c < d) { id = 1.0; d = c; }
        return d;
    }
    return sdRoundBox(R1 * p, vec3(0.27), 0.05);            
}

vec3 normalAt(vec3 p) {
    const vec2 k = vec2(1, -1); const float e = 0.0015; float i;
    return normalize(k.xyy * map(p + k.xyy * e, i) + k.yyx * map(p + k.yyx * e, i) +
                     k.yxy * map(p + k.yxy * e, i) + k.xxx * map(p + k.xxx * e, i));
}

void main() {
    float R = vAux.x, seed = vAux.y;
    float spin = vColor.r, dissolve = vColor.g, scale = max(vColor.b, 1e-3), alpha = vColor.a;
    vec3 c = vPos - (uRight * vUV.x + uUp * vUV.y) * R;     
    vec3 rd = normalize(vPos);                              

    R1 = rotZ(0.62) * rotX(0.62) * rotY(spin);
    R2 = rotY(-spin * 1.6) * rotX(0.78) * rotZ(spin * 0.7);

    
    float tc = dot(c, rd);
    float d0 = length(c - rd * tc) / scale;
    vec3 haloCol = mix(uA, uB, 0.35);
    float halo = tc > 0.0 ? uGlow * exp(-d0 * d0 * 11.0) * 0.42 : 0.0;
    if (uStyle == 3) halo *= 0.8;

    
    float Rb = 0.62 * scale;
    vec3 oc = -c;
    float b = dot(oc, rd), h = b * b - (dot(oc, oc) - Rb * Rb);
    float tHit = -1.0, id = 0.0;
    if (h > 0.0) {
        float sq = sqrt(h), t = max(-b - sq, 0.0), t1 = -b + sq;
        for (int i = 0; i < 56; i++) {
            vec3 lp = (rd * t - c) / scale;
            float d = map(lp, id) * scale;
            if (d < 0.0012) { tHit = t; break; }
            t += d * 0.9;
            if (t > t1) break;
        }
    }

    vec3 col = vec3(0.0);
    float a = 0.0;
    gl_FragDepth = gl_FragCoord.z;

    if (tHit > 0.0) {
        vec3 hp = rd * tHit;
        vec3 lp = (hp - c) / scale;
        vec3 N = normalAt(lp);
        vec3 V = -rd;
        float ndv = max(dot(N, V), 0.0);
        float fres = pow(1.0 - ndv, 3.0);
        vec3 L = normalize(vec3(0.35, 0.9, 0.25));
        vec3 Rf = reflect(rd, N);

        
        float dn = vnoise3(lp * 9.0 + seed * 13.0);
        if (dn < dissolve) discard;
        float burn = smoothstep(dissolve + 0.12, dissolve, dn) * step(0.001, dissolve);

        if (uStyle == 0) {                                  
            vec3 body = mix(uB * 0.30, uA * 0.95, 0.5 + 0.5 * dot(N, L));
            float sky = smoothstep(-0.1, 0.9, Rf.y);
            float fire = pow(0.5 + 0.5 * sin(dot(lp, vec3(9.0, 13.0, 7.0)) + uTime * 2.0 + seed * 6.0), 6.0);
            col = body * 0.75 + mix(uA, vec3(1.0), 0.6) * fres * 1.2 + vec3(1.0) * pow(max(dot(Rf, L), 0.0), 40.0) * 0.9
                + mix(uA, uB, 0.5) * sky * 0.25 + vec3(1.0) * fire * 0.25;
            a = 0.92;
        } else if (uStyle == 1) {                           
            vec3 q = R1 * lp;
            float n = fbm3(q * 4.0 + vec3(0.0, uTime * 0.9, uTime * 0.4));
            float vein = pow(1.0 - abs(2.0 * fract(n * 3.0 - uTime * 0.25) - 1.0), 8.0);
            col = mix(uB * 0.5, uA, n) * (0.55 + 0.7 * n) + mix(uA, vec3(1.0), 0.6) * vein * 0.9 + uA * fres * 1.5;
            a = 0.88;
        } else if (uStyle == 2) {                           
            col = id > 0.5 ? mix(uB, vec3(1.0), 0.55) * (1.2 + 0.3 * sin(uTime * 4.0 + seed * 9.0))
                           : mix(uA, vec3(1.0), 0.15) * (1.15 + fres);
            a = 1.0;
        } else {                                            
            vec3 q = R1 * lp * 6.0 + seed * 3.0;
            vec3 g = floor(q), f = fract(q);
            float d1 = 8.0, d2 = 8.0;
            for (int z = -1; z <= 1; z++) for (int y = -1; y <= 1; y++) for (int x = -1; x <= 1; x++) {
                vec3 o = vec3(x, y, z); vec3 r = o + vec3(hash31(g + o), hash31(g + o + 7.0), hash31(g + o + 3.0)) - f;
                float dd = dot(r, r); if (dd < d1) { d2 = d1; d1 = dd; } else if (dd < d2) d2 = dd;
            }
            float crack = smoothstep(0.10, 0.0, sqrt(d2) - sqrt(d1));
            float pulse = 0.7 + 0.3 * sin(uTime * 3.0 + seed * 5.0);
            col = vec3(0.025, 0.02, 0.035) + vec3(0.6) * pow(max(dot(Rf, L), 0.0), 30.0) * 0.4
                + uA * crack * 1.6 * pulse + uB * fres * 0.9;
            a = 1.0;
        }
        col += mix(uA, vec3(1.0), 0.5) * burn * 2.0;

        
        vec4 cq = uViewProj * vec4(vPos, 1.0), ch = uViewProj * vec4(hp, 1.0);
        gl_FragDepth = clamp(gl_FragCoord.z + (ch.z / ch.w - cq.z / cq.w) * 0.5, 0.0, 1.0);
    } else if (halo < 0.003) {
        discard;
    }

    vec3 outRgb = col * a * alpha + haloCol * halo * alpha;
    fragColor = vec4(outRgb, a * alpha);
}
