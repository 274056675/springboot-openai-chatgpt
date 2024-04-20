<template>
  <div :style="{ height: boxHeight + 'px' }" class="tab-container" id="tabContainer">
    <div
      class="tab-item"
      v-for="(item, index) in pbList"
      :key="index"
      style="max-width: 260px"
    >
      <div style="overflow: hidden">
        <img style="max-width: 260px" :src="item.image_url" />
      </div>
      <span
        class="iconfont icon-jushoucanggift star"
        :style="item.be ? 'color:#960a0f;' : ''"
      ></span>
      <!-- <span class="free">免费</span> -->
      <div>
        <p class="content">{{ item.image_title }}</p>
        <div class="tips">
          <div class="tipsLeft">
            <img
              class="avatar"
              style="width: 24px; height: 24px; border-radius: 50%"
              :src="item.wxuser_avatar"
            />
            <span style="margin-left: 6px; font-size: 12px; color: #999999">{{
              item.wxuser_name
            }}</span>
          </div>
          <div class="tipsRight">
            {{ item.star }}
            <span
              class="iconfont icon-jushoucanggift"
              :style="item.be ? 'color:#960a0f;' : ''"
            ></span>
          </div>
        </div>
      </div>
      <div v-show="isHhjl" class="image-item_delete">
        <span :style="item.isDelete ? 'display: block;' : ''"></span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    columns: {
      type: Number,
      default: 5,
    },
    columnGap: {
      type: Number,
      default: 20,
    },
    rowGap: {
      type: Number,
      default: 20,
    },
    imageList: {
      type: Array,
    },
    isHhjl: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      //瀑布流数据
      pbList: [],
      lineConentHeightList: [],
      boxHeight: 0,
    };
  },
  watch: {
    imageList(val) {
      if (val.length > 0) {
        let arr = [];
        val.forEach((item) => {
          arr.push({
            image_url:
              item.image_url1 == ""
                ? item.image_url2 == ""
                  ? item.image_url3 == ""
                    ? item.image_url4
                    : item.image_url3
                  : item.image_url2
                : item.image_url1,
            wxuser_avatar:
              item.wxuser_avatar != ""
                ? item.wxuser_avatar
                : require("../static/df_avatar_nan.png"),
            wxuser_name:
              item.wxuser_name.length > 6
                ? item.wxuser_name.slice(0, 5) + "..."
                : item.wxuser_name,
            image_title: item.image_title,
            star: item.star,
            be: item.be,
            id: item.id,
            isDelete: item.isDelete,
          });
        });
        this.pbList = [...this.pbList, ...arr];
        this.$nextTick(() => {
          setTimeout(() => {
            //实现瀑布流
            this.waterFall(
              "#tabContainer",
              ".tab-item",
              this.columns,
              this.columnGap,
              this.rowGap
            );
          }, 180);
        });
      } else {
        this.pbList = [];
      }
    },
  },
  mounted() {
    // 窗口变化自适应布局
    // window.onresize = () => {
    //   return (() => {
    //     this.waterFall('#tabContainer', '.tab-item', this.columns, this.columnGap, this.rowGap)
    //   })()
    // }
  },
  methods: {
    /**
     * @param { string } wrapIdName    容器id(或class)名称
     * @param { string } contentIdName 容器中内容项id(或class)名称
     * @param { number } column        容器中内容展示列数 手机的话建议改为2
     * @param { number } columnGap     容器中 列 间隔距离 默认为20
     * @param { number } rowGap        容器中 行 间隔距离 默认为20
     */

    //瀑布流方法：通过拿到dom循环，给每一个dom添加对应的定位位置排列出瀑布流布局。
    //通过判断列的高度，来把下一个盒子放在最短的地方补上
    waterFall(wrapIdName, contentIdName, columns, columnGap, rowGap) {
      // 获得内容可用宽度（去除滚动条宽度）
      const wrapContentWidth = document.querySelector(wrapIdName).offsetWidth - 8;

      // 间隔空白区域
      const whiteArea = (columns - 1) * columnGap;

      // 得到每列宽度(也即每项内容宽度)
      const contentWidth = parseInt((wrapContentWidth - whiteArea) / columns);

      // 得到内容项集合
      const contentList = document.querySelectorAll(contentIdName);

      // 成行内容项高度集合
      this.lineConentHeightList = [];

      for (let i = 0; i < contentList.length; i++) {
        // 动态设置内容项宽度
        contentList[i].style.width = contentWidth + "px";

        // 获取内容项高度
        const height = contentList[i].clientHeight;

        if (i < columns) {
          // 第一行按序布局
          contentList[i].style.top = 0;
          contentList[i].style.left = contentWidth * i + columnGap * i + "px";

          // 将行高push到数组
          this.lineConentHeightList.push(height);
        } else {
          // 其他行
          // 获取数组最小的高度 和 对应索引
          let minHeight = Math.min(...this.lineConentHeightList);
          let index = this.lineConentHeightList.findIndex((listH) => listH === minHeight);

          contentList[i].style.top = minHeight + rowGap + "px";
          contentList[i].style.left = (contentWidth + columnGap) * index + "px";

          // 修改最小列的高度 最小列的高度 = 当前自己的高度 + 拼接过来的高度 + 行间距
          this.lineConentHeightList[index] += height + rowGap;
        }
      }

      this.boxHeight = Math.max(...this.lineConentHeightList);
      // console.log('000-', this.lineConentHeightList, this.boxHeight);
    },
  },
};
</script>

<style lang="scss" scoped>
/* 最外层大盒子 */
.tab-container {
  position: relative;
  height: 100%;
}
/* 每个小盒子 */
.tab-container .tab-item {
  position: absolute;
  height: auto;
  border: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  background: white;
  /* 元素不能中断显示 */
  break-inside: avoid;
  text-align: center;
  border-radius: 10px;
  overflow: hidden;
  transition: 2s;
}

.image-item_delete {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  right: 6px;
  top: 6px;
  width: 14px;
  height: 14px;
  border-radius: 50%;
  border: 1px solid #ccc;
  background-color: #fff;

  span {
    display: none;
    width: 12px;
    height: 12px;
    margin: auto;
    border-radius: 50%;
    background-color: #960a0f;
  }
}
.tab-item > div > img {
  cursor: pointer;
  transition: all 0.6s;
}
.tab-item > div > img:hover {
  transform: scale(1.3);
}
.free,
.star {
  position: absolute;
  top: 6px;
  height: 20px;
  line-height: 20px;
  text-align: center;
  border-radius: 10px;
  font-size: 12px;
  background-color: #b5a39f;
  color: #fff;
  z-index: 999;
}
.star {
  left: 10px;
  width: 20px;
  border-radius: 4px;
  cursor: pointer;
}
.free {
  right: 6px;
  width: 38px;
}
.tab-container .tab-item img {
  width: 100%;
  height: auto;
}
/* 描述 */
.content {
  text-align: left;
  color: #5c5c5c;
  font-size: 14px;
  margin-top: 10px;
  padding: 0 10px 0 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  /* 将对象作为弹性伸缩盒子模型显示 */
  display: -webkit-box;
  /* 限制在一个块元素显示的文本的行数 */
  /* -webkit-line-clamp 其实是一个不规范属性，使用了WebKit的CSS扩展属性，该方法适用于WebKit浏览器及移动端；*/
  -webkit-line-clamp: 2;
  /* 设置或检索伸缩盒对象的子元素的排列方式 */
  -webkit-box-orient: vertical;
}
/* 价格和销量 */
.tips {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
  margin: 10px 0;
}
.tipsLeft {
  display: flex;
  align-items: center;
}
.tipsRight {
  padding: 3px 7px;
  /* background-color: #ff9a00; */
  color: #999999;
  border-radius: 8px;
  font-size: 14px;
}
</style>
