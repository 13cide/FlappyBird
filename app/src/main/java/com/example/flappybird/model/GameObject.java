package com.example.flappybird.model;

public abstract class GameObject
{
    public float x;
    public float y;

    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update();
}
