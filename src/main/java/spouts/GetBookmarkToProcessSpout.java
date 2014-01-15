package spouts;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.BookmarkUrl;
import redis.clients.jedis.Jedis;
import util.Conf; 
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class GetBookmarkToProcessSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Jedis jedis;
    private String host;
    private int port;
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("bookmark"));
    }

    @Override
    public void open(Map conf, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		 host = conf.get(Conf.REDIS_HOST_KEY).toString();
		 port = Integer.valueOf(conf.get(Conf.REDIS_PORT_KEY).toString());
		 connectToRedis();
		this.collector = spoutOutputCollector;
    }

    private void connectToRedis() {
        jedis = new Jedis(host, port);
        jedis.connect();
    }
    
    @Override
    public void nextTuple() {
      	try {
			
  			byte[] bb = jedis.rpop(util.Conf.GetWordCountUrlsQueueName());
	    	
	    	System.out.println("-IS CONNECTED TO REDIS----------------------------------------------------------" + jedis.isConnected());
	    
			if(bb != null)
			{	
				BookmarkUrl pUrl = (BookmarkUrl)util.Serializer.deserialize(bb);
				
				System.out.println("------------------------------------------------------------------");
		    	System.out.println("-------------------SPOUT------------------------------------------");
		    	System.out.println("------------------------------------------------------------------");
		    	System.out.println("------------------------------------------------------------------");
		    	//System.out.println("bookmark from azure queue " + res.readEntity(String.class));
		    	System.out.println("bookmark from azure queue " + pUrl.getUrl() + pUrl.getUserId() + pUrl.getBookmarkId());
		    	
		    	System.out.println("------------------------------------------------------------------");
		    	System.out.println("------------------------------------------------------------------");
		    	System.out.println("------------------------------------------------------------------");
		    	
				collector.emit(new Values(pUrl));
	    	}
	    	else
	    	{
	    		System.out.println("-------------------SPOUT - no new bookmarks to process in redis------------------------------------------");
	    		try { Thread.sleep(1000); } catch (InterruptedException e) {}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("in spout error: " + e.getMessage()); //e.printStackTrace();
		}
    }
}