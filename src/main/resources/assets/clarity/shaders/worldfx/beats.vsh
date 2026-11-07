#version 330 core




uniform mat4  uViewProj;
uniform vec3  uRight;
uniform vec3  uCenter;          
uniform float uRadius, uBarH, uTime, uSpin;
uniform int   uN;
uniform float uBands[32];
uniform float uPeaks[32];

out vec2 vUV;
out vec4 vData;                 

float band(float f, bool peak) {
    f = clamp(f, 0.0, 31.0);
    int i0 = int(floor(f)); int i1 = min(i0 + 1, 31);
    float a = peak ? uPeaks[i0] : uBands[i0];
    float b = peak ? uPeaks[i1] : uBands[i1];
    return mix(a, b, fract(f));
}

void main() {
    vec2 c = vec2((gl_VertexID & 1) == 0 ? -1.0 : 1.0, (gl_VertexID & 2) == 0 ? -1.0 : 1.0);
    if (gl_InstanceID >= uN) {                         
        float S = uRadius * 1.7;
        vec3 p = uCenter + vec3(c.x * S, 0.03, c.y * S);
        vUV = c * 1.7;
        vData = vec4(0.0, 0.0, 0.0, -1.0);
        gl_Position = uViewProj * vec4(p, 1.0);
        return;
    }
    float i = float(gl_InstanceID), N = float(uN);
    float half_ = N * 0.5;
    float k = gl_InstanceID < uN / 2 ? i : N - 1.0 - i;          
    float f = k / max(half_ - 1.0, 1.0) * 31.0;
    float lvl = band(f, false);
    float pk  = max(band(f, true), lvl);
    float h  = 0.04 + lvl * uBarH;
    float ph = 0.04 + pk * uBarH;
    float qh = max(h, ph) + 0.06;

    float a = i / N * 6.2831853 + uTime * uSpin;
    vec3 base = uCenter + vec3(cos(a), 0.0, sin(a)) * uRadius;
    vec3 hr = normalize(vec3(uRight.x, 0.0, uRight.z) + 1e-5);
    float w = 6.2831853 * uRadius / N * 0.32;
    vec3 p = base + hr * (c.x * w) + vec3(0.0, (c.y * 0.5 + 0.5) * qh + 0.02, 0.0);
    vUV = vec2(c.x, c.y * 0.5 + 0.5);
    vData = vec4(h / qh, i / N, ph / qh, qh);
    gl_Position = uViewProj * vec4(p, 1.0);
}
