package administrix.cards.rare.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class BrewingCloister extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:BrewingCloister";
    public static final String NAME = "Brewing Cloister";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int BLOCK_AMOUNT = 16;
    private static final int UPGRADE_BLOCK_AMOUNT = 8;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;

    public BrewingCloister() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.BREWING_CLOISTER, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.block = this.baseBlock = BLOCK_AMOUNT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractPotion temp = PotionHelper.getRandomPotion();

        while (temp.ID.equals("Fruit Juice") ||
                temp.ID.equals("EntropicBrew") ||
                temp.ID.equals("Fairy Potion") ||
                temp.ID.equals("Regen Potion"))
            temp = PotionHelper.getRandomPotion();

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(p.hb.cX, p.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(temp));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BrewingCloister();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMOUNT);
        }
    }

}