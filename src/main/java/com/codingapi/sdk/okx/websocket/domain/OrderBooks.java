package com.codingapi.sdk.okx.websocket.domain;

import com.codingapi.springboot.framework.math.Arithmetic;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class OrderBooks {

    private final List<Book> buys;
    private final List<Book> sells;
    private final long ts;
    private final int checksum;
    private final long localTs;

    public OrderBooks(List<List<String>> asks, List<List<String>> bids, long ts, int checksum, long localTs) {
        this.buys = this.getBuyBooks(asks);
        this.sells = this.getSellBooks(bids);
        this.ts = ts;
        this.checksum = checksum;
        this.localTs = localTs;
    }


    public long getTsDiff() {
        return (localTs - ts);
    }


    public double getBuyPrice(int deep, int floatAmountBuy) {
        double amountBids = 0;
        List<Book> bookList = sells;
        for (int i = 0; i < deep; i++) {
            Book book = bookList.get(i);
            double amount = book.getOrderSize();
            amountBids = Arithmetic.parse(amount).add(amountBids).getFloatValue();
            if (amountBids >= floatAmountBuy) {
                double price = book.getPrice();
                return Arithmetic.parse(price).add(0.01f).getFloatValue();
            }
        }
        return bookList.get(0).getPrice();
    }


    public double getSellPrice(int deep, int floatAmountSell) {
        float amountAsks = 0;
        List<Book> bookList = sells;
        for (int i = 0; i < deep; i++) {
            Book book = bookList.get(i);
            float amount = book.getOrderSize();
            amountAsks = Arithmetic.parse(amount).add(amountAsks).getFloatValue();
            if (amountAsks >= floatAmountSell) {
                double price = book.getPrice();
                return Arithmetic.parse(price).sub(0.01f).getFloatValue();
            }
        }
        return bookList.get(0).getPrice();
    }


    public List<Book> getBuyBooks(List<List<String>> bids) {
        List<Book> list = new ArrayList<>();
        for (List<String> item : bids) {
            Book book = new Book();
            book.setPrice(Double.parseDouble(item.get(0)));
            book.setInstSize(Double.parseDouble(item.get(1)));
            book.setOrderSize(Integer.parseInt(item.get(3)));
            list.add(book);
        }
        return list;
    }

    public List<Book> getSellBooks(List<List<String>> asks) {
        List<Book> list = new ArrayList<>();
        for (List<String> item : asks) {
            Book book = new Book();
            book.setPrice(Float.parseFloat(item.get(0)));
            book.setInstSize(Float.parseFloat(item.get(1)));
            book.setOrderSize(Integer.parseInt(item.get(3)));
            list.add(book);
        }
        return list;
    }
}
