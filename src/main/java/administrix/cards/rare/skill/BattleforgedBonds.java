package administrix.cards.rare.skill;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.ColouredSmokeEffect;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ApplyBulletTimeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class BattleforgedBonds extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:BattleforgedBonds";
    public static final String NAME = "Battleforged Bonds";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 4;
    private static final int STAT_AMOUNT = 4;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;

    public BattleforgedBonds() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.BATTLEFORGED_BONDS, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = STAT_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX - 210.0F * Settings.scale, p.hb.cY - 100.0F * Settings.scale, new Color(0.8F, 0.2F, 0.2F, 1.0F), false));
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX - 140.0F * Settings.scale, p.hb.cY - 40.0F * Settings.scale, new Color(0.8F, 0.6F, 0.1F, 1.0F), false));
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX - 70.0F * Settings.scale, p.hb.cY + 20.0F * Settings.scale, new Color(0.8F, 0.8F, 0.1F, 1.0F), false));
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX, p.hb.cY + 40.0F * Settings.scale, new Color(0.4F, 0.8F, 0.2F, 1.0F), false));
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX + 70.0F * Settings.scale, p.hb.cY + 20.0F * Settings.scale, new Color(0.2F, 0.6F, 0.8F, 1.0F), false));
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX + 140.0F * Settings.scale, p.hb.cY - 40.0F * Settings.scale, new Color(0.4F, 0.4F, 0.8F, 1.0F), false));
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX + 210.0F * Settings.scale, p.hb.cY - 100.0F * Settings.scale, new Color(0.6F, 0.2F, 0.6F, 1.0F), false));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyBulletTimeAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new BattleforgedBonds();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (this.upgraded) { this.retain = true; }
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.retain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}