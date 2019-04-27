package administrix.cards.common.attack;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.KinesisEffect;
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
import com.megacrit.cardcrawl.powers.StrengthPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.SpectralThiefPower;
import com.megacrit.cardcrawl.vfx.CollectorStakeEffect;
import sun.security.util.Length;

public class RegentEdict extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:RegentEdict";
    public static final String NAME = "Regent's Edict";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 4;
    private static final int UPGRADE_ATTACK_DMG = 3;
    private static final int EDICT_AMOUNT = 1;

    public RegentEdict() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.REGENT_EDICT,
                COST, CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = EDICT_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // On the third time of playing this card, steal the enemy's Strength.
        // The count removing itself is off in SpectralThiefPower itself.
        int stolen = (m.hasPower("Strength") &&
                     !m.hasPower("Artifact") &&
                     m.getPower("Strength").amount > 0) ?
                     m.getPower("Strength").amount : 0;

        if (p.hasPower(SpectralThiefPower.POWER_ID) && (p.getPower(SpectralThiefPower.POWER_ID).amount == 2))
        {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            if (m != null && (stolen > 0 || m.hasPower("Artifact"))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -stolen), -stolen));
            }
            if (stolen > 0) {
                float length = Math.min(0.1F + stolen * 0.05F, 2.4F);
                AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new KinesisEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, length, Color.RED.cpy(), Color.NAVY.cpy()), length));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, stolen), stolen));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpectralThiefPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new RegentEdict();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage((UPGRADE_ATTACK_DMG));
        }
    }

}