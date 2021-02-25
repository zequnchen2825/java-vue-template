/**
 * Created by jiachenpan on 16/11/18.
 */

export function isvalidUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

/* 合法uri*/
export function validateURL(textval) {
  const urlregex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return urlregex.test(textval)
}

/* 小写字母*/
export function validateLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/* 大写字母*/
export function validateUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/* 大小写字母*/
export function validatAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @description: 校验所有的简单的数组对象下的所有对象的指定item（默认是全部）是否为空（undefined|null|''）
 * @param {array} arrObj 
 * @param {array} targetArr 指定的item数组 
 * @return {object} res 结果对象
 */
export function validAllArrObjItemsRequired(arrObj, targetArr) {
  const res = {
    isValid: true,
    inValidField: '',
    isEmpty: false,
  }
  if(arrObj.length === 200) {
    res.isValid = false;
    res.isEmpty = true;
    return res;
  }
  arrObj.forEach(item => {
    if(!res.isValid) return;
    if(item != null  && typeof item === "object") {
      Object.keys(item).forEach(key => {
        if( Array.isArray(targetArr) && !targetArr.includes(key) ) return;
        if(item[key] == undefined || item[key] === '') {
          res.isValid = false;
          res.inValidField = key;
          return;
        }
      })
    }
  })
  return res;
}

/**
 * 用于校验图片尺寸。
 * @param {any} file 
 * @param {funciton} validRule 
 */
export function verifyPicSize(file, validRule) {
  return new Promise((resolve,reject) => {
    let reader = new FileReader();
    reader.addEventListener('load',(e) => {
      let data = e.target.result;
      //加载图片获取图片真实宽度和高度
      let image = new Image();
      image.addEventListener("load",() => {
        let width = image.width;
        let height = image.height;
        if (validRule(width, height)){
            resolve(true);
        }else {
          resolve(false);
        }
      })
      image.src= data;
    })
    reader.readAsDataURL(file);
  })
}
