package com.gaussic.util;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by asus on 2017/5/6.
 */
public class ElasticsearchManager {
    private TransportClient client;

    public ElasticsearchManager(String url,String clusterName){
        this(url,clusterName,9300);
    }

    public ElasticsearchManager(String url,String clusterName,int portNum) {
        if(client==null){
            Settings esSetting = Settings.builder()
                                 .put("cluster.name",clusterName)
                                 .put("client.transport.sniff",true)
                                 .build();
            try {
                client = new PreBuiltTransportClient(esSetting)
                         .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("tpsps4u"),portNum));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
    public void closeClient(){
        if(client==null){
            client.close();
        }
    }

    public void getIndexData(String index,String type,String id){
        GetResponse response = client.prepareGet(index, type, id).execute().actionGet();
        Map<String,Object> rpMap = response.getSource();
        if (rpMap == null) {
            System.out.println("empty");
            return;
        }
        Iterator<Map.Entry<String, Object>> rpItor = rpMap.entrySet().iterator();
        while (rpItor.hasNext()) {
            Map.Entry<String, Object> rpEnt = rpItor.next();
            System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
        }
    }

    public void scoreSearchByTerm(String index,String field,int hitSize,float score,String... fieldValues){
        QueryBuilder qb = QueryBuilders.constantScoreQuery(QueryBuilders.termsQuery(field, fieldValues)).boost(score);
        this.searchByQueyBuilder(qb,index,hitSize);
    }

    public void scoreSearchByMatch(String index,String fieldvalue,int hitSize,float score,String... fields){
        QueryBuilder qb = QueryBuilders.constantScoreQuery(QueryBuilders.multiMatchQuery(fieldvalue, fields)).boost(score);
        this.searchByQueyBuilder(qb,index,hitSize);
    }


    public void searchDataById(String index,String... ids){
        QueryBuilder qb = QueryBuilders.idsQuery().addIds(ids);
        this.searchByQueyBuilder(qb,index,ids.length);
    }

    public void searchDataByTerm(String index,String filed,int hitSize,String... fieldValues) {
        QueryBuilder qb = QueryBuilders.termsQuery(filed, fieldValues);
        this.searchByQueyBuilder(qb,index,hitSize);
    }

    //one value match muti field
    public void searchDataByMatch(String index,String filedValue,int hitSize,String... fields) {
        QueryBuilder qb = QueryBuilders.multiMatchQuery(filedValue,fields);
        this.searchByQueyBuilder(qb,index,hitSize);
    }

    //one value match all field
    public void searchDataByAllMatch(String index,int hitSize) {
        QueryBuilder qb = QueryBuilders.matchAllQuery();
        this.searchByQueyBuilder(qb,index,hitSize);
    }

    public void searchDataByAllMatch(String index,int hitSize,String[] likeTexts, MoreLikeThisQueryBuilder.Item[] likeItems) {
        QueryBuilder qb = QueryBuilders.moreLikeThisQuery(likeTexts,likeItems)
                                       .minTermFreq(1)         //最少出现的次数
                                      .maxQueryTerms(12);        // 最多允许查询的词语
        this.searchByQueyBuilder(qb,index,hitSize);
    }




    public void searchByQueyBuilder(QueryBuilder queryBuilder,String index,int hitSize){
                SearchResponse response = client.prepareSearch(index)
                                            .setSearchType(SearchType.QUERY_THEN_FETCH)
                                            .setScroll(new TimeValue(60000))
                                            .setQuery(queryBuilder)
                                            .setSize(100).execute().actionGet(); //100 hits per shard will be returned for each scroll
                //Scroll until no hits are returned
                while (true) {
                    response = client.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
                    for (SearchHit hit : response.getHits()) {
                        Iterator<Map.Entry<String, Object>> rpItor = hit.getSource().entrySet().iterator();
                        while (rpItor.hasNext()) {
                            Map.Entry<String, Object> rpEnt = rpItor.next();
                            System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
                        }
                    }
                    //Break condition: No hits are returned
                    if (response.getHits().hits().length == 0) {
                        break;
                    }
                }
    }




    public void deleteIndexData(String index,String type,String id) {
        DeleteResponse response = client.prepareDelete(index, type, id)
                .execute()
                .actionGet();
    }

    public void upadateIndexData(String index,String type,String id,Object jsonObject){
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index).type(type).id(id).doc(jsonObject);
    }
    public void addIndexData(String index,String type,String id,Object jsonObject){
        client.prepareIndex(index,type)
              .setSource(jsonObject)
              .setId(id)
              .execute()
              .actionGet();
    }

    /**
     * 对response结果的分析
     * @param response
     */
    public void testResponse(SearchResponse response) {
        // 命中的记录数
        long totalHits = response.getHits().totalHits();

        for (SearchHit searchHit : response.getHits()) {
            // 打分
            float score = searchHit.getScore();
            // 文章id
            int id = Integer.parseInt(searchHit.getSource().get("id").toString());
            // title
            String title = searchHit.getSource().get("title").toString();
            // 内容
            String content = searchHit.getSource().get("content").toString();
            // 文章更新时间
            long updatetime = Long.parseLong(searchHit.getSource().get("updatetime").toString());
        }

    }
}

