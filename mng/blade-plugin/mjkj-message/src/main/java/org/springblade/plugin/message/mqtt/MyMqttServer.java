package org.springblade.plugin.message.mqtt;

import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.codec.ByteBufferUtil;
import net.dreamlu.iot.mqtt.core.server.MqttServer;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
@ConditionalOnProperty(
	name = "message.type.name",
	havingValue = "mqttSender"
)
@Slf4j
public class MyMqttServer {

	@Value("${mqtt.server.ip}")
	private String ip;

	@Value("${mqtt.server.port}")
	private int port;

	@Value("${mqtt.server.websocket-port}")
	private int websocketPort;

	public static MqttServer mqttServer;

	/**
	 * 关闭服务器方法
	 */
	@PreDestroy
	public void close() {
		log.info("关闭服务器....");
		mqttServer.stop();
	}

	@PostConstruct
	public void start() {
		// 注意：为了能接受更多链接（降低内存），请添加 jvm 参数 -Xss129k
		mqttServer = MqttServer.create()
			// 默认：0.0.0.0
			.ip(ip)
			// 默认：1883
			.port(port).webPort(websocketPort)
			// 默认为： 8092（mqtt 默认最大消息大小），为了降低内存可以减小小此参数，如果消息过大 t-io 会尝试解析多次（建议根据实际业务情况而定）
			.readBufferSize(512)
			// 最大包体长度，如果包体过大需要设置此参数，默认为： 8092
			.maxBytesInMessage(1024 * 100)
			// 自定义认证
			.authHandler((context,uniqueId,clientId,userName,password) -> {
				//校验密码
				if(Func.isEmpty(userName)){
					return false;
				}
				if(Func.isEmpty(password)){
					return false;
				}
				String defaultUserName="MJKJ_OPEN";
				String defaultPassword="MJKJ_OPEN";
				if(Func.equals(userName,defaultUserName) && Func.equals(password,defaultPassword)){
					return true;
				}
				return false;
			})
			// 消息监听
			.messageListener((context, clientId, message) -> {
				log.info("自定义消息==========clientId:{} message:{} payload:{}", clientId, message, ByteBufferUtil.toString(message.getPayload()));
			})
			.httpEnable(true)
			.debug() // 开启 debug 信息日志
			.start();

	}
}
