<template>
 <div>
	 <div class="box-top"></div>
	 <div class="user">
	   <el-container style="width:1200px;">
	     <el-aside width="200px" class="aside">
	       <el-menu
	         :default-active="activeIndex"
	         class="el-menu-vertical-demo"
	         active-text-color="#960A0F"
	         :router="true"
	         @select="handleSelect"
	       >
	         <div v-for="(item, index) in navMenuList" :key="index">
	           <el-menu-item :index="item.path" style="text-align: center;">
	             <span class="iconfont" :class="item.icon"></span>
	             <span style="margin-left:6px;">{{ item.title }}</span>
	           </el-menu-item>
	         </div>
	       </el-menu>
	     </el-aside>
	     <el-main style="background:#FFFFFF;border-radius: 10px;">
	       <router-view></router-view>
	     </el-main>
	   </el-container>
	 </div>
 </div>
</template>

<script>
export default {
  name: 'user',
  data() {
    return {
      activeIndex: '/index/user/userinfo',
      navMenuList: [
        { path: '/index/user/equity-center', title: '权益中心', icon: 'icon-huiyuan' },
        { path: '/index/user/userinfo', title: '个人信息', icon: 'icon-yonghu' },
        { path: '/index/user/share', title: '邀请好友', icon: 'icon-fenxiang' },
        { path: '/index/user/setting', title: '账号设置', icon: 'icon-shezhi' },
        { path: '/index/user/rl-detail', title: '积分明细', icon: 'icon-jiangcheng' },
        { path: '/index/user/platform', title: '关于平台', icon: 'icon-cengji' },
      ],
    }
  },
  watch: {
    // 监听路由变化,改变当前激活菜单的 index
     $route(to, from) {
      this.activeIndex = to.path
    }
  },
  mounted() {
    this.activeIndex = this.$route.path
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    }
  },
}
</script>

<style lang="scss">
.el-main {
  padding: 20px;
}

.box-top {
		padding-top: calc((100vh - 650px) / 2);
}

.user {
  width: 1300px;
  height: 580px;
  margin: 10px auto;
  display: flex;
  justify-content: center;
}
.aside {
  background-color: #fff;
  margin-right: 20px;
  border-radius: 10px;
}
</style>