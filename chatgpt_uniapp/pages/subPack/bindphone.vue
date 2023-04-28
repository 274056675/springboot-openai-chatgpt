<template>
	<view class="bind-page">
		<u-loading-page :loading="isLoading" loadingColor="#2b88f6" color="#2b88f6"></u-loading-page>
		<view class="login-box" v-if="!isLoading">
			<view class="form-head">
				<view>绑定手机号</view>
			</view>
			<u--form ref="loginform" labelPosition="left" labelWidth=70 labelAlign="right" :labelStyle="{color:'#7e7e7e',marginRight:'10rpx'}">
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
					<u-button type="primary" @click="bindPhone" text="确认" :loading="loading" loading-text="绑定中" :customStyle="{margin:'40rpx 0'}"></u-button>
				</u-form-item>
			</u--form>
			<view class="logo-area">
				<img src="../../static/ai_avatar_old.png"></img>
				<view class="h3">超级AI大脑PC开源</view>
			</view>
			<u-popup :show="isActive" mode="center" round="5" :customStyle="{width:'90%'}" :closeable="true" @close="isActive=false">
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
	</view>
</template>

<script>
	import { sendMessageCode, getSlideCode, getWxOpen, bindPhoneApi } from '../../api/user.js';
	import {redirect_url } from '../../common/unisite.js';
	import {
		mapMutations,
		mapGetters,
		mapActions
	} from 'vuex'
	export default {
		name: 'bindphone',
		data() {
			return {
				formData: {
					phone: '',
					phoneCode: '',
				},
				seconds: 60,
				tips: '',
				loading: false,
				isActive: false,
				x: 0,
				imgData:{
					bigSrc: '',
					slideSrc: '',
					left: 0,
					top: 0,
					slideToken: ''
				},
				isLoading: true,
				bindData: {
					uuid: "",
					bindPhoneFlag: ""
				},
				moveTouch: false,
			}
		},
		computed: {
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
			},
		},
		mounted() {
			this.urlCode()
			document.body.addEventListener('mouseup',()=>{
				if(this.moveTouch) {
					this.moveTouch = false
					this.slideend()
				};
			})
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
				console.log('滑块移动距离',movePosX)
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
			submitBtn(id) {		
				this.loading = true
				this.$store.dispatch('LoginBySocial', {
					params: {
						loginType: 'wxmini',
						grant_type: 'wxmini',
						login_type: 'wxopen_qrcode',
						uuid: id
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
						uni.navigateTo({
							url:"/pages/subPack/indexpc"
						})
				}).catch((error) => {
					this.loading = false
					console.log(error)
					uni.showToast({
						title: error.data && error.data.error_description ? error.data.error_description : '登录失败',
						icon: 'none',
						duration: 2000
					});
					this.isLoading = false
					uni.hideLoading()
				})
			},
			pxToRpx(px){
				let scale=uni.upx2px(100)/100
				return px/scale
			},
			// 地址处理
			urlCode(){
				let params = {
					code: "",
					state: ""
				}
				let urlStr = window.location.href;
				let a = urlStr.split('?')[1]
				if(a.includes('&')) {
					let b = a.split('&')
					for(let i in b){
						let temp = b[i].split('=')
						if(i==0) {
							params.code = temp[1]
							continue;
						};
						params.state = temp[1]
					}
				}
				getWxOpen(params).then(res=>{
					this.bindData.uuid = res.data.uuid
					this.bindData.bindPhoneFlag = res.data.bindPhoneFlag
					if(!this.bindData.bindPhoneFlag) {
						this.isLoading = false
						uni.showToast({
							title: '请绑定手机号',
							icon: 'none',
						});
					} else {
						this.submitBtn(this.bindData.uuid)
					}
				}).catch((err)=>{
					this.isLoading = false
				})
			},
			// 确认绑定按钮
			bindPhone(){
				if (!this.formData.phone || !this.formData.phoneCode) {
					return uni.showToast({
						title: '请输入绑定相关信息',
						icon: 'none',
						duration: 2000
					});
				}
				let data = {
					uuid: this.bindData.uuid,
					phone: this.formData.phone,
					code: this.formData.phoneCode,
					inviteCode: ""
				}
				uni.showLoading({
					title: '绑定中',
					mask: false
				});
				bindPhoneApi(data).then(res=>{
					if(res.code==200&&res.data=='绑定成功'){
						uni.hideLoading()
						this.submitBtn(data.uuid)
					} else {
						uni.hideLoading()
						uni.showToast({
							title: res.data,
							icon: 'none'
						});
					}
				}).catch(()=>{
					uni.hideLoading()
				})
			}
		}
	}
</script>

<style lang="scss">
	/deep/.u-form-item__body__left__content__label {
		span {
			width: 60px;
		}
	}
	.bind-page{
		width: 100vw;
		height: 100vh;
		background-image: url(../../static/pc/pc-bg.png);
		position: fixed;
		left: 0;
		top: 0;
	}
	.login-box {
		width: 316px;
		height: 400px;
		margin: 200px auto;
		padding: 60rpx;

		.form-head {
			font-size: 20px;
			margin: 20px 0;
		}

		.yzm {
			width: 100%;
			display: flex;
			justify-content: space-between;
			.u-input{
				flex: 1;
			}
			.code-area{
				flex: 0 0 120px;
				text-align: center;
			}
		}
	}
	.logo-area{
		display: flex;
		justify-content: center;
		align-items: center;
		color: #2b88f6;
		margin-top: 30px;
		font-weight: 700;
		img{
			display: block;
			border-radius: 50%;
			width: 36px;
			height: 36px;
			margin-right: 10px;
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
</style>
