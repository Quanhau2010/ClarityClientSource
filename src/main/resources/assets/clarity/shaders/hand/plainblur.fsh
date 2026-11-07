#version 330 core



in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;   
uniform sampler2D DepthTexture;
uniform sampler2D Background;     
uniform int   selfBlur;
uniform vec2  texel;
uniform float radius;             

float inside(vec2 p) { return 1.0 - step(0.99, texture(DepthTexture, p).r); }

void main() {
    if (inside(uv) < 0.5) discard;
    if (selfBlur == 0) {
        outColor = vec4(texture(Background, uv).rgb, 1.0);
        return;
    }
    
    vec3 acc = texture(ColorTexture, uv).rgb;
    float wsum = 1.0;
    float R = max(radius, 1.0);
    for (int i = 1; i < 48; i++) {
        float fi = float(i);
        float r = sqrt(fi / 48.0) * R;
        float a = fi * 2.39996323;
        vec2 p = uv + vec2(cos(a), sin(a)) * r * texel;
        float m = inside(p);
        float w = m * exp(-(r * r) / (R * R * 0.5));
        acc += texture(ColorTexture, p).rgb * w;
        wsum += w;
    }
    outColor = vec4(acc / wsum, 1.0);
}
