module.exports = {
  root: true,
  parser: "babel-eslint",
  parserOptions: {
    sourceType: "module"
  },
  env: {
    browser: true,
    node: true,
    es6: true
  },
  extends: "eslint:recommended",
  // required to lint *.vue files
  plugins: ["html"],
  // check if imports actually resolve
  settings: {
    "import/resolver": {
      webpack: {
        config: "build/webpack.base.conf.js"
      }
    }
  },
  rules: {
    "no-unused-vars": [
      1,
      {
        vars: "all",
        args: "none"
      }
    ],
    "no-extra-boolean-cast": 1,
    "no-console": "off"
  }
};
