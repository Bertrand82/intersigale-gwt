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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visible other = (Visible) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		return true;
	}

	public void setStart(String start2) {
		try {
			int i = Integer.parseInt(start2);
			setStart(i);
		} catch (Exception e) {

		}
	}

	public void setEnd(String end2) {
		try {
			int i = Integer.parseInt(end2);
			setEnd(i);
		} catch (Exception e) {

		}
	}

}
