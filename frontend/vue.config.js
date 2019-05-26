module.exports = {
  publicPath: '/',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080'
      }
    },
    host: 'localhost',
    port: 3000
  }
}
