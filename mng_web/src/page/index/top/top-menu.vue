<template>
  <div class="top-menu">
    <el-menu :default-active="activeIndex"
             mode="horizontal"
             text-color="#333">
      <el-menu-item index="0" @click.native="openHome(itemHome)" key="0">
        <template slot="title">
          <i :class="itemHome.source"></i>
          <span>{{generateTitle(itemHome)}}</span>
        </template>
      </el-menu-item>
      <template v-for="(item,index) in items">
        <el-menu-item :index="item.id+''" @click.native="openMenu(item)" :key="index">
          <template slot="title">
            <i :class="item.source" style="padding-right: 5px;"></i>
            <span>{{generateTitle(item)}}</span>
          </template>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";

  export default {
    name: "top-menu",
    data() {
      return {
        itemHome: {
          name: '首页',
          source: 'el-icon-menu',
        },
        activeIndex: "0",
        items: [],
      };
    },
    inject: ["index"],
    created() {
      this.getMenu();
    },
    computed: {
      ...mapGetters(["tagCurrent", "menu"])
    },
    methods: {
      openHome(itemHome) {
        this.index.openMenu(itemHome);
        this.$router.push({
          path: this.$router.$avueRouter.getPath({name: itemHome.name, src: ''}, {})
        });
      },
      openMenu(item) {
        this.index.openMenu(item)
      },
      getMenu() {
        this.$store.dispatch("GetTopMenu").then(res => {
          this.items = res;
        });
      },
      generateTitle(item) {
        return this.$router.$avueRouter.generateTitle(
          item.name,
          (item.meta || {}).i18n
        );
      },
    }
  };
</script>
