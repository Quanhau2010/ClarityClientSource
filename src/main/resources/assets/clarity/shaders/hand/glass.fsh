#version 330 core


in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;    
uniform sampler2D DepthTexture;
uniform sampler2D Background;      
uniform vec2  texel;               
uniform float pxScale;             
uniform vec3  tint;
uniform float tintAmount;
uniform float refraction;
uniform float detail;
uniform float rim;

float luma(vec3 c) { return dot(c, vec3(0.299, 0.587, 0.114)); }
float outside(vec2 p) { return step(0.99, texture(DepthTexture, p).r); }

void main() {
    if (texture(DepthTexture, uv).r >= 0.99) discard;

    vec3 hand = texture(ColorTexture, uv).rgb;
    float l = luma(hand);

    
    
    float R = 11.0 * pxScale;
    vec2 n = vec2(0.0);
    float edge = 0.0;
    for (int i = 0; i < 8; i++) {
        float a = float(i) * 0.7853982 + 0.3927;
        vec2 d = vec2(cos(a), sin(a));
        float o = outside(uv + d * texel * R);
        n += d * o;
        edge += o;
    }
    edge *= 0.125;

    
    vec2 e = texel * 1.5 * pxScale;
    vec2 g = vec2(luma(texture(ColorTexture, uv + vec2(e.x, 0.0)).rgb) - luma(texture(ColorTexture, uv - vec2(e.x, 0.0)).rgb),
                  luma(texture(ColorTexture, uv + vec2(0.0, e.y)).rgb) - luma(texture(ColorTexture, uv - vec2(0.0, e.y)).rgb));

    vec2 offs = (-n * 0.020 * smoothstep(0.0, 0.5, edge) + g * 0.012 * detail) * refraction;
    vec3 bg = texture(Background, uv + offs).rgb;
    
    float disp = edge * refraction * 0.004;
    bg.r = texture(Background, uv + offs * 1.0 - n * disp).r;
    bg.b = texture(Background, uv + offs * 1.0 + n * disp).b;

    vec3 col = mix(bg, bg * tint * 1.15 + tint * 0.04, tintAmount);
    col = col * 0.9 + mix(vec3(1.0), tint, 0.5) * 0.08;      
    col *= mix(1.0, 0.82 + 0.4 * l, detail);                 
    col += vec3(1.0) * pow(max(0.0, 0.6 * g.x - 0.8 * g.y), 1.5) * detail * 1.5;  

    
    float r = smoothstep(0.0, 0.5, edge);
    col *= 1.0 - 0.18 * smoothstep(0.0, 0.12, edge) * (1.0 - r);   
    col += (vec3(1.0) * 0.45 + tint * 0.25) * r * r * rim;
    float band = fract((uv.x + uv.y) * 1.4);
    float sheen = smoothstep(0.06, 0.0, abs(band - 0.32) - 0.05) + 0.5 * smoothstep(0.03, 0.0, abs(band - 0.42) - 0.012);
    col += vec3(1.0) * sheen * 0.10 * rim;

    outColor = vec4(col, 1.0);
}
