#version 330 core




in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform sampler2D uTex;
uniform vec3  uColor1, uColor2;
uniform float uTime;

float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise(vec2 p) { vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y); }

void main() {
    float op = vColor.a;
    if (vAux.y < -50.0) {                                   
        vec2 uv = vUV;                                        
        float core = exp(-uv.y * uv.y * 60.0) * smoothstep(1.0, 0.6, abs(uv.x));
        float glow = exp(-uv.y * uv.y * 6.0) * smoothstep(1.0, 0.3, abs(uv.x)) * 0.5;
        vec3 col = mix(uColor1, vec3(1.0), 0.7) * core * 1.8 + uColor1 * glow;
        fragColor = vec4(col * op, 0.0);
        return;
    }
    vec4 t = texture(uTex, vUV);
    if (t.a < 0.03) discard;
    float dis = vAux.x;
    float n = vnoise(vUV * 22.0) * 0.7 + vnoise(vUV * 60.0) * 0.3;
    if (n < dis) discard;
    float burn = smoothstep(dis + 0.09, dis, n) * step(0.001, dis);
    float cut = exp(-abs(vAux.y) * 70.0);
    vec3 col = t.rgb + (mix(uColor1, vec3(1.0), 0.3) * cut * 1.4 + mix(uColor2, uColor1, burn) * burn * 1.6) * t.a;
    fragColor = vec4(col, t.a) * op;
}
