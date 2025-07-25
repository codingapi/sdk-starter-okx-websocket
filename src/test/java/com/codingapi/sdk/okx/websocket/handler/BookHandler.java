package com.codingapi.sdk.okx.websocket.handler;

import com.codingapi.sdk.okx.websocket.domain.BookContext;
import com.codingapi.sdk.okx.websocket.domain.OrderBooks;
import com.codingapi.sdk.okx.websocket.event.MessageEvent;
import com.codingapi.sdk.okx.websocket.protocol.answer.BooksAnswer;
import com.codingapi.springboot.framework.event.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BookHandler implements IHandler<MessageEvent> {

    @Override
    public void handler(MessageEvent event) {
        if (event.isAction("channel", "books") && !event.containsKey("event")) {
            List<BooksAnswer> answers = event.getData(BooksAnswer.class);
            for (BooksAnswer answer : answers) {
                answer.setLocalTs(event.getTs());
                BookContext.getInstance().push(answer);
            }
            OrderBooks orderBooks = BookContext.getInstance().getOrderBooks();
            log.info("books ts diff: {}", orderBooks.getTsDiff());
        }
    }
}
