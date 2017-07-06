package ru.skysoftlab.crongen;

import java.text.ParseException;
import java.util.Locale;

import net.redhogs.cronparser.CronExpressionDescriptor;

import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class CronGenComponent extends CustomField<String>{

	private static final long serialVersionUID = -8375557423068383858L;

	private TextField cronExpression;
	private Label cronText = new Label();
	private Locale locale;
	
	public CronGenComponent(String caption) {
		init(caption, Locale.ENGLISH);
	}
	
	public CronGenComponent(String caption, Locale locale) {
		init(caption, locale);
	}
	
	private void init(String caption, Locale aLocale){
		cronExpression = new TextField(caption);
		CronGenExt ext = new CronGenExt();
		ext.extend(cronExpression);
		locale = aLocale;
		cronExpression.addTextChangeListener(new TextChangeListener() {

			private static final long serialVersionUID = 6560987133628416549L;

			@Override
			public void textChange(TextChangeEvent event) {
				cronText.setCaption(cronToString(event.getText()));
			}
		});
	}

	@Override
	protected Component initContent() {
		HorizontalLayout panelContent = new HorizontalLayout();
		panelContent.addComponent(cronExpression);
		panelContent.addComponent(cronText);
		return panelContent;
	}

	@Override
	public String getValue() {
		return cronExpression.getValue();
	}

	@Override
	public void setValue(String cronExpr)
			throws com.vaadin.data.Property.ReadOnlyException,
			ConversionException {
		cronExpression.setValue(cronExpr);
		cronText.setCaption(cronToString(cronExpr));
	}
	
	@Override
	public Class<? extends String> getType() {
		return String.class;
	}
	
	private String cronToString(String cronExpr) {
		try {
			return CronExpressionDescriptor.getDescription(cronExpr, locale);
		} catch (ParseException e1) {
			e1.printStackTrace();
			return "";
		}
	}

}
