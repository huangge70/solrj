package com.bjsxt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;
//solr中增删改都需要提交事务
public class Demo {
	//@Test
	public void testInsert() throws SolrServerException, IOException{
		SolrClient client=new HttpSolrClient("http://192.168.206.128:8080/solr/");
		
		SolrInputDocument doc=new SolrInputDocument();
		doc.addField("id", "003");
		doc.addField("bjsxt", "云计算");
		client.add(doc);
		client.commit();
	}
	
	//@Test
	public void testDelete() throws SolrServerException, IOException{
		SolrClient client=new HttpSolrClient("http://192.168.206.128:8080/solr/");
		client.deleteById("001");
		client.commit();
	}
	
	@Test
	public void testQuery() throws SolrServerException, IOException{
		SolrClient client=new HttpSolrClient("http://192.168.206.128:8080/solr/");
		SolrQuery params=new SolrQuery();
		//查询全部
		//params.setQuery("*:*");
		//查询某个属性值为java的记录
		params.setQuery("bjsxt:java");
		
		params.setQuery("bjsxt:*");
		//设置分页
		//从哪个索引开始查询
		params.setStart(0);
		//查询几个
		params.setRows(2);
		//启用高亮
		params.setHighlight(true);
		//设置高亮的列
		params.addHighlightField("bjsxt");
		//设置高亮前缀
		params.setHighlightSimplePre("<span style='color:red;'>");
		//设置高亮后缀
		params.setHighlightSimplePost("</span>");
		
		QueryResponse res=client.query(params);
		//高亮的查询结果
		Map<String, Map<String, List<String>>> hh=res.getHighlighting();
		SolrDocumentList solrList=res.getResults();
		for(SolrDocument doc:solrList){
			String value=hh.get(doc.getFieldValue("id")).get("bjsxt").get(0);
			System.out.println(value);
		}
		
		//未高亮的查询结果
//		SolrDocumentList solrList=res.getResults();
//		for(SolrDocument doc:solrList){
//			String value=(String) doc.getFieldValue("bjsxt");
//			System.out.println(value);
//		}
	}
}
