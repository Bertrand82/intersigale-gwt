package bg.client.ui.lesson;

import java.awt.Color;

import bg.client.inter.sigale.model.Lexique;
import bg.client.inter.sigale.model.LexiqueFactory;
import bg.client.inter.sigale.model.UniteLexicale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Lesson extends Composite {
	private static String STR_VIDE = " ";
	private static LessonUiBinder uiBinder = GWT.create(LessonUiBinder.class);

	String colorNeutre = ConstantesSwing.color_start;
	String colorOK = ConstantesSwing.color_ok;
	String colorKO = ConstantesSwing.color_err;;

	interface LessonUiBinder extends UiBinder<Widget, Lesson> {
	}

	@UiField
	Button buttonOK;

	@UiField
	Button buttonNext;

	@UiField
	TextBox textBoxReponse;

	@UiField
	Label labelCorrection;

	@UiField
	Label labelQuestion;

	@UiField
	Label labelStat;

	private StatistiquePanelGUI statistiquePanelGUI = StatistiquePanelGUI.getInstance();;

	public Lesson() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonOK.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				validResult();
			}
		});
		buttonNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nextPhrase();
			}
		});
		textBoxReponse.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
						int cursorPosition = textBoxReponse.getCursorPos();
				 	     String text = textBoxReponse.getText();
					     if (text.length() > cursorPosition){
					    	 String text1  = text.substring(0,cursorPosition);
					    	 String text2  = text.substring(cursorPosition+1);
					    	 String  replace = text1+text2;
					    	 textBoxReponse.setText(replace);
					    	 textBoxReponse.setCursorPos(cursorPosition);
					     }
					     
			}
		});
		
		displayUniteLexicaleCourante();

	}

	private static Lesson instance = new Lesson();

	public static Widget getInstance() {
		return instance;
	}

	private Lexique getLexique() {
		return LexiqueFactory.getInstance().getLexique();
	}

	private void nextPhrase() {
		getLexique().next();
		displayUniteLexicaleCourante();
		labelCorrection.setText(STR_VIDE);
		displayResult(null, " ");
		setButtonsEtat(true);
		this.statistiquePanelGUI.updateStat(null);
		this.textBoxReponse.setText(reponseStr);
		this.textBoxReponse.setCursorPos(this.positionCuror);
		this.textBoxReponse.setFocus(true);
		
	}

	private void setButtonsEtat(boolean b) {
		this.buttonOK.setEnabled(b);
		this.buttonNext.setEnabled(!b);

	}

	private void displayResult(Boolean ok, String stat) {
		String color;
		if (ok == null) {
			color = colorNeutre;
		} else if (ok) {
			color = colorOK;
		} else {
			color = colorKO;
		}
		ConstantesSwing.setColorBackground(textBoxReponse, color);
		ConstantesSwing.setColorBackground(labelStat, color);
		labelStat.setText(stat);
	}

	private void validResult() {
		UniteLexicale ul = getLexique().getUniteLexicaleCourante();
		if (ul != null) {
			String text = ul.getPhrase_1().getText();
			UniteLexicale ulCourrante = getLexique().getUniteLexicaleCourante();
			boolean ok = ulCourrante.resultProcess(this.textBoxReponse.getText());
			String stat = " ? ";
			this.statistiquePanelGUI.updateStat(ulCourrante);
			System.out.println("ValidResult " + ok);
			// this.textFieldResponse.setText(text);
			setButtonsEtat(false);
			if (ok) {
				stat = " ok ";
			} else {
				stat = " ko ";
				System.out.println("p0 :" + getLexique().getUniteLexicaleCourante().getPhrase_0().getText());
				System.out.println("p1 :" + getLexique().getUniteLexicaleCourante().getPhrase_1().getText());
				System.out.println("p2 :" + textBoxReponse.getText());
				labelCorrection.setText(getLexique().getUniteLexicaleCourante().getPhrase_1().getText());
			}

			displayResult(ok, stat);
		}
	}
	 private int positionCuror =0;
	 private String reponseStr ="";
	    
	void displayUniteLexicaleCourante() {
		UniteLexicale ul = getLexique().getUniteLexicaleCourante();
		if (ul != null) {
			labelQuestion.setText(ul.getPhrase_0().getText());
			reponseStr=ul.getPhrase_1().getTextVisible();
			textBoxReponse.setText(reponseStr);
			positionCuror = ul.getPhrase_1().getEndVisible();
		}
	}
}
