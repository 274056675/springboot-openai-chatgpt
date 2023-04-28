<template>
	<view class="three-bounce">
		<view :style="[currStyle]" class="one"></view>
		<view :style="[currStyle]" class="two"></view>
		<view :style="[currStyle]" class="three"></view>
	</view>
</template>

<script>
		export default {
			props:{
				size:{
					type:Number,
					default:9,
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
						width:this.size+'px',
						height:this.size+'px',
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
		&>view {
			display: inline-block;
			width: 9px;
			height: 9px;
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
