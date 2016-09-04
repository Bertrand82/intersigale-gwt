package bg.client.ui.stat;

import bg.client.ui.lesson.Lesson;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Statistiques extends Composite  {

	private static StatistiquesUiBinder uiBinder = GWT.create(StatistiquesUiBinder.class);

	interface StatistiquesUiBinder extends UiBinder<Widget, Statistiques> {
	}

	public Statistiques() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label label;

	

	@UiHandler("label")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

    private static  Statistiques instance;

	public static Statistiques getInstance() {
		if (instance == null){
			instance = new Statistiques();
		}
		return instance;
	}

}
