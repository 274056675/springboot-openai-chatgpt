<template>
	<div class="three-bounce">
		<div :style="[currStyle]" class="one"></div>
		<div :style="[currStyle]" class="two"></div>
		<div :style="[currStyle]" class="three"></div>
	</div>
</template>

<script>
		export default {
			props:{
				size:{
					type:Number,
					default:18,
				},
				color:{
					type:String,
					default:'#aeadba'
				}
			},
			data() {
				return {
				}
			},
			computed:{
				currStyle(){
					return {
						width:this.size+'rpx',
						height:this.size+'rpx',
						background: this.color,
					}
				}
				
			}
		}
</script>

<style lang="scss" scoped>
	.three-bounce {
		width: 100%;
		text-align: center;
		&>div {
			display: inline-block;
			width: 18rpx;
			height: 18rpx;
			border-radius: 100%;
			top: 50%;
			// margin-top: -9px;
			-webkit-animation: bouncedelay 1.4s infinite ease-in-out;
			animation: bouncedelay 1.4s infinite ease-in-out;
			-webkit-animation-fill-mode: both;
			animation-fill-mode: both
		}

		.one {
			-webkit-animation-delay: -.32s;
			animation-delay: -.32s
		}

		.two {
			-webkit-animation-delay: -.16s;
			animation-delay: -.16s
		}
	}

	@keyframes bouncedelay {

		0%,
		100%,
		80% {
			transform: scale(0);
			-webkit-transform: scale(0)
		}

		40% {
			transform: scale(1);
			-webkit-transform: scale(1)
		}
	}
</style>
