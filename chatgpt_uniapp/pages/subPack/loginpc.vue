<template>
	<view class="login-pc">
		<view class="login-area">
			<view class="login-box">
				<view class="page-title">{{ titles.navTitle }}</view>
				<view class="form-head">
					<view>{{ titles.formTitle }}</view>
				</view>
				<u--form ref="loginform" labelPosition="left" labelWidth=60 labelAlign="right" :labelStyle="{color:'#7e7e7e',marginRight:'5px'}">
					<u-form-item label="手机号" borderBottom>
						<u--input v-model="formData.phone" border='none' clearable></u--input>
					</u-form-item>
					<u-form-item label="验证码" borderBottom>
						<view class="yzm">
							<u--input v-model="formData.phoneCode" border='none'></u--input>
							<view class="code-area">
								<u-toast ref="uToast"></u-toast>
								<u-code :seconds="seconds" ref="uCode" @change="codeChange" :keepRunning='true'></u-code>
								<u-button @tap="sendCodeBtn">{{tips}}</u-button>
							</view>
						</view>
					</u-form-item>
					<u-form-item>
						<u-button type="primary" @click="submitBtn" text="登录" :loading="loading" loading-text="登录中" :customStyle="{margin:'20px 0'}"></u-button>
					</u-form-item>
				</u--form>
				<view class="wx-login-pop">
					<text @click="openWxLogin">微信登录</text>
				</view>
				<u-popup :show="isActive" mode="center" round="5" :customStyle="{width:'360px'}" :closeable="true" @close="isActive=false">
					<view class="move-box">
						<view class="move-img-box">
							<view class="move-bgimg" :style="bgImgStyle">
								<view class="move-img-item" :style="slideImgStyle"></view>
							</view>			
						</view>
						<movable-area class="move-view" @touchend="slideend" @mouseup="slideend" @mousedown="slidestart">
							<movable-view direction="horizontal" :x="x" @change="moveChange" animation="false" class="move-btn">|||</movable-view>
						</movable-area>
						<view class="move-tips">
							拖动滑块完成拼图
						</view>
					</view>
				</u-popup>
			</view>
			<u-popup :show="isWxLoginPop" @close="isWxLoginPop=false" :closeable="true" mode="center" :closeOnClickOverlay="false" :customStyle="{padding:'40px 30px'}">
				<view class="wx-ercode">
					<view class="wx-ercode-img" id="qrcode">
						<!-- <image src="../../static/pc/qrcode_h5.png"></image> -->
						<text>nihao</text>
					</view>
					<view class="wx-ercode-text">
						打开微信扫码登录
					</view>
				</view>
			</u-popup>
		</view>
	</view>
</template>

<script>
	import { sendMessageCode, getSlideCode,getWxLogin } from '../../api/user.js'
	import unisite from '../../common/unisite.js'
	import {
		mapMutations,
		mapGetters,
		mapActions
	} from 'vuex'
	export default {
		data() {
			return {
				titles:{
					navTitle: '登 录',
					formTitle: '手机号登录'
				},
				formData: {
					phone: '',
					phoneCode: '',
				},
				seconds: 60,
				tips: '',
				loading: false,
				// 滑块验证窗口
				isActive: false,
				// 滑块的x轴距离
				x: 0,
				// 滑动图片属性
				imgData:{
					bigSrc: '',
					slideSrc: '',
					left: 0,
					top: 0,
					slideToken: ''
				},
				isWxLoginPop: false,
				moveTouch: false,
			}
		},
		mounted() {
			document.body.addEventListener('mouseup',()=>{
				if(this.moveTouch) {
					this.moveTouch = false
					this.slideend()
				};
			})
		},
		computed: {
			...mapGetters(['isLogin']),
			slideImgStyle() {
				return {
					backgroundImage: `url(${this.imgData.slideSrc})`,
					left:this.pxToRpx(this.imgData.left) +'rpx',
					top: this.pxToRpx(this.imgData.top)+'rpx',
				}
			},
			bgImgStyle(){
				return {
					backgroundImage: `url(${this.imgData.bigSrc})`,
				}
			}
		},
		methods: {
			...mapMutations(['SET_IS_LOGIN']),
			moveChange(e){
				this.x = Math.round(e.detail.x);
				this.imgData.left = Math.round(e.detail.x);					
			},
			// 滑动松手验证
			slideend(e){		
				let movePosX = this.x
				this.sendMessage(movePosX)
			},
			slidestart(){
				this.moveTouch = true
			},
			refreshSlide(){
				this.imgData.left = 0
				this.imgData.top = 0
				this.x = 0
				this.imgData.bigSrc = ''
				this.imgData.slideSrc = ''
			},
			codeChange(text) {
				this.tips = text;
			},
			sendCodeBtn() {
				if(this.tips.length>5){
					return false
				}		
				if (!this.formData.phone) {
					return uni.showToast({
						title: '请先输入手机号!',
						icon: 'none',
						duraiton: 2000
					})
				}
				let reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
				if (!reg.test(this.formData.phone)) {
					return uni.showToast({
						title: '请输入正确的手机号!',
						icon: 'none',
						duraiton: 2000
					})
				}
				this.refreshSlide()
				let that = this
				let params = {
					phone: this.formData.phone,
					index: 3
				}
				uni.showLoading({
					title: '加载中...',
				})
				getSlideCode(params).then(res=>{
					console.log(res)
					that.imgData.bigSrc = 'data:image/png;base64,'+res.data.bigImageBase64
					that.imgData.slideSrc = 'data:image/png;base64,'+res.data.smallImageBase64
					that.imgData.top = res.data.posY
					that.imgData.slideToken = res.data.token
					uni.hideLoading()
					that.isActive = true
				}).catch(()=>{
					uni.showToast({
						title: '加载失败',
						icon:'none'
					});
					uni.hideLoading()
				})
			},
			sendMessage(moveX){
				let that = this
				if (this.$refs.uCode.canGetCode) {
					this.moveTouch = false
					uni.showLoading({
						title: '校验中...',
						mask: true
					})
					let data = {
						phone: that.formData.phone,
						movePosX: moveX
					}
					sendMessageCode(data).then(res => {
						uni.hideLoading()
						that.isActive = false;
						uni.$u.toast('验证码已发送');
						console.log('验证码res==', res)
						// 验证码倒计时
						this.$refs.uCode.start();
					}).catch(err => {
						console.log(err);
						uni.hideLoading()
						that.isActive = false;
						uni.$u.toast('验证失败,请重新获取!');
					})
				}
			},
			submitBtn() {
				if (!this.formData.phone || !this.formData.phoneCode) {
					return uni.showToast({
						title: '请输入登录信息',
						icon: 'none',
						duration: 2000
					});
				}
				this.loading = true
				this.$store.dispatch('LoginBySocial', {
					params: {
						loginType: 'wxmini',
						grant_type: 'wxmini',
						invite_code: '',
						phone: this.formData.phone,
						phoneCode: this.formData.phoneCode,
						login_type: 'phone'
					},
					meta: {
						Authorization: 'd3htaW5pOnd4bWluaV9zZWNyZXQ=',
					},
				}).then(res => {
						this.loading = false
						this.SET_IS_LOGIN(true)
						// 登录成功跳转页面
						uni.hideLoading()
						uni.showToast({
							title: '登录成功！',
							icon: 'none',
							duration: 2000
						});
		
				}).catch((error) => {
					this.loading = false
					console.log(error)
					uni.showToast({
						title: error.data && error.data.error_description ? error.data.error_description : '登录失败',
						icon: 'none',
						duration: 2000
					});
					uni.hideLoading()
				})
			},
			pxToRpx(px){
				let scale=uni.upx2px(100)/100
				return px/scale
			},
			openWxLogin(){
				uni.showLoading({
					title: '加载中',
					mask: true
				})
				let data = {
					callback : `${unisite.redirect_url}/#/pages/subPack/bindphone`
				}
				getWxLogin(data).then(res=>{
					uni.hideLoading()
					let url = res.data
					window.location.href = url
				}).catch(()=>{
					uni.hideLoading()
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	/deep/.u-form-item__body__left__content__label {
		span {
			width: 60px;
		}
	}
	.login-pc{
		width: 100%;
		height: 76%;
		position: relative;
		left: 0;
		top: 0;
		.login-area{
			min-width: 760px;
			min-height:400px;
			position: absolute;
			left: 20px;
			right: 20px;
			top: 25px;
			bottom: 25px;
			padding: 10px 30px;
			box-sizing: border-box;
			background-color: #fff;
			.login-box{
				width: 100%;
				.page-title{
					font-size: 18px;
					font-weight: 700;
					text-align: center;
					padding-bottom: 10px;
					border-bottom: 1px solid skyblue;
				}
				.form-head{
					margin: 40px 0;
				}
				.yzm{
					width: 100%;
					display: flex;
					justify-content: space-between;
					.code-area{
						width: 120px;
					}
				}
				.wx-login-pop{
					width: 100%;
					height: 24px;
					line-height: 32px;
					text-align: center;
					text{
						opacity: .6;
						font-size: 12px;
						cursor: pointer;
						color: #2b88f6;
						transition: all .5s;
						&:hover{
							opacity: 1;
						}
					}
				}
			}
			.move-box{
				width: 100%;
				height: fit-content;
				padding: 30px 0;
				.move-img-box{
					width: 100%;
					margin: 15px auto;
					.move-bgimg{
						width: 300px;
						height: 150px;
						position: relative;
						left: 0;
						top: 0;
						margin: 0 auto;
						background-size: 100% 100%;
						image{
							width: 100%;
							height: 100%;
						}
					}
					.move-img-item{
						width: 40px;
						height: 40px;
						position: absolute;
						z-index: 99;
					}
				}
				
				.move-view{
					box-sizing: border-box;
					width: 300px;
					height: 20px;
					background-color: #efefef;
					margin: 15px auto;
					border-radius: 10px;
					
					.move-btn{
						width: 30px;
						height: 30px;
						line-height: 30px;
						text-align: center;
						border-radius: 15px;
						color: #fff;
						background-color: skyblue;
						top: -25%;
					}
				}
				
				.move-tips{
					text-align: center;
				}
			}
			
			.wx-ercode{
				width: 270px;
				height: 270px;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
				margin-top: 10px;
				
				image{
					width: 100%;
					height: 100%;
					border: #42b983 solid 2px;
				}
				
				.wx-ercode-text{
					margin-top: 10px;
				}
			}
		}
	}

</style>
