#version 330 core







in vec3 vPos;
in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform float uTime;
uniform int   uStyle;     
uniform vec3  uA, uB;
uniform vec3  uCam;       
uniform sampler2D uTex;   

float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise(vec2 p) { vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y); }

void main() {
    vec2 uv = vUV;
    float cube = floor(vColor.b + 0.5), seed = vAux.y;
    vec4 texel = texture(uTex, vColor.rg);
    if (texel.a < 0.1) discard;                                      
    float lum = dot(texel.rgb, vec3(0.299, 0.587, 0.114));
    float k = vColor.a;
    float e = min(min(uv.x, 1.0 - uv.x), min(uv.y, 1.0 - uv.y));      
    vec3 tint = mix(uA, uB, cube * 0.5);
    vec3 base;            
    float amt = 0.6;      
    vec3 glow = vec3(0.0);

    if (uStyle == 0) {                       
        float run = 0.55 + 0.45 * sin((uv.x + uv.y) * 6.2831 - uTime * 3.5 + cube * 2.1 + seed * 6.0);
        base = tint * (0.30 + 0.95 * lum);
        glow = mix(tint, vec3(1.0), 0.3) * exp(-e * 20.0) * (0.6 + 0.6 * run);
        if (cube > 1.5) glow += mix(uA, vec3(1.0), 0.4) * 0.25 * (0.7 + 0.3 * sin(uTime * 4.0 + seed * 9.0));
    } else if (uStyle == 1) {                
        float wy = vPos.y + uCam.y;
        float scan = 0.5 + 0.5 * sin(wy * 55.0 - uTime * 7.0);
        float sweep = exp(-pow(fract(wy * 0.6 - uTime * 0.5 + seed) - 0.5, 2.0) * 120.0);
        float flick = 0.88 + 0.12 * sin(uTime * 41.0 + seed * 17.0);
        base = tint * (0.20 + 0.9 * lum) * (0.55 + 0.45 * scan) * flick;
        amt = 0.7;
        glow = mix(tint, vec3(1.0), 0.5) * (exp(-e * 26.0) * 0.35 + sweep * 0.6);
    } else if (uStyle == 2) {                
        vec2 q = uv * 2.4 + vec2(cube * 3.7 + seed * 5.0, uTime * 0.08);
        float n = vnoise(q * 1.5) * 0.6 + vnoise(q * 3.1 + 4.0) * 0.4;
        base = mix(uB * 0.25, uA, smoothstep(0.25, 0.85, n)) * (0.35 + 0.8 * lum);
        amt = 0.75;
        vec2 g = floor(uv * 9.0 + cube * 7.0), f = fract(uv * 9.0) - 0.5;
        float h = hash12(g + seed);
        glow = vec3(1.0) * step(0.8, h) * smoothstep(0.2, 0.0, length(f)) * (0.5 + 0.5 * sin(uTime * 3.0 + h * 40.0)) * 0.8;
    } else {                                 
        float band = pow(0.5 + 0.5 * sin((uv.x * 0.8 + uv.y) * 5.0 - uTime * 2.6 + cube * 1.3 + seed * 6.0), 10.0);
        float band2 = pow(0.5 + 0.5 * sin((uv.x - uv.y * 0.6) * 3.0 + uTime * 1.7 + cube), 14.0);
        base = tint * (0.35 + 0.9 * lum);
        amt = 0.5;
        glow = mix(uA, vec3(1.0), 0.35) * band * 0.7 + uB * band2 * 0.45;
    }

    float a = amt * k * texel.a;
    vec3 rgb = base * a + glow * k * texel.a;
    if (a < 0.002 && max(rgb.r, max(rgb.g, rgb.b)) < 0.002) discard;
    fragColor = vec4(rgb, a);            
}
