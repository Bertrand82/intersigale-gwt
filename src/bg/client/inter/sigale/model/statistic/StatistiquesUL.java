package bg.client.inter.sigale.model.statistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatistiquesUL {
	/**
	 * Pas encore utilis�
	 */

	private String uniteLexicaleId = "0";

	private List<StatistiquesItem> list = new ArrayList<StatistiquesItem>();

	public StatistiquesUL() {
	}

	public String getResume() {
		int nbSucces = 0;
		int nbEchec = 0;
		int nbTotal = this.list.size();
		for (StatistiquesItem stI : this.list) {
			if (stI.isSucces()) {
				nbSucces++;
			} else {
				nbEchec++;
			}
		}
		return " Succes : " + nbSucces + " / " + nbTotal;
	}

	private int geNbSucces() {
		int i = 0;
		for (StatistiquesItem item : list) {
			if (item.isSucces()) {
				i++;
			}
		}
		return i;
	}

	public void add(boolean result) {
		StatistiquesItem item = new StatistiquesItem(result);
		list.add(item);
	}

	public boolean isLastResult(boolean b) {
		if (list.size() == 0) {
			return false;
		}
		return list.get(list.size() - 1).is(b);

	}

	public String getUniteLexicaleId() {
		return uniteLexicaleId;
	}

	public void setUniteLexicaleId(String uniteLexicaleId) {
		this.uniteLexicaleId = uniteLexicaleId;
	}

	public List<StatistiquesItem> getList() {
		return list;
	}

	public void setList(List<StatistiquesItem> list) {
		this.list = list;
	}

	public List<StatistiquesItem> getListAfterDate(Date date_0) {
		List<StatistiquesItem> list2 = new ArrayList();
		for (StatistiquesItem statistiquesItem : this.list) {
			if (statistiquesItem.getDate().after(date_0)) {
				list2.add(statistiquesItem);
			}
		}
		return list2;
	}

}
