#version 330 core



in vec2 uv;
out vec4 fragColor;

uniform sampler2D uScene;      
uniform float uTime, uOpen, uAspect, uDim;
uniform vec3  uAccent, uAccent2;
uniform int   uStyle;          
uniform int   uHasScene;       

float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise(vec2 p) { vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y); }
float fbm(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 4; i++) { s += a * vnoise(p); p = p * 2.03 + 7.1; a *= 0.5; } return s / 0.9375; }

float luma3(vec3 c) { return dot(c, vec3(0.2126, 0.7152, 0.0722)); }

void main() {
    vec3 scene = texture(uScene, uv).rgb;
    vec2 p = vec2((uv.x - 0.5) * uAspect, uv.y - 0.5);
    float t = uTime;
    vec3 col = scene * (1.0 - uDim * uOpen);
    vec3 light = vec3(0.0);

    if (uStyle == 0) {                                     
        for (int i = 0; i < 4; i++) {
            float fi = float(i);
            vec2 c = vec2(sin(t * (0.11 + fi * 0.03) + fi * 1.7) * 0.55 * uAspect,
                          cos(t * (0.09 + fi * 0.025) + fi * 2.3) * 0.38);
            float r = 0.38 + 0.1 * sin(t * 0.2 + fi);
            float g = exp(-dot(p - c, p - c) / (r * r));
            light += mix(uAccent, uAccent2, 0.5 + 0.5 * sin(fi * 1.9 + t * 0.15)) * g;
        }
        float rays = 0.5 + 0.5 * sin((p.x + p.y * 0.4) * 9.0 + t * 0.4);
        light *= 0.55 + 0.25 * rays;
    } else if (uStyle == 1) {                              
        vec2 q = p * 1.8 + vec2(t * 0.02, -t * 0.015);
        vec2 w = vec2(fbm(q + 3.1), fbm(q + 7.7));
        float n = fbm(q + w * 1.6 + t * 0.03);
        light = mix(uAccent2, uAccent, smoothstep(0.35, 0.85, n)) * smoothstep(0.3, 0.9, n) * 1.1;
    } else if (uStyle == 2) {                              
        float w1 = sin(p.x * 3.0 + t * 0.6 + sin(p.y * 2.0 + t * 0.3) * 1.5);
        float w2 = sin(p.x * 2.1 - t * 0.45 + p.y * 4.0);
        float band = smoothstep(0.55, 1.0, w1 * 0.5 + 0.5) * 0.7 + smoothstep(0.75, 1.0, w2 * 0.5 + 0.5) * 0.5;
        light = mix(uAccent2, uAccent, uv.y) * band * (0.4 + 0.6 * smoothstep(-0.6, 0.6, p.y));
    }
    col = 1.0 - (1.0 - col) * (1.0 - light * 0.32 * uOpen);  

    float vig = smoothstep(1.25, 0.25, length(p * vec2(0.85, 1.1)));
    col *= mix(1.0, vig, 0.55 * uOpen);
    col += (hash12(gl_FragCoord.xy + fract(t) * 91.0) - 0.5) * 0.018 * uOpen;   
    if (uHasScene == 0) {                                  
        float a = clamp(uDim, 0.0, 0.95) * uOpen;
        vec3 l = light * 0.32 * uOpen;
        fragColor = vec4(l, a);          
        return;
    }
    fragColor = vec4(col * uOpen, uOpen);
}
