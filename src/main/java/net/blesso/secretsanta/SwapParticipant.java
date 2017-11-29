package net.blesso.secretsanta;

/**
 * A person participating in the gift swap. Tracks the person's name and email and expects the email to be 
 * unique among all {@link SwapParticipant}'s
 */
public class SwapParticipant implements Comparable<SwapParticipant>{

	private final String name;
	private final String email;

	public SwapParticipant(String name, String email) {
		this.name = name;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	
	@Override
	public int hashCode() {
		return email.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		SwapParticipant other = (SwapParticipant) obj;
		return email.equals(other.email);
	}

	@Override
	public int compareTo(SwapParticipant other) {
		return email.compareTo(other.email);
	}
	@Override
	public String toString() {
		return this.name;
	}
}
