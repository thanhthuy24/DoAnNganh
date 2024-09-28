const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 3000 // Thay bằng cổng bạn muốn sử dụng
  }
})