<template>
  <div class="avue-top">
    <div class="top-bar__left">
      <div
        class="avue-breadcrumb"
        :class="[{ 'avue-breadcrumb--active': isCollapse }]"
        v-if="showCollapse"
      >
        <i class="icon-navicon" @click="setCollapse"></i>
      </div>
    </div>
    <div class="top-bar__title">
      <div class="top-bar__item top-bar__item--show" v-if="showMenu">
        <top-menu ref="topMenu"></top-menu>
      </div>
      <span class="top-bar__item" v-if="showSearch">
        <top-search></top-search>
      </span>
    </div>
    <div class="top-bar__right">
      <el-tooltip v-if="showColor" effect="dark" :content="$t('navbar.color')" placement="bottom">
        <div class="top-bar__item">
          <top-color></top-color>
        </div>
      </el-tooltip>
      <!-- <el-tooltip
        v-if="showDebug"
        effect="dark"
        :content="logsFlag?$t('navbar.bug'):logsLen+$t('navbar.bugs')"
        placement="bottom"
      >
        <div class="top-bar__item">
          <top-logs></top-logs>
        </div>
      </el-tooltip>-->
      <el-tooltip v-if="showLock" effect="dark" :content="$t('navbar.lock')" placement="bottom">
        <div class="top-bar__item">
          <top-lock></top-lock>
        </div>
      </el-tooltip>
      <el-tooltip v-if="showTheme" effect="dark" :content="$t('navbar.theme')" placement="bottom">
        <div class="top-bar__item top-bar__item--show">
          <top-theme></top-theme>
        </div>
      </el-tooltip>
      <el-tooltip effect="dark" :content="$t('navbar.notice')" placement="bottom">
        <div class="top-bar__item top-bar__item--show">
          <top-notice></top-notice>
        </div>
      </el-tooltip>
      <!-- <el-tooltip effect="dark" :content="$t('navbar.language')" placement="bottom">
        <div class="top-bar__item top-bar__item--show">
          <top-lang></top-lang>
        </div>
      </el-tooltip>-->
      <el-tooltip
        v-if="showFullScren"
        effect="dark"
        :content="isFullScren?$t('navbar.screenfullF'):$t('navbar.screenfull')"
        placement="bottom"
      >
        <div class="top-bar__item">
          <i :class="isFullScren?'icon-tuichuquanping':'icon-quanping'" @click="handleScreen"></i>
        </div>
      </el-tooltip>
      <img class="top-bar__img" :src="userInfo.avatar" />
      <el-dropdown>
        <span class="el-dropdown-link">
          {{userInfo.userName}}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <router-link to="/">{{$t('navbar.dashboard')}}</router-link>
          </el-dropdown-item>
          <el-dropdown-item>
            <router-link to="/info/index">{{$t('navbar.userinfo')}}</router-link>
          </el-dropdown-item>
          <el-dropdown-item
            v-if="this.website.switchMode"
            @click.native="switchDept"
          >{{$t('navbar.switchDept')}}</el-dropdown-item>
          <el-dropdown-item @click.native="logout" divided>{{$t('navbar.logOut')}}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-dialog title="用户信息选择" append-to-body :visible.sync="userBox" width="350px">
        <avue-form ref="form" :option="userOption" v-model="userForm" @submit="submitSwitch" />
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { resetRouter } from '@/router/router'
import { mapActions, mapGetters, mapMutations, mapState } from 'vuex'
import { fullscreenToggel, listenfullscreen } from '@/util/util'
import topLock from './top-lock'
import topMenu from './top-menu'
import topSearch from './top-search'
import topTheme from './top-theme'
import topLogs from './top-logs'
import topColor from './top-color'
import topNotice from './top-notice'
import topLang from './top-lang'


export default {
  components: {
    topLock,
    topMenu,
    topSearch,
    topTheme,
    topLogs,
    topColor,
    topNotice,
    topLang,
  },
  name: 'top',
  data() {
    return {
      userBox: false,
      userForm: {
        deptId: '',
        roleId: '',
      },
      userOption: {
        labelWidth: 70,
        submitBtn: true,
        emptyBtn: false,
        submitText: '切换',
        column: [
          {
            label: '部门',
            prop: 'deptId',
            type: 'select',
            props: {
              label: 'deptName',
              value: 'id',
            },
            dicUrl: '/api/blade-system/dept/select',
            span: 24,
            display: false,
            rules: [
              {
                required: true,
                message: '请选择部门',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '角色',
            prop: 'roleId',
            type: 'select',
            props: {
              label: 'roleName',
              value: 'id',
            },
            dicUrl: '/api/blade-system/role/select',
            span: 24,
            display: false,
            rules: [
              {
                required: true,
                message: '请选择角色',
                trigger: 'blur',
              },
            ],
          },
        ],
      },
    }
  },
  filters: {},
  created() {},
  mounted() {
    listenfullscreen(this.setScreen)
  },
  computed: {
    ...mapState({
      showDebug: (state) => state.common.showDebug,
      showTheme: (state) => state.common.showTheme,
      showLock: (state) => state.common.showLock,
      showFullScren: (state) => state.common.showFullScren,
      showCollapse: (state) => state.common.showCollapse,
      showSearch: (state) => state.common.showSearch,
      showMenu: (state) => state.common.showMenu,
      showColor: (state) => state.common.showColor,
    }),
    ...mapGetters([
      'userInfo',
      'isFullScren',
      'tagWel',
      'tagList',
      'isCollapse',
      'tag',
      'logsLen',
      'logsFlag',
    ]),
  
  },
  methods: {
    ...mapMutations(['SET_TENANT_NAME']),
    handleScreen() {
      fullscreenToggel()
    },
    setCollapse() {
      this.$store.commit('SET_COLLAPSE')
    },
    setScreen() {
      this.$store.commit('SET_FULLSCREN')
    },
    switchDept() {
      const userId = this.userInfo.user_id
      const deptColumn = this.findObject(this.userOption.column, 'deptId')
      deptColumn.dicUrl = `/api/blade-system/dept/select?userId=${userId}`
      deptColumn.display = true
      const roleColumn = this.findObject(this.userOption.column, 'roleId')
      roleColumn.dicUrl = `/api/blade-system/role/select?userId=${userId}`
      roleColumn.display = true
      this.userBox = true
    },
    submitSwitch(form, done) {
      this.$store.dispatch('refreshToken', form).then(() => {
        this.userBox = false
        this.$router.push({ path: '/' })
      })
      done()
    },
    logout() {
      this.$confirm(this.$t('logoutTip'), this.$t('tip'), {
        confirmButtonText: this.$t('submitText'),
        cancelButtonText: this.$t('cancelText'),
        type: 'warning',
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          resetRouter()
          this.$router.push({ path: '/login' })
        })
      })
    },
  
  },
}
</script>

<style lang="scss" scoped>
.switch-user-box {
  display: inline-block;
  /deep/.el-button {
    height: 64px;
    margin: 0 10px;
    line-height: 64px;
    font-size: 18px;
    padding: 0;
  }
}
</style>
<style lang="scss">
.switch-content-popover {
  max-height: 400px;
  overflow-y: auto;
}
</style>
