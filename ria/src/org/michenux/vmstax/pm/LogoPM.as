package org.michenux.vmstax.pm
{
	import com.google.analytics.components.FlexTracker;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	import flash.net.navigateToURL;
	import flash.utils.Timer;
	
	import mx.controls.Image;
	import mx.managers.PopUpManager;
	
	import org.michenux.vmstax.models.Resources;
	
	import spark.components.TitleWindow;
	
	public class LogoPM
	{		
		[Inject (source="tracker")]
		public var tracker:FlexTracker ;
		
		public function LogoPM()
		{
		}
			
		public function navigateToFacebook(event:MouseEvent):void
		{
			tracker.trackPageview("/vmstax/socialnetwork/facebook");
			navigateToURL(new URLRequest('http://www.facebook.com/pages/Vmstax/173344652711508?sk=info'),'_blank');
		}
		
		public function navigateToTwitter(event:MouseEvent):void
		{
			tracker.trackPageview("/vmstax/socialnetwork/twitter");
			navigateToURL(new URLRequest('http://twitter.com/#!/vmstax'),'_blank');
		}
		
		public function makePayment():void{
			
			//https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=HT7U7VVREJRWW&lc=FR&item_name=Vmstax&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted
			
			tracker.trackPageview("/vmstax/donation");
			
			var url:String = "https://www.paypal.com/cgi-bin/webscr";
			var request:URLRequest = new URLRequest(url);
			var variables:URLVariables = new URLVariables();
			variables.cmd="_donations";
			variables.currency_code="EUR"; //USD
			variables.business="HT7U7VVREJRWW";
			variables.item_name = "Vmstax";
			variables.amount= "";
			variables.quantity = 1;
			variables.tax = '';
			request.data = variables;
			request.method = URLRequestMethod.POST;
			navigateToURL(request,"_blank");
		}
	}
}