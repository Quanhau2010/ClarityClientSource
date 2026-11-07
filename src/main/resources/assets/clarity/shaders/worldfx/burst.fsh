#version 330 core
in vec2 vUV;
in vec4 vData;
out vec4 fragColor;

uniform int   uStyle;
uniform vec3  uColor1, uColor2;
uniform int   uNatural;

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
    float seed = vData.x, t = vData.y, s2 = vData.z;
    float fade = (1.0 - t) * (1.0 - t);
    vec3 c1 = uColor1, c2 = uColor2;
    vec3 col; float a = 0.0;

    if (uStyle == 0) {                                   
        float f = (1.0 - uv.x * uv.x) * smoothstep(-1.0, 0.2, uv.y) * smoothstep(1.0, 0.6, uv.y);
        vec3 sc = mix(vec3(1.0, 0.95, 0.8), uNatural == 1 ? vec3(1.0, 0.55, 0.15) : c1, smoothstep(0.0, 0.6, t));
        col = sc * f * 1.6 * (1.0 - t);
    } else if (uStyle == 1) {                            
        vec3 sc = mix(c1, c2, s2);
        col = (sc * star4(uv, 7.0) * 1.4 + vec3(1.0) * exp(-r * r * 30.0) * 0.8) * fade;
    } else if (uStyle == 2) {                            
        float d = sdHeart(uv * 1.15); float m = aa(d);
        vec3 hc = uNatural == 1 ? mix(vec3(1.0, 0.25, 0.4), vec3(1.0, 0.5, 0.7), s2) : mix(c1, c2, s2);
        hc += vec3(0.3) * smoothstep(0.3, 0.0, length(uv - vec2(-0.3, 0.3)));
        col = hc * m * (1.0 - t * t); a = m * (1.0 - t * t);
    } else if (uStyle == 3) {                            
        vec2 p = uv;
        float d = max(abs(p.x) * 1.8 + p.y * 0.55 - 0.75, -p.y - 0.95);
        float m = aa(d);
        vec3 sc = mix(c1, c2, s2);
        float glint = pow(max(0.0, sin((p.x - p.y) * 3.0 + t * 20.0)), 8.0);
        col = (sc * (0.6 + 0.4 * (1.0 - r)) + vec3(1.0) * glint * 0.8) * m * fade; a = m * fade * 0.7;
    } else if (uStyle == 4) {                            
        float d = length(uv * vec2(1.0, 0.85)) - 0.8; float m = aa(d);
        vec3 bc = uNatural == 1 ? vec3(0.55, 0.02, 0.03) : c1;
        bc += vec3(0.35, 0.15, 0.15) * smoothstep(0.35, 0.0, length(uv - vec2(-0.25, 0.25)));
        col = bc * m * fade; a = m * fade;
    } else if (uStyle == 5) {                            
        vec3 mc = mix(c1, c2, s2);
        col = (mc * exp(-r * r * 5.0) * 0.9 + vec3(1.0) * exp(-r * r * 25.0) * 0.7) * fade;
    } else if (uStyle == 6) {                            
        if (vData.w < 0.5) {
            float R = 0.86;
            float ring = exp(-pow((r - R) / (0.03 + 0.06 * t), 2.0));
            col = mix(vec3(1.0), c1, 0.4 + 0.6 * t) * ring * 1.5 * fade + c2 * exp(-pow((r - R) / 0.25, 2.0)) * 0.25 * fade;
            col *= smoothstep(1.0, 0.95, r);
        } else {
            float f = (1.0 - uv.x * uv.x) * smoothstep(-1.0, 0.2, uv.y) * smoothstep(1.0, 0.6, uv.y);
            col = mix(vec3(1.0), c1, t) * f * 1.4 * (1.0 - t);
        }
    } else {                                             
        float m = aa(max(abs(uv.x) - 0.85, abs(uv.y) - 0.55));
        vec3 cc = uNatural == 1 ? hsv2rgb(vec3(seed, 0.75, 1.0)) : mix(c1, c2, s2);
        col = cc * m * (1.0 - t * t); a = m * (1.0 - t * t);
    }
    fragColor = vec4(col, a);
}
