package com.hakkicanbuluc.mrfruitninja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Fruit {
    public static float radius = 60f;

    Vector2 pos, velocity;

    public boolean living = true;

    int height = Gdx.graphics.getHeight();

    public enum Type {
        REGULAR, EXTRA, ENEMY, LIFE
    }
    Type type;

    public Fruit(Vector2 pos, Vector2 velocity) {
        this.pos = pos;
        this.velocity = velocity;
        type = Type.REGULAR;
    }

    public boolean clicked(Vector2 click) {
        return pos.dst2(click) <= radius * radius + 1;
    }

    public final Vector2 getPos() {
        return pos;
    }

    public boolean outOfScreen() {
        return (pos.y < -2f * radius);
    }

    public void update(float dt) {
        velocity.y -= dt * (height * 0.2f);
        velocity.x -= dt * Math.signum(velocity.x) * 5f;
        pos.mulAdd(velocity, dt);
    }
}
