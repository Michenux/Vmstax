/*
Modified from sos-logging-target: 
    http://soenkerohde.com/2008/08/sos-logging-target/

Modified to output to Firebug plugin for Firefox. Also uses
SwizTraceTarget as base class in order to allow traces from Swiz. 

Modifications Copyright (c) 2010 Shawn Church
All rights reserved.

Copyright (c) 2008-2009, S?nke Rohde
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

* Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.
* Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
* Neither the name of Adobe Systems Incorporated nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/


package com.schurchcomputers.flex.swiz
{

    import flash.external.*;
    import 	org.swizframework.utils.logging.*;
    
    public class FirebugTraceTarget extends SwizTraceTarget
    {

        public var includeCategory: Boolean = true;
        public var includeTime: Boolean = true;
        public var includeDate: Boolean = false;
        public var includeLevel: Boolean = true;
        public var fieldSeparator: String = " | ";

		public function FirebugTraceTarget()
		{
			super();
		}

        override protected function logEvent(event: SwizLogEvent): void
        {
            var log:Object = {message:event.message};

            if (includeDate || includeTime)
            {
                var d:Date = new Date();
                if (includeDate)
                {
                    log.date = d.getFullYear() + "/" +
                        padTime(Number(d.getMonth() + 1)) + "/" +
                        padTime(Number(d.getDate()));
                }

                if (includeTime)
                {
                    log.time = padTime(d.getHours()) + ":" +
                        padTime(d.getMinutes()) + ":" +
                        padTime(d.getSeconds()) + "." +
                        padTime(d.getMilliseconds(), true);
                }
            }

            if(includeLevel)
            {
                log.level = getLogLevel(event.level);
            }

            log.category = includeCategory ? SwizLogger(event.target).category : "";
       
       
            var parts: Array=[];
            for each (var part: String in ["date", "time", "level", "message", "category"])
            {
                if (log[part]) parts.push(log[part]);
            }
       
            var line: String = parts.join(fieldSeparator);
            doLog(line);
        }

        private function doLog(line: String): void
        {
            try
            {
                ExternalInterface.call("console.log", line);
            } 
            catch(e: *) 
            {
                trace(line);
            }
        }


        private function padTime(num:Number, millis:Boolean = false):String
        {
            if(millis)
            {
                if (num < 10)
                    return "00" + num.toString();
                else if (num < 100)
                    return "0" + num.toString();
                else
                    return num.toString();
            }
            else
            {
                return num > 9 ? num.toString() : "0" + num.toString();
            }
        }
        private function getLogLevel(level:int):String
        {
            var result: String;
            var levelStr: String = "000" + level;
            levelStr = levelStr.substr(levelStr.length-4,4)
            
            if (level <= SwizLogEventLevel.DEBUG)
                return "DEBUG(" + levelStr + ")"
            if (level <= SwizLogEventLevel.INFO)
                return "INFO (" + levelStr + ")"
            if (level <= SwizLogEventLevel.WARN)
                return "WARN (" + levelStr + ")"
            if (level <= SwizLogEventLevel.ERROR)
                return "ERROR(" + levelStr + ")"
            
            return "FATAL(" + levelStr + ")";
        }        
 
    }
}