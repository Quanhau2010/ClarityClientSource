#version 330 core
in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;
uniform float time;
uniform float aspect;
uniform vec2  texel;
uniform float pxScale;

float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise(vec2 p) {
    vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y);
}
float fbm(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 4; i++) { s += a * vnoise(p); p = p * 2.03 + 7.1; a *= 0.5; } return s / 0.9375; }
float luma(vec3 c) { return dot(c, vec3(0.299, 0.587, 0.114)); }
float handAt(vec2 p) { return 1.0 - step(0.99, texture(DepthTexture, p).r); }





float edgeField(bool inside, float scalePx, out vec2 nrm) {
    float acc = 0.0;
    nrm = vec2(0.0);
    float radii[3] = float[3](0.25, 0.55, 1.0);
    float wts[3]   = float[3](0.5, 0.3, 0.2);
    for (int r = 0; r < 3; r++) {
        float cnt = 0.0;
        for (int k = 0; k < 12; k++) {
            float a = float(k) * 0.5235988 + float(r) * 0.26;
            vec2 d = vec2(cos(a), sin(a));
            float h = handAt(uv + d * texel * scalePx * radii[r]);
            float hit = inside ? 1.0 - h : h;
            cnt += hit;
            nrm += d * hit * wts[r];
        }
        acc += (cnt / 12.0) * wts[r];
    }
    nrm = length(nrm) > 1e-4 ? normalize(nrm) : vec2(0.0, 1.0);
    return clamp(acc * (inside ? 2.0 : 1.6), 0.0, 1.0);
}



uniform vec3 uAccent, uAccent2;

void main() {
    if (handAt(uv) < 0.5) discard;
    vec2 n;
    float e = edgeField(true, 14.0 * pxScale + 3.0, n);
    vec3 orig = texture(ColorTexture, uv).rgb;
    vec2 p = vec2(uv.x * aspect, uv.y) * 3.0;
    float t = time * 0.04;
    float n1 = fbm(p + vec2(t, -t * 0.6)), n2 = fbm(p * 1.8 + 5.2 - vec2(t * 0.7, 0.0));
    vec3 col = vec3(0.01, 0.008, 0.03)
             + uAccent  * pow(smoothstep(0.35, 0.9, n1), 1.6) * 0.9
             + uAccent2 * pow(smoothstep(0.45, 0.95, n2), 2.0) * 0.7;
    vec2 g = p * 18.0;
    float h = hash12(floor(g));
    if (h > 0.88) col += vec3(1.0) * smoothstep(0.1, 0.0, length(fract(g) - 0.5)) * (0.6 + 0.4 * sin(time * 3.0 + h * 40.0)) * 1.4;
    col *= 0.7 + 0.6 * luma(orig);                                            
    col += mix(uAccent, uAccent2, 0.5) * pow(e, 2.0) * 0.8;
    outColor = vec4(col, 1.0);
}
