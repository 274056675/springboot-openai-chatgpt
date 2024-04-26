const CompressionWebpackPlugin = require('compression-webpack-plugin')
const TerserPlugin = require('terser-webpack-plugin')
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin')
let optimization = {}
let css = {}
if (process.env.NODE_ENV === 'production') {
    optimization.minimize = true
    optimization.minimizer = [new TerserPlugin({
        parallel: true,
        sourceMap: true,
        terserOptions: {
            warnings: false,
            compress: {
                drop_console: true, // 注释console
                drop_debugger: true, // 注释debugger
                pure_funcs: ["console.log"]
            }
        }
    })]
    css.extract = {
        ignoreOrder: true
    }
}
module.exports = {
    publicPath: "/",
    lintOnSave: true,
    productionSourceMap: false,
    chainWebpack: (config) => {
        //忽略的打包文件
        config.externals({
            'vue': 'Vue',
            'vue-router': 'VueRouter',
            'vuex': 'Vuex',
            'axios': 'axios',
            'element-ui': 'ELEMENT',
        })
        const entry = config.entry('app')
        entry
            .add('babel-polyfill')
            .end()
        entry
            .add('classlist-polyfill')
            .end()
        entry
            .add('@/mock')
            .end()
    },
    css,
    configureWebpack: {
        optimization,
        plugins: [
            new CompressionWebpackPlugin({
                // 正在匹配需要压缩的文件后缀
                test: /.(js|css|svg|woff|ttf|json|html)$/,
                // 大于10kb的会压缩
                threshold: 10240
                    // 其余配置查看compression-webpack-plugin
            }),
            new MonacoWebpackPlugin(),
        ]
    },
    devServer: {
        // 端口配置
        port: 1888,
        // 反向代理配置
        proxy: {
            '/api': {
                //本地服务接口地址
                target: 'http://127.0.0.1:9872',
                ws: true,
                pathRewrite: {
                    '^/api': '/'
                }
            }
        },
        disableHostCheck: true
    }
}