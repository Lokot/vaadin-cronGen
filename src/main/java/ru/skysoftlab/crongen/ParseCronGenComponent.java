package ru.skysoftlab.crongen;

import java.text.ParseException;
import java.util.Locale;

import net.redhogs.cronparser.CronExpressionDescriptor;
import net.redhogs.cronparser.Options;

import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class ParseCronGenComponent extends CustomField<String> {

	private static final long serialVersionUID = -8375557423068383858L;

	private TextField cronExpression = new TextField();
	private Label cronText = new Label();
	private Locale locale;
	private CronGenExt ext = new CronGenExt();

	public ParseCronGenComponent(String caption) {
		init(caption, Locale.ENGLISH);
	}

	public ParseCronGenComponent(String caption, Locale locale) {
		init(caption, locale);
	}

	private void init(String caption, Locale aLocale) {
		setCaption(caption);
		ext.extend(cronExpression);
		locale = aLocale;
		cronExpression.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 6560987133628416549L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				cronText.setValue(cronToString(event.getProperty().getValue().toString()));
				
			}
		});
	}

	@Override
	protected Component initContent() {
		HorizontalLayout panelContent = new HorizontalLayout();
		panelContent.addComponent(cronExpression);
		panelContent.addComponent(cronText);
		panelContent.setComponentAlignment(cronExpression, Alignment.MIDDLE_CENTER);
		panelContent.setComponentAlignment(cronText, Alignment.MIDDLE_CENTER);
		return panelContent;
	}

	@Override
	public String getValue() {
		return cronExpression.getValue();
	}

	@Override
	public void setValue(String cronExpr) throws com.vaadin.data.Property.ReadOnlyException, ConversionException {
		if (cronExpr != null && cronExpr.length() > 0) {
			cronExpression.setValue(cronExpr);
			cronText.setValue(cronToString(cronExpr));
		}
	}

	@Override
	public Class<? extends String> getType() {
		return String.class;
	}

	private String cronToString(String cronExpr) {
		try {
			return CronExpressionDescriptor.getDescription(cronExpr, Options.twentyFourHour(), locale);
		} catch (IllegalArgumentException | ParseException e1) {
			return "";
		}
	}

	@Override
	public void clear() {
		setValue("");
	}

	@Override
	protected void setInternalValue(String newValue) {
		setValue(newValue);
	}

	@Override
	protected String getInternalValue() {
		return getValue();
	}
	
}
