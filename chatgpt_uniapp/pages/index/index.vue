<template>
	<view class="box">
		<view class="container">
			<view class="iframe">
				<loginpc v-if="!isLogin"/>
				<chatpc v-if="isLogin"/>
			</view>
			<view class="content">
				<view class="h1">
					<img src="../../static/ai_avatar.png"></img>超级AI大脑PC开源
				</view>
				<view class="member-box" v-if="isLogin">
					<u-button type="primary" text="vip充值" :customStyle="{width:'120px'}" @click="openMember"></u-button>
				</view>
			</view>
		</view>
		<u-popup
		  :show="payMemberPop"
		  mode="center"
		  :closeable="true"
		  :closeOnClickOverlay="false"
		  @close="payMemberPop = false"
		  round="5"
		  :customStyle="{width:'400px', padding:'10px'}"
		>
		  <view class="pay-member-title"> 开通VIP </view>
		  <member-pay-pc @pay-close="payClose"></member-pay-pc>
		</u-popup>
	</view>
</template>

<script>
	import loginpc from "../subPack/loginpc.vue";
	import chatpc from "../subPack/chatpc.vue";
	import {
		mapGetters,
	} from 'vuex';
	import MemberPayPc from "@/components/member-pay-pc.vue";
	export default {
		components:{loginpc,chatpc,MemberPayPc},
		data() {
			return {
				payMemberPop: false,
			};
		},
		computed: {
			...mapGetters(['isLogin']),
		},
		mounted() {},
		methods:{
			payClose() {
				this.payMemberPop = false;
			},
			openMember(){
				this.payMemberPop = true
			},
		}
	}
</script>

<style lang="scss" scoped>
	.box{
		width: 100vw;
		height: 100vh;
		background-image: url(../../static/pc/pc-bg.png);
		
		.container{
			width: 100%;
			height: 100%;
			min-height: 690px;
			display: flex;
			justify-content: center;
			align-items: center;
			margin: auto;
			position: fixed;
			z-index: 1;
			top: 0;
			right: 0;
			bottom: 0;
			left: 0;
			.iframe{
				min-width: 812px;
				min-height:600px;
				width: 60%;
				height: 80%;
				background-image: url(../../static/pc/iMac.png);
				background-size: 100% 100%;	
				background-repeat: no-repeat;
				background-position: center;
			}
			.content{
				color: #2b88f6;
				margin-left: 60px;
				width: 400px;
				.h1{
					font-size: 34px;
					font-weight: bold;
					padding: 30px 0;
					display: flex;
					align-items: center;
					img{
						display: block;
						height: 48px;
						width: 48px;
						border-radius: 50%;
						margin-right: 10px;
					}
				}
				.tips{
					font-size: 14px;
					margin: 0;
					padding: 4px 0 0 0;
					text-align: center;
					width: 260px;
					color: #42b983;
				}
				
				.qrcode-box{
					border: #42b983 solid 2px;
					padding: 5px;
					box-sizing: content-box;
					width: 260px;
				}
				.member-box{
					margin: 20px 0;
					width: 350px;
				}
			}
		}
	}

</style>

