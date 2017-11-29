package net.blesso.secretsanta;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatcherTest {
	
	public static final SwapParticipant SWAPPER1 = new SwapParticipant("One", "one@example.com");
	public static final SwapParticipant SWAPPER2 = new SwapParticipant("Two", "two@example.com");
	public static final SwapParticipant SWAPPER3= new SwapParticipant("Three", "three@example.com");
	public static final SwapParticipant SWAPPER4 = new SwapParticipant("Four", "four@example.net");
	public static final SwapParticipant SWAPPER5 = new SwapParticipant("Five", "five@example.com");
	public static final SwapParticipant SWAPPER6 = new SwapParticipant("Six", "six@example.com");
	public static final SwapParticipant SWAPPER7 = new SwapParticipant("Seven", "seven@example.com");
	
	private final static Set<GiftMatch> BLACKLIST = Collections.unmodifiableSet(
	            new HashSet<>(Arrays.asList(
	                    new GiftMatch(SWAPPER1, SWAPPER2),
	                    new GiftMatch(SWAPPER5,SWAPPER2),
	                    new GiftMatch(SWAPPER3, SWAPPER4),
	                    new GiftMatch(SWAPPER6, SWAPPER7)
	                    )));
	
	private final static List<SwapParticipant> ALL_SWAPPERS = Collections.unmodifiableList(
	            Arrays.asList(SWAPPER1, SWAPPER2, SWAPPER5, SWAPPER3, SWAPPER4, SWAPPER6, SWAPPER7)
	                    );

	
    
    public List<GiftMatch> runTest(List<SwapParticipant> swappers, Set<GiftMatch> blackList) {
    	final Matcher matcher = new Matcher(swappers, blackList);
        final List<GiftMatch> matches = matcher.createMatches();
        new SysoutNotifier().notify(matches);
        return matches;
    }




    @Test
    public void testTwoSwappers() {
    	List<GiftMatch> matches = runTest(Arrays.asList(SWAPPER1, SWAPPER7), Collections.emptySet());
    	assertThat(matches.size(), equalTo(2));
    	assertThat(matches.get(0).getGiver(), equalTo(matches.get(1).getReceiver()));
    	assertThat(matches.get(1).getGiver(), equalTo(matches.get(0).getReceiver()));
    }
    @Test
    public void testBlacklist() {
    	List<GiftMatch> matches = runTest(Arrays.asList(SWAPPER1, SWAPPER2), BLACKLIST);
    	assertThat(matches.size(), equalTo(0));
    }

    @Test
    public void testPrintCycle() {
        for(int i=0; i<10; i++) {
            System.out.println("\ncycle " + i);
			final List<GiftMatch> matches = runTest(ALL_SWAPPERS, BLACKLIST);
            verifyMatches(matches, ALL_SWAPPERS);
        }
    }

    private void verifyMatches(List<GiftMatch> matches, Collection<SwapParticipant> participants) {
        final Set<SwapParticipant> givers = new HashSet<>();
        final Set<SwapParticipant> receivers = new HashSet<>();
        for (GiftMatch key : matches) {
            givers.add(key.getGiver());
            receivers.add(key.getReceiver());
        }
        assertThat("givers not equal to receivers", givers.size(), equalTo(receivers.size()));
        assertThat("givers not equal to matches", givers.size(), equalTo(matches.size()));
        assertThat("matches not equal to people", matches.size(), equalTo(participants.size()));
        // TODO Auto-generated method stub
        
    }

}
