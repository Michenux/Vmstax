package org.michenux.vmstax.dstat;


public class DStatLineMetric20 extends DStatLineMetric {

	private long field11;
	private long field12;
	private long field13;
	private long field14;
	private long field15;
	private long field16;
	private long field17;
	private long field18;
	private long field19;
	private long field20;

	public long getField11() {
		return field11;
	}

	public void setField11(long field11) {
		this.field11 = field11;
	}

	public long getField12() {
		return field12;
	}

	public void setField12(long field12) {
		this.field12 = field12;
	}

	public long getField13() {
		return field13;
	}

	public void setField13(long field13) {
		this.field13 = field13;
	}

	public long getField14() {
		return field14;
	}

	public void setField14(long field14) {
		this.field14 = field14;
	}

	public long getField15() {
		return field15;
	}

	public void setField15(long field15) {
		this.field15 = field15;
	}

	public long getField16() {
		return field16;
	}

	public void setField16(long field16) {
		this.field16 = field16;
	}

	public long getField17() {
		return field17;
	}

	public void setField17(long field17) {
		this.field17 = field17;
	}

	public long getField18() {
		return field18;
	}

	public void setField18(long field18) {
		this.field18 = field18;
	}

	public long getField19() {
		return field19;
	}

	public void setField19(long field19) {
		this.field19 = field19;
	}

	public long getField20() {
		return field20;
	}

	public void setField20(long field20) {
		this.field20 = field20;
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
		
		DStatLineMetric20 oDStatLineMetric = (DStatLineMetric20) p_oDStatLineMetric ;
		DStatLineMetric20 oCurrentLineMetric = (DStatLineMetric20) p_oCurrentLineMetric ;
		
		oDStatLineMetric.setField11(oDStatLineMetric.getField11()
				+ oCurrentLineMetric.getField11());
		oDStatLineMetric.setField12(oDStatLineMetric.getField12()
				+ oCurrentLineMetric.getField12());
		oDStatLineMetric.setField13(oDStatLineMetric.getField13()
				+ oCurrentLineMetric.getField13());
		oDStatLineMetric.setField14(oDStatLineMetric.getField14()
				+ oCurrentLineMetric.getField14());
		oDStatLineMetric.setField15(oDStatLineMetric.getField15()
				+ oCurrentLineMetric.getField15());
		oDStatLineMetric.setField16(oDStatLineMetric.getField16()
				+ oCurrentLineMetric.getField16());
		oDStatLineMetric.setField17(oDStatLineMetric.getField17()
				+ oCurrentLineMetric.getField17());
		oDStatLineMetric.setField18(oDStatLineMetric.getField18()
				+ oCurrentLineMetric.getField18());
		oDStatLineMetric.setField19(oDStatLineMetric.getField19()
				+ oCurrentLineMetric.getField19());
		oDStatLineMetric.setField20(oDStatLineMetric.getField20()
				+ oCurrentLineMetric.getField20());
	}

	/**
	 * @param iNbPoints
	 * @param r_oDStatLineMetric
	 */
	@Override
	protected void mergePart2(int iNbPoints, DStatLineMetric p_oDStatLineMetric) {

		super.mergePart2(iNbPoints, p_oDStatLineMetric);
		
		DStatLineMetric20 oDStatLineMetric = (DStatLineMetric20) p_oDStatLineMetric ;
		oDStatLineMetric.setField11(oDStatLineMetric.getField11()
				/ iNbPoints);
		oDStatLineMetric.setField12(oDStatLineMetric.getField12()
				/ iNbPoints);
		oDStatLineMetric.setField13(oDStatLineMetric.getField13()
				/ iNbPoints);
		oDStatLineMetric.setField14(oDStatLineMetric.getField14()
				/ iNbPoints);
		oDStatLineMetric.setField15(oDStatLineMetric.getField15()
				/ iNbPoints);
		oDStatLineMetric.setField16(oDStatLineMetric.getField16()
				/ iNbPoints);
		oDStatLineMetric.setField17(oDStatLineMetric.getField17()
				/ iNbPoints);
		oDStatLineMetric.setField18(oDStatLineMetric.getField18()
				/ iNbPoints);
		oDStatLineMetric.setField19(oDStatLineMetric.getField19()
				/ iNbPoints);
		oDStatLineMetric.setField20(oDStatLineMetric.getField20()
				/ iNbPoints);
	}
}
