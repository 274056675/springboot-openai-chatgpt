<script>
	import {
		mapGetters,
		mapMutations
	} from 'vuex'
	import {
		getSysConifgApi,
	} from '@/api/user.js'
		// getSpecificConifgApi,
	import {
		getStorage
	} from '@/common/storage.js';
	import unisite from '@/common/unisite.js'
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
			setTimeout(() => {
				if (this.alipingTime) {
					this.alipingTime = false
				} else {
					this.setWorkType()
					this.initFun()
					this.initMqtt()
				}
			}, 2000)

		},
		onHide: function() {
			console.log('App Hide')
		},
		data() {
			return {
				client: null, //mqtt实例
				pingTime: 0,
				//刷新token锁
				refreshLock: false,
				//刷新token
				tokenTimer: null,
				worktimer: null,
				pingTimer: null,
				isInit: false,
				alipingTime: false,
			}
		},
		mounted() {
			this.setWorkType()
		},
		computed: {
			...mapGetters(['isLogin', 'mqttKey']),
		},
		watch: {
			mqttKey: {
				handler(value) {
					if (value) {
						this.initMqtt()
					} else {
						if (this.client) {
							this.client.end()
						}
					}
				},
				immediate: true,
			}
		},
		methods: {
			...mapMutations([ 'SET_CURR_MQTT_MSG', 'SET_RECON_MQTT_TIME', 'SET_SYS_CONFIG',  'SET_SPECIFIC_CONFIG']),
			setWorkType() {
				if (this.worktimer) {
					clearInterval(this.worktimer)
				}
				this.worktimer = setInterval(async () => {
					this.alipingTime = true
					uni.getNetworkType({
						success: async (res) => {
							if (res.networkType != 'none') {
								clearInterval(this.worktimer)
								this.initFun()
							}
						}
					})
				}, 300);
			},
			initFun() {
				this.isInit = true
				getSysConifgApi().then(res => {
					let data = res.data
					let sysConfig = {}
					data.forEach(item => {
						if (['wechat_group', 'kf_qr_code','buy_kf_qrcode','index_banner'].includes(item.code)) {
							sysConfig[item.code] = item.file_url
						} else {
							sysConfig[item.code] = item.val
						}
					})
					this.SET_SYS_CONFIG(sysConfig)
				}).catch((error) => {
					if (error.errMsg && error.errMsg.indexOf('(-1009)') != -1) {
						//没有网络
						this.initFun()
					}
				})
				//获取系统配置
				// getSpecificConifgApi().then(res => {
				// 	let data = res.data
				// 	let sysConfig = {}
				// 	data.forEach(item => {
				// 		sysConfig[item.withdrawal_type] = item.withdrawal_value
				// 	})
				// 	this.SET_SPECIFIC_CONFIG(sysConfig)
				// })

				// #ifndef MP-WEIXIN
				this.refreshToken();
				// #endif

				if (this.pingTimer) {
					clearInterval(this.pingTimer)
				}
				//监听连接
				this.pingTimer = setInterval(() => {
					let time = new Date().getTime()
					if (this.pingTime) {
						if ((time - this.pingTime) / 1000 >= 5) {
							this.SET_RECON_MQTT_TIME(new Date().getTime())
							this.pingTime = time
						}
					}
				}, 1000)
				//监听网络
			},
			initMqtt() {
				try {
					if (this.client) {
						this.client.end()
					}
					var mqtt = require('@/common/mqtt.min.js')
					console.log(unisite.mqttMy)
					this.client = mqtt.connect(unisite.mqttMy, {
						clientId: new Date().getTime(),
						connectTimeout: 2000,
						reconnectPeriod: 4000,
						keepalive: 4,
						username: unisite.mqttUserName,
						password: unisite.mqttPassword,
						clean: true,
					})
					this.client.on('connect', () => {
						console.log('连接成功:', unisite.mqttMy)
						//订阅聊天主题
						this.client.subscribe(this.mqttKey, (err) => {
							if (!err) {
								console.log('聊天订阅成功:' + this.mqttKey)
							}
							this.SET_RECON_MQTT_TIME(new Date().getTime())
						})
					}).on('message', (topic, message) => {
						message = JSON.parse(message)
						console.log(topic + '主题信息:', message, topic == this.mqttKey)
						//聊天回复
						if (topic == this.mqttKey) {
							this.SET_CURR_MQTT_MSG(message)
						} 
					}).on('reconnect', (error) => {
						console.log('正在重连...', error)
						if (!this.pingTime) {
							this.pingTime = new Date().getTime()
						}
					}).on('error', (error) => {
						console.log('连接失败...', error)
					}).on('packetreceive', function(packet) {
						// console.log(` ${packet.cmd}`)
						this.pingTime = new Date().getTime()
					})

				} catch (error) {}
			},
			// 定时检测token
			refreshToken() {
				this.tokenTimer = setInterval(() => {
					const token = getStorage({ name: "token", debug: true, }) || {};
					if (token && token.content) {
						let day = (new Date().getTime() - token.datetime) / (1000 * 60 * 60 * 24)
						if (day >= 1 && !this.refreshLock) {
							this.refreshLock = true;
							this.$store
								.dispatch("refreshToken")
								.then(() => {
									this.refreshLock = false;
								})
								.catch(() => {
									this.refreshLock = false;
								});
						}
					}
				}, 60000);
			},
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import "@/uni_modules/uview-ui/index.scss";

	/* #ifdef H5 */
	uni-page-head {
		.uni-page-head__title {
			font-weight: normal;
		}

		.uni-page-head-btn {
			display: inherit;
		}
	}

	/* #endif */



	//设置多选框的样式
	//设置圆角
	checkbox.round .wx-checkbox-input,
	checkbox.round .uni-checkbox-input {
		border-radius: 100upx;
	}

	//设置背景色
	checkbox.red[checked] .wx-checkbox-input,
	checkbox.red.checked .uni-checkbox-input {
		background-color: #ffffff !important; //背景
		border-color: #ffffff !important; //边框
		color: #2681FB !important; //对勾
	}
</style>