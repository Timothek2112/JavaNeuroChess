package com.mycompany.javachess.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.*;

public class SimpleObject extends Rectangle {
    public Sprite sprite;
    public SimpleObject(Texture tex){
        if(tex != null)
            sprite = new Sprite(tex);
    }
    public void draw(SpriteBatch batch){
        batch.draw(sprite, x, y, width, height);
    }
    public void update(float delta) {}
}
