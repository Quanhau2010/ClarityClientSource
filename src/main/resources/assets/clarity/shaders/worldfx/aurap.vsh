#version 330 core




uniform mat4  uViewProj;
uniform vec3  uRight, uUp;
uniform vec3  uCenter;
uniform float uHeight, uRadius, uTime, uSize;
uniform int   uMode;      
uniform int   uTail;

out vec2 vUV;
out vec4 vData;           

float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
vec4 hash41(float p) { vec4 p4 = fract(vec4(p) * vec4(0.1031, 0.1030, 0.0973, 0.1099)); p4 += dot(p4, p4.wzxy + 33.33); return fract((p4.xxyz + p4.yzzw) * p4.zywx); }

void main() {
    vec2 c = vec2((gl_VertexID & 1) == 0 ? -1.0 : 1.0, (gl_VertexID & 2) == 0 ? -1.0 : 1.0);
    vec3 pos; float sz, alpha, tailT = 0.0, seed;
    if (uMode == 0) {
        int orb = gl_InstanceID / uTail;
        int s   = gl_InstanceID - orb * uTail;
        vec4 h = hash41(float(orb) * 5.7 + 1.0);
        seed = h.x;
        tailT = float(s) / float(uTail);
        float tt = uTime - tailT * 0.55;
        float ang = h.y * 6.2831853 + tt * (1.2 + 0.6 * h.z) * (h.w > 0.3 ? 1.0 : -1.0);
        float tilt = (h.z - 0.5) * 1.2;
        float R = uRadius * (0.85 + 0.3 * h.w);
        vec3 o = vec3(cos(ang) * R, 0.0, sin(ang) * R);
        o.y = o.x * sin(tilt) * 0.6 + sin(tt * 1.7 + h.x * 9.0) * 0.12;
        pos = uCenter + vec3(0.0, uHeight * (0.35 + 0.35 * h.x), 0.0) + o;
        sz = uSize * 0.11 * (1.0 - tailT * 0.85);
        alpha = (1.0 - tailT) * (1.0 - tailT);
    } else {
        vec4 h = hash41(float(gl_InstanceID) * 3.3 + 7.0);
        seed = h.x;
        float H = uHeight * 1.35;
        float y = mod(h.y * H + uTime * (0.6 + 0.9 * h.z), H);
        float ang = h.w * 6.2831853 + uTime * 0.6 * (h.x - 0.5);
        float R = uRadius * (0.55 + 0.6 * sqrt(h.z));
        pos = uCenter + vec3(cos(ang) * R, y - 0.1, sin(ang) * R);
        sz = uSize * 0.05 * (0.6 + 0.8 * h.x);
        float yt = y / H;
        alpha = smoothstep(0.0, 0.1, yt) * (1.0 - smoothstep(0.6, 1.0, yt));
    }
    vec3 world = pos + (c.x * uRight + c.y * uUp) * sz;
    vUV = c;
    vData = vec4(seed, alpha, tailT, float(uMode));
    gl_Position = uViewProj * vec4(world, 1.0);
}
