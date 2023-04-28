<template>

	<view id="chatDetail" @click="hide" ref="chatDetail" class="flex-column-start">
		<view style="margin-bottom: 15px;">
			<view class="chat-time" v-if="showTime(item.message_time,index)&&!isPreview">{{showTime(item.message_time,index)}}</view>
			<view style="display:flex;padding:5px 10px 0" :class="{
					'nv-rowever':item.message_type=='q',
					'nv-row':item.message_type!='q',
				}">
				<image v-if="item.message_type=='q'" class="chat-img" :src="userInfo.wxAvatar?userInfo.wxAvatar:dfAvatar" mode="aspectFill">
				</image>
				<image v-else class="chat-img" src="../../static/ai_avatar.png" mode="aspectFill"></image>

				<view style="flex-direction: row;justify-content: center;align-items: center;">
					<view class="nv-chat" style="flex-direction: column;flex-wrap:wrap;">
						<view :class="item.message_type=='q'?'margin-right nv-rowever':'margin-left nv-row'" :style="'border-radius: 5px;align-items: center;margin-left:10px;border: 1rpx solid #f0f0f0;'" v-if="item.view_type == 'image'">
							<image :lazy-load="true" :src="item.message_content" @click="previewImage(item.message_content)" mode="widthFix" style="width: 130px;border-radius: 5px;"></image>
						</view>
						<view class="detail-loading margin-left gr-bg nv-row" v-else-if="item.view_type =='loading'">
							<view class="loading-text" style="padding-right: 5px;">
								{{item.loading_text?item.loading_text:'请稍等'}}
							</view>
							<view class="loading">
								<loading-view-pc :size="10"></loading-view-pc>
							</view>
						</view>
						<view class="error-box margin-left gr-bg nv-row padding-chat text_value" v-else-if="item.view_type=='error'" :style="'border-radius: 5px;align-items: center;'">
							<text :style="'font-size: 16px;color:red;letter-spacing: 1px;word-wrap: break-word;word-break: break-all;max-width:'+chatDetailBoxMaxWidth+'px'">
								{{item.message_content}}
							</text>
						</view>
						<view v-else :class="item.message_type=='q'?'margin-right white-bg nv-rowever':'margin-left gr-bg nv-row'" class="padding-chat text_value" :style="'border-radius: 5px;align-items: center;'">
							<text :style="'font-size: 16px;letter-spacing: 1px;word-wrap: break-word;word-break: break-all;max-width:'+chatDetailBoxMaxWidth+'px'">
								{{currText}}
							</text>
							<view class="operation-btn" v-if="item.message_type=='a' && isBtn && !isPreview">
								<view class="btn-item" @click="operationBtnFun('copy')">
									<image class="btn-item-icon" src="../../static/chat-copy.png"></image>
									<view class="btn-item-text">
										复制
									</view>
								</view>
								<view class="btn-item" @click="operationBtnFun('fy')">
									<image class="btn-item-icon" src="../../static/chat-fy.png"></image>
									<view class="btn-item-text">
										Ai翻译
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="content-fy-box" v-if="isFy">
				<view class="fy-box">
					<view class="nv-chat">
						<view class="padding-chat text_value margin-left gr-bg nv-row" style="border-radius: 5px;align-items: center;margin-left: 94rpx;">
							<text class="fy-text" :style="'max-width: 280px'">
<!-- 							<text class="fy-text" :style="'max-width:'+chatDetailBoxMaxWidth+'px'"> -->
								{{fyObj.translate_after?fyObj.translate_after:''}}
							</text>
							<view class="fy-loading">
								<view class="loading" v-if="fyLoading=='loading'">
									<view class="text">
										翻译中
									</view>
									<loading-view-pc :size="14" color="#c4c6c8"></loading-view-pc>
								</view>
								<view class="loading-succer" v-else-if="fyLoading=='success'">
									<u-icon name="checkmark-circle-fill" size="12" color="#c4c6c8"></u-icon>
									<view class="text">
										翻译完成
									</view>
								</view>
								<view class="loading-succer" v-else-if="fyLoading=='error'">
									<u-icon name="close-circle-fill" size="12" color="#c4c6c8"></u-icon>
									<view class="text">
										翻译失败
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<lang-switch ref="langSwitch" @set-lang="aiTranslateFun"></lang-switch>
	</view>
</template>

<script>
	import {
		formatChatTime
	} from '../../common/utils.js'
	import {
		mapState,
		mapGetters
	} from 'vuex'
	import {
		translateMessageApi
	} from '@/api/user.js'
	import LoadingViewPc from "@/components/loading-view-pc.vue"
	import LangSwitch from '@/components/lang-switch.vue'
	import {
		setTimeout
	} from '../../common/mqtt.min.js'
	export default {
		name:"uniChatDetailPc",
		components: {
			LoadingViewPc,
			LangSwitch
		},
		props: {
			item: {
				type: Object,
				default: {}
			},
			index: {
				type: Number,
				default: 0
			},
			list: {
				type: Array,
				default: []
			},
			setScrollIndexFun: {
				type: Function,
			},
			isPreview: {
				type: Boolean,
				default: false
			},
			dfAvatar:String,
		},
		data() {
			return {
				chatDetailBoxMaxWidth: 0,
				systemInfo: uni.getSystemInfoSync(),
				currText: '',
				isBtn: false,
				isFy: false,
				fyLoading: '',
				fyObj: {}, //翻译obj
				loadingAd: '', //等等回复的广告
				adTimer: null,
				isAdError: false,
				textTimer: null,
			};
		},
		watch: {
			isAdError(value) {
				if (value && this.adTimer) {
					clearInterval(this.adTimer)
				}
			},
			item: {
				handler(value) {
					this.initFun()
				},
				deep: true,
				immediate: true,
			}

		},
		computed: {
			...mapGetters(['sysConfig', 'userInfo']),
		},
		created() {
			this.chatDetailBoxMaxWidth = this.systemInfo.screenWidth - 110;
		},
		mounted() {},
		methods: {
			initFun() {
				if (this.textTimer) {
					return false
				}
				if (this.item.view_type == 'text') {
					let text = ''
					text = this.item.message_content.replace(/(\r\n|\n|\r)/gm, '\n')
					text = text.replace(/\n\n/gm, '\n')
					if (text.indexOf('\n') == 0) {
						text = text.substring(1)
					}
					if (this.item.linkOpen) {
						this.currText = ''
						text = text.split('')
						let index = 0
						let currHeight = 0
						this.textTimer = setInterval(() => {
							if (index >= text.length - 1) {
								clearInterval(this.textTimer)
								this.textTimer = true
								this.isBtn = true
							}
							this.currText = this.currText + text[index]
							index++
							this.$emit('set-scroll-index', {
								type: 'detail'
							})
						}, 60)
					} else {
						this.currText = text
						this.isBtn = true
					}

				}
			},
			formatChatTime(time) {
				return formatChatTime(time.replace(/-/g, "/"))
			},
			hide() {
				uni.hideKeyboard()
			},
			showTime(time, index) {
				if (!time) {
					return false
				}
				var last_index = index - 1;
				if (last_index >= 0) {
					var last_time = this.list[last_index]['message_time'];
					var difference = (new Date(time).getTime() - new Date(last_time).getTime());
					if ((difference / 10) < 7200) {
						return false;
					} else {
						return this.formatChatTime(time)
					}
				} else {
					return this.formatChatTime(time)
				}
			},
			previewImage(image) {
				let images = [];
				images.push(image)
				uni.previewImage({
					urls: images,
					longPressActions: {
						itemList: ['发送给朋友', '保存图片', '收藏'],
						success: function(data) {
							console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
						},
						fail: function(err) {
							console.log(err.errMsg);
						}
					}
				});
			},

			operationBtnFun(type) {
				if (type == 'copy') {
					let issueData = {}
					this.list.forEach(item => {
						if (item.id == this.item.pid) {
							issueData = item
						}
					})
					let copyText = `问题：${issueData.message_content}回答：${this.currText}`
					if (this.isFy && this.fyObj.translate_after) {
						copyText = `${copyText}翻译（${this.fyObj.target_lang}）：${this.fyObj.translate_after}`
					}
					uni.setClipboardData({
						data: copyText, //要被复制的内容
						success: () => { //复制成功的回调函数
							uni.showToast({ //提示
								title: '复制成功'
							})
						}
					});
				} else if (type == 'fy') {
					if (this.fyLoading == 'loading') {
						return false
					}
					this.$refs.langSwitch.langShow = true
				} 
			},
			aiTranslateFun(data) {
				this.isFy = true
				this.fyLoading = 'loading'
				translateMessageApi(this.item.id).then(res => {
					if (res.data && res.data.translate_after) {
						this.fyObj = res.data
						this.fyLoading = 'success'
					} else {
						this.fyLoading = 'error'
					}
				}).catch(() => {
					this.fyLoading = 'error'
				})
			}
		},
		beforeDestroy() {
			if (this.adTimer) {
				clearInterval(this.adTimer)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.operation-btn {
		font-size: 28rpx;
		color: #2B4079;
		display: flex;
		align-items: center;
		padding-top: 10rpx;

		.btn-item {
			display: flex;
			align-items: center;
			margin-left: 10px;

			&:nth-child(1) {
				margin-left: 0;
			}
		}

		.btn-item-icon {
			width: 17px;
			height: 17px;
		}

		.btn-item-text {
			font-size: 12px;
			padding-left: 3px;
		}
	}

	.record {
		position: fixed;
		bottom: 150px;
		width: 350rpx;
		left: 200rpx;
		height: 350rpx;
		background-color: #4c4c4c;
		border-radius: 20rpx;
		justify-content: center;
		align-items: center;

	}

	.tis {
		width: 100%;
		height: 10vw;

		justify-content: center;
		font-size: 24rpx !important;
		color: white !important;
	}

	.ing {
		width: 100%;

	}

	.voice-icon {

		font-size: 120rpx;
		color: #f09b37;
		// color: white;
	}

	.header-dh {
		position: fixed;
		justify-content: flex-end;
		padding-bottom: 15rpx;
		width: 750rpx;
		background-color: #f1f1f1;
		z-index: 20;
	}

	.padding-chat {
		padding: 8px 9px;
	}

	.margin-right {
		margin-right: 10px;
	}

	.margin-left {
		margin-left: 10px;
	}

	.padding-left {
		padding-left: 10px;
	}

	.flex-column-start {
		flex-direction: column;
		justify-content: center;
	}

	.chat-img {
		border-radius: 8px;
		width: 37px;
		height: 37px;
		flex: 0 0 37px;
		background-color: #FFFFFF;
	}

	.flex-row-around {
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
	}

	.dh-input {
		font-size: 30rpx;
		width: 280px;
		height: 35px;
		border-radius: 5px;
		padding-left: 15rpx;
		background-color: #FFFFFF;
	}

	.wid {
		width: 240px;
	}


	.flex-row-around {
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
		flex-wrap: wrap;
	}

	.flex-column-center {
		flex-direction: column;
		justify-content: center;
		align-items: center;
		position: fixed !important;
		background-color: #f3f3f3;
		border-radius: 5px;
		z-index: 9999999;
	}

	.padding-top {
		padding-top: 5px;
	}

	.padding-right {
		padding-right: 10px;
	}

	.justify-end {
		flex-direction: row-reverse;
	}

	.nv-chat {
		flex-wrap: wrap-reverse;
	}

	.nv-row {
		flex-direction: row
	}

	.nv-rowever {
		flex-direction: row-reverse
	}

	.white-bg {
		background-color: #2B70F9;
		color: #fff;
	}

	.gr-bg {
		background-color: #ffffff;
	}

	.more-layer {
		height: 220rpx;
		border-top-width: 2rpx;
		border-top-color: #e6e5e8;
	}

	.emoji-layer {
		height: 500rpx;
		width: 750rpx;
		flex-wrap: wrap;
		border-top-width: 2rpx;
		border-top-color: #e6e5e8;
		// position: absolute;
	}


	.chat-time {
		text-align: center;
		padding-top: 9px;
		font-size: 14px;
		color: #8f8f8f;
		margin-bottom: 10px;
	}


	.ing {
		top: 25px;
		position: absolute;
		text-align: center;
		justify-content: center;
		align-items: center;
	}

	.detail-loading {
		text-align: left;
		padding: 9px 20px;
		text-align: initial;
		border-radius: 5px;
		.loading-text {
			font-size: 15px;
			display: inline-block;
		}
		.loading{
			display: inline-block;
			width: 50px;
		}
	}

	.content-fy-box {
		padding: 5px 10px 0;
		box-sizing: border-box;
		display: flex;
		flex-direction: row;

		.fy-box {
			flex-direction: row;
			justify-content: center;
			align-items: center;
		}

		.fy-text {
			font-size: 30rpx;
		}

		.fy-loading {
			color: #c4c6c8;
			font-size: 20rpx;

			.loading,
			.loading-succer {
				display: flex;
				align-items: center;
			}

			.loading {
				.text {
					padding-right: 8rpx;
				}
			}

			.loading-succer {
				.text {
					padding-left: 8rpx;
				}
			}
		}
	}
</style>