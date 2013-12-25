package org.michenux.vmstax;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.michenux.vmstax.dstat.DStatParser;
import org.michenux.vmstax.vmstat.aix.VmAIXParser;
import org.michenux.vmstax.vmstat.linux24.VmLinux24Parser;
import org.michenux.vmstax.vmstat.linux26.VmLinux26Parser;
import org.michenux.vmstax.vmstat.solaris.VmSolarisParser;

/**
 * @author Michenux
 * 
 */
public class VmstaxServiceImpl {

	private static final int BUFFER = 2048;

	/**
	 * Liste des parsers
	 */
	private List<VmstaxParser> parsers = new ArrayList<VmstaxParser>();

	private List<VmstaxFormat> formats = new ArrayList<VmstaxFormat>();

	/**
	 * Map des parsers
	 */
	private Map<String, VmstaxParser> vmstaxParsers = new TreeMap<String, VmstaxParser>();

	
	public VmstaxServiceImpl() {
		parsers.add( new VmLinux26Parser());
		parsers.add( new VmLinux24Parser());
		parsers.add( new VmSolarisParser());
		parsers.add( new VmAIXParser());
		parsers.add( new DStatParser());
		
		this.afterPropertiesSet();
	}
	
	public VmstaxResult getVmstaxData(byte[] p_oContent, String p_sFormat,
			String p_sFileName, boolean p_bZipped ) throws Exception {
		
	VmstaxResult r_oVmstaxResult = new VmstaxResult();
	List<VmstaxData> r_listVmstaxData = new ArrayList<VmstaxData>();
	r_oVmstaxResult.setVmstaxDatas(r_listVmstaxData);

		VmstaxContext oVmstaxContext = new VmstaxContext();
		
		if ( ! p_bZipped) {
			r_listVmstaxData.addAll(parseLogFile(p_oContent, p_sFormat,
				p_sFileName, oVmstaxContext));
		}
		else {
			r_listVmstaxData.addAll( parseZipContent(p_oContent, p_sFormat,
				p_sFileName, oVmstaxContext));
		}
		
		r_oVmstaxResult.setMessage(oVmstaxContext.messageToString());	

		return r_oVmstaxResult;
	}

	/**
	 * @param p_oContent
	 * @param p_sFormat
	 * @param p_sFileName
	 * @return
	 * @throws IOException
	 * @throws VmstaxParseException
	 * @throws Exception
	 */
	private List<VmstaxData> parseLogFile(byte[] p_oContent, String p_sFormat,
			String p_sFileName, VmstaxContext p_oVmstaxContext ) throws IOException, VmstaxParseException,
			Exception {

		InputStream oIs = new ByteArrayInputStream(p_oContent);
		List<String> listLines = null;
		try {
			listLines = IOUtils.readLines(oIs);
		} finally {
			oIs.close();
		}

		List<VmstaxData> r_listVmstaxData = new ArrayList<VmstaxData>();
		VmstaxParser oVmstaxParser = (VmstaxParser) this.vmstaxParsers
				.get(p_sFormat);
		if (oVmstaxParser != null) {
			r_listVmstaxData.addAll(oVmstaxParser.parse(listLines, p_oVmstaxContext));
			for( VmstaxData oVmstaxData : r_listVmstaxData ) {
				oVmstaxData.setFileName(p_sFileName);
			}
		} else {
			throw new Exception("Can't find parser for format : " + p_sFormat);
		}
		
		System.out.println("< parseLogFile");
		return r_listVmstaxData;
	}


	/**
	 * @param p_bContent
	 * @param p_sFormat
	 * @param p_sZipFileName
	 * @return
	 * @throws Exception
	 */
	private List<VmstaxData> parseZipContent(byte[] p_bContent,
			String p_sFormat, String p_sZipFileName, VmstaxContext p_oVmstaxContext) throws Exception {

		System.out.println("> parseZipContent");
		List<VmstaxData> r_listVmstaxData = new ArrayList<VmstaxData>();

		byte data[] = new byte[BUFFER];

		ByteArrayInputStream fis = new ByteArrayInputStream(p_bContent);
		try {
			ZipInputStream zin = new ZipInputStream(fis);
			try {
				int count;
				int nbFiles = 0 ;
				ZipEntry entry;
				
				while ((entry = zin.getNextEntry()) != null) {
					if (!entry.isDirectory()) {
		
						ByteArrayOutputStream fos = new ByteArrayOutputStream();
						try {
							while ((count = zin.read(data, 0, BUFFER)) != -1) {
								fos.write(data, 0, count);
							}
							fos.flush();
						} finally {
							fos.close();
						}
						
						r_listVmstaxData.addAll(this.parseLogFile(
								fos.toByteArray(), p_sFormat,
								entry.getName(),
								p_oVmstaxContext));
						
						nbFiles++ ;
					}
				}
				
				if ( nbFiles == 0 ) {
					throw new Exception("Invalid zip file or no file found inside");
				}
				
			} finally {
				zin.close();
			}
		} finally {
			fis.close();
		}
		
		return r_listVmstaxData;
	}

	/**
	 * @return
	 */
	public List<VmstaxParser> getParsers() {
		return this.parsers;
	}

	/**
	 * @param parsers
	 */
	public void setParsers(List<VmstaxParser> parsers) {
		this.parsers = parsers;
	}

	public void afterPropertiesSet() {
		for (VmstaxParser oVmstaxParser : this.parsers) {
			this.vmstaxParsers.put(oVmstaxParser.getName(), oVmstaxParser);
			System.out.println("found parser : " + oVmstaxParser.getName());
			this.formats.add(new VmstaxFormat(oVmstaxParser.getName(),
					oVmstaxParser.getDisplayName(), oVmstaxParser.getIcon()));
		}
	}

	public List<VmstaxFormat> getVmstaxFormats() throws Exception {
		return this.formats;
	}
}