package com.example.imanol.pvp_battle;

/**
 * Created by Imanol on 05/06/2017.
 */

public class vec3 {

    public float x, y, z;

    public vec3(float a, float b, float c)
    {
        x = a;
        y = b;
        z = c;

    }

    public vec3 add(vec3 vec3_2)
    {
        return new vec3(vec3_2.x + x, vec3_2.y + y, vec3_2.z + z);
    }

    public vec3 sub(vec3 vec3_2)
    {
        return new vec3(vec3_2.x - x, vec3_2.y - y, vec3_2.z - z);
    }
}