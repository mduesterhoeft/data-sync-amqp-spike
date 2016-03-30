package com.epages;

import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableRabbit
public class MessagingConfig {

    public static final String DATA_SYNC_TEST_EXCHANGE = "data-sync-test-exchange";

    @Bean
    Exchange exchange() {
        return new TopicExchange(DATA_SYNC_TEST_EXCHANGE, true, false);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(3);
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(10);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        //set the channel to transacted so that messages sent are rolled back when db transaction fails
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setReplyTimeout(10000L);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        final Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setJsonObjectMapper(objectMapper);
        jsonMessageConverter.setCreateMessageIds(true);
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(BusinessUnit.class);
        jsonMessageConverter.setClassMapper(classMapper);
        final ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter(jsonMessageConverter);
        messageConverter.addDelgate(CONTENT_TYPE_JSON, jsonMessageConverter);
        return messageConverter;
    }

}
