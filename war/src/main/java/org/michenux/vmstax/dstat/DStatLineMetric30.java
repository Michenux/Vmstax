package org.michenux.vmstax.dstat;


public class DStatLineMetric30 extends DStatLineMetric20 {

	private long field21;
	private long field22;
	private long field23;
	private long field24;
	private long field25;
	private long field26;
	private long field27;
	private long field28;
	private long field29;
	private long field30;
	
	public long getField21() {
		return field21;
	}

	public void setField21(long field21) {
		this.field21 = field21;
	}

	public long getField22() {
		return field22;
	}

	public void setField22(long field22) {
		this.field22 = field22;
	}

	public long getField23() {
		return field23;
	}

	public void setField23(long field23) {
		this.field23 = field23;
	}

	public long getField24() {
		return field24;
	}

	public void setField24(long field24) {
		this.field24 = field24;
	}

	public long getField25() {
		return field25;
	}

	public void setField25(long field25) {
		this.field25 = field25;
	}

	public long getField26() {
		return field26;
	}

	public void setField26(long field26) {
		this.field26 = field26;
	}

	public long getField27() {
		return field27;
	}

	public void setField27(long field27) {
		this.field27 = field27;
	}

	public long getField28() {
		return field28;
	}

	public void setField28(long field28) {
		this.field28 = field28;
	}

	public long getField29() {
		return field29;
	}

	public void setField29(long field29) {
		this.field29 = field29;
	}

	public long getField30() {
		return field30;
	}

	public void setField30(long field30) {
		this.field30 = field30;
	}

	/**
	 * @param r_oDStatLineMetric
	 * @param oLineMetric
	 * @param oCurrentLineMetric
	 */
	@Override
	protected void mergePartOne(DStatLineMetric p_oDStatLineMetric,
			DStatLineMetric p_oCurrentLineMetric) {

		super.mergePartOne(p_oDStatLineMetric, p_oCurrentLineMetric);
		
		DStatLineMetric30 oDStatLineMetric = (DStatLineMetric30) p_oDStatLineMetric ;
		DStatLineMetric30 oCurrentLineMetric = (DStatLineMetric30) p_oCurrentLineMetric ;
		
		oDStatLineMetric.setField21(oDStatLineMetric.getField21()
				+ oCurrentLineMetric.getField11());
		oDStatLineMetric.setField22(oDStatLineMetric.getField22()
				+ oCurrentLineMetric.getField12());
		oDStatLineMetric.setField23(oDStatLineMetric.getField23()
				+ oCurrentLineMetric.getField13());
		oDStatLineMetric.setField24(oDStatLineMetric.getField24()
				+ oCurrentLineMetric.getField14());
		oDStatLineMetric.setField25(oDStatLineMetric.getField25()
				+ oCurrentLineMetric.getField15());
		oDStatLineMetric.setField26(oDStatLineMetric.getField26()
				+ oCurrentLineMetric.getField16());
		oDStatLineMetric.setField27(oDStatLineMetric.getField27()
				+ oCurrentLineMetric.getField17());
		oDStatLineMetric.setField28(oDStatLineMetric.getField28()
				+ oCurrentLineMetric.getField18());
		oDStatLineMetric.setField29(oDStatLineMetric.getField29()
				+ oCurrentLineMetric.getField19());
		oDStatLineMetric.setField30(oDStatLineMetric.getField30()
				+ oCurrentLineMetric.getField20());
	}

	/**
	 * @param iNbPoints
	 * @param r_oDStatLineMetric
	 */
	@Override
	protected void mergePart2(int iNbPoints, DStatLineMetric p_oDStatLineMetric) {

		super.mergePart2(iNbPoints, p_oDStatLineMetric);
		
		DStatLineMetric30 oDStatLineMetric = (DStatLineMetric30) p_oDStatLineMetric ;
		oDStatLineMetric.setField21(oDStatLineMetric.getField21()
				/ iNbPoints);
		oDStatLineMetric.setField22(oDStatLineMetric.getField22()
				/ iNbPoints);
		oDStatLineMetric.setField23(oDStatLineMetric.getField23()
				/ iNbPoints);
		oDStatLineMetric.setField24(oDStatLineMetric.getField24()
				/ iNbPoints);
		oDStatLineMetric.setField25(oDStatLineMetric.getField25()
				/ iNbPoints);
		oDStatLineMetric.setField26(oDStatLineMetric.getField26()
				/ iNbPoints);
		oDStatLineMetric.setField27(oDStatLineMetric.getField27()
				/ iNbPoints);
		oDStatLineMetric.setField28(oDStatLineMetric.getField28()
				/ iNbPoints);
		oDStatLineMetric.setField29(oDStatLineMetric.getField29()
				/ iNbPoints);
		oDStatLineMetric.setField30(oDStatLineMetric.getField30()
				/ iNbPoints);
	}
}
