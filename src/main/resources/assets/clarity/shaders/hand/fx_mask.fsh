#version 330 core


in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;
uniform vec2  srcTexel;
uniform float spread;

void main() {
    vec4 acc = vec4(0.0);
    for (int i = 0; i < 4; i++) {
        vec2 o = vec2((i & 1) == 0 ? -spread : spread, (i & 2) == 0 ? -spread : spread) * srcTexel;
        vec2 p = uv + o;
        float m = 1.0 - step(0.99, texture(DepthTexture, p).r);
        acc += vec4(texture(ColorTexture, p).rgb * m, m);
    }
    outColor = acc * 0.25;
}
