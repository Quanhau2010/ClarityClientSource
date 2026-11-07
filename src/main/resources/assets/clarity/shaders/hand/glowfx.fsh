#version 330 core

in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;
uniform sampler2D GlowTexture;
uniform vec3  glowColor;
uniform float intensity;
uniform int   mode;        
uniform float time;
uniform float pulse;

vec3 hsv2rgb(vec3 c) {
    vec3 p = abs(fract(c.xxx + vec3(1.0, 2.0 / 3.0, 1.0 / 3.0)) * 6.0 - 3.0);
    return c.z * mix(vec3(1.0), clamp(p - 1.0, 0.0, 1.0), c.y);
}
float hash12(vec2 p) { vec3 p3 = fract(vec3(p.xyx) * 0.1031); p3 += dot(p3, p3.yzx + 33.33); return fract((p3.x + p3.y) * p3.z); }

void main() {
    if (texture(DepthTexture, uv).r < 0.99) discard;          
    vec4 g = textureLod(GlowTexture, uv, 0.0);
    if (g.a < 0.0015) discard;                                

    float s = clamp(g.a * 2.0, 0.0, 1.0);                     
    s = pow(s, 0.6);                                          

    vec3 c;
    if (mode == 1) {
        vec3 avg = g.rgb / g.a;
        float mx = max(max(avg.r, avg.g), avg.b);
        c = mx > 1e-3 ? avg / mx : vec3(1.0);
        c = clamp(mix(vec3(dot(c, vec3(0.3333))), c, 1.4), 0.0, 1.0);
    } else if (mode == 2) {
        vec2 q = uv - 0.5;
        c = hsv2rgb(vec3(fract(atan(q.y, q.x) / 6.2831853 + time * 0.15), 0.7, 1.0));
    } else {
        c = glowColor;
    }
    float p = 1.0 + pulse * 0.3 * sin(time * 3.0);
    vec3 add = clamp(c * s * intensity * p, 0.0, 1.0);
    vec3 base = texture(ColorTexture, uv).rgb;
    vec3 col = 1.0 - (1.0 - base) * (1.0 - add);              
    col += (hash12(gl_FragCoord.xy) - 0.5) / 255.0;           
    outColor = vec4(col, 1.0);
}
