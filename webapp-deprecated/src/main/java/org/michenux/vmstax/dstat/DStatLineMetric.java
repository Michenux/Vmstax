package org.michenux.vmstax.dstat;

import java.util.List;

import org.michenux.vmstax.charts.LineMetric;

public class DStatLineMetric extends LineMetric {

	private long field1 ;
	private long field2 ;
	private long field3 ;
	private long field4 ;
	private long field5 ;
	private long field6 ;
	private long field7 ;
	private long field8 ;
	private long field9 ;
	private long field10 ;
	
	public long getField1() {
		return field1;
	}
	public void setField1(long field1) {
		this.field1 = field1;
	}
	public long getField2() {
		return field2;
	}
	public void setField2(long field2) {
		this.field2 = field2;
	}
	public long getField3() {
		return field3;
	}
	public void setField3(long field3) {
		this.field3 = field3;
	}
	public long getField4() {
		return field4;
	}
	public void setField4(long field4) {
		this.field4 = field4;
	}
	public long getField5() {
		return field5;
	}
	public void setField5(long field5) {
		this.field5 = field5;
	}
	public long getField6() {
		return field6;
	}
	public void setField6(long field6) {
		this.field6 = field6;
	}
	public long getField7() {
		return field7;
	}
	public void setField7(long field7) {
		this.field7 = field7;
	}
	public long getField8() {
		return field8;
	}
	public void setField8(long field8) {
		this.field8 = field8;
	}
	public long getField9() {
		return field9;
	}
	public void setField9(long field9) {
		this.field9 = field9;
	}
	public long getField10() {
		return field10;
	}
	public void setField10(long field10) {
		this.field10 = field10;
	}
	@Override
	public String toString() {
		return "DStatLineMetric [field1=" + field1
				+ ", field2=" + field2 + ", field3=" + field3 + ", field4="
				+ field4 + ", field5=" + field5 + ", field6=" + field6
				+ ", field7=" + field7 + ", field8=" + field8 + ", field9="
				+ field9 + ", field10=" + field10 + ", lineNum="
				+ getLineNumber() + "]";
	}
	
	
	public DStatLineMetric mergeLineMetrics(
			List<DStatLineMetric> p_listLineToMerge) throws InstantiationException, IllegalAccessException {
		int iNbPoints = p_listLineToMerge.size();
		DStatLineMetric r_oDStatLineMetric = p_listLineToMerge.get(0).getClass().newInstance();
		for (DStatLineMetric oLineMetric : p_listLineToMerge) {
			DStatLineMetric oCurrentLineMetric = (DStatLineMetric) oLineMetric ;
			r_oDStatLineMetric.setLineNumber(r_oDStatLineMetric.getLineNumber()
					+ oLineMetric.getLineNumber());
			mergePartOne(r_oDStatLineMetric, oCurrentLineMetric);
		}
		mergePart2(iNbPoints, r_oDStatLineMetric);
		
		return r_oDStatLineMetric;
	}
	
	/**
	 * @param r_oDStatLineMetric
	 * @param oLineMetric
	 * @param oCurrentLineMetric
	 */
	protected void mergePartOne(DStatLineMetric r_oDStatLineMetric, DStatLineMetric oCurrentLineMetric) {
		
		r_oDStatLineMetric.setField1(r_oDStatLineMetric.getField1() + oCurrentLineMetric.getField1());
		r_oDStatLineMetric.setField2(r_oDStatLineMetric.getField2() + oCurrentLineMetric.getField2());
		r_oDStatLineMetric.setField3(r_oDStatLineMetric.getField3() + oCurrentLineMetric.getField3());
		r_oDStatLineMetric.setField4(r_oDStatLineMetric.getField4() + oCurrentLineMetric.getField4());
		r_oDStatLineMetric.setField5(r_oDStatLineMetric.getField5() + oCurrentLineMetric.getField5());
		r_oDStatLineMetric.setField6(r_oDStatLineMetric.getField6() + oCurrentLineMetric.getField6());
		r_oDStatLineMetric.setField7(r_oDStatLineMetric.getField7() + oCurrentLineMetric.getField7());
		r_oDStatLineMetric.setField8(r_oDStatLineMetric.getField8() + oCurrentLineMetric.getField8());
		r_oDStatLineMetric.setField9(r_oDStatLineMetric.getField9() + oCurrentLineMetric.getField9());
		r_oDStatLineMetric.setField10(r_oDStatLineMetric.getField10() + oCurrentLineMetric.getField10());
	}
	
	/**
	 * @param iNbPoints
	 * @param r_oDStatLineMetric
	 */
	protected void mergePart2(int iNbPoints, DStatLineMetric r_oDStatLineMetric) {
		r_oDStatLineMetric.setLineNumber(r_oDStatLineMetric.getLineNumber()
				/ iNbPoints);
		r_oDStatLineMetric.setField1(r_oDStatLineMetric.getField1() / iNbPoints );
		r_oDStatLineMetric.setField2(r_oDStatLineMetric.getField2() / iNbPoints );
		r_oDStatLineMetric.setField3(r_oDStatLineMetric.getField3() / iNbPoints );
		r_oDStatLineMetric.setField4(r_oDStatLineMetric.getField4() / iNbPoints );
		r_oDStatLineMetric.setField5(r_oDStatLineMetric.getField5() / iNbPoints );
		r_oDStatLineMetric.setField6(r_oDStatLineMetric.getField6() / iNbPoints );
		r_oDStatLineMetric.setField7(r_oDStatLineMetric.getField7() / iNbPoints );
		r_oDStatLineMetric.setField8(r_oDStatLineMetric.getField8() / iNbPoints );
		r_oDStatLineMetric.setField9(r_oDStatLineMetric.getField9() / iNbPoints );
		r_oDStatLineMetric.setField10(r_oDStatLineMetric.getField10() / iNbPoints );
	}
}
