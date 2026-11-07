#version 330 core

in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;
uniform sampler2D WispTexture;
uniform vec3  wispColor;
uniform float intensity;
uniform int   mode;          
uniform float time;
uniform vec2  texel;         
uniform float aspect;

vec3 hsv2rgb(vec3 c) {
    vec3 p = abs(fract(c.xxx + vec3(1.0, 2.0 / 3.0, 1.0 / 3.0)) * 6.0 - 3.0);
    return c.z * mix(vec3(1.0), clamp(p - 1.0, 0.0, 1.0), c.y);
}
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }
float noise(vec2 p) {
    vec2 i = floor(p), f = fract(p);
    f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1, 0)), f.x),
               mix(hash12(i + vec2(0, 1)), hash12(i + vec2(1, 1)), f.x), f.y);
}

void main() {
    if (texture(DepthTexture, uv).r < 0.99) discard;            

    
    vec2 r1 = texel * 2.2, r2 = texel * 4.8;
    float probe = textureLod(WispTexture, uv, 0.0).a
                + textureLod(WispTexture, uv + vec2( r2.x, 0.0), 0.0).a
                + textureLod(WispTexture, uv + vec2(-r2.x, 0.0), 0.0).a
                + textureLod(WispTexture, uv + vec2(0.0,  r2.y), 0.0).a
                + textureLod(WispTexture, uv + vec2(0.0, -r2.y), 0.0).a;
    if (probe < 1e-4) discard;

    
    
    vec4 g = textureLod(WispTexture, uv, 0.0) * 0.20;
    for (int i = 0; i < 6; i++) {
        float a = float(i) * 1.0471976;                       
        vec2 d = vec2(cos(a), sin(a));
        g += textureLod(WispTexture, uv + d * r1, 0.0) * 0.085;
        g += textureLod(WispTexture, uv + vec2(-d.y, d.x) * r2, 0.0) * 0.048;
    }

    
    
    
    float a0 = g.a;
    float s = 1.0 - exp(-a0 * 1.5);
    s *= mix(smoothstep(0.0, 0.35, a0), 1.0, 0.45);
    s *= 1.25;                                                  

    
    vec2 q = vec2(uv.x * aspect, uv.y);
    s *= 0.85 + 0.3 * noise(q * 9.0 + vec2(0.0, -time * 1.3));

    vec3 c;
    if (mode == 1) {
        vec3 avg = g.rgb / max(g.a, 1e-4);
        float mx = max(max(avg.r, avg.g), avg.b);
        c = mx > 1e-3 ? avg / mx : vec3(1.0);
        c = clamp(mix(vec3(dot(c, vec3(0.3333))), c, 1.4), 0.0, 1.0);
    } else if (mode == 2) {
        c = hsv2rgb(vec3(fract(uv.y * 0.6 - time * 0.25 + g.a * 0.3), 0.7, 1.0));
    } else {
        c = wispColor;
    }
    vec3 add = clamp(c * s * intensity, 0.0, 1.0);
    vec3 base = texture(ColorTexture, uv).rgb;
    vec3 col = 1.0 - (1.0 - base) * (1.0 - add);                
    col += (hash12(gl_FragCoord.xy) - 0.5) / 255.0;             
    outColor = vec4(col, 1.0);
}
