#version 330 core
in vec2 vUV;
in vec4 vData;
out vec4 fragColor;
uniform vec3  uColor1, uColor2;
uniform float uOpacity, uTime;
void main() {
    float r = length(vUV);
    if (r > 1.0) discard;
    float seed = vData.x, alpha = vData.y * uOpacity;
    vec3 col;
    if (vData.w < 0.5) {
        vec3 oc = mix(uColor1, uColor2, vData.z * 0.8 + seed * 0.2);
        col = oc * exp(-r * r * 4.0) * 0.9 + vec3(1.0) * exp(-r * r * 22.0) * (1.0 - vData.z) * 0.8;
    } else {
        float fl = 0.65 + 0.35 * sin(uTime * (7.0 + 5.0 * seed) + seed * 30.0);
        vec3 sc = mix(uColor1, uColor2, seed);
        col = (sc * exp(-r * r * 6.0) + vec3(1.0) * exp(-r * r * 30.0) * 0.6) * fl;
    }
    fragColor = vec4(col * alpha * smoothstep(1.0, 0.8, r), 0.0);
}
