package org.michenux.vmstax.charts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "type", "displayName", "fieldx", "fieldy", "minField",
		"form", "strokeColor", "strokeWeight", "solidColorColor",
		"solidColorAlpha" })
public class SerieMetaData {

	@XmlElement(required = true)
	protected String type;

	@XmlElement(required = true)
	protected String displayName;

	@XmlElement(required = true)
	protected String fieldx;

	@XmlElement(required = true)
	protected String fieldy;

	@XmlElement(required = false)
	protected String minField;
	protected String form;

	@XmlElement(required = true)
	protected int strokeColor = -1;

	@XmlElement(required = true)
	protected int strokeWeight = -1;

	@XmlElement(required = false)
	protected int solidColorColor = -1;

	@XmlElement(required = false)
	protected double solidColorAlpha = -1.0D;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMinField() {
		return this.minField;
	}

	public void setMinField(String minField) {
		this.minField = minField;
	}

	public String getForm() {
		return this.form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public int getStrokeColor() {
		return this.strokeColor;
	}

	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	public int getStrokeWeight() {
		return this.strokeWeight;
	}

	public void setStrokeWeight(int strokeWeight) {
		this.strokeWeight = strokeWeight;
	}

	public int getSolidColorColor() {
		return this.solidColorColor;
	}

	public void setSolidColorColor(int solidColorColor) {
		this.solidColorColor = solidColorColor;
	}

	public double getSolidColorAlpha() {
		return this.solidColorAlpha;
	}

	public void setSolidColorAlpha(double solidColorAlpha) {
		this.solidColorAlpha = solidColorAlpha;
	}

	public String getFieldx() {
		return this.fieldx;
	}

	public void setFieldx(String fieldx) {
		this.fieldx = fieldx;
	}

	public String getFieldy() {
		return this.fieldy;
	}

	public void setFieldy(String fieldy) {
		this.fieldy = fieldy;
	}
}
