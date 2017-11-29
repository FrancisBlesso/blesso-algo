package net.blesso.secretsanta;

import java.util.Collection;

public interface SantaNotifier {
	/**
	 * Handles notifying participants of their gift match.
	 * @param matches the matches to report
	 */
	void notify(Collection<GiftMatch> matches);
}
