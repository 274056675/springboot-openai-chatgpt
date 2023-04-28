<template>

	<u-popup :show="langShow" mode="center" :round="20" :closeOnClickOverlay="true" @close="langShow=false" :customStyle="{width:'450px'}">
		<view class="lang-switch-box">
			<view class="input-box">
				<view class="label">
					当前语言
				</view>
				<u-input class="weui-input" inputAlign="center" placeholder="如：中文" border="bottom" v-model="currLang" />
			</view>
			<view class="icon-box" @click="swapFun">
				<u-icon :name="icon" size="16"></u-icon>
			</view>
			<view class="input-box">
				<view class="label">
					翻译语言
				</view>
				<u-input class="weui-input" inputAlign="center" placeholder="如：英文" border="bottom" v-model="tranLang" />
			</view>
		</view>
		<view class="btn-box">
			<view class="btn" @click="langShow=false">取消</view>
			<view class="btn" @click="submitFun">确认</view>
		</view>
	</u-popup>

</template>

<script>
	import { mapGetters } from 'vuex'
	export default {
		name: "lang-switch",

		data() {
			return {
				langShow: false,
				icon: require('../static/lang_switch.png'),
				currLang: '中文',
				tranLang: '英文',
			};
		},
		computed: {
			...mapGetters(['settingObj'])
		},
		watch: {
			langShow(value) {
				if (value) {
					let lang = this.settingObj.translate_lang
					if (lang.indexOf(' → ') != -1) {
						lang = lang.split(' → ')
						this.currLang = lang[0]
						this.tranLang = lang[1]
					} else {
						this.currLang = '中文'
						this.tranLang = '英文'
					}
				}
			}
		},
		methods:{
			submitFun(){
				if(!this.currLang){
					uni.showToast({
						title: '请填写当前语言',
						icon: 'none',
						duration: 2000
					});
					return false
				}
				if(!this.tranLang){
					uni.showToast({
						title: '请填写翻译语言',
						icon: 'none',
						duration: 2000
					});
					return false
				}
				this.$emit('set-lang',{
					currLang:this.currLang,
					tranLang:this.tranLang
				})
				this.langShow=false
				
			},
			swapFun(){
				let lang=this.currLang
				this.currLang=this.tranLang
				this.tranLang=lang
			}
		},
	}
</script>

<style lang="scss" scoped>
	.lang-switch-box {
		display: flex;
		justify-content: center;
		align-items: flex-end;
		padding: 60rpx 40rpx;

		.icon-box {
			padding: 0 20rpx;
			margin-bottom: 10rpx;
		}

		.input-box {
			.label {
				font-size: 28rpx;
				text-align: center;
				color: #999;
			}

			.weui-input {
				text-align: center;
			}
		}
	}

	.btn-box {
		display: flex;
		align-items: center;
		padding: 30rpx 0;
		font-size: 32rpx;
		border-top: 1rpx solid #f1f1f1;

		.btn {
			flex: 1;
			text-align: center;
			font-size: 32rpx;

			&:nth-child(2) {
				color: #2D4375;
			}
		}
	}
</style>
