package com.codingapi.sdk.okx.websocket.domain;

import com.codingapi.sdk.okx.websocket.exception.Crc32ChecksumException;
import com.codingapi.sdk.okx.websocket.protocol.answer.BooksAnswer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;

public class BookContext {

    @Getter
    private final static BookContext instance = new BookContext();
    private final static CRC32 crc32 = new CRC32();

    private BookContext(){
        this.asks = new ArrayList<>();
        this.bids = new ArrayList<>();
    }


    /**
     * 卖方深度
     */
    private List<List<String>> asks;

    /**
     * 买方深度
     */
    private List<List<String>> bids;


    /**
     * 数据校验
     */
    @Getter
    private int checksum;

    /**
     * 接受时间
     */
    @Getter
    private long localTs;


    /**
     * 深度产生的时间
     */
    @Getter
    private long ts;


    public void clear() {
        this.asks.clear();
        this.bids.clear();
    }

    private void pushBids(List<List<String>> newBids){
        Iterator<List<String>> iterator = bids.iterator();
        while (iterator.hasNext()){
            List<String> item = iterator.next();
            String price = item.get(0);

            Iterator<List<String>> newIterator = newBids.iterator();
            while (newIterator.hasNext()){
                List<String> newBig = newIterator.next();
                if(newBig.get(0).equals(price)){
                    //价格相同
                    String sz =newBig.get(1);
                    if(sz.equals("0")) {
                        iterator.remove();
                        newIterator.remove();
                    }else{
                        item.set(1, sz);
                        newIterator.remove();
                    }
                }
            }
        }

        bids.addAll(newBids);
        bids.sort((o1, o2) -> (Float.parseFloat(o1.get(0)) > Float.parseFloat(o2.get(0))?-1:1));
    }

    private void pushAsks(List<List<String>> newAsks) {
        Iterator<List<String>> iterator = asks.iterator();
        while (iterator.hasNext()) {
            List<String> item = iterator.next();
            String price = item.get(0);

            Iterator<List<String>> newIterator = newAsks.iterator();
            while (newIterator.hasNext()) {
                List<String> newBig = newIterator.next();
                if (newBig.get(0).equals(price)) {
                    String sz = newBig.get(1);
                    if (sz.equals("0")) {
                        iterator.remove();
                        newIterator.remove();
                    } else {
                        item.set(1, sz);
                        newIterator.remove();
                    }
                }
            }
        }
        asks.addAll(newAsks);
        asks.sort((o1, o2) -> (Float.parseFloat(o1.get(0)) > Float.parseFloat(o2.get(0))?1:-1));
    }

    private int crc32(){
        int top = bids.size();
        if(bids.size()>25&asks.size()>25){
            top = 25;
        }

        StringBuilder builder = new StringBuilder();
        for(int i=0;i<top;i++){
            List<String> bid = bids.get(i);
            List<String> ask = asks.get(i);
            builder.append(bid.get(0)).append(":").append(bid.get(1)).append(":");
            builder.append(ask.get(0)).append(":").append(ask.get(1));
            if(i+1<top){
                builder.append(":");
            }
        }
        crc32.reset();
        crc32.update(builder.toString().getBytes());
        return (int)crc32.getValue();
    }

    public void push(BooksAnswer answer){
        this.pushBids(answer.getBids());
        this.pushAsks(answer.getAsks());
        int nowCrc = this.crc32();
        if(nowCrc == answer.getChecksum()){
            this.checksum =answer.getChecksum();
            this.localTs = answer.getLocalTs();
            this.ts = answer.getTs();
        }else{
            System.out.println("Books CRC32 CheckSum Error=============================>");
            System.out.println(bids.size()+"/"+asks.size());
            System.out.println(nowCrc+","+ answer.getChecksum());
            System.out.println(answer.getBids().size()+","+ answer.getAsks().size());
            throw new Crc32ChecksumException(nowCrc, answer.getChecksum());
        }
    }

    public OrderBooks getOrderBooks() {
        return new OrderBooks(asks, bids, ts, checksum, localTs);
    }

}
