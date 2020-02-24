<h2><p>LogStreamlin</p></h2>
<p>CLI application for processing log files by filtering and aggregation.<p>
<h3><p>Filters:<p></h3>
<p>You can use three kinds of filters(filter by User, filter by time period and by message pattern.<p>
<h3><p>Aggregators:<p></h3>
<p>You can aggregate filtered data by User or by time Unit (SECONDS, MINUTES,HOURS, DAYS, MONTHS, YEARS)<p>
<h3><p>For example:<p></h3>
<p>-in F:\Temp\LogStreamline\testlogs -out F:\Temp\LogStreamline\result.log -fu
ElonTusk -at DAYS -ft 2020-05-02T06:12:01 -ff 2020-02-17T06:12:01 -tn 1<p>
<p>will get all log message with user ElonTusk recorded from 2020-02-17T06:12:01
to 2020-05-02T06:12:01 and write to file result.log.
Aggregated by users and days result will be printed to console using only one
thread<p>
<p>Example of the format StreamLine can work with by default:<p>
<p>'[main] DEBUG - User : ElonTusk; 2020-02-29T06:49:16.9736776 - ElonTusk bought
Tesla.'<p>
