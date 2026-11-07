#version 330 core
in vec2 vUV;
in vec4 vData;
out vec4 fragColor;

uniform int   uStyle;
uniform float uTime;
uniform vec3  uColor1, uColor2;
uniform int   uNatural;
uniform float uOpacity;

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

void main() {
    vec2 uv = vUV;
    float r = length(uv);
    float seed = vData.x, alpha = vData.y * uOpacity, s2 = vData.z;
    vec3 col; float a = 0.0;
    bool nat = uNatural == 1;

    if (uStyle == 0) {                                   
        vec3 sc = nat ? vec3(0.95, 0.97, 1.0) : mix(uColor1, uColor2, s2);
        float ang = atan(uv.y, uv.x);
        float arms = pow(abs(cos(ang * 3.0)), 18.0) * smoothstep(0.9, 0.2, r) * 0.6;
        float f = exp(-r * r * 5.0) + arms;
        col = sc * f; a = f * 0.8;
    } else if (uStyle == 1) {                            
        vec3 rc = nat ? vec3(0.65, 0.75, 0.95) : uColor1;
        float f = (1.0 - uv.x * uv.x) * smoothstep(-1.0, 0.6, uv.y) * smoothstep(1.0, 0.7, uv.y);
        col = rc * f * 0.55; a = f * 0.25;
    } else if (uStyle == 2) {                            
        vec2 p = uv * vec2(1.6, 1.15);
        float d = length(p) - 1.0;
        d = max(d, -(length(uv - vec2(0.0, 1.05)) - 0.32));          
        float m = aa(d);
        vec3 pc = nat ? mix(vec3(1.0, 0.72, 0.82), vec3(1.0, 0.9, 0.94), s2) : mix(uColor1, uColor2, s2);
        pc = mix(pc * 0.85, pc, smoothstep(-1.0, 0.6, uv.y)) + vec3(0.08) * (1.0 - r);
        col = pc * m; a = m * 0.95;
    } else if (uStyle == 3) {                            
        float fl = 0.6 + 0.4 * sin(uTime * (8.0 + 6.0 * seed) + seed * 40.0);
        vec3 ec = nat ? mix(vec3(1.0, 0.45, 0.1), vec3(1.0, 0.8, 0.3), s2) : mix(uColor1, uColor2, s2);
        float f = exp(-r * r * 9.0) * 2.2 + exp(-r * r * 2.0) * 0.6;
        col = ec * f * fl; a = 0.0;
    } else if (uStyle == 4) {                            
        float blink = pow(0.5 + 0.5 * sin(uTime * (1.2 + seed) + seed * 30.0), 6.0);
        vec3 fc = nat ? vec3(0.75, 1.0, 0.35) : uColor1;
        float f = exp(-r * r * 12.0) * 2.2 + exp(-r * r * 2.2) * 0.7;
        col = fc * f * (0.3 + 0.7 * blink); a = 0.0;
    } else if (uStyle == 5) {                            
        float tw = 0.6 + 0.4 * sin(uTime * (3.0 + 4.0 * seed) + seed * 50.0);
        vec3 stc = nat ? mix(vec3(1.0, 0.95, 0.8), vec3(0.7, 0.85, 1.0), s2) : mix(uColor1, uColor2, s2);
        float f = star4(uv, 8.0) * 2.0 + exp(-r * r * 14.0) * 1.2;
        col = stc * f * tw; a = 0.0;
    } else if (uStyle == 6) {                            
        vec2 p = uv * vec2(1.9, 1.0);
        float d = max(length(p - vec2(0.55, 0.0)), length(p + vec2(0.55, 0.0))) - 1.15;
        float m = aa(d);
        float rib = smoothstep(0.06, 0.0, abs(uv.x)) * step(abs(uv.y), 0.9);
        vec3 lc = nat ? mix(vec3(0.95, 0.45, 0.1), vec3(0.75, 0.2, 0.08), s2) : mix(uColor1, uColor2, s2);
        if (nat && s2 > 0.75) lc = vec3(0.95, 0.75, 0.15);
        lc *= 0.8 + 0.3 * smoothstep(-1.0, 1.0, uv.y + uv.x * 0.3);
        col = mix(lc, lc * 0.6, rib) * m; a = m * 0.95;
    } else {                                             
        float d = sdHeart(uv * 1.15);
        float m = aa(d);
        vec3 hc = nat ? mix(vec3(1.0, 0.3, 0.45), vec3(1.0, 0.55, 0.75), s2) : mix(uColor1, uColor2, s2);
        hc += vec3(0.25) * smoothstep(0.3, 0.0, length(uv - vec2(-0.3, 0.3)));
        col = hc * m + hc * exp(-max(d, 0.0) * 10.0) * (1.0 - m) * 0.4; a = m * 0.9;
    }
    fragColor = vec4(col, a) * alpha;
}
