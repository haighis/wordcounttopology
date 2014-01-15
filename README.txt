Word Count Topology

A Storm topology that will consume new fireflyy bookmarks from redis pub/sub word count queue and then using Apache Tika will parse content and count total words. I.e. 4021 words for the url http://www.news.com

GetBookmarkToProcessSpout --> GetContentFromBookmarkUrlBolt --> CountTotalWordsBolt 

Sample usage

You need to have java and maven setup and working prior to running this sample.

Compile the code first and then run the 
topology. From a windows commpand prompt execute the following.

Compile code first using the command:

mvn clean

mvn compile

Then execute the topology:

mvn exec:java -Dexec.mainClass="WordCountTopologyMain" 

If you are on windows you may get a build failure stating it cannot delete a
temporary log file. This is permission issue on windows which can be ignored. 
If you scroll up your command window you will see the topology ran successfully.
