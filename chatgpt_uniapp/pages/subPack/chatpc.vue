<template>
	<view class="chat-pc">
		<view class="chat-area">
			<view class="chat-box">
				<scroll-view :animation="scrollAnimationData" ref="scrollview" @click="hideKey" scroll-y="true" class="scroll-view" :style="chatBodyBottom" :scroll-into-view="scrollIntoID" :scroll-with-animation="false" @scrolltoupper="getTopDataFun" @touchstart="scrollTouchstartFun">
					<u-loadmore v-show="pageObj.total >= pageObj.size" :status="loadinStatus" nomoreText="没有更多消息了" loadmoreText="下拉查看更多记录" />
					<view v-for="(item, index) in msgList" :key="item.id" :id="'chat_' + item.id">
						<uni-chat-detail-pc style="flex: 1" :list="msgList" :item="item" :index="index"  :dfAvatar="dfAvatar" @set-scroll-index="setScrollIndexFun" @open-pay-member="openPayMember">
						</uni-chat-detail-pc>
					</view>
					<view style="height: 0px" :id="scrollLastID"></view>
				</scroll-view>
				<view class="chat-foot-box">
					<u--textarea maxlength="-1" :cursorSpacing="15" :showConfirmBar="false" :placeholder="sysConfig.content_tip" ref="sendTextarea" class="dh-input" v-model="msg" border="none" autoHeight @confirm="sendIssueFun" @linechange="setinputHeight"></u--textarea>
					<view class="foot-btn" :class="{ active: msg && !isSend && !isWaiting }" @click="sendIssueFun">
						<view v-show="!isSend && !isWaiting">发送</view>
						<loading-view-pc v-show="isSend || isWaiting"></loading-view-pc>
					</view>
				</view>
			</view>
		</view>
		<u-popup :show="payMemberPop" mode="center" :closeable="true" :closeOnClickOverlay="false" @close="payMemberPop = false" round="5" :customStyle="{width:'400px', padding:'10px'}">
			<view class="pay-member-title"> 开通VIP </view>
			<member-pay-pc @pay-close="payClose"></member-pay-pc>
		</u-popup>
		<u-overlay :show="showTip" @click="showTip = false">
			<view class="tip-warp-box" v-if="showTip" style="padding-top: 20px">
				<view class="tip_q" style="
		        text-align: right;
		        font-size: 12px;
		        padding-right: 10px;
		        color: red;
		      ">
					↙ 发送问题
				</view>
				<uni-chat-detail-pc :list="tipObj.msgList" :item="tipObj.my"></uni-chat-detail-pc>
				<view class="tip_a" style="
		        text-align: left;
		        font-size: 12px;
		        padding-left: 10px;
		        color: red;
		      ">
					↘ 等待AI回答问题
				</view>
				<uni-chat-detail-pc :list="tipObj.msgList" :item="tipObj.ai"></uni-chat-detail-pc>
			</view>
		</u-overlay>
		<more-chat-num ref="moreChatNum" pageName="chat" @set-vip-fun="payMemberPop = true" advice="pc"></more-chat-num>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapActions,
		mapMutations
	} from "vuex";
	import unisite from "@/common/unisite.js";
	import {
		sendIssueApi,
		getHistoryListApi,
		getLastMessageApi,
	} from "@/api/user.js";
	import LoadingViewPc from "@/components/loading-view-pc.vue";
	import MemberPayPc from "@/components/member-pay-pc.vue";
	import MoreChatNum from "@/components/more-chat-num.vue";
	import {
		setTimeout
	} from "timers";
	import {
		userInfo
	} from "os";
	import {
		log
	} from "../../common/mqtt.min";
	export default {
		components: {
			LoadingViewPc,
			MemberPayPc,
			MoreChatNum,
		},
		data() {
			return {
				isInit: false,
				pageCode: "chat",
				dfAvatar: require("../../static/chat_df_avatar.png"),
				msg: "", //输入框值
				loadinStatus: "loadmore", //聊天记录loading状态
				isSend: false, //是否发送中
				isWaiting: false, //是否等待发送
				pageObj: {
					//分页
					current: 1,
					size: 10,
					total: 0,
					statId: "", //起始聊天记录id
				},
				isGetData: false, //是否获取数据
				systemInfo: uni.getSystemInfoSync(), //设备信息
				scrollAnimationData: {},
				menuAnimationData: {},
				safeBottom: 0,
				srcollHeight: 0,
				scrollIntoID: "chat_0",
				scrollLastID: `chat_${new Date().getTime()}`,
				showMore: false,
				msgList: [], //聊天消息数据
				currKey: "0",
				showTip: false, //展示发送问题
				tipObj: {
					my: {},
					ai: {},
					msgList: [],
				},
				isScroll: true, //是否允许滚动显示
				isRecon: false, //是否正在获取未接收信息
				currLastMessageId: "", //当前最后一条数据的id
				loadingTimer: {}, //loading
				inputHeigth: 57,
				payMemberPop: false,
				pyshow: false,
				clientWH: {},
			};
		},
		computed: {
			...mapGetters([
				"mqttKey",
				"userInfo",
				"currMqttMsg",
				"reconMqttTime",
				"sysConfig",
				"isLogin",
			]),
				// "currMoreData",
			chatBodyBottom() {
				let boxHeight = this.clientWH.height*0.8*0.68
				if(boxHeight>400){
					return `height:${boxHeight - 55}px`
				} else {
					return `height:345px;`;
				}
			},
			footAvatarBottom() {
				return `bottom:${this.safeBottom + 12}px`;
			},
			footBtnBottom() {
				return `bottom:${this.safeBottom + 10}px`;
			},
		},
		watch: {
			//监视ai有没有返回回答
			currMqttMsg: {
				handler(message) {
					this.setMessageFun(message);
				},
				deep: true,
			},
			reconMqttTime(value) {
				this.searchNotMessage();
			},
			// 聊天窗口的高度
			inputHeigth(value) {
				// #ifdef H5
				this.srcollHeight = this.systemInfo.windowHeight - 45 - value;
				// #endif
				// #ifndef H5
				this.srcollHeight =
					this.systemInfo.screenHeight -
					this.systemInfo.statusBarHeight -
					45 -
					this.safeBottom -
					value;
				// #endif
			},
			isLogin(value) {
				if (value) {
					this.getUserInfoActions()
						.then((res) => {
							/* if(res.data.code===401) */
							if (this.userInfo.stopSendTime) {
								uni.reLaunch({
									url: "/pages/index/index",
								});
							}
							this.getHistoryListFun("one");
							this.isInit = true;
						})
						.catch((rej) => {
							uni.hideLoading();
						});
				}
			},
		},
		onLoad(option) {
			console.log("onload", this.systemInfo.windowHeight);

			//未选择记录隐藏分享按钮
			uni.hideShareMenu();
			if (option.key != undefined) {
				this.currKey = option.key;
			}
		},
		onShow() {
			let inputHeigth = this.inputHeigth;
			this.inputHeigth = 0;
			setTimeout(() => {
				this.inputHeigth = inputHeigth;
			}, 100);

			this.safeBottom =
				this.systemInfo.safeAreaInsets.bottom > 0 ?
				this.systemInfo.safeAreaInsets.bottom :
				0;
			// #ifdef H5
			if (!this.systemInfo.windowTop) {
				this.srcollHeight = this.systemInfo.windowHeight - this.inputHeigth;
			} else {
				this.systemInfo.windowHeight = uni.getSystemInfoSync().windowHeight;
				this.systemInfo.windowHeight =
					this.systemInfo.windowHeight + this.systemInfo.windowTop;
				this.srcollHeight = this.systemInfo.windowHeight - this.inputHeigth;
			}
			// #endif
			// #ifndef H5
			this.srcollHeight =
				this.systemInfo.screenHeight -
				this.systemInfo.statusBarHeight -
				45 -
				this.safeBottom -
				this.inputHeigth;
			// #endif
			if (this.isInit) {
				this.getUserInfoActions();
			}
			uni.hideTabBar({
				animation: false,
			});
		},
		beforeDestroy() {
			window.removeEventListener('resize',this.resizeFun)
		},
		mounted() {
			uni.showLoading({
				title: "加载中",
				mask: true,
			});
			this.clientWH = {
				width: document.body.clientWidth,
				height: document.body.clientHeight
			}
			window.addEventListener('resize',this.resizeFun)
			//获取用户信息，获取失败说明未登录或者token过期，跳转至index
			if (this.isLogin) {
				this.getUserInfoActions()
					.then((res) => {
						/* if(res.data.code===401) */
						if (this.userInfo.stopSendTime) {
							uni.reLaunch({
								url: "/pages/index/index",
							});
						}
						this.getHistoryListFun("one");
						this.isInit = true;
					})
					.catch((rej) => {
						console.log("失败");
						uni.hideLoading();
					});
			} else {
				uni.hideLoading();
				uni.showToast({
					icon: "none",
					title: "请先登录",
				});
			}
		},
		methods: {
			...mapActions(["getUserInfoActions"]),
			resizeFun(){
				this.clientWH.width= document.body.clientWidth
				this.clientWH.height= document.body.clientHeight
				console.log('aaa==',this.clientWH)
			},
			//根据时间是否显示判断加上active类
			showTime(time, index) {
				if (!time) {
					return false;
				}
				var last_index = index - 1;
				if (last_index >= 0) {
					var last_time = this.msgList[last_index]["message_time"];
					var difference =
						new Date(time).getTime() - new Date(last_time).getTime();
					if (difference / 10 < 7200) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}
			},
			//发送问题
			async sendIssueFun() {
				console.log('type==', uni.getSystemInfoSync())
				if (!this.isLogin) {
					return false;
				}
				//判断用户信息是否填写完整
				let isUserInfo = true;
				if (!this.userInfo.wxName) {
					isUserInfo = false;
				}
				if (
					this.sysConfig.switch_phone === "true" &&
					this.sysConfig.login_phone_flag !== "all" &&
					!this.userInfo.phone
				) {
					isUserInfo = false;
				}
				if (!isUserInfo) {
					uni.navigateTo({
						url: `/pages/info/info?type=openInfo`,
					});
					return false;
				}
				//判断是否在等待回复
				let msg = this.msg;
				if (msg === "" || this.isSend || this.isWaiting) {
					return false;
				}
				//判断次数是否用完
				if (
					this.sysConfig.question_num_flag == "true" &&
					this.userInfo.questionCou <= 0 &&
					!this.userInfo.memberFlag
				) {
					this.$refs.moreChatNum.questionShow = true;
					// uni.showToast({
					// 	title: '次数已用完，请前往app获取更多次数!',
					// 	icon: 'none',
					// 	duration: 4000
					// })
					this.getUserInfoActions();
					return false;
				}
				//发送
				this.isSend = true;
				this.msg = "";
				let id = new Date().getTime();
				this.msgList.push({
					message_time: this.getTimeFun(),
					message_type: "q",
					view_type: "text",
					message_content: msg,
					id,
				});
				console.log(this.msgList);
				setTimeout(() => {
					this.setScrollIndexFun();
				}, 30);
				//将发送的问题，发送给后端，后端进行校验，将校验结果返回给前端，前端将结果进行展示
				sendIssueApi({
						question: msg,
						modelType: this.currKey,
						startMessageId: this.pageObj.statId,
					})
					.then((res) => {
						let data = res.data;
						console.log("====>发送的问题", data);
						this.msgList = this.msgList.map((msgItem) => {
							//将用户输入的问题（后端处理之后）,其他属性添加上
							if (msgItem.id == id) {
								msgItem = {
									...data[0],
								};
							}
							return msgItem;
						});
						if (data.length >= 2) {
							//发送的问题包含敏感词，向用户展示你的问题包含敏感词，当作ai回答展示
							this.msgList.push(data[1]);
							this.currLastMessageId = data[1].id;
							this.pageObj.total = this.pageObj.total + 2;
							setTimeout(() => {
								this.setScrollIndexFun();
							}, 30);
							this.isWaiting = false;
							this.isSend = false;
						} else {
							//正常发送
							let currDataId = data[0].id;
							this.currLastMessageId = currDataId;
							//添加等待loading
							this.msgList.push({
								message_time: data[0].message_time,
								message_type: "a",
								view_type: "loading",
								message_content: "",
								id: new Date().getTime(),
								pid: currDataId,
								loading_text: this.sysConfig.ai_doing_message ?
									this.sysConfig.ai_doing_message : "Ai正在回复中,请稍等",
								loading_state: "1",
							});
							this.pageObj.total++;
							//30秒后更换提示并且放开输入
							let loadingNum = 0;
							this.loadingTimer["chat_" + currDataId] = setTimeout(() => {
								this.msgList = this.msgList.map((item) => {
									if (item.view_type == "loading" && item.pid == currDataId) {
										item.loading_text = this.sysConfig.ai_doing_longtime_message ?
											this.sysConfig.ai_doing_longtime_message :
											"Ai正在思考中，请稍等7777";
										item.loading_state = "2";
									}
									if (item.view_type == "loading" && item.loadin_status == "1") {
										loadingNum++;
									}
									return item;
								});
								this.isWaiting = false;
							}, 30000);
							setTimeout(() => {
								this.setScrollIndexFun();
							}, 30);
							this.pageObj.total++;
							this.isWaiting = true;
							this.isSend = false;
						}
						//重新获取次数
						this.getUserInfoActions().then(() => {
							if (this.userInfo.stopSendTime) {
								uni.reLaunch({
									url: "/pages/index/index",
								});
							}
						});
					})
					.catch(() => {
						this.msgList = this.msgList.filter((item) => item.id != id);
						this.isSend = false;
					});
			},
			scrollTouchstartFun(event) {
				this.isScroll = false;
			},
			getTimeFun() {
				let date = new Date();
				let year = date.getFullYear(); //年
				let month = date.getMonth() + 1; //月
				let day = date.getDate(); //日
				let hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours(); //时
				let minute =
					date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes(); //分
				let second =
					date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds(); //秒
				return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
			},
			//滚动到顶部触发加载历史信息
			getTopDataFun() {
				if (this.isGetData) {
					return false;
				}
				if (this.pageObj.total == this.msgList.length) {
					this.loadinStatus = "nomore";
					return false;
				}
				this.loadinStatus = "loading";
				this.isGetData = true;
				this.pageObj.current++;
				this.getHistoryListFun();
			},
			//获取历史信息
			getHistoryListFun(type) {
				let data = {
					current: this.pageObj.current,
					size: this.pageObj.size,
					modelType: this.currKey,
				};
				if (type != "one") {
					data.startNum = this.pageObj.statId;
				}
				getHistoryListApi(data).then((res) => {
					let data = res.data;
					data.records.forEach((item) => {
						this.$set(item, "checked", undefined);
					});
					this.pageObj.total = data.total;
					this.msgList = [...data.records, ...this.msgList];

					if (this.pageObj.total == this.msgList.length) {
						this.loadinStatus = "nomore";
					} else {
						this.loadinStatus = "loadmore";
					}
					if (this.msgList.length > 0) {
						let endId = this.msgList[data.records.length - 1].id;
						if (type != "one") {
							endId = this.msgList[data.records.length].id;
						}
						setTimeout(()=>{this.scrollIntoID = "chat_" + endId;},30)
						if (type == "one") {
							this.pageObj.statId = endId;
							this.currLastMessageId = endId;
							let currMsgList = uni.$u
								.deepClone(this.msgList)
								.reverse()
								.filter((item) => {
									if (item.message_type == "q") {
										return false;
									}
									return true;
								});
						}
					} 
					// else {
					// 	if (type == "one" && this.currKey == this.currMoreData.val) {
					// 		this.tipObj.msgList = [{
					// 				message_type: "q",
					// 				view_type: "text",
					// 				message_content: this.currMoreData.example_q,
					// 				id: new Date().getTime(),
					// 			},
					// 			{
					// 				message_type: "a",
					// 				view_type: "text",
					// 				message_content: this.currMoreData.example_a,
					// 				id: new Date().getTime() + "asd",
					// 			},
					// 		];
					// 		this.tipObj.my = this.tipObj.msgList[0];
					// 		this.tipObj.ai = this.tipObj.msgList[1];
					// 		this.showTip = true;
					// 	}
					// }
					if (type == "one") {
						uni.hideLoading();
						setTimeout(() => {
							this.setScrollIndexFun();
						}, 500);
					}
					this.isGetData = false;
				});
				uni.hideLoading();
			},
			//ai返回回答，将回答展示出来
			setMessageFun(messgData) {
				let message = uni.$u.deepClone(messgData);
				if (message.message_type == "a") {
					if (this.loadingTimer["chat_" + message.pid]) {
						//如果在三十秒之前ai已经回答完毕则关闭展示ai正在思考的定时器
						clearTimeout(this.loadingTimer["chat_" + message.pid]);
					}
					let isAddMessage = false;
					let currPidIndex = "";
					this.isWaiting = false;
					this.isScroll = true;
					this.msgList = this.msgList.map((item, index) => {
						if (item.id == message.pid) {
							currPidIndex = index;
						}
						if (item.pid == message.pid) {
							isAddMessage = true;
							item = {
								...message,
								linkOpen: this.userInfo.viewModel == "1",
							};
						}
						return item;
					});
					if (!isAddMessage) {
						this.msgList.splice(currPidIndex + 1, 0, {
							...message,
						});
					}
					let lastData = this.msgList[this.msgList.length - 1];
					if (lastData.view_type == "loading") {
						this.currLastMessageId = lastData.pid;
					} else {
						this.currLastMessageId = lastData.id;
					}
					this.setScrollIndexFun();
				}
			},
			//查询未接收的信息
			searchNotMessage() {
				if (this.isRecon || !this.currLastMessageId) {
					return false;
				}
				this.isRecon = true;
				let startMessageId = "";
				this.msgList.forEach((item) => {
					if (item.view_type == "loading" && !startMessageId) {
						startMessageId = item.pid;
					}
				});
				if (!startMessageId) {
					startMessageId = this.currLastMessageId;
				}
				getLastMessageApi({
						modelType: this.currKey,
						startNum: this.currLastMessageId,
					})
					.then((res) => {
						console.log("未接收的信息", res.data);
						if (res.data.length > 0) {
							res.data.forEach((item) => {
								this.setMessageFun(item);
							});
						}
						this.isRecon = false;
					})
					.catch(() => {
						this.isRecon = false;
					});
			},
			setScrollIndexFun(data = {}) {
				if (data.type == "detail" && !this.isScroll) {
					return false;
				}
				this.scrollLastID = `chat_${new Date().getTime()}`;
				setTimeout(() => {
					this.scrollIntoID = this.scrollLastID;
				}, 200);
			},
			verifyMemberFun() {
				if (this.sysConfig.switch_pay === "true") {
					return this.userInfo.memberFlag;
				} else {
					return true;
				}
			},
			openPayMember() {
				uni.showToast({
					title: "请开通VIP后使用本功能",
					icon: "none",
					duration: 2000,
				});
				setTimeout(() => {
					this.payMemberPop = true;
				}, 500);
			},
			skipPageFun(type) {
				uni.navigateTo({
					url: `/pages/${type}/${type}`,
				});
			},
			payClose() {
				this.payMemberPop = false;
			},
			translatePageTop(h) {
				// #ifndef  APP-NVUE
				var scrollAnimation = uni.createAnimation({
					duration: 220,
					timingFunction: "ease",
				});

				var footAnimation = uni.createAnimation({
					duration: 220,
					timingFunction: "ease",
				});
				if (h == 0) {
					scrollAnimation.translateY(-h).step();
					footAnimation.translateY(-h).step();
				} else {
					scrollAnimation.translateY(-h + 34).step();
					footAnimation.translateY(-h + 34).step();
				}

				this.scrollAnimationData = scrollAnimation.export();
				this.footAnimationData = footAnimation.export();

				// #endif

				// #ifdef  APP-NVUE
				let foot = this.getEl(this.$refs.foot);
				let scrollview = this.getEl(this.$refs.scrollview);
				h = h - this.safeBottom;
				animation.transition(
					scrollview, {
						styles: {
							transform: "translateY(-" + h + ")",
						},
						duration: 220, //ms
						timingFunction: "linear",
						needLayout: false,
						delay: 0, //ms
					},
					function() {}
				);
				animation.transition(
					foot, {
						styles: {
							transform: "translateY(-" + h + ")",
						},
						duration: 220, //ms
						timingFunction: "linear",
						needLayout: false,
						delay: 0, //ms
					},
					function() {}
				);
				// #endif
			},
			getEl(el) {
				if (typeof el === "string" || typeof el === "number") return el;
				if (WXEnvironment) {
					return el.ref;
				} else {
					return el instanceof HTMLElement ? el : el.$el;
				}
			},
			//隐藏键盘
			hideKey() {
				uni.hideKeyboard();
				this.inputFocus();
			},
			inputFocus() {
				var that = this;

				// #ifndef  APP-NVUE
				if (this.showMore) {
					let move = uni.upx2px(180);
					var menuAnimation = uni.createAnimation({
						duration: 220,
						timingFunction: "ease",
					});
					menuAnimation.translateX(0).step();

					this.menuAnimationData = menuAnimation.export();
					this.showMore = false;
				}
				// #endif

				// #ifdef  APP-NVUE
				if (this.showEmoji) {
					let main_chatDetail = this.getEl(this.$refs.chatDetail);
					let main_foot = this.getEl(this.$refs.foot);
					uni.hideKeyboard();
					this.hideEmojiPanel();
				} else {
					this.adjustPosition = true;
				}
				if (this.showMore) {
					let moreMenu = this.getEl(this.$refs.moreMenu);
					animation.transition(
						moreMenu, {
							styles: {
								transform: "translateX(0)",
							},
							duration: 400, //ms
							timingFunction: "linear",
							needLayout: false,
							delay: 0, //ms
						},
						function(res) {
							that.scrollBottom();
						}
					);
					this.showMore = false;
				}
				// #endif
			},
			setinputHeight(event) {
				this.inputHeigth = 37 + event.detail.height;
			},
		},
	};
</script>

<style lang="scss" scoped>
	.chat-pc {
		width: 100%;
		height: 76%;
		position: relative;
		left: 0;
		top: 0;

		.chat-area {
			min-width: 760px;
			min-height:400px;
			position: absolute;
			left: 20px;
			right: 20px;
			top: 25px;
			bottom: 25px;
			padding: 0 5px;
			box-sizing: border-box;

			.chat-box {
				background-color: #f4f4f4;
				height: 100%;
				border-radius: 5px;

				.chat-foot-box {
					position: relative;
					display: flex;
					align-items: flex-start;
					justify-content: center;
					border-top: 1px solid #e9e9e9;
					padding: 8px 6px;

					/deep/.u-textarea {
						margin: 0 10px;
						padding: 0;

						.u-textarea__field {
							padding: 9px;
						}
					}

					.dh-input {
						font-size: 15px;
						min-height: 30px;
						max-height: 140px;
						overflow: scroll;
						// flex: 1;
						border-radius: 20px;
						padding: 0 6px;
						background-color: #ffffff;
						margin: 0 6px;
						border: 1px solid #ededed;
					}

					.foot-btn {
						height: 37px;
						width: 70px;
						border-radius: 20px;
						text-align: center;
						line-height: 37px;
						background-color: #2b70f9;
						color: #fff;
						font-size: 16px;
						opacity: 0.4;

						&.active {
							opacity: 1;
						}
					}
				}
			}
		}
	}
</style>