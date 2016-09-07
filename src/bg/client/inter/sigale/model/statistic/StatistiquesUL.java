package bg.client.inter.sigale.model.statistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatistiquesUL {

	public static final String TAG_ROOT = "ULStat";
	public static final String TAG_ID = "ID";
	public static final String TAG_ITEMS = "items";
	private String uniteLexicaleId = "0";

	private List<StatistiquesItem> list = new ArrayList<StatistiquesItem>();

	public StatistiquesUL() {
	}

	public String getResume() {
		int nbSucces = 0;
		int nbTotal = this.list.size();
		for (StatistiquesItem stI : this.list) {
			if (stI.isSucces()) {
				nbSucces++;
			} 
		}
		return " Succes : " + nbSucces + " / " + nbTotal;
	}


	public void add(boolean result) {
		StatistiquesItem item = new StatistiquesItem(result);
		list.add(item);
		StatistiquesLexiqueFactory.getInstance().saveStatisticUL(this);
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
		List<StatistiquesItem> list2 = new ArrayList<StatistiquesItem>();
		for (StatistiquesItem statistiquesItem : this.list) {
			if (statistiquesItem.getDate().after(date_0)) {
				list2.add(statistiquesItem);
			}
		}
		return list2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((uniteLexicaleId == null) ? 0 : uniteLexicaleId.hashCode());
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
		StatistiquesUL other = (StatistiquesUL) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (uniteLexicaleId == null) {
			if (other.uniteLexicaleId != null)
				return false;
		} else if (!uniteLexicaleId.equals(other.uniteLexicaleId))
			return false;
		return true;
	}

}
