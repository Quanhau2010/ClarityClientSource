#version 330 core




uniform mat4  uViewProj;
uniform vec3  uRight, uUp;
uniform float uTime;
uniform vec2  uBox;          
uniform vec3  uCamMod;       
uniform int   uStyle;        
uniform float uSize, uSpeed;
uniform sampler2D uHeight;   
uniform vec2  uHmOff;        
uniform float uHmCamY;       
uniform int   uUseHeight;
uniform int   uHmSize;

out vec2 vUV;
out vec4 vData;              

float hash11(float p) { p = fract(p * 0.1031); p *= p + 33.33; p *= p + p; return fract(p); }
vec4 hash41(float p) { vec4 p4 = fract(vec4(p) * vec4(0.1031, 0.1030, 0.0973, 0.1099)); p4 += dot(p4, p4.wzxy + 33.33); return fract((p4.xxyz + p4.yzzw) * p4.zywx); }

void main() {
    float id = float(gl_InstanceID);
    vec4 h  = hash41(id * 1.37 + 0.5);
    vec4 h2 = hash41(id * 3.11 + 9.1);
    vec2 c  = vec2((gl_VertexID & 1) == 0 ? -1.0 : 1.0, (gl_VertexID & 2) == 0 ? -1.0 : 1.0);
    float W = uBox.x, H = uBox.y, t = uTime;

    
    float fall = 1.2, swayA = 0.5, swayF = 0.6, sz = 0.06, spin = 0.0;
    if (uStyle == 1) { fall = 16.0; swayA = 0.0; sz = 0.02; }
    else if (uStyle == 2) { fall = 1.1; swayA = 1.1; swayF = 0.8; sz = 0.10; spin = 1.8; }
    else if (uStyle == 3) { fall = -1.3; swayA = 0.5; swayF = 1.3; sz = 0.075; }
    else if (uStyle == 4) { fall = 0.0; swayA = 1.6; swayF = 0.25; sz = 0.11; }
    else if (uStyle == 5) { fall = 2.4; swayA = 0.15; sz = 0.13; spin = 0.6; }
    else if (uStyle == 6) { fall = 1.6; swayA = 1.3; swayF = 0.7; sz = 0.12; spin = 2.2; }
    else if (uStyle == 7) { fall = 0.9; swayA = 0.6; swayF = 0.5; sz = 0.09; spin = 0.4; }
    sz *= uSize * (0.75 + 0.5 * h2.x);

    vec3 p = vec3(h.x * W, h.y * H, h.z * W);
    float ft = t * uSpeed * (0.75 + 0.5 * h.w);
    p.y -= fall * ft;
    float ph = h2.y * 6.2831853;
    p.x += sin(t * swayF * (0.7 + 0.6 * h2.z) + ph) * swayA;
    p.z += cos(t * swayF * 0.8 * (0.7 + 0.6 * h2.w) + ph * 1.3) * swayA;
    if (uStyle == 1) { p.x += ft * 1.2; }                        

    vec3 rel;
    rel.xz = mod(p.xz - uCamMod.xz, W) - W * 0.5;
    rel.y  = mod(p.y - uCamMod.y, H) - H * 0.4;

    float alpha = 1.0 - smoothstep(0.36 * W, 0.5 * W, max(abs(rel.x), abs(rel.z)));
    alpha *= smoothstep(-0.4 * H, -0.3 * H, rel.y) * (1.0 - smoothstep(0.48 * H, 0.6 * H, rel.y));
    alpha *= smoothstep(0.8, 2.2, length(rel));                 

    if (uUseHeight == 1) {
        ivec2 hp = ivec2(floor(uHmOff + rel.xz));
        if (hp.x >= 0 && hp.y >= 0 && hp.x < uHmSize && hp.y < uHmSize) {
            float ground = texelFetch(uHeight, hp, 0).r;
            alpha *= smoothstep(0.0, 0.5, rel.y + uHmCamY - ground);   
        }
    }
    if (alpha < 0.004) { gl_Position = vec4(2.0, 2.0, 2.0, 1.0); vUV = c; vData = vec4(0.0); return; }

    vec3 world;
    if (uStyle == 1) {                                           
        vec3 axis = normalize(vec3(1.2, -fall, 0.0));
        vec3 side = normalize(cross(axis, rel));
        world = rel + side * (c.x * sz) + axis * (c.y * 0.32 * uSize);
    } else {
        float a = h2.z * 6.2831853 + t * spin * (h.w - 0.5) * 2.0;
        float ca = cos(a), sa = sin(a);
        vec2 rc = vec2(c.x * ca - c.y * sa, c.x * sa + c.y * ca);
        world = rel + (rc.x * uRight + rc.y * uUp) * sz;
    }
    vUV = c;
    vData = vec4(h.w, alpha, h2.x, h2.w);
    gl_Position = uViewProj * vec4(world, 1.0);
}
