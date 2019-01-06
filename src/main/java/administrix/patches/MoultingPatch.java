package administrix.patches;

// There's no logic available for powers
// to solely check manually discarding cards, otherwise,
// so this enables Moulting from Cast off Regrets to work.

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = GameActionManager.class,
        method = "incrementDiscard"
)

public class MoultingPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(boolean q) {
        if (AbstractDungeon.player.hasPower("AdministrixMod:Moulting"))
            AbstractDungeon.player.getPower("AdministrixMod:Moulting").onSpecificTrigger();
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    "com.megacrit.cardcrawl.characters.AbstractPlayer", "updateCardsOnDiscard");

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}