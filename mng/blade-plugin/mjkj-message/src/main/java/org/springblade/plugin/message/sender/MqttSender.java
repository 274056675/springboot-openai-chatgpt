package org.springblade.plugin.message.sender;


import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.codec.MqttQoS;
import org.springblade.plugin.message.mqtt.MyMqttServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 生产者-直连交换机模式
 * 此模式下路由键匹配，同一账号多处在线都可接收到消息，未登录账号无法接收推送
 * 且再登录账号不会有推送，路由消息丢失，适用于web端
 *
 * @author weikun
 */
@Slf4j
@Component("mqttSender")
@ConditionalOnProperty(
	name = "message.type.name",
	havingValue = "mqttSender"
)
public class MqttSender implements IMqSender{

	/**
	 * 发送消息
	 * @param topic
	 * @param content
	 */
	@Override
	public void sendSocketMsg(String topic,String content){
		MyMqttServer.mqttServer.publishAll(topic,
			ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8)));
	}


}
