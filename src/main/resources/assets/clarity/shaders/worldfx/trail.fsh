#version 330 core




in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform float uTime;
uniform int   uStyle;     
uniform float uOpacity;

float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float vnoise2(vec2 p) {
    vec2 i = floor(p), f = fract(p); f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x), mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y);
}
float fbm2(vec2 p) { float s = 0.0, a = 0.5; for (int i = 0; i < 4; i++) { s += a * vnoise2(p); p = p * 2.03 + 7.1; a *= 0.5; } return s / 0.9375; }
float noise1(float x) { float i = floor(x), f = fract(x); f = f * f * (3.0 - 2.0 * f); return mix(hash11(i), hash11(i + 1.0), f); }

void main() {
    if (uStyle == 8) {                                
        float yy = vUV.y;
        float core = exp(-yy * yy * 30.0), glow = exp(-yy * yy * 2.6) * 0.45;
        vec3 lc = mix(vColor.rgb, vec3(1.0), 0.55) * core + vColor.rgb * glow;
        float sf = smoothstep(1.4, 1.1, abs(yy));
        fragColor = vec4(lc * vColor.a * uOpacity * sf, 0.0);
        return;
    }
    float t = clamp(vUV.x, 0.0, 1.0);
    float y = vUV.y;
    float d = vAux.x;
    float seed = vAux.y;
    vec3  c = vColor.rgb;

    float fade = 1.0 - t;
    fade *= fade;
    fade *= smoothstep(0.0, 0.03, t);                 
    fade *= uOpacity;

    vec3 col;
    float a = 0.0;

    if (uStyle == 1) {                                
        float band  = smoothstep(1.0, 0.8, abs(y));
        float shade = 0.65 + 0.35 * (1.0 - y * y);
        float sheen = smoothstep(0.55, 0.9, abs(y)) * band;
        float wave  = 0.85 + 0.15 * sin(d * 3.0 - uTime * 3.0);
        col = c * (shade * wave + sheen * 0.45) * band;
        a = band * 0.65;
    } else if (uStyle == 2) {                         
        float j = (noise1(d * 4.0 - uTime * 7.0 + seed * 13.0) - 0.5) * 0.75
                + (noise1(d * 15.0 + uTime * 11.0 + seed * 7.0) - 0.5) * 0.35;
        float yy = y - j;
        float core = exp(-yy * yy * 140.0);
        float glow = exp(-yy * yy * 7.0) * 0.45;
        float flick = 0.75 + 0.25 * noise1(uTime * 30.0 + seed * 5.0);
        col = (mix(c, vec3(1.0), 0.75) * core * 1.6 + c * glow) * flick;
    } else if (uStyle == 3) {                         
        float body = exp(-y * y * 3.0) * (1.0 - t) * 0.5;
        float cell = d * 5.0;
        float id = floor(cell);
        float h = hash11(id + seed * 17.0);
        float oy = (hash11(id * 1.7 + 3.0) - 0.5) * 1.4;
        vec2  q = vec2((fract(cell) - 0.5) * 2.2, (y - oy) * 2.6);
        float tw = 0.5 + 0.5 * sin(uTime * (6.0 + 6.0 * h) + h * 40.0);
        float spark = smoothstep(0.35, 0.0, length(q)) * step(0.35, h) * tw;
        spark += smoothstep(0.06, 0.0, abs(q.x) * abs(q.y)) * smoothstep(0.9, 0.0, length(q)) * step(0.35, h) * tw * 0.6;
        col = c * body + mix(c, vec3(1.0), 0.6) * spark * 1.3;
    } else if (uStyle == 4) {                         
        col = vec3(0.0);
        for (int i = 0; i < 6; i++) {
            float fi = float(i);
            float h = hash11(fi * 7.13 + seed * 3.0);
            float spread = (0.25 + 0.75 * t);                     
            float off = (h - 0.5) * 1.5 * spread;
            float sway = sin(d * (1.6 + h * 1.4) - uTime * (2.2 + h) + fi * 2.1) * 0.32 * spread
                       + (noise1(d * 0.9 + fi * 5.0 - uTime * 0.8) - 0.5) * 0.4 * spread;
            float yy = y - off - sway;
            float thin = 0.035 + 0.05 * t;
            float strand = exp(-yy * yy / (thin * thin));
            float halo = exp(-yy * yy / (thin * thin * 14.0)) * 0.22;
            
            float gust = smoothstep(0.35, 0.95, noise1(d * 0.7 - uTime * (3.2 + h * 2.0) + fi * 9.0));
            float dash = 0.35 + 0.65 * gust;
            col += mix(c, vec3(1.0), 0.45 * gust) * (strand * 1.05 + halo) * dash * (0.65 + 0.35 * h);
        }
        col += c * exp(-y * y * 2.2) * 0.07 * (1.0 - t);          
    } else if (uStyle == 5) {                         
        float yy = y * 0.75 + 0.05;
        float body = smoothstep(1.05, 0.15, abs(yy)) ;
        float edge = exp(-pow((yy + 0.55) * 4.0, 2.0));           
        float rays = 0.45 + 0.55 * pow(fbm2(vec2(d * 3.5 - uTime * 0.6, seed * 9.0)), 1.5);
        float shimmer = 0.75 + 0.25 * sin(d * 9.0 + uTime * 4.0 + fbm2(vec2(d, uTime * 0.3)) * 6.0);
        vec3 top = mix(c, vec3(0.75, 0.55, 1.0) * length(c), 0.35);
        vec3 ac = mix(c, top, smoothstep(-0.6, 0.9, yy));
        col = ac * body * rays * shimmer * 0.75 + mix(c, vec3(1.0), 0.5) * edge * rays * 0.9;
    } else if (uStyle == 6) {                         
        col = vec3(0.0);
        for (int i = 0; i < 2; i++) {
            float ph = d * 2.4 - uTime * 2.0 + float(i) * 3.14159 + seed * 6.0;
            float cy = sin(ph) * 0.62;
            float thick = 0.07 + 0.19 * abs(cos(ph));                 
            float yy = (y - cy) / thick;
            float band = smoothstep(1.0, 0.6, abs(yy));
            float sheen = pow(max(0.0, cos(ph)), 6.0);                
            float shade = 0.55 + 0.45 * (1.0 - yy * yy);
            vec3 sc = mix(c, mix(c, vec3(1.0), 0.6), float(i) * 0.25);
            col += sc * band * shade * 0.9 + vec3(1.0) * band * sheen * 0.55 + sc * exp(-yy * yy * 0.25) * 0.08;
            a = max(a, band * 0.35);
        }
    } else if (uStyle == 7) {                         
        float spread = 0.45 + 0.9 * t;
        vec2 q = vec2(d * 1.6 - uTime * 0.7, y / spread * 1.3);
        float w = fbm2(q + vec2(seed * 11.0, 0.0));
        float m = fbm2(q * 1.7 + w * 2.2 + vec2(-uTime * 0.35, uTime * 0.2));
        float dens = smoothstep(0.35, 0.85, m) * exp(-pow(y / (spread * 0.95), 2.0));
        float wisp = pow(smoothstep(0.55, 0.95, m), 3.0) * exp(-pow(y / spread, 2.0));
        col = c * dens * 0.75 + mix(c, vec3(1.0), 0.55) * wisp * 0.9;
    } else {                                          
        float core = exp(-y * y * 45.0);
        float glow = exp(-y * y * 4.0);
        float flow = noise1(d * 2.5 - uTime * 5.0 + seed * 9.0);
        float pulse = 0.65 + 0.35 * sin(d * 5.0 - uTime * 9.0);
        col = c * glow * (0.45 + 0.55 * flow) * pulse + mix(c, vec3(1.0), 0.65) * core * 1.1;
    }

    float sideFade = smoothstep(1.4, 1.15, abs(y));       
    fragColor = vec4(col * fade * sideFade, a * fade * sideFade);
}
