package administrix.cards.rare.skill;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.ColouredSmokeEffect;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class AmassedMasks extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:AmassedMasks";
    public static final String NAME = "Amassed Masks";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;

    public AmassedMasks() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.AMASSED_MASKS, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX - 180.0F * Settings.scale, p.hb.cY + 100.0F * Settings.scale, new Color(0.8F, 0.6F, 0.75F, 1.0F), false));
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX - 180.0F * Settings.scale, p.hb.cY - 20.0F * Settings.scale, new Color(0.1F, 0.5F, 0.55F, 1.0F), false));
        AbstractDungeon.effectsQueue.add(new ColouredSmokeEffect(p.hb.cX - 180.0F * Settings.scale, p.hb.cY - 140.0F * Settings.scale, new Color(0.8F, 0.5F, 0.4F, 1.0F), false));

        // Generate a random Attack, Skill, and Power, all set to 0.
        // No generating itself, though, and no generating healing.
        AbstractCard a = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
        AbstractCard b = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();

        while (b instanceof AmassedMasks)
            b = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();

        if (this.upgraded) {
            a.upgrade();
            b.upgrade();
            c.upgrade();
        }

        a.setCostForTurn(0);
        b.setCostForTurn(0);
        c.setCostForTurn(0);

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(a, true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(b, true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AmassedMasks();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}