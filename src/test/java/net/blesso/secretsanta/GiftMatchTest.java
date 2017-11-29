package net.blesso.secretsanta;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class GiftMatchTest {
	
	private static final SwapParticipant BOB = new SwapParticipant("bob", "bob@example.com");
	private static final SwapParticipant MARY = new SwapParticipant("mary", "mary@example.com");
	private static final SwapParticipant JOE = new SwapParticipant("joe", "joe@example.com");
    private static final GiftMatch bobMary = new GiftMatch(BOB, MARY);
    private static final GiftMatch maryBob = new GiftMatch(MARY, BOB);

    @Test
    public void testEqualsTrue() {
        final GiftMatch rel2 = new GiftMatch(BOB, MARY);
        final GiftMatch rel3 = new GiftMatch(MARY, BOB);
        assertThat(bobMary.equals(rel2), equalTo(true));
        assertThat(maryBob.equals(rel3), equalTo(true));
    }

    @Test
    public void testEqualsReverseFalse() {
        assertThat(bobMary.equals(maryBob), equalTo(false));
        assertThat(maryBob.equals(bobMary), equalTo(false));
    }
    @Test
    public void testHashCodeReverseNotEquals() {
        assertThat(bobMary.hashCode() != maryBob.hashCode(), equalTo(true));
    }

    @Test
    public void testHashCodeEquals() {
        final GiftMatch rel2 = new GiftMatch(BOB, MARY);
        final GiftMatch rel3 = new GiftMatch(MARY, BOB);
        assertThat(maryBob.hashCode(), equalTo(rel3.hashCode()));
        assertThat(bobMary.hashCode(), equalTo(rel2.hashCode()));
    }

    @Test
    public void testEqualsFalse() {
        final GiftMatch rel1 = new GiftMatch(BOB, MARY);
        final GiftMatch rel2 = new GiftMatch(BOB, JOE);
        final GiftMatch rel3 = new GiftMatch(JOE, MARY);
        assertThat(rel1.equals(rel2), CoreMatchers.equalTo(false));
        assertThat(rel1.equals(rel3), CoreMatchers.equalTo(false));
        assertThat(rel2.equals(rel3), CoreMatchers.equalTo(false));
        assertThat(rel3.equals(rel2), CoreMatchers.equalTo(false));
        assertThat(rel2.equals(null), CoreMatchers.equalTo(false));
    }

    @Test
    public void testHashCodeNotSame() {
        final GiftMatch rel1 = new GiftMatch(BOB, MARY);
        final GiftMatch rel2 = new GiftMatch(BOB, JOE);
        final GiftMatch rel3 = new GiftMatch(MARY, BOB);
        final GiftMatch rel4 = new GiftMatch(JOE, MARY);
        assertThat(rel1.hashCode() == rel2.hashCode(), CoreMatchers.equalTo(false));
        assertThat(rel1.hashCode() == rel3.hashCode(), CoreMatchers.equalTo(false));
        assertThat(rel1.hashCode() == rel4.hashCode(), CoreMatchers.equalTo(false));
        assertThat(rel2.hashCode() == rel3.hashCode(), CoreMatchers.equalTo(false));
        assertThat(rel2.hashCode() == rel4.hashCode(), CoreMatchers.equalTo(false));
        assertThat(rel3.hashCode() == rel4.hashCode(), CoreMatchers.equalTo(false));
    }

}
