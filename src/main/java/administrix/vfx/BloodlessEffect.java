package administrix.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// A rotated version of the BiteEffect, shielding one's self.

public class BloodlessEffect extends AbstractGameEffect {
    private static AtlasRegion top;
    private static AtlasRegion bot;
    private float x;
    private float y;
    private float sY;
    private float dY;
    private float y2;
    private float sY2;
    private float dY2;
    private static final float DUR = 1.0F;

    public BloodlessEffect(float x, float y) {
        this(x, y, new Color(0.9F, 0.9F, 0.9F, 0.1F));
    }

    public BloodlessEffect(float x, float y, Color c) {
        if (top == null) {
            bot = ImageMaster.vfxAtlas.findRegion("combat/biteBot");
            top = ImageMaster.vfxAtlas.findRegion("combat/biteTop");
        }
        this.x = x - (float)top.packedWidth / 2.0F;
        this.sY = y - (float)top.packedHeight / 2.0F + 150.0F * Settings.scale;
        this.dY = y - 0.0F * Settings.scale;
        this.y = this.sY;
        this.sY2 = y - (float)(top.packedHeight / 2) - 100.0F * Settings.scale;
        this.dY2 = y - 90.0F * Settings.scale;
        this.y2 = this.sY2;
        this.duration = 1.0F;
        this.startingDuration = 1.0F;
        this.color = c;
        this.scale = 1.0F * Settings.scale;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 0.5F) * 2.0F);
            this.y = Interpolation.bounceIn.apply(this.dY, this.sY, (this.duration - 0.5F) * 2.0F);
            this.y2 = Interpolation.bounceIn.apply(this.dY2, this.sY2, (this.duration - 0.5F) * 2.0F);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration * 2.0F);
            this.y = Interpolation.fade.apply(this.sY, this.dY, this.duration * 2.0F);
            this.y2 = Interpolation.fade.apply(this.sY2, this.dY2, this.duration * 2.0F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(top, this.y, this.x, (float)top.packedWidth / 2.0F, (float)top.packedHeight / 2.0F, (float)top.packedWidth, (float)top.packedHeight, this.scale + MathUtils.random(-0.05F, 0.05F), this.scale + MathUtils.random(-0.05F, 0.05F), 90.0F);
        sb.draw(bot, this.y2, this.x, (float)top.packedWidth / 2.0F, (float)top.packedHeight / 2.0F, (float)top.packedWidth, (float)top.packedHeight, this.scale + MathUtils.random(-0.05F, 0.05F), this.scale + MathUtils.random(-0.05F, 0.05F), 90.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}