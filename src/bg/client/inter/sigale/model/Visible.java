package bg.client.inter.sigale.model;

/**
 * Gere les informations de visibilité dans une phrase de "reponse"
 * 
 * @author Bertrand
 *
 */

public class Visible {

	public static final String TAG_ROOT = "visible";
	public static final String TAG_start = "start";
	public static final String TAG_end = "end";

	private int start = 0;

	private int end = 0;

	public Visible() {

	}

	public Visible(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return Math.min(start, end);
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return Math.max(start, end);
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
