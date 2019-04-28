package administrix.cards.uncommon.attack;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.WiltingPower;
import com.megacrit.cardcrawl.vfx.combat.FlyingSpikeEffect;

public class UnstableVigor extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:UnstableVigor";
    public static final String NAME = "Unstable Vigor";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_ATTACK_DMG = 2;
    private static final int STRENGTH_AMOUNT = 2;
    private static final int UPGRADE_STRENGTH_AMOUNT = 2;
    private static final int WILTING_AMOUNT = 1;

    public UnstableVigor() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.UNSTABLE_VIGOR,
                COST, CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = STRENGTH_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
        for(int i = 0; i < 4; i++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(p.hb.cX - MathUtils.random(-160.0F, 160.0F) * Settings.scale, p.hb.cY - MathUtils.random(120.0F, 160.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.RED.cpy())));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(p.hb.cX - MathUtils.random(-160.0F, 160.0F) * Settings.scale, p.hb.cY - MathUtils.random(120.0F, 160.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.BLUE.cpy())));
        }
        for(int i = 0; i < 8; i++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(p.hb.cX - MathUtils.random(-160.0F, 160.0F) * Settings.scale, p.hb.cY + MathUtils.random(20.0F, 60.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.RED.cpy())));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(p.hb.cX - MathUtils.random(-160.0F, 160.0F) * Settings.scale, p.hb.cY + MathUtils.random(20.0F, 60.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.BLUE.cpy())));
        }
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WiltingPower(p, WILTING_AMOUNT, false), WILTING_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UnstableVigor();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_ATTACK_DMG);
            this.upgradeMagicNumber(UPGRADE_STRENGTH_AMOUNT);
        }
    }

}