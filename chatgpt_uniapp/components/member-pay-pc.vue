<template>
	<view>
		<view class="member-pay-box">
			<view class="pay-list">
				<view class="list-item" v-for="(item,index) in goodsData" :key="index" @click="setCurrGoodsFun(item)" :class="item.id==currGoods.id?'active':''">
					<view class="item-tip" v-if="item.top_tip">
						<view class="tip">
							{{item.top_tip}}
						</view>
					</view>
					<view class="item-content">
						<view class="name">
							{{item.title}}
						</view>
						<view class="dis-money">
							<view class="money-type">
								¥
							</view>
							<view class="money-num">
								{{item.price}}
							</view>
						</view>
						<view class="rqw-money">
							¥{{item.cost_price}}
						</view>
						<view class="average" v-if="item.remark">
							{{item.remark}}
						</view>
					</view>
				</view>
			</view>
			<view class="pay-type">
				<view class="type-title">
					支付方式
				</view>
				<view class="type-list">
					<view class="type-list-item">
						<image src="../static/pay-logo-text.png"></image>
					</view>
				</view>
			</view>
			<u-button text="立即支付" type="primary" @click="getPayData"></u-button>
		</view>
		
		<u-popup 
		  :show="payCodeShow" 
		  mode="center"
		  :closeable="true"
		  :closeOnClickOverlay="false"
		  @close="payPopClose"
		  round="5"
		  :customStyle="{width:'300px', padding:'30px'}"
		>
			<view class="content">
				<view class="content-title">
					使用微信扫码支付
				</view>
				<view class="content-img">
					<img :src="payData.wx_result">
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import { getGoodsListApi,getPayDataByPc,sendPayByPc,getWxPayOpen,getWxPayState } from '@/api/user.js'
	import { mapActions,mapGetters } from 'vuex'
	export default {
		name:"member-pay-pc",
		data() {
			return {
				goodsData: [],
				currGoods: {},
				payCodeShow: false,
				timer: null,
				payData:{}, // wx_result orderCode
			};
		},
		computed:{
			...mapGetters(['isLogin'])
		},
		watch:{
			isLogin(value){
				if(value){
					this.initFun()
				}
			}
		},
		mounted() {
				this.initFun()
		},
		beforeDestroy() {
			this.timer = null
		},
		methods: {
			...mapActions(['getUserInfoActions']),
			initFun(){
				uni.showLoading({
					title: '加载中',
					mask: true
				})
				getGoodsListApi().then(res => {
					let data = res.data
					this.goodsData = data
					uni.hideLoading()
					if (data.length > 0) {
						this.currGoods = data[0]
					}
				}).catch(()=>{
					uni.hideLoading()
					uni.showToast({
						title: '加载失败!',
						icon: 'none',
					})
					this.$emit('pay-close')
				})
			},
			setCurrGoodsFun(data) {
				this.currGoods = data
			},
			// 支付按钮
			getPayData() {
				//获取微信支付参数
				uni.showLoading({
					title: '拉起支付中...',
					mask: true,
				});
				let params = {
					goodsId: this.currGoods.id,
					type: "qrcode"
				}
				getWxPayOpen(params).then(res=>{
					this.payData = res.data
					uni.hideLoading()
					this.payCodeShow = true
					this.payResultSearch(this.payData.orderCode)
				}).catch(()=>{
					uni.showToast({
						title: '加载失败',
						icon:'none',
					})
					uni.hideLoading()
				})
			},
			// 支付状态查询
			payResultSearch(ord_id) {
				this.timer = null
				getWxPayState(ord_id).then(res=>{
					// true or false
					if(res.data){
						clearTimeout(this.timer)
						this.$emit('pay-close')
						uni.showToast({
							title: '支付成功！',
							icon:'none',
							duration: 3000
						})
					} else {
						this.timer = setTimeout(()=>{
							this.payResultSearch(ord_id)
						},1500)							
					}
				}).catch(()=>{
					clearTimeout(this.timer)
					this.$emit('pay-close')
				})
			},
			payPopClose(){
				this.payCodeShow = false
				clearInterval(this.timer)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.member-pay-box {
		background-color: #fff;
		font-family: SimHei;
		padding: 15px;

		.pay-list {
			display: grid;
			grid-template-columns: repeat(3, 32%);
			justify-content: space-between;
			margin-bottom: 15px;
			grid-gap: 10px 0px;

			.list-item {
				border-radius: 10px;
				border: 2px solid #8496BA;
				padding: 15px 0;
				position: relative;
				.item-tip{
					text-align: center;
					position: absolute;
					left: 0;
					top: -10px;
					width: 100%;
					font-size: 12px;
					display: flex;
					justify-content: center;
					.tip{
						background-color:#FC904D;
						padding: 5px 6px;
						border-radius: 15px;
						color: #fff;
						min-width: 35px;
					}
				}
				.item-content {
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-items: center;
					padding:0 3px;
					text-align: center;

					.name {
						font-size: 16px;
						margin-bottom: 8px;
						color: #304173;
						font-weight: 600
					}

					.dis-money {
						display: flex;
						align-items: flex-end;
						margin-bottom: 12px;
						color: #2D3E7A;
						font-weight: bold;

						.money-type {
							font-size: 16px;
						}

						.money-num {
							font-size: 23px;
						}
					}

					.rqw-money {
						font-size: 14px;
						text-decoration: line-through;
						color: #6A7278;
						margin-bottom: 8px;
						font-size: 500;
					}

					.average {
						font-size: 15px;
						color: #29406A;
						font-size: 500;
					}
				}

				&.active {
					border-color: #3E72E2;
					border-width: 3px;
					background-image: linear-gradient(to bottom,#FCFFFA,#EFF8FF,#CADEFD);

					.name {
						color: #000;
					}

					.dis-money {
						color: #3E6FED;
					}

					.average {
						color: #000;
					}
				}
			}
		}

		.pay-type {
			margin-bottom: 15px;

			.type-title {
				font-size: 16px;
				font-weight: 600;
				color: #000;
				margin-bottom: 10px;
			}

			.type-list-item {
				display: flex;
				align-items: center;
				justify-content: center;
				border: 1px solid #B4CBF7;
				background-image: linear-gradient(to bottom,#FCFFFA,#EFF8FF 30%,#CADEFD);
				width: 150px;
				height: 40px;
				border-radius: 8px;
				
				image{
					width: 60%;
					height: 60%;
				}
			}
		}
	}
	.content{
		text-align: center;

		.content-tip{
			font-size: 14px;
		}
	}
</style>
