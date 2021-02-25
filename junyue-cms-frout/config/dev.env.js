'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
  //  BASE_API: '"http://math-thinking-admin.dev1.qlchat.com"'
  // BASE_API: '"http://readadmin.dev1.qlchat.com"'
  //BASE_API: '"http://hxy-admin-04.dev1.nicebooker.com/JMS/USER/LIST"',
  // BASE_API: '"http://127.0.0.1"'
  // BASE_API: '"http://readadmin.dev2.qlchat.com"'
  //  BASE_API: '"http://localhost:9118/"'
  // BASE_API: '"http://readadmin.dev1.qlchat.com"'
  // BASE_API: '"http://hxy-admin-01.dev1.nicebooker.com/"'
  // BASE_API: '"http://127.0.0.1"' // 不要再打开这个 用下面的  vue自带代理功能
     // BASE_API: '"http://localhost:9100/"'
  // BASE_API: '"http://censor.dev1.qlchat.com/censor"'
 BASE_API: '"http://localhost:9100/junyue-cms-admin"'
});
