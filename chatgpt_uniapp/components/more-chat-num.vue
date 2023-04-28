<template>
	<view class="more-chat-num">
		<u-modal :show="questionShow" :title="title" :content='content' :closeOnClickOverlay="true" @close="questionShow=false" :showConfirmButton="false">
			<view class="content">
				<view class="content-text">
					<view class="text-item" v-for="(item,index) in textList" :key="index">
						<view class="text">
							{{index+1}}、{{item.text}}
						</view>
						<view class="tip" v-if="item.tip">
							{{item.tip}}
						</view>
					</view>
				</view>
			</view>
			<view slot="confirmButton" class="content-btn">
				<u-button :customStyle="item.customStyle" :openType="item.openType" class="btn" v-for="(item,index) in btnList" :key="index" :type="item.type" @click="clickFun(item)">{{item.text}}</u-button>
			</view>
		</u-modal>
	</view>
</template>

<script>
	import {
		mapGetters,
	} from 'vuex'
	export default {
		name: "more-chat-num",
		props: {
			pageName: String,
			title:{
				type:String,
				default:'获取更多次数'
			},
			advice:{
				type: String,
				default:'not-pc'
			},
		},
		data() {
			return {
				questionShow: false,
				content:'',
			};
		},
		computed: {
			...mapGetters(['userInfo', 'sysConfig']),
			textList() {			
				let textList = []
				if (this.sysConfig.switch_pay == 'true') {
					textList = [{
							text: '开通VIP享无限次',
						},
						...textList
					]
				}
				return textList
			},
			btnList() {
				let type = ['primary', 'success']
				
				let btnList = []
				if (this.sysConfig.switch_pay == 'true') {
					btnList = [{
							text: '开通VIP',
							code: 'cwhy',
							type: '',
							openType: ''
						},
						...btnList
					]
				}
				btnList = btnList.map((item, index) => {
					if (index != btnList.length - 1) {
						item.customStyle = {
							marginRight: '20rpx'
						}
					} else {
						item.customStyle = {}
					}
					item.type = type[index]
					return item
				})
				return btnList
			}
		},
		methods: {
			clickFun(data) {
				if (data.code == 'cwhy') {
					this.questionShow = false
					this.$emit('set-vip-fun')
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.more-chat-num {
		.content {
			width: 100%;

			.content-text {
				padding: 12px;
				font-size: 28rpx;

				.text-item {
					display: flex;
					align-items: center;
					flex-wrap: wrap;

					.tip {
						font-size: 24rpx;
					}
				}
			}
		}

		.content-btn {
			display: flex;
			align-items: center;

			button {
				flex: 1;
				border: none !important;
			}
		}
	}
</style>
