package bg.client.ui.stat;

import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.ui.stat.statCanvas.StatistiquesGlobalPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class Statistiques extends Composite {

	private static StatistiquesUiBinder uiBinder = GWT.create(StatistiquesUiBinder.class);

	interface StatistiquesUiBinder extends UiBinder<Widget, Statistiques> {
	}

	@UiField
	Label labelTitle;

	@UiField
	Label labeNombreDeQuestions;

	@UiField
	SimplePanel panelCanvas;

	@UiField
	Button buttonRefresh;

	private static Statistiques instance;

	public Statistiques() {
		initWidget(uiBinder.createAndBindUi(this));
		panelCanvas.add(StatistiquesGlobalPanel.getInstance());
		refresh();
	}

	public static Statistiques getInstance() {
		if (instance == null) {
			instance = new Statistiques();
		}
		return instance;
	}
	
	private void refresh() {
		Lexique lexique = LexiqueFactory.getInstance().getLexique();
		this.labelTitle.setText("Statistics of :"+lexique.getName());
		this.labeNombreDeQuestions.setText("Number of questions : "+lexique.getListUniteLexicale().size());
		StatistiquesGlobalPanel.getInstance().refresh(lexique);
	}

	@UiHandler("buttonRefresh")
	void onClick(ClickEvent e) {
		refresh();
	}

}
