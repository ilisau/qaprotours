package com.solvd.qaprotours.config.kafka.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.ReceiverRecord;

@Service("deleteHandler")
@Slf4j
@RequiredArgsConstructor
public class DeleteHandler implements Handler {

    private final ElasticsearchClient client;

    @Override
    public void handleMessage(final ReceiverRecord<String, Object> r) {
        try {
            if (r.key().endsWith("_delete")) {
                String id = r.key().substring(0, r.key().length() - 7);
                DeleteRequest.Builder builder
                        = new DeleteRequest.Builder();
                DeleteRequest request = builder
                        .id(id)
                        .build();
                client.delete(request);
                r.receiverOffset().acknowledge();
            }
        } catch (Exception e) {
            log.warn("Request is not sent because of exception: "
                    + e.getMessage());
        }
    }

}
