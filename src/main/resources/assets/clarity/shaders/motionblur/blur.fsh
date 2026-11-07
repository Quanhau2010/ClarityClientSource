#version 330 core






uniform sampler2D uScene;
uniform mat3  uDelta;    
uniform vec2  uFocal;    
uniform float uScale;    
uniform float uMaxLen;   
uniform vec2  uSize;     
uniform int   uSamples;

in vec2 uv;
out vec4 fragColor;


float ign(vec2 p) {
    return fract(52.9829189 * fract(dot(p, vec2(0.06711056, 0.00583715))));
}

void main() {
    vec4 base = texture(uScene, uv);

    vec2 ndc = uv * 2.0 - 1.0;
    vec3 p = uDelta * vec3(ndc / uFocal, -1.0);
    if (p.z > -1e-3) { fragColor = base; return; }          
    vec2 prev = p.xy * uFocal / -p.z;

    vec2 vel = (ndc - prev) * (0.5 * uScale);               
    float len = length(vel);
    if (len * max(uSize.x, uSize.y) < 0.75) { fragColor = base; return; }
    if (len > uMaxLen) vel *= uMaxLen / len;

    float j = ign(gl_FragCoord.xy);
    float inv = 1.0 / float(uSamples);
    vec3 acc = vec3(0.0);
    for (int i = 0; i < uSamples; i++) {
        float t = (float(i) + j) * inv - 0.5;               
        acc += texture(uScene, uv + vel * t).rgb;
    }
    fragColor = vec4(acc * inv, base.a);
}
