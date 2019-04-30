package administrix;

import administrix.AdministrixMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingSpikeEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;

public abstract class PseudoFormCharacter extends CustomPlayer {

    private static String state;

    protected AbstractAnimation animationP;
    protected AbstractAnimation animationA;
    protected AbstractAnimation animationS;

    protected AbstractRoom currentRoom;

    public PseudoFormCharacter(String name, PlayerClass playerClass,
                               String[] orbTexturesA, String orbVfxPathA, float[] layerSpeedsA,
                               AbstractAnimation animationP, AbstractAnimation animationA,
                               AbstractAnimation animationS) {
        super(name, playerClass, new CustomEnergyOrb(orbTexturesA, orbVfxPathA, layerSpeedsA), animationA);

        this.animationP = animationP;
        this.animationA = animationA;
        this.animationS = animationS;

        currentRoom = null;
    }

    public void swap(String chooseForm)
    {
        switch (chooseForm) {
            case "Attack":
                this.animation = animationA;
                break;
            case "Skill":
                this.animation = animationS;
                break;
            default:
                this.animation = animationP;
                break;
        }
    }

    public void swish(String chooseFlicker) {
        Color switchColor;

        if (!chooseFlicker.equals(state)) {
            state = chooseFlicker;
            return;
        }

        switch (chooseFlicker) {
            case "Attack":
                switchColor = Color.RED.cpy();
                break;
            case "Skill":
                switchColor = Color.BLUE.cpy();
                break;
            default:
                switchColor = Color.GOLD.cpy();
                break;
        }

        if (MathUtils.randomBoolean()) {
            for (int i = 0; i < 4; i++) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(AbstractDungeon.player.hb.cX - MathUtils.random(-160.0F, 160.0F) * Settings.scale, AbstractDungeon.player.hb.cY - MathUtils.random(150.0F, 180.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, switchColor)));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(AbstractDungeon.player.hb.cX - 120.0F * Settings.scale, AbstractDungeon.player.hb.cY - 120.0F * Settings.scale, 0.0F, 40.0F * Settings.scale, 20.0F, 0.8F, switchColor, switchColor)));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(AbstractDungeon.player.hb.cX + 120.0F * Settings.scale, AbstractDungeon.player.hb.cY - 120.0F * Settings.scale, 0.0F, 40.0F * Settings.scale, 340.0F, 0.8F, switchColor, switchColor)));
        }
    }

    @Override
    public void update() {
        super.update();
        if (currentRoom == null || !(currentRoom.equals(AbstractDungeon.getCurrRoom())))
        {
            currentRoom = AbstractDungeon.getCurrRoom();
            state = "Default";
            swap("Default");
            if (MathUtils.randomBoolean()) {
                for (int i = 0; i < 8; i++) {
                    AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(AbstractDungeon.player.hb.cX - MathUtils.random(-160.0F, 160.0F) * Settings.scale, AbstractDungeon.player.hb.cY - MathUtils.random(120.0F, 160.0F) * Settings.scale));
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    AbstractDungeon.effectsQueue.add(new PlasmaOrbPassiveEffect(AbstractDungeon.player.hb.cX - MathUtils.random(-160.0F, 160.0F) * Settings.scale, AbstractDungeon.player.hb.cY - MathUtils.random(120.0F, 160.0F) * Settings.scale));
                }
            }
        }
    }

    @Override
    public void applyStartOfTurnPostDrawRelics() {
        super.applyStartOfTurnPostDrawRelics();
        swap("Default");
    }

    @Override
    public void onVictory() {
        super.onVictory();
        swap("Default");
        powers.clear();
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            this.swap("Attack");
            this.swish("Attack");
            this.useFastAttackAnimation();
        } else if (c.type == AbstractCard.CardType.SKILL) {
            this.swap("Skill");
            this.swish("Skill");
        } else {
            this.swap("Default");
            this.swish("Default");
        }

        c.calculateCardDamage(monster);
        c.use(this, monster);
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(c, monster));
        if (!c.dontTriggerOnUseCard) {
            this.hand.triggerOnOtherCardPlayed(c);
        }

        this.hand.removeCard(c);
        this.cardInUse = c;
        c.target_x = (float)(Settings.WIDTH / 2);
        c.target_y = (float)(Settings.HEIGHT / 2);
        if (c.costForTurn > 0 && !c.freeToPlayOnce && (!this.hasPower("Corruption") || c.type != AbstractCard.CardType.SKILL)) {
            this.energy.use(c.costForTurn);
        }

        if (!this.hand.canUseAnyCard() && !this.endTurnQueued) {
            AbstractDungeon.overlayMenu.endTurnButton.isGlowing = true;
        }

    }

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
    }

    @Override
    public void applyStartOfCombatPreDrawLogic() {
        super.applyStartOfCombatPreDrawLogic();
    }
}