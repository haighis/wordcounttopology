package bolts;

import model.BookmarkUrl;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.joda.time.DateTime;
import org.xml.sax.ContentHandler;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import storm.trident.state.Serializer;
import util.Conf;
import util.UrlUtil;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class GetContentFromBookmarkUrlBolt extends BaseBasicBolt {
     
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		
	}
	
	public void cleanup() {}

	@Override
	public void execute(Tuple input , BasicOutputCollector collector) { //
		
		try {
		
			BookmarkUrl url = (BookmarkUrl)input.getValueByField("bookmark");
			
			Parser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			ParseContext parseContext = new ParseContext();
			URL urlObject = new URL(url.getUrl());
			ContentHandler handler = new BodyContentHandler(10 * 1024 * 1024);
			parser.parse((InputStream) urlObject.getContent(), handler,
					metadata, parseContext);
			String[] mimeDetails = metadata.get("Content-Type").split(";");
			
			//TODO limit mime types we can process. HTML, PDF, word, txt
			
			System.out.println("*****************************************************************");
			System.out.println("parsing content ********************************************");
			System.out.println("*****************************************************************");
		
			System.out.println("parse content" + handler.toString());
			
			System.out.println("*****************************************************************");
			System.out.println("parsing content ********************************************");
			System.out.println("*****************************************************************");
			
			collector.emit(new Values(url));
			
			//collector.emit(new Values(entry.getUserId(), product, categ));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("in bolt exception" + e.getMessage()); //e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("wordstocount"));
	}
}
