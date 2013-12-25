package org.michenux.vmstax.utils
{
	public class DateUtils
	{
		
		public function DateUtils()
		{
		}
		
		/**
 		* Convertit un nombre de secondes en date
 		*/
		public static function secondToDate( pNbTotalSeconds :Number):Date {
			var nbHours:Number = pNbTotalSeconds / 3600 ;
			var remainSeconds:Number = pNbTotalSeconds % 3600 ;
			var nbMinutes:Number = remainSeconds / 60 ;
			var nbSeconds:Number = remainSeconds % 60 ;
			var newDate:Date = new Date(0,0,0,0,0,0,0);
			newDate.setUTCHours(nbHours,nbMinutes,nbSeconds);
    		return newDate ;
		}
			
			
		/**
 		* Convertit un nombre de secondes en date
 		*/
		public static function utcHourToString( pDate :Date):String {
			var sHeure:String = "";
			sHeure += pDate.getUTCHours() < 10 ? "0" + pDate.getUTCHours() : pDate.getUTCHours();
			sHeure += ":" ;
			sHeure += pDate.getUTCMinutes() < 10 ? "0" + pDate.getUTCMinutes() : pDate.getUTCMinutes();
			sHeure += ":" ;
			sHeure += pDate.getUTCSeconds() < 10 ? "0" + pDate.getUTCSeconds() : pDate.getUTCSeconds();
			return sHeure ;
		}
		
		/**
		 * Convertit une durée en String
		 */
		public static function dureeToString(
			pDureeSeconds: Number, 
			withSeconds: Boolean,
			withHours: Boolean,
			withDays: Boolean ):String {
			
			var nbDays:Number = Math.floor( pDureeSeconds / ( 3600 * 24 ));
			var remainSeconds:Number = pDureeSeconds % ( 3600 * 24 );
			var nbHours:Number = Math.floor(remainSeconds / 3600 );
			remainSeconds = remainSeconds % 3600 ;
			var nbMinutes:Number = Math.floor(remainSeconds / 60 );
			var nbSeconds:Number = remainSeconds % 60 ;
			
			var r_sDuree: String = "";
			if ( withDays ) {
				r_sDuree = nbDays + "d" ;
			}
			if ( withHours ) {
				r_sDuree += nbHours < 10 ? "0" + nbHours : nbHours ;
				r_sDuree += "h" ;
			}
			r_sDuree += nbMinutes < 10 ? "0" + nbMinutes : nbMinutes;
			r_sDuree += "m" ;
			if ( withSeconds ) {
				r_sDuree += nbSeconds < 10 ? "0" + nbSeconds : nbSeconds;
				r_sDuree += "s" ;
			}
			return r_sDuree ;
		}
	}
}