#version 330 core

in  vec2 uv;
out vec4 outColor;

uniform sampler2D EntityMask;
uniform sampler2D ChamsTexture;

uniform int   mode;          
uniform float time;
uniform float patternScale;
uniform float opacity;
uniform float scrollSpeed;
uniform vec3  tintColor;
uniform float tintAmount;

float hash(vec2 p) {
    return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453);
}

void main() {
    vec4 m = texture(EntityMask, uv);
    if (m.a < 0.02) discard;

    vec3  base = m.rgb;
    float a    = m.a;

    vec2  p = uv * patternScale;

    if (mode == 1) {
        
        float s = 0.55 + 0.45 * sin((p.y + time * 6.0) * 3.14159);
        base *= s;
    } else if (mode == 2) {
        
        vec2  g    = abs(fract(p) - 0.5);
        float line = smoothstep(0.42, 0.5, max(g.x, g.y));
        base = mix(base * 0.35, base, line);
        a   *= mix(0.7, 1.0, line);
    } else if (mode == 3) {
        
        base *= 0.65 + 0.7 * hash(floor(p * 8.0) + floor(time * 12.0));
    } else if (mode == 4) {
        
        float b = 0.6 + 0.4 * sin((p.y - time * 4.0) * 6.2831);
        base *= b;
        a    *= 0.8 + 0.2 * sin(time * 3.0);
    } else if (mode == 5) {
        
        vec2 tuv = vec2(m.r, fract(m.g + time * scrollSpeed));
        vec4 tex = texture(ChamsTexture, tuv);
        if (tex.a < 0.05) discard;
        base  = tex.rgb;
        a    *= tex.a;
    }

    base = mix(base, tintColor, clamp(tintAmount, 0.0, 1.0));

    outColor = vec4(base, a * clamp(opacity, 0.0, 1.0));
}
