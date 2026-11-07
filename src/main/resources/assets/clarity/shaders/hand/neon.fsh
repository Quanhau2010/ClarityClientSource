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
    bool inside = handAt(uv) > 0.5;
    vec2 n;
    float e = edgeField(inside, 16.0 * pxScale + 4.0, n);
    if (!inside && e < 0.01) discard;
    float hum = 0.9 + 0.1 * sin(time * 50.0) * step(0.97, hash11(floor(time * 6.0)));   
    vec3 c = mix(uAccent, uAccent2, 0.5 + 0.5 * sin(uv.y * 6.0 + time * 0.8));
    if (inside) {
        vec3 orig = texture(ColorTexture, uv).rgb;
        float tube = smoothstep(0.55, 0.95, e);
        vec3 col = orig * 0.12 + c * 0.06;
        col += c * tube * 1.3 * hum + vec3(1.0) * pow(tube, 4.0) * 0.6 * hum;
        outColor = vec4(col, 1.0);
    } else {
        outColor = vec4(c * hum, pow(e, 1.8) * 0.75);
    }
}
