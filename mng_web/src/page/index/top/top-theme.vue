<template>
  <div>
    <el-dialog title="选择"
               append-to-body
               :visible.sync="box"
               width="50%">
      <el-radio-group v-model="text"
                      class="list">
        <el-row :span="24">
          <el-col v-for="(item,index) in list"
                  :key="index"
                  :md="4"
                  :xs="12"
                  :sm="4">
            <el-radio :label="item.value">{{item.name}}</el-radio>
          </el-col>
        </el-row>
      </el-radio-group>
    </el-dialog>

    <span>
      <i class="icon-zhuti"
         @click="open"></i>
    </span>
  </div>
</template>

<script>
import { setTheme } from "@/util/util";
import { mapGetters } from "vuex";
export default {
  data () {
    return {
      box: false,
      text: "",
      list: [
        {
          name: "默认主题",
          value: "default"
        },
        {
          name: "白色主题",
          value: "theme-white"
        },
        {
          name: "黑色主题",
          value: "theme-dark"
        },
        {
          name: "炫彩主题",
          value: "theme-star"
        },
        {
          name: "智能主题",
          value: "theme-bule"
        },
        {
          name: "iview主题",
          value: "theme-iview"
        },
        {
          name: "vip主题",
          value: "theme-vip"
        },
        {
          name: "cool主题",
          value: "theme-cool"
        },
        {
          name: "d2主题",
          value: "theme-d2"
        },
        {
          name: "hey主题",
          value: "theme-hey"
        },
        {
          name: "lte主题",
          value: "theme-lte"
        }
      ]
    };
  },
  watch: {
    text: function (val) {
      this.$store.commit("SET_THEME_NAME", val);
      setTheme(val);
    }
  },
  computed: {
    ...mapGetters(["themeName"])
  },
  mounted () {
    this.text = this.themeName;
    if (!this.text) {
      this.text = "";
    }
  },
  methods: {
    open () {
      this.box = true;
    }
  }
};
</script>

<style lang="scss" scoped>
.list {
  width: 100%;
}
</style>

