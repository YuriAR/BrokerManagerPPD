package sample;

import br.ifce.jmsservice.JMSManagerService;
import br.ifce.jmsservice.JMSServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurireis on 01/03/17.
 */
public class Manager {

    public JMSServices jmsservice;
    public UICallback callback;

    public Manager(UICallback callback){
        this.callback = callback;
        br.ifce.jmsservice.JMSManagerService service = new
                JMSManagerService();
        jmsservice = service.getJMSManagerPort();
    }

    public void createQueue(String queueName){
        jmsservice.createQueue(queueName);
        callback.refreshUI();
    }

    public void createTopic(String topicName){
        jmsservice.createTopic(topicName);
        callback.refreshUI();
    }

    public List<String> listQueues(){
        List<String> toReturn = new ArrayList<>();
        for (String queue : jmsservice.listQueues()){
            queue = queue + " - " + messagesInQueue(queue).toString();
            toReturn.add(queue);
        }
        return toReturn;
    }

    public List<String> listTopics(){
        List<String> toReturn = new ArrayList<>();
        for (String topic : jmsservice.listTopics()){
            topic = topic + " - " + messagesInQueue(topic).toString();
            toReturn.add(topic);
        }
        return toReturn;
    }

    private Integer messagesInQueue(String queueName){
        return jmsservice.messagesInQueue(queueName);
    }

    public void deleteDest(String destName){
        jmsservice.deleteDestination(destName);
        callback.refreshUI();
    }
}
