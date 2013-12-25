package org.michenux.vmstax.dstat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.michenux.vmstax.VmstaxContext;
import org.michenux.vmstax.VmstaxData;
import org.michenux.vmstax.VmstaxParseException;
import org.michenux.vmstax.VmstaxParser;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.dstat.cpu.CpuDStatParser;
import org.michenux.vmstax.dstat.disk.DiskDStatParser;
import org.michenux.vmstax.dstat.io.IODStatParser;
import org.michenux.vmstax.dstat.mem.MemDStatParser;
import org.michenux.vmstax.dstat.network.NetworkDStatParser;
import org.michenux.vmstax.dstat.paging.PagingDStatParser;
import org.michenux.vmstax.dstat.process.ProcDStatParser;
import org.michenux.vmstax.dstat.swap.SwapDStatParser;
import org.michenux.vmstax.dstat.system.SystemDStatParser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVParser;

/**
 * @author Michenux
 * 
 */
@Service("dstatParser")
public class DStatParser implements VmstaxParser, InitializingBean {

	private static List<DStatColParser> parsers = new ArrayList<DStatColParser>();

	private CSVParser csvParser = new CSVParser(',', '"', '\\');

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.michenux.vmstax.VmstaxParser#parse(java.util.List)
	 */
	@Override
	public List<VmstaxData> parse(List<String> p_listLines,
			VmstaxContext p_oVmstaxContext) throws VmstaxParseException {

		List<VmstaxData> r_listVmstaxData = new ArrayList<VmstaxData>();

		try {
			ListIterator<String> iterLines = p_listLines.listIterator();

			String[] nextLine = csvParser.parseLine(iterLines.next());
			if (nextLine == null || nextLine.length == 0
					|| !nextLine[0].startsWith("Dstat")) {
				throw new VmstaxParseException("Header not recognized");
			}

			// Rien à lire sur cette ligne (Author...)
			iterLines.next();
			
			while( iterLines.hasNext()) {
				r_listVmstaxData.add(parseBloc(p_oVmstaxContext, iterLines));
			}

		} catch (IOException oException) {
			throw new VmstaxParseException("Parse failed", oException);
		} catch (InstantiationException oException) {
			throw new VmstaxParseException("Failed to instanciate col parser",
					oException);
		} catch (IllegalAccessException oException) {
			throw new VmstaxParseException("Failed to instanciate col parser",
					oException);
		}

		return r_listVmstaxData;
	}

	/**
	 * @param p_oVmstaxContext
	 * @param p_iterLines
	 * @return
	 * @throws IOException
	 * @throws VmstaxParseException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected VmstaxData parseBloc(VmstaxContext p_oVmstaxContext,
			ListIterator<String> p_iterLines) throws IOException,
			VmstaxParseException, InstantiationException,
			IllegalAccessException {
		VmstaxData r_oVmstaxData = new VmstaxData();
		List<DStatLineMetric> listMetrics = new ArrayList<DStatLineMetric>();
		
		// Host et user
		String[] nextLine = csvParser.parseLine(p_iterLines.next());
		String sHost = nextLine[1];
		String sUser = nextLine[6];
		// Commande vmstat et date
		nextLine = csvParser.parseLine(p_iterLines.next());
		String sCommand = nextLine[1];
		String sDate = nextLine[6];

		System.out.println("host: " + sHost);
		System.out.println("user: " + sUser);
		System.out.println("command: " + sCommand);
		System.out.println("date: " + sDate);

		// Rien à lire sur cette ligne
		p_iterLines.next();

		// Parsing de l'entête
		nextLine = csvParser.parseLine(p_iterLines.next());
		List<DStatColParser> listParsers = new ArrayList<DStatColParser>();

		int iInputCol = 0;
		int iOutputFieldIndex = 1;
		List<String> unkownColumns = new ArrayList<String>();

		while (iInputCol < nextLine.length) {
			String sColName = nextLine[iInputCol];
			if (!sColName.isEmpty()) {
				System.out.println("col name : " + sColName);
				DStatColParser oDStatColParser = findParser(sColName);
				if (oDStatColParser != null) {
					oDStatColParser.setColIndexes(iInputCol, iOutputFieldIndex);
					System.out.println("adding column parser : "
							+ oDStatColParser.getClass().getName() + " at col "
							+ iInputCol);
					listParsers.add(oDStatColParser);
					iInputCol += oDStatColParser.getInputNbCols() - 1;
					iOutputFieldIndex += oDStatColParser.getOutputNbCols();
				} else {
					unkownColumns.add(sColName);
				}
			}
			iInputCol++;
		}

		if (!unkownColumns.isEmpty()) {
			StringBuilder sMessage = new StringBuilder(
					"The following columns were ignored : \n");
			for (String sColumn : unkownColumns) {
				sMessage.append("- ");
				sMessage.append(sColumn);
				sMessage.append('\n');
			}
			sMessage.append("Contact author if you would like to see them "
					+ "implemented into the software");
			p_oVmstaxContext.addMessage(sMessage.toString());
		}

		Class<? extends DStatLineMetric> lineMetricClass = null;
		if (iOutputFieldIndex < 11) {
			lineMetricClass = DStatLineMetric.class;
		} else if (iOutputFieldIndex < 21) {
			lineMetricClass = DStatLineMetric20.class;
		} else if (iOutputFieldIndex < 31) {
			lineMetricClass = DStatLineMetric30.class;
		} else {
			throw new VmstaxParseException(
					"Too many columns in csv, max is "
							+ 30
							+ "\n"
							+ "Contact author if you wish vmstax to manage more columns.");
		}

		// Ignore la deuxieme ligne d'entete
		p_iterLines.next();

		// Rien à lire sur cette ligne vide
		p_iterLines.next();

		// Commence le parsing des données
		int iLineNumber = 1;
		int iRealLineNumber = 9;

		// TODO: des lignes à ignorer ?
		boolean bStopMarker = false ;
		while (p_iterLines.hasNext() && !bStopMarker ) {
			String sLine = p_iterLines.next();
			if ( !sLine.isEmpty()) {
							
				nextLine = csvParser.parseLine(sLine);

				bStopMarker = nextLine.length >= 0 && nextLine[0].startsWith("Host");
				
				if ( !bStopMarker ) {
					try {
						DStatLineMetric oDStatLineMetric = lineMetricClass
								.newInstance();
						oDStatLineMetric.setLineNumber(iLineNumber);
						for (DStatColParser oDStatParser : listParsers) {
							oDStatParser.parse(nextLine, oDStatLineMetric);
						}
						// System.out.println(oDStatLineMetric.toString());
						listMetrics.add(oDStatLineMetric);
					} catch (Exception e) {
						StringBuffer sb = new StringBuffer();
						sb.append("Parsing error, line : ");
						sb.append(iRealLineNumber);
						if (e.getMessage() != null) {
							sb.append(" (");
							sb.append(e.toString());
							sb.append(')');
						}
						throw new VmstaxParseException(sb.toString(), e);
					}
					iLineNumber++;
				}
				else {
					p_iterLines.previous();
					iRealLineNumber-- ;
				}
			}
			iRealLineNumber++;
		}

		// merge
		if (listMetrics.size() > 210) {
			listMetrics = mergePoints(listMetrics);
			System.out.println("merged metrics[size=" + listMetrics.size()
					+ "]");
		}
		r_oVmstaxData.setMetrics(listMetrics);

		// Ajout des définitions de graphiques
		List<ChartMetaData> listChartMetadatas = new ArrayList<ChartMetaData>();
		for (DStatColParser oDStatColParser : listParsers) {
			listChartMetadatas.addAll(oDStatColParser.getChartMetadatas());
		}
		r_oVmstaxData.setChartMetadatas(listChartMetadatas);

		return r_oVmstaxData;
	}

	/**
	 * @param sColName
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private DStatColParser findParser(String sColName)
			throws InstantiationException, IllegalAccessException {
		DStatColParser r_oDStatColParser = null;

		Iterator<DStatColParser> iterParsers = this.parsers.iterator();
		boolean bTrouve = false;
		while (iterParsers.hasNext() && !bTrouve) {
			DStatColParser oDStatColParser = iterParsers.next();
			bTrouve = oDStatColParser.accept(sColName);
			if (bTrouve) {
				r_oDStatColParser = oDStatColParser.getClass().newInstance();
				r_oDStatColParser.setColHeader(sColName);
			}
		}

		return r_oDStatColParser;
	}

	private List<DStatLineMetric> mergePoints(
			List<DStatLineMetric> p_listMetrics) throws InstantiationException,
			IllegalAccessException {
		List<DStatLineMetric> r_mergedMetrics = new ArrayList<DStatLineMetric>();
		List<DStatLineMetric> keepForMerge = new ArrayList<DStatLineMetric>();

		int iNbPoints = p_listMetrics.size();
		double incStep = iNbPoints / 210.0D;

		System.out.println("inc step = " + incStep);

		double currentStep = incStep;
		Iterator<DStatLineMetric> iterMetrics = p_listMetrics.listIterator();
		DStatLineMetric oCurrentMetric = null;
		while (iterMetrics.hasNext()) {
			oCurrentMetric = iterMetrics.next();

			if (oCurrentMetric.getLineNumber() + 1 > currentStep) {
				if (!(keepForMerge.isEmpty())) {
					keepForMerge.add(oCurrentMetric);
					oCurrentMetric = oCurrentMetric
							.mergeLineMetrics(keepForMerge);
					keepForMerge.clear();
				}
				r_mergedMetrics.add(oCurrentMetric);
				currentStep += incStep;
			} else {
				keepForMerge.add(oCurrentMetric);
			}

		}

		if (!(keepForMerge.isEmpty())) {
			r_mergedMetrics.add(oCurrentMetric.mergeLineMetrics(keepForMerge));
		}

		return r_mergedMetrics;
	}

	@Override
	public String getName() {
		return "dstat";
	}

	@Override
	public String getDisplayName() {
		return "dstat";
	}

	@Override
	public String getIcon() {
		return "linux26Icon";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		parsers.add(new CpuDStatParser());
		parsers.add(new DiskDStatParser());
		parsers.add(new MemDStatParser());
		parsers.add(new ProcDStatParser());
		parsers.add(new PagingDStatParser());
		parsers.add(new SwapDStatParser());
		parsers.add(new NetworkDStatParser());
		parsers.add(new SystemDStatParser());
		parsers.add(new IODStatParser());
	}
}
