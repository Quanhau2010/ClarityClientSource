#version 330 core




in vec3 vPos;
in vec2 vUV;
in vec4 vColor;
in vec2 vAux;
out vec4 fragColor;

uniform vec3  uColor1;      
uniform vec3  uColor2;      
uniform float uRange;
uniform float uTrail;
uniform float uOpacity;
uniform float uTime;

void main() {
    float d = distance(vPos, vColor.xyz);
    float behind = vColor.w - d;
    if (behind < -0.25) discard;
    float front = exp(-pow(behind / 0.42, 2.0));
    float k = (behind > 0.0 ? exp(-behind / uTrail) * 0.7 : 0.0) + front;
    k *= 1.0 - smoothstep(uRange * 0.55, uRange, d);
    if (k < 0.004) discard;

    
    vec2 e2 = vec2(min(vUV.x, 1.0 - vUV.x) * vAux.x, min(vUV.y, 1.0 - vUV.y) * vAux.y);
    float ed = min(e2.x, e2.y);
    vec2 fw = fwidth(vUV) * vAux;
    float px = max(max(fw.x, fw.y), 1e-5);
    float line = 1.0 - smoothstep(px * 0.7, px * 2.0, ed);          
    float glow = exp(-ed / (0.05 + px * 2.5)) * 0.5;                 
    float sheet = front * (0.16 + 0.1 * sin(dot(vPos, vec3(7.0)) + uTime * 10.0));   

    vec3 col = mix(uColor2, uColor1, front);
    vec3 c = mix(col, vec3(1.0), 0.45 * front) * (line * 1.35 + glow) + col * sheet;
    fragColor = vec4(c * k * uOpacity, 0.0);
}
