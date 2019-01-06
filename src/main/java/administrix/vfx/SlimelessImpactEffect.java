package administrix.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;

// One of many base-game effect recolouring copies.
// This is for Bludgeon- the default rendering is based on bright colours,
// and otherwise adds green to dark colours used.

public class SlimelessImpactEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.0F;
    private float x;
    private float y;
    private float targetY;
    private static AtlasRegion img;
    private boolean impactHook;

    public SlimelessImpactEffect(float x, float y) {
        this(x, y, new Color(0.95F, 0.9F, 0.8F, 0.0F));
    }

    public SlimelessImpactEffect(float x, float y, Color newColor) {
        this.impactHook = false;
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/weightyImpact");
        }

        this.scale = Settings.scale;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = (float)Settings.HEIGHT - (float)img.packedHeight / 2.0F;
        this.duration = 1.0F;
        this.targetY = y - 180.0F * Settings.scale;
        this.rotation = MathUtils.random(-1.0F, 1.0F);
        this.color = newColor;
    }

    public void update() {
        this.y = Interpolation.fade.apply((float)Settings.HEIGHT, this.targetY, 1.0F - this.duration / 1.0F);
        this.scale += Gdx.graphics.getDeltaTime();
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
            CardCrawlGame.sound.playA("ATTACK_IRON_2", -0.5F);
        } else if (this.duration < 0.2F) {
            if (!this.impactHook) {
                this.impactHook = true;
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(this.color));
                CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.MED, true);

                int i;
                for(i = 0; i < 5; ++i) {
                    AbstractDungeon.effectsQueue.add(new DamageImpactCurvyEffect(this.x + (float)img.packedWidth / 2.0F, this.y + (float)img.packedWidth / 2.0F));
                }

                for(i = 0; i < 30; ++i) {
                    AbstractDungeon.effectsQueue.add(new UpgradeShineParticleEffect(this.x + MathUtils.random(-100.0F, 100.0F) * Settings.scale + (float)img.packedWidth / 2.0F, this.y + MathUtils.random(-50.0F, 120.0F) * Settings.scale + (float)img.packedHeight / 2.0F));
                }
            }

            this.color.a = Interpolation.fade.apply(0.0F, 0.5F, 0.2F / this.duration);
        } else {
            this.color.a = Interpolation.pow2Out.apply(0.6F, 0.0F, this.duration / 1.0F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        Color renderColor = new Color(this.color);
        sb.setColor(renderColor);
        sb.draw(img, this.x, this.y + 140.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight * (this.duration + 0.2F) * 5.0F, this.scale * MathUtils.random(0.99F, 1.01F) * 0.3F, this.scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (this.duration + 0.8F), this.rotation);
        renderColor.r *= 0.85F;
        renderColor.g *= 0.85F;
        renderColor.b *= 0.85F;
        sb.setColor(renderColor);
        sb.draw(img, this.x, this.y + 40.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight * (this.duration + 0.2F) * 5.0F, this.scale * MathUtils.random(0.99F, 1.01F) * 0.7F, this.scale * MathUtils.random(0.99F, 1.01F) * 1.3F * (this.duration + 0.8F), this.rotation);
        renderColor.r *= 0.85F;
        renderColor.g *= 0.85F;
        renderColor.b *= 0.85F;
        sb.setColor(renderColor);
        sb.draw(img, this.x, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight * (this.duration + 0.2F) * 5.0F, this.scale * MathUtils.random(0.99F, 1.01F), this.scale * MathUtils.random(0.99F, 1.01F) * (this.duration + 0.8F), this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}