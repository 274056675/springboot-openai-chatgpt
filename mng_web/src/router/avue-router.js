let RouterPlugin = function () {
  this.$router = null;
  this.$store = null;

};
RouterPlugin.install = function (vue, option = {}) {
  this.$router = option.router;
  this.$store = option.store;
  this.$vue = new vue({ i18n: option.i18n });

  // 这个的作用是 为了检查出网页链接，因为本项目用到了 iframe
  function isURL(s) {
    if (s.includes('html')) return true;
    return /^http[s]?:\/\/.*/.test(s)
  }

  // 将参数处理为参数的形式拼接
  function objToform(obj) {
    let result = [];
    Object.keys(obj).forEach(ele => {
      result.push(`${ele}=${obj[ele]}`);
    })
    return result.join('&');
  }

  this.$router.$avueRouter = {
    //全局配置
    routerList: [],//添加变量用于判断重复路由
    $website: this.$store.getters.website,
    group: '',
    meta: {},
    safe: this,
    // 设置标题
    setTitle: (title) => {
      const defaultTitle = this.$vue.$t('title');
      title = title ? `${title}-${defaultTitle}` : defaultTitle;
      document.title = title;
    },
    closeTag: (value) => {
      let tag = value || this.$store.getters.tag;
      if (typeof value === 'string') {
        tag = this.$store.getters.tagList.filter(ele => ele.value === value)[0]
      }
      this.$store.commit('DEL_TAG', tag)
    },
    generateTitle: (title, key) => {
      if (!key) return title;
      const hasKey = this.$vue.$te('route.' + key)
      if (hasKey) {
        // $t :this method from vue-i18n, inject in @/lang/index.js
        const translatedTitle = this.$vue.$t('route.' + key)

        return translatedTitle
      }
      return title
    },
    //处理路由
    getPath: function (params) {
      let { src } = params
      let result = src || '/'
      if (isURL(src)) {
        if (src.indexOf('?type=file')) {
          //文档管理   
          result = `/fileiframe/urlPath?${objToform(params)}`
        } else {
          result = `/myiframe/urlPath?${objToform(params)}`
        }
      }
      return result
    },
    //正则处理路由
    vaildPath: function (list, path) {
      let result = false;
      list.forEach(ele => {
        if (new RegExp("^" + ele + ".*", "g").test(path)) {
          result = true
        }

      })
      return result;
    },
    //设置路由值
    getValue: function (route) {
      let value = "";
      if (route.query.src) {
        value = route.query.src;
      } else {
        value = route.path;
      }
      return value;
    },
    //动态路由
    // 路由是专门的一个接口获取
    /**
     * aMenu: 接受到的动态路由数据 menu的结构外层有父级path 里面有一个childen 记录页面的路由
     * first: 为了区分外界 调用formatRoutes 和 当前文件调用 formatRoutes
    */
    formatRoutes: function (aMenu = [], first) {
      const aRouter = []
      // 获取到全局配置中的 props
      const propsConfig = this.$website.menu.props;
      // 设置 props默认值 作用就是将字段设置成配置的
      const propsDefault = {
        label: propsConfig.label || 'name',
        path: propsConfig.path || 'path',
        icon: propsConfig.icon || 'icon',
        children: propsConfig.children || 'children',
        meta: propsConfig.meta || 'meta',
      }
      // 如果没有权限菜单就结束
      if (aMenu.length === 0) return;
      // 开始处理menu
      for (let i = 0; i < aMenu.length; i++) {
        // 取到当前要处理的一项
        const oMenu = aMenu[i];

        // 判断this.routerList中是否已经存在该path，存在就跳出
        if (this.routerList.includes(oMenu[propsDefault.path])) return

        // 这一块的赋值 也就是取到返回的值
        let path = (() => {
          if (first) {
            // 将 '/index' 替换为 ''
            return oMenu[propsDefault.path].replace('/index', '')
          } else {
            return oMenu[propsDefault.path]
          }
        })(),
          //特殊处理组件 执行完这个 component 也就是精确到具体的文件了  views文件夹下面就是具体的页面代码
          component = oMenu.path.indexOf('/views') == 0 ? 'research' + oMenu.path : 'views' + oMenu.path,
          name = oMenu[propsDefault.label],
          icon = oMenu[propsDefault.icon],
          children = oMenu[propsDefault.children],
          meta = oMenu[propsDefault.meta] || {};
        // meta中 keepalive 的处理
        meta = Object.assign(meta, (function () {
          if (option.keepAlive === true || oMenu.isKeep == 2) {
            return {
              keepAlive: true
            }
          }
        })());
        //是否有子路由
        const isChild = children.length !== 0;
        const oRouter = {
          path: path,
          component(resolve) {
            // 判断是否为首路由
            if (first) {
              require(['../page/index'], resolve)
              return
              // 判断是否为多层路由
            } else if (isChild && !first) {
              require(['../page/index/layout'], resolve)
              return
              // 判断是否为最终的页面视图
            } else {
              if (component.indexOf('/views/tool/') != -1) {
                let keyName = component.split('/views/tool/')[1].split('/')[0]
                require([`../research/views/tool/${keyName}.vue`], resolve)
              } else {
                require([`../${component}.vue`], resolve)
              }
            }
          },
          name: name,
          icon: icon,
          meta: meta,
          redirect: (() => {
            // 第一次进来但是没有子路由的 需要添加redirect
            if (!isChild && first && !isURL(path)) return `${path}/index`
            else return '';
          })(),
          // 整理子路由的route 配置
          // 处理是否为一级路由
          children: !isChild ? (() => {
            if (first) {
              // 这里的isURL判断，因为这个网站有使用 iframe。所以需要判断是否为网页链接
              if (!isURL(path)) oMenu[propsDefault.path] = `${path}/index`;
              return [{
                component(resolve) {
                  require([`../${component}.vue`], resolve)
                },
                icon: icon,
                name: name,
                meta: meta,
                path: 'index'
              }]
            }
            return [];
          })() : (() => {
            /**
             * 这里是重点，当有子路由的时候 会再去执行 formatRoutes 方法，然后又会有一个新的 aMenu for循环。
             * 最后返回的是一个数组 aRouter 这个数组就会作为 childen的值被 return
            */
            return this.formatRoutes(children, false)
          })()
        }
        aRouter.push(oRouter)
      }
      // for循环结束
      // 这个first 卡的其实就是首路由
      if (first) {
        if (!this.routerList.includes(aRouter[0][propsDefault.path])) {
          this.safe.$router.addRoutes(aRouter)
          this.routerList.push(aRouter[0][propsDefault.path])
        }
      } else {
        // 这里返回的是子组件
        return aRouter
      }
    }
  }
}
export default RouterPlugin;
