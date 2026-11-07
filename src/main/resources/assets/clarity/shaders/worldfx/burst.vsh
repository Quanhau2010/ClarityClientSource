#version 330 core



uniform mat4  uViewProj;
uniform vec3  uRight, uUp;
uniform vec4  uBurst[32];
uniform vec4  uInfo[32];
uniform int   uPer;
uniform int   uStyle;   
uniform float uSize;

out vec2 vUV;
out vec4 vData;          

float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
vec4 hash41(float p) { vec4 p4 = fract(vec4(p) * vec4(0.1031, 0.1030, 0.0973, 0.1099)); p4 += dot(p4, p4.wzxy + 33.33); return fract((p4.xxyz + p4.yzzw) * p4.zywx); }

void main() {
    int b = gl_InstanceID / uPer;
    int k = gl_InstanceID - b * uPer;
    vec4 B = uBurst[b], I = uInfo[b];
    float age = B.w, life = I.z, t = age / life;
    vec2 c = vec2((gl_VertexID & 1) == 0 ? -1.0 : 1.0, (gl_VertexID & 2) == 0 ? -1.0 : 1.0);
    if (t >= 1.0 || t < 0.0) { gl_Position = vec4(2.0, 2.0, 2.0, 1.0); vUV = c; vData = vec4(0.0); return; }

    vec4 h  = hash41(float(k) * 7.13 + I.x * 113.0);
    vec4 h2 = hash41(float(k) * 2.71 + I.x * 57.0 + 3.0);
    float sc = I.y;
    float z = h.x * 2.0 - 1.0, an = h.y * 6.2831853;
    vec3 dir = vec3(sqrt(1.0 - z * z) * cos(an), z, sqrt(1.0 - z * z) * sin(an));

    float drag = 3.0, grav = 6.0, speed = mix(1.5, 4.5, h.z) * sc, sz = 0.06 * uSize;
    if (uStyle == 0) { drag = 4.0; grav = 9.0; speed *= 1.4; dir.y = abs(dir.y) * 0.8 + 0.2; }
    else if (uStyle == 1) { drag = 3.5; grav = 1.5; sz = 0.11 * uSize; }
    else if (uStyle == 2) { drag = 2.5; grav = -1.8; speed *= 0.6; dir.y = abs(dir.y); sz = 0.10 * uSize; }
    else if (uStyle == 3) { drag = 2.5; grav = 8.0; sz = 0.09 * uSize; dir.y = abs(dir.y) * 0.7 + 0.3; }
    else if (uStyle == 4) { drag = 1.5; grav = 13.0; speed *= 0.8; sz = 0.05 * uSize; dir.y = abs(dir.y) * 0.6 + 0.3; }
    else if (uStyle == 7) { drag = 2.2; grav = 4.0; sz = 0.07 * uSize; dir.y = abs(dir.y) * 0.8 + 0.4; }

    float e = (1.0 - exp(-drag * age)) / drag;
    vec3 pos = dir * speed * e - vec3(0.0, 0.5 * grav * age * age * 0.35, 0.0);
    vec3 vel = dir * speed * exp(-drag * age) - vec3(0.0, grav * age * 0.35, 0.0);

    if (uStyle == 5) {                                   
        float rr = (1.0 - exp(-2.5 * age)) * (0.6 + 0.8 * h.z) * sc;
        float aa = an + age * 5.0 * (h.w > 0.5 ? 1.0 : -1.0);
        pos = vec3(cos(aa) * rr, age * (0.7 + 0.8 * h2.x) * sc, sin(aa) * rr);
        sz = 0.07 * uSize;
    }
    if (uStyle == 6 && k == 0) {                         
        float R = (0.25 + 2.0 * (1.0 - pow(1.0 - t, 2.5))) * sc;
        vec3 world = B.xyz + (c.x * uRight + c.y * uUp) * R;
        vUV = c; vData = vec4(h.w, t, h2.x, 0.0);
        gl_Position = uViewProj * vec4(world, 1.0);
        return;
    }

    vec3 world;
    if (uStyle == 0 || (uStyle == 6 && k > 0)) {         
        vec3 rel = B.xyz + pos;
        vec3 axis = length(vel) > 1e-4 ? normalize(vel) : uUp;
        vec3 side = normalize(cross(axis, rel) + 1e-5);
        float len = clamp(length(vel) * 0.05, 0.03, 0.35) * uSize;
        world = rel + side * c.x * 0.025 * uSize + axis * c.y * len;
    } else {
        float pop = sin(min(t * 4.0, 1.0) * 1.5707963);
        float s = sz * pop * (uStyle == 2 ? 1.0 : (1.0 - t * 0.5));
        float rot = h2.y * 6.2831853 + age * (uStyle == 3 || uStyle == 7 ? 9.0 : 2.0) * (h2.z - 0.5) * 2.0;
        float cr = cos(rot), sr = sin(rot);
        vec2 rc = vec2(c.x * cr - c.y * sr, c.x * sr + c.y * cr);
        if (uStyle == 7) rc.y *= 0.55 + 0.45 * cos(age * 12.0 * h2.w);  
        world = B.xyz + pos + (rc.x * uRight + rc.y * uUp) * s;
    }
    vUV = c;
    vData = vec4(h.w, t, h2.x, 1.0);
    gl_Position = uViewProj * vec4(world, 1.0);
}
