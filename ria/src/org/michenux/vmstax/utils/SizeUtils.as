package org.michenux.vmstax.utils
{
	import mx.formatters.NumberFormatter;
	
	public class SizeUtils
	{
		
		public function SizeUtils()
		{
		}
		
		/**
 		* Convertit un nombre de byte en chaine
 		*/
		public static function byteSizeToString( pSize :Number ):String {
			var oSizeFormat: NumberFormatter = new NumberFormatter();
			var sSize:String = "";
			var sUnit:String = "";
			
			// octets
			if ( pSize < 1000 ) {
				sSize = pSize + "";
				sUnit = "b" ;
			}
			// kb
			else if ( pSize < 1000000 ) {
				sSize = Math.round( pSize / 1000 ) + ""; 
				oSizeFormat.precision = 3 - sSize.length ;
				oSizeFormat.useThousandsSeparator = false ;
				sUnit = "Kb" ;
				sSize = oSizeFormat.format( pSize / 1000 );			
			}
			// Mb
			else if ( pSize < 1000000000 ) {
				sSize = Math.round( pSize / 1000 / 1000 ) + ""; 
				oSizeFormat.precision = 3 - sSize.length ;
				oSizeFormat.useThousandsSeparator = false ;
				sUnit = "Mb" ;
				sSize = oSizeFormat.format( pSize / 1000 / 1000 );
			} 
			// Giga
			else {
				sSize = Math.round( pSize / 1000 / 1000 / 1000 ) + "";
				oSizeFormat.precision = 3 - sSize.length ;
				oSizeFormat.useThousandsSeparator = false ;
				sUnit = "Gb" ;
				sSize = oSizeFormat.format( pSize / 1000 / 1000 / 1000 );
			}
			return deleteZeroEnding(sSize) + sUnit ;
		}
		
		/**
 		* Convertit un nombre de secondes en date
 		*/
		public static function kbSizeToString( pSize :Number ):String {
			var oSizeFormat: NumberFormatter = new NumberFormatter();
			var sSize:String = "";
			var sUnit:String = "";
			// Kb
			if ( pSize < 1000 ) {
				sUnit = "Kb" ;
				sSize = pSize + "";
			}
			// Mb
			else if ( pSize < 1000000 ) {
				sSize = Math.round( pSize / 1000 ) + "";
				oSizeFormat.precision = 3 - sSize.length ;
				oSizeFormat.useThousandsSeparator = false ;
				sUnit = "Mb" ;
				sSize = oSizeFormat.format( pSize / 1000 );
			}
			// Gb
			else {
				sSize = Math.round( pSize / 1000 / 1000 ) + "";
				oSizeFormat.precision = 3 - sSize.length ;
				oSizeFormat.useThousandsSeparator = false ;
				sUnit = "Gb" ;
				sSize = oSizeFormat.format( pSize / 1000 / 1000 );
			} 
			return deleteZeroEnding(sSize) + sUnit ;
		}
		
	   /**
 		* Convertit un nombre en chaine à unité
 		*/
		public static function numberToString( pNumber :Number ):String {
			var oSizeFormat: NumberFormatter = new NumberFormatter();
			var sSize:String = "";
			var sUnit:String = "";
			
			// Kb
			if ( pNumber < 1000 ) {
				sSize = pNumber + "";
			}
			// Mb
			else if ( pNumber < 1000000 ) {
				sSize = Math.round( pNumber / 1000 ) + "";
				oSizeFormat.precision = 3 - sSize.length ;
				oSizeFormat.useThousandsSeparator = false ;
				sUnit = "k";
				sSize = oSizeFormat.format( pNumber / 1000 );
			}
			// Gb
			else if ( pNumber < 1000000000 ) {
				sSize = Math.round( pNumber / 1000 / 1000 ) + "";
				oSizeFormat.precision = 3 - sSize.length ;
				oSizeFormat.useThousandsSeparator = false ;
				sUnit = "m";
				sSize = oSizeFormat.format( pNumber / 1000 / 1000 );
			} 
			else {
				sSize = Math.round( pNumber / 1000 / 1000 / 1000) + "";
				oSizeFormat.precision = 3 - sSize.length ;
				oSizeFormat.useThousandsSeparator = false ;
				sUnit = "g";
				sSize = oSizeFormat.format( pNumber / 1000 / 1000 / 1000 );
			}
			return deleteZeroEnding(sSize) + sUnit ;
		}
		
		
		private static function deleteZeroEnding( sChaine: String ): String {
			var r_sChaine: String = sChaine ;
			if ( r_sChaine.indexOf('.') != -1 ) {
				while( r_sChaine.charAt( r_sChaine.length-1) == '0' ) {
					r_sChaine = r_sChaine.substring(0, r_sChaine.length-1 );
				}
				if( r_sChaine.charAt( r_sChaine.length-1) == '.' ) {
					r_sChaine = r_sChaine.substring(0, r_sChaine.length-1 );
				}
			}
			return r_sChaine ;
		}
	}
}