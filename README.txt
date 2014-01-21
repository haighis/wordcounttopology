Word Count Topology

A Storm topology that will:
- Consume new fireflyy bookmarks from redis pub/sub word count queue - spout
- Save Html source an azure blob with the bookmarkid as the filename - bolt
- Use Apache Tika will parse Html source to return raw content without html tags - bolt
- Save Raw content without html tags to Azure blob with the bookmarkid as the filename - bolt
- Count total words. I.e. 4021 words for the url http://www.news.com - bolt

GetBookmarkToProcessSpout --> GetContentFromBookmarkUrlBolt --> SaveHtmlSourceToAzureBlob --> SaveRawContentOnlyToAzureBlob --> CountTotalWordsBolt 

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


https://github.com/nathanmarz/storm/wiki/Running-topologies-on-a-production-cluster

Create Java jar with dependencies

mvn -f pom.xml package

Submit Test Topology 

/opt/storm-0.9.0.1/bin/storm jar /home/haighis/storm-starter-0.0.1-SNAPSHOT-jar-with-dependencies.jar storm.starter.ExclamationTopology wills-test-topology

Submit Word Count Toplogy to Storm in production cluster

/opt/storm-0.9.0.1/bin/storm jar /home/[user]/[jarname].jar storm.consume.WordCountTopologyMain [SOMEHOSTNAME] 1

View Topology in Production Cluster

/opt/storm-0.9.0.1/bin/storm list

Kill Topology in Production Cluster

/opt/storm-0.9.0.1/bin/storm kill WordCountTopology

