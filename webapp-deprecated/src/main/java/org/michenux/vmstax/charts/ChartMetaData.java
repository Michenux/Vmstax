package org.michenux.vmstax.charts;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "panelTitle", "horizAxisTitle",
		"horizAxisMaximum", "vertAxisTitle", "vertAxisMinimum",
		"vertAxisMaximum", "vertAxisInterval", "vertAxisMinorInterval",
		"vertUnit", "dataTipMode", "dataTipTemplate", "series" })
public class ChartMetaData {

	@XmlElement(required = true)
	@XmlID
	protected String id;

	@XmlElement(required = true)
	protected String panelTitle;

	@XmlElement(required = false)
	protected String horizAxisTitle;

	@XmlElement(required = false)
	protected int horizAxisMaximum;

	@XmlElement(required = false)
	protected String vertAxisTitle;

	@XmlElement(required = true)
	protected int vertAxisMinimum = -1;

	@XmlElement(required = false)
	protected int vertAxisMaximum = -1;

	@XmlElement(required = false)
	protected int vertAxisInterval = -1;

	@XmlElement(required = false)
	protected int vertAxisMinorInterval = -1;

	@XmlElement
	protected String vertUnit;

	@XmlElement(required = true)
	protected String dataTipMode = "multiple";

	@XmlElement(required = true)
	protected String dataTipTemplate;

	@XmlElementWrapper(required = true)
	@XmlElement(name = "serie", required = true)
	protected List<SerieMetaData> series = new ArrayList<SerieMetaData>();

	public String getPanelTitle() {
		return this.panelTitle;
	}

	public void setPanelTitle(String panelTitle) {
		this.panelTitle = panelTitle;
	}

	public String getHorizAxisTitle() {
		return this.horizAxisTitle;
	}

	public void setHorizAxisTitle(String horizAxisTitle) {
		this.horizAxisTitle = horizAxisTitle;
	}

	public int getHorizAxisMaximum() {
		return this.horizAxisMaximum;
	}

	public void setHorizAxisMaximum(int horizAxisMaximum) {
		this.horizAxisMaximum = horizAxisMaximum;
	}

	public String getVertAxisTitle() {
		return this.vertAxisTitle;
	}

	public void setVertAxisTitle(String vertAxisTitle) {
		this.vertAxisTitle = vertAxisTitle;
	}

	public int getVertAxisMinimum() {
		return this.vertAxisMinimum;
	}

	public void setVertAxisMinimum(int vertAxisMinimum) {
		this.vertAxisMinimum = vertAxisMinimum;
	}

	public int getVertAxisMaximum() {
		return this.vertAxisMaximum;
	}

	public void setVertAxisMaximum(int vertAxisMaximum) {
		this.vertAxisMaximum = vertAxisMaximum;
	}

	public int getVertAxisInterval() {
		return this.vertAxisInterval;
	}

	public void setVertAxisInterval(int vertAxisInterval) {
		this.vertAxisInterval = vertAxisInterval;
	}

	public String getDataTipMode() {
		return this.dataTipMode;
	}

	public void setDataTipMode(String dataTipMode) {
		this.dataTipMode = dataTipMode;
	}

	public List<SerieMetaData> getSeries() {
		return this.series;
	}

	public void setSeries(List<SerieMetaData> series) {
		this.series = series;
	}

	public String getDataTipTemplate() {
		return this.dataTipTemplate;
	}

	public void setDataTipTemplate(String dataTipTemplate) {
		this.dataTipTemplate = dataTipTemplate;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVertAxisMinorInterval() {
		return this.vertAxisMinorInterval;
	}

	public void setVertAxisMinorInterval(int vertAxisMinorInterval) {
		this.vertAxisMinorInterval = vertAxisMinorInterval;
	}

	public String getVertUnit() {
		return this.vertUnit;
	}

	public void setVertUnit(String vertUnit) {
		this.vertUnit = vertUnit;
	}
}
