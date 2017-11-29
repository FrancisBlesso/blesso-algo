package net.blesso.secretsanta;

/**
 * A match between two {@link SwapParticipant}'s, one gives and the other receives.
 *
 */
public class GiftMatch implements Comparable<GiftMatch> {
    
    private final SwapParticipant giver;
    private final SwapParticipant receiver;
    
    
    public GiftMatch(SwapParticipant giver, SwapParticipant receiver) {
        this.giver = giver;
        this.receiver = receiver;
    }

    public SwapParticipant getGiver() {
        return giver;
    }

    public SwapParticipant getReceiver() {
        return receiver;
    }
    
    public GiftMatch reverse() {
        return new GiftMatch(receiver, giver);
    }

    /**
     * @return true if both the giver and receiver match the other object
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof GiftMatch)) return false;
        final GiftMatch other = (GiftMatch) obj;
        return (other.giver.equals(giver) && other.receiver.equals(receiver));
    }
    
    @Override
    public int hashCode() {
        return 5* giver.hashCode() + 3* receiver.hashCode();
    }
    
    @Override
    public int compareTo(GiftMatch o) {
        if (o.giver != giver) {
            return giver.compareTo(o.giver);
        } else {
            return receiver.compareTo(o.receiver);
        }
    }
    
    @Override
    public String toString() {
        return giver + "->" + receiver;
    }
}
