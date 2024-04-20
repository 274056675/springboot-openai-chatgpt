const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        // allowedHosts: [
        //   'mj.ink', // 允许访问的域名地址，即花生壳内网穿透的地址
        //   '.mj.ink'   // .是二级域名的通配符   
        // ],
        compress: false,
        port: 1889, // 访问端口
        proxy: {
            '/api': {
                //本地服务接口地址
                target: 'http://localhost:9872',
                ws: true,
                pathRewrite: {
                    '^/api': '/'
                }
            }
        },
        client: {
            progress: true,
            overlay: false,
        },
    },
    chainWebpack: config => {
        config.plugin('html').tap(args => {
            args[0].title = '超级AI大脑';
            return args;
        })
    }
})