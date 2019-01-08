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
//solr����ɾ�Ķ���Ҫ�ύ����
public class Demo {
	//@Test
	public void testInsert() throws SolrServerException, IOException{
		SolrClient client=new HttpSolrClient("http://192.168.206.128:8080/solr/");
		
		SolrInputDocument doc=new SolrInputDocument();
		doc.addField("id", "003");
		doc.addField("bjsxt", "�Ƽ���");
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
		//��ѯȫ��
		//params.setQuery("*:*");
		//��ѯĳ������ֵΪjava�ļ�¼
		params.setQuery("bjsxt:java");
		
		params.setQuery("bjsxt:*");
		//���÷�ҳ
		//���ĸ�������ʼ��ѯ
		params.setStart(0);
		//��ѯ����
		params.setRows(2);
		//���ø���
		params.setHighlight(true);
		//���ø�������
		params.addHighlightField("bjsxt");
		//���ø���ǰ׺
		params.setHighlightSimplePre("<span style='color:red;'>");
		//���ø�����׺
		params.setHighlightSimplePost("</span>");
		
		QueryResponse res=client.query(params);
		//�����Ĳ�ѯ���
		Map<String, Map<String, List<String>>> hh=res.getHighlighting();
		SolrDocumentList solrList=res.getResults();
		for(SolrDocument doc:solrList){
			String value=hh.get(doc.getFieldValue("id")).get("bjsxt").get(0);
			System.out.println(value);
		}
		
		//δ�����Ĳ�ѯ���
//		SolrDocumentList solrList=res.getResults();
//		for(SolrDocument doc:solrList){
//			String value=(String) doc.getFieldValue("bjsxt");
//			System.out.println(value);
//		}
	}
}
