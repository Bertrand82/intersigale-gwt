package bg.client.inter.sigale.model.statistic;

import java.util.Date;

public class StatistiquesItem {

	boolean succes;

	Date date = new Date();

	public StatistiquesItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatistiquesItem(boolean result_) {
		succes = result_;
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

}
