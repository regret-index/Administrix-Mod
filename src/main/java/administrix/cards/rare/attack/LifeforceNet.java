package administrix.cards.rare.attack;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.vfx.combat.FlyingSpikeEffect;

public class LifeforceNet extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:LifeforceNet";
    public static final String NAME = "Lifeforce Net";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ALL_ENEMY;
    private static final int ATTACK_DAMAGE = 4;
    private static final int UPGRADE_ATTACK_DAMAGE = 1;
    private static final int RANDOM_ATTACK_TIMES = 2;

    public LifeforceNet() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.LIFEFORCE_NET, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DAMAGE;
        this.baseMagicNumber = this.magicNumber = RANDOM_ATTACK_TIMES;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster(true), new DamageInfo(p, this.baseDamage), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DEFECT_BEAM"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.0F));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for(int i = 0; i < 4; i++) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(mo.hb.cX - MathUtils.random(-120.0F, 120.0F) * Settings.scale, mo.hb.cY + MathUtils.random(-20.0F, 40.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.SKY.cpy())));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(mo.hb.cX - MathUtils.random(-120.0F, 120.0F) * Settings.scale, mo.hb.cY + MathUtils.random(-40.0F, 20.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.CHARTREUSE.cpy())));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(new FairyPotion()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LifeforceNet();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_ATTACK_DAMAGE);
        }
    }
}
