#version 330 core



in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;
uniform sampler2D Background;      
uniform sampler2D HaloTexture;     
uniform vec2  texel;
uniform float pxScale;
uniform vec3  holoColor;
uniform float opacity;
uniform float scanlines;
uniform float glitch;
uniform float halo;
uniform float hasBackground;
uniform float time;

float luma(vec3 c) { return dot(c, vec3(0.299, 0.587, 0.114)); }
float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }

void main() {
    float depth = texture(DepthTexture, uv).r;
    vec3 base = texture(ColorTexture, uv).rgb;

    if (depth >= 0.99) {                                       
        if (halo < 0.01) discard;
        float a = textureLod(HaloTexture, uv, 0.0).a;
        if (a < 0.002) discard;
        float s = clamp(a * 2.0, 0.0, 1.0);
        s = s * (2.0 - s);
        vec3 add = clamp(holoColor * s * halo * 0.85, 0.0, 1.0);
        vec3 col = 1.0 - (1.0 - base) * (1.0 - add);
        col += (hash12(gl_FragCoord.xy) - 0.5) / 255.0;
        outColor = vec4(col, 1.0);
        return;
    }

    
    float line = floor(gl_FragCoord.y / (6.0 * pxScale));
    float tg = floor(time * 12.0);
    float gl = step(0.975, hash11(line * 0.37 + tg)) * glitch;
    vec2 suv = uv + vec2((hash11(line + tg * 1.7) - 0.5) * 0.03 * gl, 0.0);

    
    vec2 ca = vec2(texel.x * (2.0 + 6.0 * gl) * pxScale, 0.0);
    float lr = luma(texture(ColorTexture, suv + ca).rgb);
    float lg = luma(texture(ColorTexture, suv).rgb);
    float lb = luma(texture(ColorTexture, suv - ca).rgb);
    vec3 lum = vec3(lr, lg, lb);

    
    float R = 5.0 * pxScale;
    float edge = 0.0;
    for (int i = 0; i < 8; i++) {
        float a = float(i) * 0.7853982 + 0.3927;
        edge += step(0.99, texture(DepthTexture, uv + vec2(cos(a), sin(a)) * texel * R).r);
    }
    edge *= 0.125;
    float rim = smoothstep(0.0, 0.45, edge);

    vec3 holo = holoColor * (0.25 + 1.25 * lum);
    vec3 bg = hasBackground > 0.5 ? texture(Background, uv).rgb : base * 0.3;
    vec3 col = mix(bg, holo, opacity);

    
    float sl = 0.5 + 0.5 * cos(gl_FragCoord.y / pxScale * 2.0943951);
    col *= 1.0 - scanlines * 0.28 * sl;
    float bar = exp(-pow((fract(uv.y * 0.7 - time * 0.35) - 0.5) * 9.0, 2.0));
    col += holoColor * bar * 0.22;

    col += holoColor * rim * 0.9 + vec3(1.0) * rim * rim * 0.25;
    col += holoColor * gl * 0.35;
    col *= 0.94 + 0.06 * hash11(floor(time * 24.0));          
    outColor = vec4(col, 1.0);
}
