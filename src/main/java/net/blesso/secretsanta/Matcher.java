package net.blesso.secretsanta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Matcher participants in a secret santa or yankee swap gift exchange.
 * Each person is matched to a different person. There is also a blacklist
 * that prevents people from being matched, for instance two people who live
 * in the same household.
 *
 */
public class Matcher {
    
	/** People who will exchange gifts. */
    private final List<SwapParticipant> participants;
    
    /** Set of all pairs of people, minus any matches specified by the blacklist. */
    private final Set<GiftMatch> allRelationships;
    
    /** Sets of people to exclude. Any pair of participants in the blacklist won't be matched for gifts. */
    private final Set<GiftMatch> blacklist;
    
    public Matcher(List<SwapParticipant> participants, Set<GiftMatch> blacklist) {
		this.participants = participants;
		
		final Set<GiftMatch> set = new TreeSet<>();
        for (int i=0; i<participants.size(); i++) {
            final SwapParticipant person1 = participants.get(i);
            for (int j=i+1; j<participants.size(); j++) {
                final SwapParticipant person2 = participants.get(j);
                set.add(new GiftMatch(person1, person2));
                set.add(new GiftMatch(person2, person1));
            }
        }
        for (GiftMatch skip : blacklist) {
        	set.remove(skip);
        	set.remove(skip.reverse());
        }
        allRelationships = Collections.unmodifiableSet((set));
        this.blacklist = Collections.unmodifiableSet(blacklist);
    }

	public int countGivers(Collection<GiftMatch> keys) {
        final Set<SwapParticipant> people = new HashSet<>();
        for (GiftMatch key : keys) {
            people.add(key.getGiver());
        }
        return people.size();
    }
    
    public int countReceivers(Collection<GiftMatch> keys) {
        final Set<SwapParticipant> people = new HashSet<>();
        for (GiftMatch key : keys) {
            people.add(key.getReceiver());
        }
        return people.size();
    }
    
    public List<GiftMatch> createMatches() {
        final List<GiftMatch> matches = new ArrayList<>();
        if(allRelationships.size() == 0) {
        	return matches;
        }
        final List<GiftMatch> grabBag = new ArrayList<>(allRelationships);
        final Set<SwapParticipant> giversSoFar = new HashSet<>();
        Collections.shuffle(grabBag);
        GiftMatch thisPair = grabBag.get(0);
        while(thisPair != null) {
            final List<GiftMatch> allForGiver = findGivers(thisPair.getGiver(), grabBag);
            final List<GiftMatch> allForReceiver = findReceivers(thisPair.getReceiver(), grabBag);
            matches.add(thisPair);
            grabBag.removeAll(allForGiver);
            grabBag.removeAll(allForReceiver);
            giversSoFar.add(thisPair.getGiver());
            Collections.shuffle(grabBag);
            thisPair = getNext(grabBag, giversSoFar, thisPair);
        }
        if (countGivers(allRelationships) != matches.size()) {
            return createMatches();
        }
        Collections.sort(matches);
        return matches;
    }

    private static GiftMatch getNext(List<GiftMatch> remaining, Set<SwapParticipant> giversSoFar, GiftMatch current) {
        if (remaining.size() == 0) {
            return null;
        }
        if (remaining.size() == 1) {
            return remaining.get(0);
        }
//        if (remaining.size() == 2) {
//            final RelationshipKey first = remaining.get(0);
//            if (giversSoFar.contains(first.getReceiver())) {
//                return remaining.get(1);
//            } else { 
//                return first;
//            }
//        }
//        for(RelationshipKey key : remaining) {
//            if (giversSoFar.contains(key.getReceiver())) {
//                continue;
//            }
//            if (key.getGiver() == current.getReceiver()) {
//                return key;
//            }
//        }
        return remaining.get(0);
    }

    
    public boolean isValidCycle(Collection<GiftMatch> keys) {
        if (keys.size() != participants.size()) {
            return false;
        }
        final List<SwapParticipant> givers = new ArrayList<>();
        final List<SwapParticipant> receivers = new ArrayList<>();
        for (GiftMatch key : keys) {
            givers.add(key.getGiver());
            receivers.add(key.getReceiver());
        }
        if (givers.size() != participants.size()) {
            return false;
        }
        if (receivers.size() != participants.size()) {
            return false;
        }
        final List<GiftMatch> chainedList = new ArrayList<>();
        final List<GiftMatch> remainingKeys = new ArrayList<>(keys);
        chainedList.add(remainingKeys.get(0));
        remainingKeys.remove(0);

        return true;
        
    }
    
    private List<GiftMatch> findGivers(SwapParticipant person, Collection<GiftMatch> relationships) {
        List<GiftMatch> keys = new ArrayList<>();
        for (GiftMatch key : relationships) {
            if (person == key.getGiver()) {
                keys.add(key);
            }
        }
        
        return keys;
    }

    
    private List<GiftMatch> findReceivers(SwapParticipant person, Collection<GiftMatch> relationships) {
        List<GiftMatch> keys = new ArrayList<>();
        for (GiftMatch key : relationships) {
            if (person == key.getReceiver()) {
                keys.add(key);
            }
        }
        
        return keys;
    }
}
