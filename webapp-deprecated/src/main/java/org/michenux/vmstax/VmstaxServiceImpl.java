package org.michenux.vmstax;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.michenux.vmstax.utils.MyFileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author Michenux
 * 
 */
@Service("vmstaxService")
public class VmstaxServiceImpl implements VmstaxService, InitializingBean {

	private static final int BUFFER = 2048;

	/**
	 * Espace de stockage
	 */
	private String storePath;

	/**
	 * Liste des parsers
	 */
	private List<VmstaxParser> parsers;

	/**
	 * Liste des formats supportés
	 */
	private List<VmstaxFormat> formats = new ArrayList<VmstaxFormat>();

	/**
	 * Map des parsers
	 */
	private Map<String, VmstaxParser> vmstaxParsers = new TreeMap<String, VmstaxParser>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.michenux.vmstax.VmstaxService#getVmstaxData(byte[],
	 * java.lang.String, java.lang.String)
	 */
	public VmstaxResult getVmstaxData(byte[] p_oContent, String p_sFormat,
			String p_sFileName, boolean p_bZipped ) throws Exception {
		
		VmstaxResult r_oVmstaxResult = new VmstaxResult();
		List<VmstaxData> r_listVmstaxData = new ArrayList<VmstaxData>();
		r_oVmstaxResult.setVmstaxDatas(r_listVmstaxData);
		try {
			System.out.println("format=" + p_sFormat);
			System.out.println("fileName=" + p_sFileName);
			System.out.println("zipped=" + p_bZipped);
			
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
			System.out.println("message: " + r_oVmstaxResult.getMessage());
			
		} catch( Exception oException ) {
			oException.printStackTrace();
			throw oException ;
		}
		
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
		
		System.out.println("> parseLogFile");
		SimpleDateFormat oSdf = new SimpleDateFormat("yyyyMMdd-HH'h'mm'm'ss's'SSS");
		String sFormat = oSdf.format(new Date());
		String sFileName = this.storePath + "/" + sFormat + ".txt." + p_sFormat;
		System.out.println("file name = " + sFileName);
		FileUtils.writeByteArrayToFile(new File(sFileName), p_oContent);

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
				System.out.println("line count : " + oVmstaxData.getMetrics().size());
				System.out.println("chart count : " + oVmstaxData.getChartMetadatas().size());
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
		
		File oTempDir = MyFileUtils.createTempDir("vmstax-");
		
		File oZipFile = new File(oTempDir, p_sZipFileName);
		FileUtils.writeByteArrayToFile(oZipFile, p_bContent);

		byte data[] = new byte[BUFFER];

		FileInputStream fis = new FileInputStream(oZipFile);
		try {
			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fis));
			try {
				int count;
				int nbFiles = 0 ;
				ZipEntry entry;
				
				while ((entry = zin.getNextEntry()) != null) {
					if (!entry.isDirectory()) {
		
						File oEntryFile = new File(oTempDir, entry.getName());
						FileOutputStream fos = new FileOutputStream(oEntryFile);
						try {
							BufferedOutputStream dest = new BufferedOutputStream(fos, 2048);
							try {
								while ((count = zin.read(data, 0, BUFFER)) != -1) {
									dest.write(data, 0, count);
								}
								dest.flush();
							} finally {
								dest.close();
							}
							
						} finally {
							fos.close();
						}
						
						r_listVmstaxData.addAll(this.parseLogFile(
								FileUtils.readFileToByteArray(oEntryFile), p_sFormat,
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
		
		FileUtils.deleteDirectory(oTempDir);

		System.out.println("< parseZipContent");
		
		return r_listVmstaxData;
	}

	/**
	 * @return
	 */
	public String getStorePath() {
		return this.storePath;
	}

	/**
	 * @param p_sStorePath
	 */
	public void setStorePath(String p_sStorePath) {
		this.storePath = p_sStorePath;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		for (VmstaxParser oVmstaxParser : this.parsers) {
			this.vmstaxParsers.put(oVmstaxParser.getName(), oVmstaxParser);
			System.out.println("found parser : " + oVmstaxParser.getName());
			this.formats.add(new VmstaxFormat(oVmstaxParser.getName(),
					oVmstaxParser.getDisplayName(), oVmstaxParser.getIcon()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.michenux.vmstax.VmstaxService#getVmstaxFormats()
	 */
	public List<VmstaxFormat> getVmstaxFormats() throws Exception {
		return this.formats;
	}
}