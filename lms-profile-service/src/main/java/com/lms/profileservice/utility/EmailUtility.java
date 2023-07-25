//package com.lms.profileservice.utility;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//public class EmailUtility {
//    @Autowired
//    private KafkaTemplate<Object, Object> kafkaTemplate;
//
//    @Value("${topic.email}")
//    String kafkaTopic;
//
//    public void sendEmail(String from, String to, String subject, String body){
//        KafkaRequest kafkaRequest = new KafkaRequest();
//        EmailRequest emailRequest = new EmailRequest();
//        try {
//            log.info("Sending email message to kafka topic : {}", kafkaTopic);
//            emailRequest.setFrom(from);
//            emailRequest.setTo(to);
//            emailRequest.setSubject(subject);
//            emailRequest.setBody(body);
//            emailRequest.setAttachment("");
//
//            kafkaRequest.setData(emailRequest);
//            kafkaTemplate.send(kafkaTopic, kafkaRequest);
//            log.info("Email topic sent to kafka successfully");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//    }
//}
