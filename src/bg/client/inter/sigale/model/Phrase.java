package bg.client.inter.sigale.model;

import java.util.ArrayList;
import java.util.List;

public class Phrase {

	public static final String TAG_ROOT = "phrase";

	String text;

	public List<Visible> listVisible = new ArrayList<Visible>();

	public List<Visible> getListVisible() {
		return listVisible;
	}

	public void setListVisible(List<Visible> listVisible) {
		this.listVisible = listVisible;
	}

	public Phrase() {
		super();
	}

	public Phrase(String text) {
		this();
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}

	public int getEndVisible(int i) {
		if (i >= listVisible.size()) {
			return 0;
		}
		return listVisible.get(i).getEnd();
	}

	public int getwStartVisible(int i) {
		if (i >= listVisible.size()) {
			return 0;
		}
		return listVisible.get(i).getStart();
	}

	public void setSelected(int selectionStart, int selectionEnd) {
		if (selectionStart< 0){
			return;
		}
		if (selectionEnd <0){
			return;
		}
		boolean showText = (selectionStart != selectionEnd);
		if (showText) {
			Visible visible;
			if (this.listVisible.isEmpty()) {
				visible = new Visible();
				this.listVisible.add(visible);
			} else {
				visible = this.listVisible.get(0);
			}
			visible.setStart(Math.min(selectionStart, selectionEnd));
			visible.setEnd(Math.max(selectionStart, selectionEnd));

		} else {
			this.listVisible.removeAll(this.listVisible);
		}
	}

	public String getTextVisible() {
		String s = "";
		if (listVisible.isEmpty()) {
		} else {
			s = getTextVisible(this.text, this.listVisible.get(0));
		}
		return s;
	}

	private String getTextVisible(String s, Visible visible) {
		if (s == null) {
			return "";
		}
		if (visible == null) {
			return s;
		}
		int start = visible.getStart();
		int end = visible.getEnd();
		String t = "";
		for (int i = 0; i < s.length(); i++) {
			char c;
			if (i >= start && i < end) {
				c = s.charAt(i);
			} else if (s.charAt(i) == ' ') {
				c = ' ';
			} else {
				c = '.';
			}
			t += c;
		}
		return t;
	}

	public boolean isShowText() {

		return !listVisible.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listVisible == null) ? 0 : listVisible.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
	/**
	 * Ne prend pas en compte la visibilité
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phrase other = (Phrase) obj;
		
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
