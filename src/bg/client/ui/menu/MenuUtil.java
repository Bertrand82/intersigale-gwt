package bg.client.ui.menu;

import com.google.gwt.user.client.ui.Button;

public class MenuUtil {

	public static void selectButton(Button buttonSelected){
		if(buttonSelected != null){
			buttonSelected.getElement().getStyle().setProperty("borderWidth", "3px");
		}
	}
}
