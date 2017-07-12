package com.example.imanol.pvp_battle;

/**
 * Created by Imanol on 07/06/2017.
 */

public class vec2 {
    public float x, y;

    public vec2(float a, float b)
    {
        x = a;
        y = b;
    }

    public vec2 add(vec3 vec2_2)
    {
        return new vec2(vec2_2.x + x, vec2_2.y + y);
    }

    public vec2 sub(vec3 vec2_2)
    {
        return new vec2(vec2_2.x - x, vec2_2.y - y);
    }

}

