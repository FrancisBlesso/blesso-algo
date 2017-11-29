
package net.blesso.secretsanta;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link SantaNotifier} implementation that writes to {@link System#out}.
 *
 */
public class SysoutNotifier {

    public void notify(Collection<GiftMatch> matches) {
    	if (matches.size() == 0) {
    		 System.out.println("no matches");
    		 return;
    	}
        final Set<GiftMatch> matchesToLog = new HashSet<>(matches);
        GiftMatch current = matchesToLog.iterator().next();
        while(current != null) {
            System.out.println(current);
            matchesToLog.remove(current);
            GiftMatch next = null;
            for (GiftMatch key : matchesToLog) {
                if (current.getReceiver() == key.getGiver()) {
                    next = key;
                    break;
                }
            }
            if (next == null && matchesToLog.size() > 0) {
                System.out.println("**");
                current = matchesToLog.iterator().next();
            } else  {
                current = next;
            }
        }
    }

}
