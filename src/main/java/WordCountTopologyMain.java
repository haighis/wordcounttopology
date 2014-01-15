import spouts.GetBookmarkToProcessSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import bolts.GetContentFromBookmarkUrlBolt;

public class WordCountTopologyMain {
	public static void main(String[] args) throws InterruptedException {
         
		/**
        //Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("get-bookmark-url-azure", new ConsumeBookmarkSpout());
		builder.setBolt("save-to-audit", new SaveToAuditQueueBolt()).shuffleGrouping("get-bookmark-url-azure");
		builder.setBolt("save-to-redis", new SaveToLocalQueuesBolt()).shuffleGrouping("save-to-audit");
				
        //Configuration
		Config conf = new Config();
		conf.put(util.Conf.REDIS_HOST_KEY, "localhost");
		conf.put(util.Conf.REDIS_PORT_KEY, util.Conf.DEFAULT_JEDIS_PORT);
		
		conf.setDebug(true);
        //Topology run
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("ConsumeBookmarkTopology", conf, builder.createTopology());
		Thread.sleep(1000);
		//cluster.shutdown();
		**/
		
		 //Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("get-bookmark-url-process", new GetBookmarkToProcessSpout());
		builder.setBolt("get-content-from-bookmark-url", new GetContentFromBookmarkUrlBolt()).shuffleGrouping("get-bookmark-url-process");
			
        //Configuration
		Config conf = new Config();
		conf.put(util.Conf.REDIS_HOST_KEY, "localhost");
		conf.put(util.Conf.REDIS_PORT_KEY, util.Conf.DEFAULT_JEDIS_PORT);
		
		conf.setDebug(true);
        //Topology run
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Count-Words-Topology", conf, builder.createTopology());
		Thread.sleep(1000);
		//cluster.shutdown();
		
	}
}
