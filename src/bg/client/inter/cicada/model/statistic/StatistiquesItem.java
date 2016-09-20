package bg.client.inter.cicada.model.statistic;

import java.util.Date;

public class StatistiquesItem {

	public static final String TAG_ROOT = "item";
	public static final String TAG_Date = "date";
	public static final String TAG_succes = "succes";

	boolean succes;

	Date date = new Date();

	public StatistiquesItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatistiquesItem(boolean result_) {
		succes = result_;
	}

	public StatistiquesItem(boolean succes2, long time) {
		succes = succes2;
		this.date = new Date(time);
	}

	public boolean isSucces() {

		return succes;
	}

	public boolean is(boolean b) {
		return b == succes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (succes ? 1231 : 1237);
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
		StatistiquesItem other = (StatistiquesItem) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (succes != other.succes)
			return false;
		return true;
	}

}
