<template>
  <div class="avue-searchs"
       @click.self="handleEsc">
    <div class="avue-searchs__title">菜单搜索</div>
    <div class="avue-searchs__content">
      <div class="avue-searchs__form">
        <el-input :placeholder="$t('search')"
                  v-model="value"
                  @keydown.esc.native="handleEsc">
          <el-button slot="append"
                     icon="el-icon-search"></el-button>
        </el-input>
        <p>
          <el-tag>你可以使用快捷键esc 关闭</el-tag>
        </p>
      </div>
      <div class="avue-searchs__list">
        <el-scrollbar class="avue-searchs__scrollbar">
          <div class="avue-searchs__item"
               v-for="(item,index) in menus"
               :key="index"
               @click="handleSelect(item)">
            <i :class="[item[iconKey],'avue-searchs__item-icon']"></i>
            <span class="avue-searchs__item-title">{{item[labelKey]}}</span>
            <div class="avue-searchs__item-path">
              {{item[pathKey]}}
            </div>
          </div>
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>

<script>
  import config from './sidebar/config.js'
  import {mapGetters} from "vuex";

  export default {
    data() {
      return {
        config: config,
        value: "",
        menus: [],
        menuList: []
      }
    },
    created() {
      this.getMenuList();
    },
    watch: {
      value() {
        this.querySearch();
      },
      menu() {
        this.getMenuList();
      }
    },
    computed: {
      labelKey() {
        return this.website.menu.props.label || this.config.propsDefault.label;
      },
      pathKey() {
        return this.website.menu.props.path || this.config.propsDefault.path;
      },
      iconKey() {
        return this.website.menu.props.icon || this.config.propsDefault.icon;
      },
      childrenKey() {
        return (
          this.website.menu.props.children || this.config.propsDefault.children
        );
      },
      ...mapGetters(["menu", "website"])
    },
    methods: {
      handleEsc() {
        this.$parent.isSearch = false;
      },
      getMenuList() {
        const findMenu = list => {
          for (let i = 0; i < list.length; i++) {
            const ele = Object.assign({}, list[i]);
            if (this.validatenull(ele[this.childrenKey])) {
              this.menuList.push(ele);
            } else {
              findMenu(ele[this.childrenKey]);
            }
          }
        };
        this.menuList = [];
        findMenu(this.menu);
        this.menus = this.menuList;
      },
      querySearch() {
        var restaurants = this.menuList;
        var queryString = this.value
        this.menus = queryString
          ? this.menuList.filter(this.createFilter(queryString))
          : restaurants;
      },
      createFilter(queryString) {
        return restaurant => {
          return (
            restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) ===
            0
          );
        };
      },
      handleSelect(item) {
        this.handleEsc();
        this.value = "";
        this.$router.push({
          path: this.$router.$avueRouter.getPath({
            name: item[this.labelKey],
            src: item[this.pathKey]
          }, item.meta),
          query: item.query
        });
      }
    }
  }
</script>

<style lang="scss" scoped>
  .avue-searchs {
    padding-top: 50px;
    width: 100%;
    height: 100%;
    background-color: #fff;
    z-index: 1024;

    &__title {
      margin-bottom: 60px;
      text-align: center;
      font-size: 42px;
      font-weight: bold;
      letter-spacing: 2px;
      text-indent: 2px;
    }

    &__form {
      margin: 0 auto 50px auto;
      width: 50%;
      text-align: center;

      p {
        margin-top: 20px;
      }
    }

    &__scrollbar {
      height: 400px;
    }

    &__list {
      box-sizing: border-box;
      padding: 20px 30px;
      margin: 0 auto;
      width: 70%;
      border-radius: 4px;
      border: 1px solid #ebeef5;
      background-color: #fff;
      overflow: hidden;
      color: #303133;
      transition: 0.3s;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }

    &__item {
      padding: 5px 0;
      border-bottom: 1px dashed #eee;

      &-icon {
        margin-right: 5px;
        font-size: 18px;
      }

      &-title {
        font-size: 20px;
        font-weight: 500;
        color: #333;
      }

      &-path {
        line-height: 30px;
        color: #666;
      }
    }
  }
</style>
