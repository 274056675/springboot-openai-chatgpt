<template>
  <basic-container>
    <h3>存储</h3>
    <el-tag class="title"
            size="small">基本读写删(持久化存储)
    </el-tag>
    <div class="box">
      <el-button type="primary"
                 size="small"
                 @click="setItem({name:'username', value:'avuex'});">set('username', 'avuex')
      </el-button>

      <el-button type="success"
                 size="small"
                 @click="getItem({name:'username'});">get('username')
      </el-button>

      <el-button type="danger"
                 size="small"
                 @click="delItem({name:'username'});">remove('username')
      </el-button>
    </div>
    <el-tag class="title"
            size="small">设置session(session存储)
    </el-tag>
    <div class="box">
      <el-button type="primary"
                 size="small"
                 @click="setItem({name:'username', value:'avuex',type:'session'});">set('username', 'avuex')
      </el-button>

      <el-button type="success"
                 size="small"
                 @click="getItem({name:'username',type:'session'});">get('username')
      </el-button>

      <el-button type="danger"
                 size="small"
                 @click="delItem({name:'username',type:'session'});">remove('username')
      </el-button>
    </div>
    <el-tag class="title"
            size="small">获取所有可以获得的数据
    </el-tag>
    <div class="box">
      <el-button type="success"
                 size="small"
                 @click="getAll()">getAll(持久化存储)
      </el-button>
      <el-button type="success"
                 size="small"
                 @click="getAll({type:'session'})">getAll(session存储)
      </el-button>
      <el-button type="danger"
                 size="small"
                 @click="clearAll()">delAll(持久化存储)
      </el-button>
      <el-button type="danger"
                 size="small"
                 @click="clearAll({type:'session'})">delAll(session存储)
      </el-button>
    </div>
  </basic-container>

</template>

<script>
  import {
    setStore,
    getStore,
    removeStore,
    clearStore,
    getAllStore
  } from "@/util/store";

  export default {
    name: "store",
    methods: {
      setItem(params = {}) {
        const {name, value, type} = params;
        setStore({
          name: name,
          content: value,
          type: type
        });
        this.$message(`设置数据 ${name} = ${value}`);
      },
      getItem(params = {}) {
        const {name, type} = params;
        const content = getStore({
          name: name,
          type: type
        });
        this.$message(`获取数据 ${name} = ${content}`);
      },
      delItem(params = {}) {
        const {name, type} = params;
        removeStore({name, type});
        this.$message(`删除数据 ${name}`);
      },
      clearAll(params = {}) {
        clearStore(params);
        this.$message(`清除全部数据完成`);
      },
      getAll(params = {}) {
        const list = getAllStore(params);
        window.console.log(list);
        this.$message(`结果已经打印到控制台`);
      }
    }
  };
</script>

<style lang="scss">
  .title {
    margin-bottom: 10px;
  }

  .box {
    margin-bottom: 20px;
  }
</style>
