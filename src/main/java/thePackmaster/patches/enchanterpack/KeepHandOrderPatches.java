package thePackmaster.patches.enchanterpack;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import javassist.CtBehavior;
import thePackmaster.ThePackmaster;

import javax.smartcardio.Card;
import java.util.Iterator;

@SpirePatch2(clz = CardGroup.class, method = "refreshHandLayout")
public class KeepHandOrderPatches {

    public static CardGroup oldHand =new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static boolean isOldHandValid = false;

    @SpirePostfixPatch
    public static void updateOldHand() {
        if (AbstractDungeon.player.chosenClass == ThePackmaster.Enums.THE_PACKMASTER) {
            if (!isOldHandValid) {
                oldHand = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    oldHand.addToTop(c);
                }
                isOldHandValid = true;
            }
        }
    }

    public static class GroupSizeLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "size");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }

    @SpireInsertPatch(locator = GroupSizeLocator.class)
    public static void reorderHand(CardGroup __instance) {
        if (AbstractDungeon.player.chosenClass == ThePackmaster.Enums.THE_PACKMASTER) {
            CardGroup newHandOrdered = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : oldHand.group) {
                if (__instance.group.contains(c)) {
                    newHandOrdered.addToTop(c);
                }
            }
            for (AbstractCard c : __instance.group) {
                if (!newHandOrdered.group.contains(c)) {
                    newHandOrdered.addToHand(c);
                    //current weak point, I'm sure this doesn't account for every situation where oldHand shouldn't be invalidated
                    //but I couldn't find any
                    if (!AbstractDungeon.isScreenUp) isOldHandValid = false;
                }
            }
            __instance.group = newHandOrdered.group;
        }
    }
}


