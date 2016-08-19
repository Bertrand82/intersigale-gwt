package bg.client.lesson;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class Lesson extends Composite {

	private static LessonUiBinder uiBinder = GWT.create(LessonUiBinder.class);

	interface LessonUiBinder extends UiBinder<Widget, Lesson> {
	}

	public Lesson() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonOK.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("OK");
			}
		});
		buttonNext.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Next");
			}
		});
	}

	@UiField
	Button buttonOK;
	
	@UiField
	Button buttonNext;

	

	


	private static Lesson instance= new Lesson();
	public static Widget getInstance() {
		return instance;
	}


}
