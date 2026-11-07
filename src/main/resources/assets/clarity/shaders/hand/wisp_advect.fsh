#version 330 core





in vec2 uv;
out vec4 outColor;

uniform sampler2D History;
uniform sampler2D Mask;
uniform vec2  wind;         
uniform float dt;
uniform float decay;        
uniform float turbulence;
uniform float time;
uniform float aspect;
uniform float rise;         
uniform vec2  texel;        
uniform float spread;       

float hash(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.x + p3.y) * p3.z);
}
float noise(vec2 p) {
    vec2 i = floor(p), f = fract(p);
    f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash(i), hash(i + vec2(1, 0)), f.x),
               mix(hash(i + vec2(0, 1)), hash(i + vec2(1, 1)), f.x), f.y);
}

vec2 curl(vec2 p) {
    const float e = 0.12;
    float a = noise(p + vec2(0, e)), b = noise(p - vec2(0, e));
    float c = noise(p + vec2(e, 0)), d = noise(p - vec2(e, 0));
    return vec2(a - b, d - c) / (2.0 * e);
}

void main() {
    vec2 q = vec2(uv.x * aspect, uv.y);
    vec2 flow = curl(q * 3.5 + vec2(0.0, -time * 0.8)) + 0.5 * curl(q * 8.0 + vec2(time * 0.5, time * 0.3));
    vec2 v = wind + vec2(0.0, rise) + flow * (turbulence * 0.10) * vec2(1.0 / aspect, 1.0);

    
    
    vec2 src = uv - v * dt;
    vec2 o = texel * spread;
    vec4 h = texture(History, src) * 0.36
           + (texture(History, src + vec2(o.x, 0.0)) + texture(History, src - vec2(o.x, 0.0))
            + texture(History, src + vec2(0.0, o.y)) + texture(History, src - vec2(0.0, o.y))) * 0.16;
    h *= decay;
    vec4 m = texture(Mask, uv);
    outColor = max(h, m);
}
